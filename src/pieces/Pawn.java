package pieces;


import board.ChessBoard;
import board.Tile;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Pawn implements Piece{
    private final Color pieceColor;
    // String representation of the piece. Such as ♞.
    private final String imageRep = "♟";
    // If a piece is a white piece then true, if black piece then false
    private final boolean isWhiteTeam;
    private boolean isMoved;
    private final int point = 4;
    private final boolean isPiece = true;

    @Override
    public boolean isPiece(){
        return isPiece;
    }

    public Pawn(boolean isWhiteTeam){
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

    /** Returns all the moves that a pawn on the index would make according
     * to the chess board that is given. */
    @Override
    public Set<String> movesSet(String index, ChessBoard cb){
        Set<String> moves = new HashSet<>();
        Set<String> attackSet = attackSet(index, cb);
        String enPassant = cb.getEnPassant();
        boolean pass = false;
        // Checking if enPassant square is next to the index square
        if (!enPassant.isEmpty()){
            int rowEn = cb.convertToRow(enPassant);
            int colEn = cb.convertToCol(enPassant);
            int col = cb.convertToCol(index);
            int row = cb.convertToRow(index);
            if (colEn - 1 == col || colEn + 1 == col){
                if (rowEn == row){
                    pass = true;
                }
            }
        }
        // Check for opposing piece at attack
        for (String attacked : attackSet){
            if (cb.opposingTeams(index, attacked)){
                moves.add(attacked);
            }
            if (pass){
                int colAtt = cb.convertToCol(attacked);
                int colEn = cb.convertToCol(enPassant);
                if (colAtt == colEn){
                    moves.add(attacked);
                }
            }

        }
        Piece p = cb.getPieceAt(index);
        int iterations = 1;
        if (!p.isMoved()){
            iterations = 2;
        }
        Set<String> tempMoves = new HashSet<>();
        Piece.attackVertically(index, true, iterations, tempMoves, cb);

        for (String move : tempMoves){
            if (!cb.isPieceHere(move)){
                moves.add(move);
            }
        }
        return moves;
    }

    /** Returns a set of string indices that if a pawn were to be on
     * the index, then the tiles it would be attacking. */
    @Override
    public Set<String> attackSet(String index, ChessBoard cb) {
        int col = cb.convertToCol(index);
        int row = cb.convertToRow(index);
        Set<String> pawnAttackSet = new HashSet<>();
        // Take into account the team the pawn is on
        int increment = -1;
        if (isWhiteTeam()){
            increment = 1;
        }
        // Get each diagonal tile to the pawn.
        pawnAttacksHelper(col-1, row + increment, pawnAttackSet, cb);
        pawnAttacksHelper(col+1, row + increment, pawnAttackSet, cb);
        return pawnAttackSet;
    }

    private void pawnAttacksHelper(int col, int row, Set<String> s, ChessBoard cb){
        if (cb.possibleTile(col, row)){
            s.add(cb.convertToIndex(col, row));
        }
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
