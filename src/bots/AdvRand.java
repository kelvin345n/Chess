package bots;

import board.Game;
import board.Gamelogic;

import java.util.List;
import java.util.Random;

/** This bot is also a random move bot, but if there exists a move that creates
 * checkmate, then this bot takes it.  */
public class AdvRand implements Bot{
    public AdvRand(){

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
        for (String move: moves){
            game.tempMove(move);
            if (game.tempGameOver()){
                if (game.getPoints(turn) == 1){
                    game.reverseMove();
                    // if this move leads to checkmate... return move.
                    return move;
                }
            }
            game.reverseMove();
        }
        return moves.get(rand.nextInt(moves.size()));
    }
}
