package bots;

import board.ChessBoard;
import board.Game;

public interface Bot {
    /** When given a chessboard of the current position, the bot returns the next best
     * move from the current possible moves */
    String nextMove(Game game);

    String name();

}
