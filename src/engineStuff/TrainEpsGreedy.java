package engineStuff;

import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import board.GameBvBGreedy;
import board.GameBvBNoI;
import bots.Bot;
import bots.EpsGreedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Used to train a neural network using eps greedy method */
public class TrainEpsGreedy {
    private final List<GameDataset> data;
    private final NeuralNet2 nn;
    private float learningRate;
    private float eps;
    private float stepSize;
    public TrainEpsGreedy(NeuralNet2 nn, float learningRate, float eps, float stepSize){
        this.nn = nn;
        this.learningRate = learningRate;
        this.eps = eps;
        this.stepSize = stepSize;
        // Experience buffer is 10,000 positions
        data = new ArrayList<>(10000);
    }

    public void setLearningRate(float lr){
        learningRate = lr;
    }
    public void setEpsilon(float eps){
        this.eps = eps;
    }
    public void setStepSize(float stepSize){
        this.stepSize = stepSize;
    }
    public float getLearningRate(){
        return learningRate;
    }
    public float getEpsilon(){
        return eps;
    }
    public float getStepSize(){
        return stepSize;
    }

    /** Simulates 1 game.  for 1 epoch, meaning we play that many games, add them into one training set
     * and update the parameters in the neural network by learning rate one time. */
    public void sim(){
        EpsGreedy randy = new EpsGreedy(nn, eps);

        // I guess randy plays against himself.
        GameBvBGreedy sim = new GameBvBGreedy(randy, stepSize);
        List<GameDataset> simData = sim.getData();
        for (GameDataset gd : simData){
            if (data.size() == 10000){
                data.removeLast();
            }
            data.addFirst(gd);
        }
        Collections.shuffle(data);

        int batchSize = 100;
        Matrix[][] trainingExamples = new Matrix[batchSize][];
        Matrix[][] trainActual = new Matrix[batchSize][];
        // Keeps track of what index we are at for the training examples array
        int i = 0;
        // we iterate over all positions in every game we played.
        for (int j = 0; j < batchSize; j++){
            GameDataset ds = data.get(j);
            trainingExamples[i] = ds.getPosition();
            trainActual[i++] = ds.getValue();
        }
        System.out.println("Cost before training: " + nn.cost(trainingExamples, trainActual));
        System.out.println("Training \uD83D\uDCAA ");
        nn.trainNetwork(trainingExamples, trainActual, learningRate);
        System.out.println("Cost after training: " + nn.cost(trainingExamples, trainActual));
    }

}
