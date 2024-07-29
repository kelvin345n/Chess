package board;

import FrameworkML.Matrix;
import bots.Bot;
import engineStuff.PositionEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** This is to play a game of bot vs bot where there is no interface to watch. */
public class GameBvBNoI {
    private List<Matrix[]> whitePositions;
    private List<Matrix[]> blackPositions;
    private Game g;

    public GameBvBNoI(ChessBoard b, List<String> moves, Bot oneBot, Bot twoBot){
        whitePositions = new ArrayList<>();
        blackPositions = new ArrayList<>();

        g = new Game(b, moves);
        Gamelogic gl = g.getGamelogic();
        Random rand = new Random();
        // Chooses randomly if the oneBot should be white if true.
        boolean oneBotIsWhite = rand.nextBoolean();
        while(!g.isGameOver()){
            String move;
            if (gl.isWhiteTurn() == oneBotIsWhite){
                move = oneBot.nextMove(g);
            } else {
                move = twoBot.nextMove(g);
            }
            if (gl.isWhiteTurn()){
                whitePositions.add(PositionEncoder.encode(g));
            } else {
                blackPositions.add(PositionEncoder.encode(g));
            }
            g.move(move);
        }
    }
    public GameBvBNoI(Bot oneBot, Bot twoBot){
        this(new ChessBoard(), new ArrayList<>(), oneBot, twoBot);
    }

    public List<Matrix[]> getWhitePositions(){
        return whitePositions;
    }
    public List<Matrix[]> getBlackPositions(){
        return blackPositions;
    }
    public Matrix[] getWhiteOutcome() {
        return outcomeHelper(true);
    }
    public Matrix[] getBlackOutcome(){
        return outcomeHelper(false);
    }
    private Matrix[] outcomeHelper(boolean isWhite){
        float[] outcomes = new float[3]; // Array to store win, draw, lose
        float points = g.getPoints(isWhite);
        if (points == 1f) {
            outcomes[0] = 1f; // Win
        } else if (points == 0.5f) {
            outcomes[1] = 1f; // Draw
        } else {
            outcomes[2] = 1f; // Lose
        }
        return new Matrix[]{new Matrix(1, 3, outcomes)};
    }
}
