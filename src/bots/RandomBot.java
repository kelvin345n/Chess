package bots;

import board.ChessBoard;
import board.Game;
import board.Gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** This bot chooses a random move from the current position. */
public class RandomBot implements Bot {

    public RandomBot(){
    }
    /**
     * When given a chessboard of the current position, the bot returns a random move
     * from the current position for the next current player.
     *
     * @param game
     */
    @Override
    public String nextMove(Game game) {
        Random rand = new Random();
        Gamelogic logic = game.getGamelogic();
        boolean turn = logic.isWhiteTurn();
        List<String> moves = logic.allPossibleMoves(logic.isWhiteTurn());
        return moves.get(rand.nextInt(moves.size()));
    }

    @Override
    public String name() {
        return "Randy";
    }
}
