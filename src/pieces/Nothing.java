package pieces;

import board.ChessBoard;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Nothing implements Piece{
    private final Color pieceColor;
    // String representation of the piece. Such as ♞.
    private final String imageRep = "";
    // If a piece is a white piece then true, if black piece then false
    private final boolean isWhiteTeam;
    private boolean isMoved;
    private final int point = 0;
    private final boolean isPiece = false;

    @Override
    public boolean isPiece(){
        return isPiece;
    }

    public Nothing(boolean isWhiteTeam){
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

    /** Returns empty set */
    @Override
    public Set<String> movesSet(String index, ChessBoard cb){
        return new HashSet<>();
    }

    /** Returns empty set */
    @Override
    public Set<String> attackSet(String index, ChessBoard cb) {
        return new HashSet<>();
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
