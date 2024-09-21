package bots;

import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import board.Game;
import board.Gamelogic;
import engineStuff.PositionEncoder;

import java.util.List;
import java.util.Random;

import static engineStuff.PositionEncoder.encode;

public class EpsGreedy implements Bot{
    private NeuralNet2 nn;
    private float eps;
    // If random move was last made then set to true. If greedy
    // move was made then set to false.
    private boolean randomMove;
    /** This bot uses the epsilon-greedy method in choosing moves.
     * The epsilon value is */
    public EpsGreedy(NeuralNet2 chessEngine, float epsilon){
        if (epsilon < 0 || epsilon > 1){
            throw new IllegalArgumentException("Epsilon is a probability" +
                    "and cannot be less than 0 or greater than 1");
        }
        nn = chessEngine;
        eps = epsilon;
        randomMove = false;
    }

    /**
     * When given a chessboard of the current position, the bot returns the next best
     * move from the current possible moves
     *
     * @param game
     */
    @Override
    public String nextMove(Game game) {
        Gamelogic logic = game.getGamelogic();
        List<String> moves = logic.allPossibleMoves(logic.isWhiteTurn());
        Random rand = new Random();
        if (rand.nextFloat() < eps){
            // choose random move.
            return moves.get(rand.nextInt(moves.size()));
        }
        // For greedy move choice, we choose the move that when in
        // the next position has the lowest value for the next player.
        float value = Float.POSITIVE_INFINITY;
        String bestMove = "";
        for (String move: moves){
            game.tempMove(move);
            Matrix[] pos = PositionEncoder.encode(game);
            Matrix[] infer = nn.inference(pos);
            float ev = infer[0].getElement(1, 1);
            if (ev < value){
                value = ev;
                bestMove = move;
            }
            game.reverseMove();
        }
        return bestMove;
    }

    public boolean isRandomMove(){
        return randomMove;
    }

    @Override
    public String name() {
        return "EpsGreedy";
    }
}
