package pieces;

import board.ChessBoard;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Bishop implements Piece{
    private final Color pieceColor;
    // String representation of the piece. Such as ♞.
    private final String imageRep = "♝";
    // If a piece is a white piece then true, if black piece then false
    private final boolean isWhiteTeam;
    private boolean isMoved;
    private final int point = 3;
    private final boolean isPiece = true;
    @Override
    public boolean isPiece(){
        return isPiece;
    }

    public Bishop(boolean isWhiteTeam){
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
    public int points(){
        return point;
    }

    @Override
    public Set<String> movesSet(String index, ChessBoard cb){
        Set<String> moves = new HashSet<>();
        for(String att : attackSet(index, cb)){
            if (!cb.sameTeams(index, att)){
                moves.add(att);
            }
        }
        return moves;
    }

    @Override
    public Set<String> attackSet(String index, ChessBoard cb) {
        Set<String> attacks = new HashSet<>();
        Piece.attackDiagonallyRight(index, true, 7, attacks, cb);
        Piece.attackDiagonallyRight(index, false, 7, attacks, cb);
        Piece.attackDiagonallyLeft(index, true, 7, attacks, cb);
        Piece.attackDiagonallyLeft(index, false, 7, attacks, cb);
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
}
