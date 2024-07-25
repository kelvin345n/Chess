package pieces;

import board.ChessBoard;

import java.awt.*;
import java.util.Set;


public interface Piece {
    /** returns all the valid moves that the piece can make on a given chessboard,
     * and given starting position. */
    Set<String> movesSet(String index, ChessBoard cb);

    /** Returns all the squares that the piece is attacking on a given chess board
     * and given position. */
    Set<String> attackSet(String index, ChessBoard cb);

    /** Returns whether the piece in question is on white team. */
    boolean isWhiteTeam();

    /** Returns if a piece has moved yet. */
    boolean isMoved();

    /** Sets that the piece moved to true. */
    void setMoved();

    /** Returns the string representation of that piece. Useful for StdDraw */
    String getImageRep();

    /** Returns the color of the specific piece. */
    Color getPieceColor();

    /** Returns false if the piece is a nothing piece. */
    boolean isPiece();

    /** Returns how many points that piece is worth. Only used
     * to determine insufficient material. */
    int points();

    /** Returns true if piece is a pawn. False else-wise */
    static boolean isPawn(Piece piece){
        return piece.getImageRep().equals("♟");
    }

    /** Returns true if piece is a king. False else-wise */
    static boolean isKing(Piece piece){
        return piece.getImageRep().equals("♚");
    }

    /** Returns true if the piece is a rook. */
    static boolean isRook(Piece piece){
        return piece.getImageRep().equals("♜");
    }

    /** Returns true if the piece is a knight. */
    static boolean isKnight(Piece piece){
        return piece.getImageRep().equals("♞");
    }

    static boolean isQueen(Piece piece){
        return piece.getImageRep().equals("♛");
    }
    static boolean isBishop(Piece piece){
        return piece.getImageRep().equals("♝");
    }

    /** Given a piece at the index, checks up to the number of iterations in
     * the specified direction. Forward is different depending on the team
     * the piece is on. A piece will move "forward" to the opposing side.
     * And if not blocked, any piece blocks its path to the number of iterations,
     * then it gets added to the moves set. Assumes index is a valid position.
     * */
    static void attackVertically(String index, boolean forward, int numIterations,
                                 Set<String> attackSet, ChessBoard cb){
        if (!cb.possibleTile(index)){
            return;
        }

        boolean blocked = false;
        int row = cb.convertToRow(index);
        int col = cb.convertToCol(index);
        Piece p = cb.getPieceAt(index);

        // If white and forward or black and backwards then increment is 1.
        int increment = -1;
        if (p.isWhiteTeam() == forward){
            increment = 1;
        }

        int newRow = row;
        for (int i = 1; i <= numIterations; i++){
            newRow += increment;
            if (!cb.possibleTile(col, newRow)){
                break;
            }
            if (!blocked){
                if (cb.isPieceHere(col, newRow)){
                    blocked = true;
                }
                // Add the new index whether it is blocking or not b/c
                // the piece is attacking it either way.
                attackSet.add(cb.convertToIndex(col, newRow));
            } else {
                break;
            }
        }
    }

    /** Given a piece at the index, checks up to the number of iterations in
     * the specified direction. Right is different depending on the team
     * the piece is on. A piece will move to the king side if set to true.
     * For white, the piece would move right. Left for black.
     * And if not blocked, then any piece blocks its path to the number of iterations,
     * then it gets added to the moves set. Assumes index is a valid position.
     * */
    static void attackHorizontally(String index, boolean kingSide, int numIterations,
                                   Set<String> attackSet, ChessBoard cb){
        if (!cb.possibleTile(index)){
            return;
        }

        boolean blocked = false;
        int row = cb.convertToRow(index);
        int col = cb.convertToCol(index);

        int increment = -1;
        if (kingSide){
            increment = 1;
        }
        int newCol = col;
        for (int i = 1; i <= numIterations; i++){
            newCol += increment;
            if (!cb.possibleTile(newCol, row)){
                break;
            }
            if (!blocked){
                if (cb.isPieceHere(newCol, row)){
                    blocked = true;
                }
                // Add the new index whether it is blocking or not b/c
                // the piece is attacking it either way.
                attackSet.add(cb.convertToIndex(newCol, row));
            } else {
                break;
            }
        }
    }

    /** Given a piece at the index, checks up to the number of iterations in
     * the specified direction. This function checks the right slash (/) on the x.
     * If up is set to true then we will check the diagonal moving up and to the right.
     * The right refers to the king side. If down is set to true, then we
     * move down and to the left.
     * */
    static void attackDiagonallyRight(String index, boolean up, int numIterations,
                                      Set<String> attackSet, ChessBoard cb){
        if (!cb.possibleTile(index)){
            return;
        }
        boolean blocked = false;
        int col = cb.convertToCol(index);
        int row = cb.convertToRow(index);
        int increment = -1;
        if (up){
            increment = 1;
        }
        int newCol = col;
        int newRow = row;
        for (int i = 0; i < numIterations; i++){
            newRow += increment;
            newCol += increment;
            if (!cb.possibleTile(newCol, newRow)){
                break;
            }
            if (!blocked){
                if (cb.isPieceHere(newCol, newRow)){
                    blocked = true;
                }
                // Add the new index whether it is blocking or not b/c
                // the piece is attacking it either way.
                attackSet.add(cb.convertToIndex(newCol, newRow));
            } else {
                break;
            }
        }
    }
    /** Given a piece at the index, checks up to the number of iterations in
     * the specified direction. This function checks the left slash (\) on the x.
     * If up is set to true then we will check the diagonal moving up and to the left.
     * The right refers to the king side. If down is set to true, then we
     * move down and to the right.
     * */
    static void attackDiagonallyLeft(String index, boolean up, int numIterations,
                                      Set<String> attackSet, ChessBoard cb){
        if (!cb.possibleTile(index)){
            return;
        }
        boolean blocked = false;
        int col = cb.convertToCol(index);
        int row = cb.convertToRow(index);
        int increment = -1;
        if (up){
            increment = 1;
        }
        int newCol = col;
        int newRow = row;
        for (int i = 0; i < numIterations; i++){
            newRow += increment;
            newCol -= increment;
            if (!cb.possibleTile(newCol, newRow)){
                break;
            }
            if (!blocked){
                if (cb.isPieceHere(newCol, newRow)){
                    blocked = true;
                }
                // Add the new index whether it is blocking or not b/c
                // the piece is attacking it either way.
                attackSet.add(cb.convertToIndex(newCol, newRow));
            } else {
                break;
            }
        }
    }

    /** If an index is possible on the chessboard then add it to the set */
    static void addIfPossible(String index, Set<String> set, ChessBoard cb){
        if (cb.possibleTile(index)){
            set.add(index);
        }
    }

}
