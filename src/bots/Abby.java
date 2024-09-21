package bots;

import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import FrameworkML.NeuralNetReader;
import FrameworkML.Operations;
import board.Game;
import board.Gamelogic;
import engineStuff.PositionEncoder;

import java.util.List;
import java.util.Random;

public class Abby implements Bot{
    NeuralNet2 Abby;
    public Abby(){
        Abby = NeuralNetReader.loadNetwork("Alulu");
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
        boolean turn = logic.isWhiteTurn();
        List<String> moves = logic.allPossibleMoves(logic.isWhiteTurn());

        // The best move consists of the lowest value.
        String bestMove = "";
        float value = 0;
        int i = 0;
        for (String move: moves){
            game.tempMove(move);
            if (game.tempGameOver()){
                if (game.getPoints(turn) == 1){
                    game.reverseMove();
                    // if this move leads to checkmate... return move.
                    return move;
                }
            }
            // At the start we initialize best move to the first move.
            if (i == 0){
                bestMove = move;
                value = calculateEv(game);
                i++;
            } else {
                float newVal = calculateEv(game);
//                System.out.println(newVal + ": " + move);
                if (newVal < value){
                    value = newVal;
                    bestMove = move;
                }
            }
            game.reverseMove();
        }
        return bestMove;
    }

    @Override
    public String name() {
        return "Abby";
    }

    public float calculateEv(Game g){
        Matrix[] currPos = PositionEncoder.encode(g);
        Matrix[] infer = Abby.inference(currPos);
        Matrix[] points = new Matrix[]{
                // 1 point for win, 0.5 for draw, 0 for loss
                new Matrix(1, 3, new float[]{1f, 0.5f, 0f})
        };
        Matrix ev = Operations.hadamard(infer[0], points[0]);
        return Operations.elementSum(ev);
    }
}
