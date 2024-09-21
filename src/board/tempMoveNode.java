package board;

import pieces.Piece;

/** Every temp move that is made will be added to a temp move
 * node that we can later retrieve to reinstate the game board.  */
public class tempMoveNode {
    // All these are for the temp move.
    public String startTile;
    public String endTile;
    public Piece startPiece;
    public Piece endPiece;

    public String enPassTile;
    public Piece enPassPiece;
    public String tempPosition;

    public String rookCastleEnd;
    public String rookCastleStart;
    public Piece rookCastle;

    // Tracks what the start piece isMoved attribute was set to.
    public boolean startPieceMove;
    public tempMoveNode(){

    }

}
