package engineStuff;
import board.*;
import pieces.Piece;
import FrameworkML.Matrix;

/** Used to encode a position of a current chess game into a tensor to use
 * in neural network training. */
public class positionEncoder {

    /** Given a Game we encode the current position into a 10x10x16 tensor for the current color
     * (Color next to move)
     *
     * Note: for the given color we make it so the color is facing up. White is facing up normally,
     * but if it is black's turn we make black face up. Basically inverting the position.
     *
     * Matrix 1: current color's one-hot encoding of pawns
     * Matrix 2: curr color one-hot of knights
     * Matrix 3: curr color one-hot of bishops
     * Matrix 4: curr color one-hot of rooks
     * Matrix 5: curr color 1-hot of queen
     * Matrix 6: curr color 1-hot of king
     * Matrix 7: curr color castling privileges and en-passant squares
     *          - bottom row will right side will either be 0000, or 1111 depending if right side castling is possible same as left side.
     *          - since en passant can't be on the bottom row, then it won't interfere with this.
     * Matrix 8: curr color squares it is attacking
     * */
    public static Matrix[] encode(Game chessGame){
        // Holds all 16 matrices
        Matrix[] tensor = new Matrix[16];
        for (int i = 0; i < tensor.length; i++){
            tensor[i] = new Matrix(10, 10);
        }
        Gamelogic gl = chessGame.getGamelogic();
        boolean color = gl.isWhiteTurn();

        // Adding pieces and attacking
        if (color){
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    processBoard(j, i, tensor, chessGame);
                }
            }
        } else {
            for (int i = 7; i >= 0; i--) {
                for (int j = 7; j >= 0; j--) {
                    processBoard(j, i, tensor, chessGame);
                }
            }
        }
        // Fill in the castling and enPassant matrices (7, 15)
        // The opponent color does not need the enPassant square because they is not moving.
        ChessBoard b = gl.getChessBoard();

        // Adding enpassanting
        String enpass = b.getEnPassant();
        if (!enpass.isEmpty()){
            // Add 2 to adjust for padding and matrix indexing
            int row = convertRow(b.convertToRow(enpass), color) + 2;
            int col = convertCol(b.convertToCol(enpass), color) + 2;
            // We also need to be able to switch it for any color.
            // Since enpass will only be on the current color, we add one hot encoding to matrix 7 (idx 6)
            tensor[6].setElement(col, row, 1);
        }
        // Now the tricky part is doing king castling.

        // Adding castling

        // This should go on matrix 7 (index 6)
        if (gl.kingCastle(color)){
            for (int r = 2; r <= 5; r++){
                if (color){
                    // the kingside for white is on the right
                    tensor[6].setElement(r+4,  2, 1);
                } else {
                    // king side for black is on their left.
                    tensor[6].setElement(r, 2, 1);
                }
            }
        }
        if (gl.queenCastle(color)){
            for (int r = 2; r <= 6; r++){
                if (color){
                    // the queen side for white is on the left
                    tensor[6].setElement(r, 2, 1);
                } else {
                    // queen side for black is on their right.
                    tensor[6].setElement(r+3, 2, 1);
                }
            }
        }
        // This should go on matrix 15 (index 14)
        if (gl.kingCastle(!color)){
            for (int r = 2; r <= 5; r++){
                if (color){
                    // the king side for white would be on the left if its black's perspective
                    tensor[14].setElement(r+4, 9, 1);
                } else {
                    // king side for black is on the right in white's perspective.
                    tensor[14].setElement(r, 9, 1);
                }
            }
        }
        if (gl.queenCastle(!color)){
            for (int r = 2; r <= 6; r++){
                if (color){
                    // the queen side for white is on the right
                    tensor[14].setElement(r, 9, 1);
                } else {
                    // queen side for black is on their left.
                    tensor[14].setElement(r+3, 9, 1);
                }
            }
        }
        return tensor;
    }

    /** This function starts to populate the tensor based on what is at the rowIdx, and colIdx of the
     * chess board. Does not fill in the castling and enPassant matrices (7, 15) because these do not
     * need to be iterated over. */
    private static void processBoard(int rowIdx, int colIdx, Matrix[] tensor, Game chessGame){
        Gamelogic gl = chessGame.getGamelogic();
        ChessBoard b = gl.getChessBoard();
        boolean color = gl.isWhiteTurn();
        String index = b.convertToIndex(rowIdx, colIdx);
        Piece currPiece = b.getPieceAt(index);
        Tile currTile = b.getTile(index);

        // Add 2 to adjust for the padding and matrix indexing
        rowIdx = convertRow(rowIdx, color)+2;
        colIdx = convertRow(colIdx, color)+2;

        // What matrix index in tensor should we place the one-hot encoding.
        int matrixIdx = -1;
        // Here negative 1 denotes that we should not one-hot encode for any piece
        if (currPiece.isPiece()){
            matrixIdx = getMatrixIndexForPiece(currPiece);
            if (currPiece.isWhiteTeam() != color){
                // Means that the piece is not the current color so we just add it
                // to the opponent matrices.
                matrixIdx += 8;
            }
        }
        if (matrixIdx >= 0){
            // One hot encode.
            tensor[matrixIdx].setElement(rowIdx, colIdx, 1);
        }
        // Determine attack matrix indices based on the color
        int whiteAttack = color ? 7 : 15;
        int blackAttack = color ? 15 : 7;
        // Matrices 8 and 16 for attacking tiles.
        if (currTile.isWhiteAttacking()){
            tensor[whiteAttack].setElement(rowIdx, colIdx, 1);
        }
        if (currTile.isBlackAttacking()){
            tensor[blackAttack].setElement(rowIdx, colIdx, 1);
        }
    }

    /** Given a piece, returns for at what matrix index this piece should go. */
    private static int getMatrixIndexForPiece(Piece currPiece){
        if (Piece.isPawn(currPiece)){
            return 0;
        } else if (Piece.isKnight(currPiece)) {
            return 1;
        } else if (Piece.isBishop(currPiece)) {
            return 2;
        } else if (Piece.isRook(currPiece)) {
            return 3;
        } else if (Piece.isQueen(currPiece)) {
            return 4;
        } else {
            // Means that the piece must be a king.
            return 5;
        }
    }

    /** Given a row in the chess board return the row that will be seen if we
     * invert the chess board only if the color is black.  */
    private static int convertRow(int row, boolean color){
        if (!color){
            return 7 - row;
        }
        return row;
    }
    /** Given a row in the chess board return the row that will be seen if we
     * invert the chess board only if the color is black.  */
    private static int convertCol(int col, boolean color){
        if (!color){
            return 7 - col;
        }
        return col;
    }
}
