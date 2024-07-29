package bots;

import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import FrameworkML.Operations;
import board.ChessBoard;
import board.Game;
import board.Gamelogic;
import engineStuff.PositionEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** This bot uses alpha beta pruning to determine the next best move. */
public class AlphaBota implements Bot {
    NeuralNet2 engine;
    public AlphaBota(NeuralNet2 chessEngine){
        engine = chessEngine;
    }

    /**
     * When given a chessboard of the current position, the bot returns the next best
     * move from the current possible moves
     *
     * @param game
     */
    @Override
    public String nextMove(Game game) {
        Random rand = new Random();
        // Probably to start
        Gamelogic logic = game.getGamelogic();
        List<String> moves = logic.allPossibleMoves(logic.isWhiteTurn());

        // Instead of getting the max value of the next move, we get the min value
        // because when we evaluate the next move using the neural network, it evaluates that position
        // as if the next color was next to act. So we choose the move that decreases the
        // opponent's EV.

        return null;
    }

    /**
     * PSEUDO: Using recursive strategy.
     *
     * public String alphabeta(Game game, float alpha, float beta, boolean minimizingPlayer){
     *
     *      returns 'next best move'
     * }
     *
     * Maybe create a hashmap associating every move with its expected outcome.
     * we then choose the move with the lowest value.
     *
     * for every move in 'moves' (in current position):
     *      - we must get create a new game using move. -> game
     *      - and we call alphabeta on this new game, with depth-1, and switch the minimizingPlayer.
     *      - where value is equal to this new call.
     *
     *      - We do this until depth == 0. If this occurs then we take this game, and encode its position.
     *      - We then pass this encoded position into the neural network. This gives us a vector.
     *      - Multiply this vector by (1, 0.5, 0) to get the expected points that the current player will receive in this position.
     *      -
     *      - We then recurse back up the tree
     *
     *
     *
     * */



    /** Given a current position, does alpha-beta pruning to
     * determine the best move for the current player */
    public float alphabeta(Game g, float alpha, float beta, int depth){
        Gamelogic logic = g.getGamelogic();
        float value;
        if (depth == 0){



            Matrix[] encoded = PositionEncoder.encode(g);
            // This should give us a row vector (win draw loss) that represent probabilities for each outcome.
            Matrix[] inference = engine.inference(encoded);
            // win receives 1 point, draw = 0.5, and loss = 0.
            Matrix values = new Matrix(1, 3, new float[]{1f, 0.5f, 0f});

            Matrix m = Operations.hadamard(inference[0], values);
            float ev = Operations.elementSum(m);

        }

        for (String move : logic.allPossibleMoves(logic.isWhiteTurn())){
            // For every move create a new game after making that move.
            List<String> copy = new ArrayList<>(logic.movesList());
            copy.add(move);
            // New game with this new move.
            Game newGame = new Game(new ChessBoard(), copy);
            // TODO: We have to code if the new game is game over then give value
            //          automatically.


        }

        return 0;
    }

}
