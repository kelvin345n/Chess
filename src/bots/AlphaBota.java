package bots;

import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import FrameworkML.Operations;
import board.ChessBoard;
import board.Game;
import board.Gamelogic;
import engineStuff.PositionEncoder;
import org.objectweb.asm.tree.analysis.Value;

import java.util.*;

/** This bot uses alpha beta pruning to determine the next best move. */
public class AlphaBota implements Bot {
    NeuralNet2 engine;
    // How deep in the tree we want to search
    int depth;
    // Epsilon value to judge moves close in EV
    float eps;
    public AlphaBota(NeuralNet2 chessEngine, int depth, float eps){
        engine = chessEngine;
        this.depth = depth;
        this.eps = eps;
    }

    /**
     * When given a chessboard of the current position, the bot returns the next best
     * move from the current possible moves
     *
     * @param game
     */
    @Override
    public String nextMove(Game game) {
        return nextMoveHelper(game, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    @Override
    public String name() {
        return "AlphaBeta";
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

    private String nextMoveHelper(Game game, float alpha, float beta){
        Random rand = new Random();
        // We will store all the moves that could be used.
        Gamelogic logic = game.getGamelogic();
        // Keep track of all the candidate moves that are close in EV.
        List<String> candidateMoves = new ArrayList<>();
        List<String> moves = logic.allPossibleMoves(logic.isWhiteTurn());
        Collections.shuffle(moves);
        // The averageEV of all candidateMoves
        float avgEv = 0;
        float totalEv = 0;
        for (String move : moves){
            game.tempMove(move);
            // This gives the expected value for the currColor
            float eval = 1f - alphabeta(game, alpha, beta, depth-1, false);
            System.out.println(move + ": " + eval);
            game.reverseMove();
            if (eval > (avgEv + eps)){
                candidateMoves.clear();
                candidateMoves.add(move);
                totalEv = eval;
                avgEv = eval;
            } else if (eval >= avgEv || eval > (avgEv - eps)) {
                candidateMoves.add(move);
                totalEv += eval;
                avgEv = totalEv/candidateMoves.size();
            }
            alpha = Math.max(alpha, eval);
            if (beta <= alpha){
                break;
            }
        }
        return candidateMoves.get(rand.nextInt(candidateMoves.size()));
    }


    /** Given a current position, does alpha-beta pruning to
     * determine the best move for the current player */
    private float alphabeta(Game g, float alpha, float beta, int depth, boolean currColor){
        Gamelogic logic = g.getGamelogic();
        // The other color made the current move. So since the neural network evaluates the
        // position for the color next to move, we must also return the value for the
        // color that is next to move.
        if (g.tempGameOver()){
            return g.getPoints(logic.isWhiteTurn());
        }
        if (depth == 0){
            return evOfPosition(g);
        }

        if (currColor){
            float maxEval = Float.NEGATIVE_INFINITY;
            List<String> moves = logic.allPossibleMoves(logic.isWhiteTurn());
            Collections.shuffle(moves);
            for (String move : moves){
                g.tempMove(move);
                // This gives the expected value for the currColor
                float eval = 1f - alphabeta(g, alpha, beta, depth-1, false);
                g.reverseMove();
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha){
                    break;
                }
            }
            return maxEval;
        } else {
            float minEval = Float.POSITIVE_INFINITY;
            List<String> moves = logic.allPossibleMoves(logic.isWhiteTurn());
            Collections.shuffle(moves);
            for (String move : moves){
                g.tempMove(move);
                // The opposing color wants to minimize the curr colors EV
                float eval = alphabeta(g, alpha, beta, depth-1, true);
                g.reverseMove();
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha){
                    break;
                }
            }
            return minEval;
        }
    }

    /** Given a chess game, we evaluate that current position given using the
     * given neural network. And return the expected value. */
    public float evOfPosition(Game g){
        Matrix[] encoded = PositionEncoder.encode(g);
        // This should give us a row vector (win draw loss) that represent probabilities for each outcome.
        Matrix[] inference = engine.inference(encoded);
        // win receives 1 point, draw = 0.5, and loss = 0.
        Matrix values = new Matrix(1, 3, new float[]{1f, 0.5f, 0f});
        Matrix m = Operations.hadamard(inference[0], values);
        return Operations.elementSum(m);
    }
}
