package board;

import FrameworkML.Matrix;
import bots.Bot;
import bots.EpsGreedy;
import engineStuff.PositionEncoder;

import java.util.*;

public class GameBvBGreedy {
    private List<Matrix[]> positions;
    // The first position in positions, and the first value in tempDiffValue
    // correspond.
    private List<Float> tempDiffValue;
    private Game g;
    private boolean randomMove;

    public GameBvBGreedy(ChessBoard b, List<String> moves, EpsGreedy oneBot, EpsGreedy twoBot){
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
                randomMove = oneBot.isRandomMove();
            } else {
                move = twoBot.nextMove(g);
                randomMove = twoBot.isRandomMove();
            }
            if (!randomMove){
                if (gl.isWhiteTurn()){
                    whitePositions.add(PositionEncoder.encode(g));
                } else {
                    blackPositions.add(PositionEncoder.encode(g));
                }
            }
            g.move(move);
        }
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
