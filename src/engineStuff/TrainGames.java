package engineStuff;

import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import board.GameBvBNoI;
import bots.AlphaBota;
import bots.Bot;

import java.util.ArrayList;
import java.util.List;

public class TrainGames {
    /** Given a number of games for 1 epoch, meaning we play that many games, add them into one training set
     * and update the parameters in the neural network by learning rate one time. */
    public static void sim(int gamesCount, NeuralNet2 nn, float learningRate){
        Bot randy = new AlphaBota(nn, 2, 0.1f);
        // We play a random game, each position we record.
        // Then we back propagate and update each position and its recorded outcome.
        List<Matrix[]> whitePositions;
        List<Matrix[]> blackPositions;
        // Append each encoded position in either positions list according to the color.
        // The next color to move is considered 'their' position. White to move means white's position
        // So start of the game is considered white's position.

        // Keeps track of all the positions and their outcomes. index 0 of games should take
        // a color's position and index 0 of outcomes will take that positions outcome.
        List<List<Matrix[]>> games = new ArrayList<>();
        List<Matrix[]> outcomes = new ArrayList<>();

        int positionCounts = 0;
        // We play gameCount amount of simulations and add them to make a training batch.
        for (int i = 0; i < gamesCount; i++){
            // I guess randy plays against himself.
            GameBvBNoI sim = new GameBvBNoI(randy, randy);
            whitePositions = sim.getWhitePositions();
            blackPositions = sim.getBlackPositions();

            games.add(whitePositions);
            outcomes.add(sim.getWhiteOutcome());
            games.add(blackPositions);
            outcomes.add(sim.getBlackOutcome());

            positionCounts += whitePositions.size() + blackPositions.size();
        }

        Matrix[][] trainingExamples = new Matrix[positionCounts][];
        Matrix[][] trainActual = new Matrix[positionCounts][];
        // Keeps track of what index we are at for the training examples array
        int i = 0;

        // we iterate over all positions in every game we played.
        for (int j = 0; j < games.size(); j++){
            List<Matrix[]> positions = games.get(j);
            Matrix[] outcome = outcomes.get(j);
            for (Matrix[] pos : positions){
                trainingExamples[i] = pos;
                trainActual[i++] = outcome;
            }
        }

        System.out.println("Cost before training: " + nn.cost(trainingExamples, trainActual));
        System.out.println("Training \uD83D\uDCAA ");
        nn.trainNetwork(trainingExamples, trainActual, learningRate);
        System.out.println("Cost after training: " + nn.cost(trainingExamples, trainActual));
    }






}
