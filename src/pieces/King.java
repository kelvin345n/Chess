package pieces;

import board.ChessBoard;
import board.Tile;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class King implements Piece{
    private final Color pieceColor;
    // String representation of the piece. Such as ♞.
    private String imageRep = "♚";
    // If a piece is a white piece then true, if black piece then false
    private final boolean isWhiteTeam;
    private boolean isMoved;
    private final int point = 0;
    private final boolean isPiece = true;
    @Override
    public boolean isPiece(){
        return isPiece;
    }


    public King(boolean isWhiteTeam){
        isMoved = false;
        this.isWhiteTeam = isWhiteTeam;
        if (isWhiteTeam){
            // Color is set to white
            pieceColor = new Color(252, 251, 244);
        } else {
            // Color is set to black
            pieceColor = new Color(0, 0, 0);
        }
    }

    @Override
    public Set<String> movesSet(String index, ChessBoard cb){
        Set<String> moves = new HashSet<>();
        Piece startPiece = cb.getPieceAt(index);
        for(String att : attackSet(index, cb)){
            Tile tile = cb.getTile(att);
            // If the king is a white piece and the tile it wants to go to
            // is not attacked by black. Or vice-versa
            if ((startPiece.isWhiteTeam() && !tile.isBlackAttacking()) ||
                    !startPiece.isWhiteTeam() && !tile.isWhiteAttacking()){
                if (!cb.sameTeams(index, att)){
                    moves.add(att);
                }
            }
        }
        boolean checked = cb.isKingChecked(isWhiteTeam);
        // If the king has not moved, and not in check, check if he can castle.
        if (!isMoved && !checked){
            // Represent the columns that the rooks are on.
            int colA = 0;
            int colH = 7;
            // if black then we check the rooks on the 8th rank.
            int row = 7;
            if (isWhiteTeam){
                row = 0;
            }
            castlingChecker(colA, row, index, moves, cb);
            castlingChecker(colH, row, index, moves, cb);
        }
        return moves;
    }


    /** When given a position for a rook, checks if it has moved, and is currently attacking
     * its king. If it is then the move is added to the king move set. */
    private void castlingChecker(int col, int row, String kingIndex, Set<String> kingMovesSet, ChessBoard cb){
        // If the king has not moved, we check if he can castle.
        if (!isMoved){
            Tile t = cb.getTile(col, row);
            Piece rook = t.getPiece();
            if (Piece.isRook(rook)){
                // The rook has not moved.
                if (!rook.isMoved()){
                    Set<String> rookAttacks = rook.attackSet(cb.convertToIndex(col, row), cb);
                    // Check if the rook is attacking the king.
                    if (rookAttacks.contains(kingIndex)){
                        int kingCol = cb.convertToCol(kingIndex);
                        int kingRow = cb.convertToRow(kingIndex);
                        int inc = 2;
                        int mult = -1;
                        if (col == 0){
                            inc = -2;
                            mult = 1;
                        }
                        boolean clear = true;
                        // Checking if the other team is attacking any of the tiles that the
                        // king will be moving over.
                        if (isWhiteTeam){
                                // If the tile the king will move over is attacked then break;
                            if (cb.getTile(kingCol + inc + mult, kingRow).isBlackAttacking()){
                                    clear = false;
                            }
                        } else {
                                // If the tile the king will move over is attacked then break;
                                if (cb.getTile(kingCol + inc + mult, kingRow).isWhiteAttacking()){
                                    clear = false;
                                }
                            }
                        if (clear){
                            kingMovesSet.add(cb.convertToIndex(kingCol + inc, kingRow));
                        }
                    }
                }
            }
        }
    }


    @Override
    public Set<String> attackSet(String index, ChessBoard cb) {
        Set<String> attacks = new HashSet<>();
        Piece.attackVertically(index, true, 1, attacks, cb);
        Piece.attackVertically(index, false, 1, attacks, cb);
        Piece.attackHorizontally(index, true, 1, attacks, cb);
        Piece.attackHorizontally(index, false, 1, attacks, cb);

        Piece.attackDiagonallyRight(index, true, 1, attacks, cb);
        Piece.attackDiagonallyRight(index, false, 1, attacks, cb);
        Piece.attackDiagonallyLeft(index, true, 1, attacks, cb);
        Piece.attackDiagonallyLeft(index, false, 1, attacks, cb);
        return attacks;
    }

    @Override
    public void setMoved(){
        isMoved = true;
    }

    /**
     * Sets the move status of the piece to the given boolean value.
     *
     * @param b
     */
    @Override
    public void setMoved(boolean b) {
        isMoved = b;
    }

    @Override
    public boolean isMoved(){
        return isMoved;
    }

    @Override
    public boolean isWhiteTeam(){
        return isWhiteTeam;
    }

    @Override
    public String getImageRep(){
        return imageRep;
    }

    @Override
    public Color getPieceColor(){
        return pieceColor;
    }

    @Override
    public int points(){
        return point;
    }

}
