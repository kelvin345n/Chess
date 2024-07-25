package board;

import edu.princeton.cs.algs4.StdDraw;
import pieces.Piece;

import java.awt.*;

/** This will represent the floor pieces for the chess board.
 * In other words the alternating tiles for a chess board */
public class Tile {
    private Color backgroundColor;
    private Piece piece;
    private boolean alternate;
    // Determines if any white and black pieces can potentially attack this tile.
    // Useful when determining where a king can move on the board.
    private boolean isWhiteAttacking;
    private boolean isBlackAttacking;

    public Tile(boolean alternateTile){
        // These are the two colors that the tiles on the chess board
        // alternate between.
        alternate = alternateTile;
        if (alternateTile){
            backgroundColor = new Color(209, 139, 71);
        } else {
            backgroundColor = new Color(255, 206, 159);
        }
        isWhiteAttacking = false;
        isBlackAttacking = false;
    }
    public Piece getPiece(){
        return piece;
    }
    /** Overwrites the existing piece and places a new piece at this
     * specific tile on the board */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public void setWhiteAttacking(boolean isWhiteAttacking){
        this.isWhiteAttacking = isWhiteAttacking;
    }
    public void setBlackAttacking(boolean isBlackAttacking){
        this.isBlackAttacking = isBlackAttacking;
    }
    public boolean isWhiteAttacking(){
        return isWhiteAttacking;
    }
    public boolean isBlackAttacking(){
        return isBlackAttacking;
    }

    public Tile clone(){
        Tile clone = new Tile(alternate);
        clone.setPiece(piece);
        clone.setWhiteAttacking(isWhiteAttacking);
        clone.setBlackAttacking(isBlackAttacking);
        return clone;
    }

    /**
     * Draws the tile to the screen at location x, y. If a valid filepath is provided,
     * we draw the image located at that filepath to the screen. Otherwise, we fall
     * back to the character and color representation for the tile.
     *
     * Note that the image provided must be of the right size (16x16). It will not be
     * automatically resized or truncated.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void draw(double x, double y) {
        // Colors in the square tile
        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
        // Draws in the piece
        StdDraw.setPenColor(piece.getPieceColor());
        StdDraw.text(x + 0.5, y + 0.35, piece.getImageRep());
    }
}
