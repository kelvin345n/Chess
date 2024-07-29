package pieces;

import board.ChessBoard;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Knight implements Piece{
    private final Color pieceColor;
    // String representation of the piece. Such as ♞.
    private final String imageRep = "♞";
    // If a piece is a white piece then true, if black piece then false
    private final boolean isWhiteTeam;
    private boolean isMoved;
    private final int point = 1;
    private final boolean isPiece = true;

    @Override
    public boolean isPiece(){
        return isPiece;
    }

    public Knight(boolean isWhiteTeam){
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
        Set<String> attacks = attackSet(index, cb);
        for (String att : attacks){
            if (cb.sameTeams(index, att)){
                // Do not add to moves if the attacked piece is on the same team.
                continue;
            }
            moves.add(att);
        }
        return moves;
    }

    @Override
    public Set<String> attackSet(String index, ChessBoard cb) {
        Set<String> attacks = new HashSet<>();
        int col = cb.convertToCol(index);
        int row = cb.convertToRow(index);
        knightAttackHelper(col, row, 1, 1, cb, attacks);
        knightAttackHelper(col, row, 1, -1, cb, attacks);
        knightAttackHelper(col, row, -1, 1, cb, attacks);
        knightAttackHelper(col, row, -1, -1, cb, attacks);
        return attacks;
    }
    /** Given a direction such as right (horzInc = 1) and up (vertInc = -1), we
     * add the moves a knight makes the upper right quadrant if it is a valid position. */
    private void knightAttackHelper(int col, int row, int horzInc, int vertInc,
                                    ChessBoard cb, Set<String> attackSet){
        int cTemp = col + horzInc * 2;
        int rTemp = row + vertInc;
        Piece.addIfPossible(cb.convertToIndex(cTemp, rTemp), attackSet, cb);
        cTemp = col + horzInc;
        rTemp = row + vertInc * 2;
        Piece.addIfPossible(cb.convertToIndex(cTemp, rTemp), attackSet, cb);
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
