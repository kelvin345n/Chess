package board;

import FrameworkML.Matrix;
import bots.Bot;
import bots.EpsGreedy;
import engineStuff.GameDataset;
import engineStuff.PositionEncoder;

import java.util.*;

public class GameBvBGreedy {
    private Game g;
    private boolean randomMove;
    // Stores the position and the temporal difference value of that position.
    private List<GameDataset> data;

    public GameBvBGreedy(ChessBoard b, List<String> moves, EpsGreedy bot, float stepSize){
        data = new ArrayList<>();
        g = new Game(b, moves);
        Gamelogic gl = g.getGamelogic();
        while(!g.isGameOver()){
            String move = bot.nextMove(g);
            randomMove = bot.isRandomMove();
            if (!randomMove){
                // Means greedy move was taken.
                dataAdder(bot, stepSize);
            }
            g.move(move);
        }
        // Game is over.
        // The position with the ending position is not temporally different
        // and just the reward. Greedy value is the value of the current position
        // once the game has ended.
        float reward = g.getPoints(!gl.isWhiteTurn());
        float update = bot.getGreedyValue() + stepSize*(reward - bot.getGreedyValue());
        // This is the value we want the current position to get closer to.
        Matrix[] val = new Matrix[]{new Matrix(1, 1, new float[]{
                update
        })};
        GameDataset gd = new GameDataset(PositionEncoder.encode(g), val);
        data.add(gd);
    }
    public GameBvBGreedy(EpsGreedy bot, float stepSize){
        this(new ChessBoard(), new ArrayList<>(), bot, stepSize);
    }

    /** Adds position to list and its update value to list if greedy move was made. */
    private void dataAdder(EpsGreedy bot, float stepSize){
        // We get the value of the new position and have to subtract greedy value
        // by 1 because that greedy value is the value for the other player.
        float update = bot.getCurrValue() + stepSize*((1 - bot.getGreedyValue()) - bot.getCurrValue());
        // This is the value we want the current position to get closer to.
        Matrix[] val = new Matrix[]{new Matrix(1, 1, new float[]{
                update
        })};
        GameDataset gd = new GameDataset(PositionEncoder.encode(g), val);
        data.add(gd);
    }

    public List<GameDataset> getData(){
        return data;
    }
}
