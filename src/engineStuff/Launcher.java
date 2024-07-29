package engineStuff;

import FrameworkML.ActivationFunctions.Relu;
import FrameworkML.ActivationFunctions.Softmax;
import FrameworkML.CostFunctions.MeanSquareError;
import FrameworkML.Layers.Conv3D;
import FrameworkML.Layers.Dense;
import FrameworkML.Layers.Layer;
import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import FrameworkML.NeuralNetReader;
import FrameworkML.Operations;
import board.Game;

// Chess Engine Architecture

/**
 *         Layer[] arch = new Layer[]{
 *                 new Conv3D(3, 3, 7, 1, 1, 7, new Relu()),
 *                 new Conv3D(3, 3, 2, 1, 1, 1, new Relu(), true),
 *                 new Dense(72, new Relu()),
 *                 new Dense(36, new Relu()),
 *                 new Dense(3, new Softmax())
 *         };
 *         int[] inputShape = new int[]{10, 10, 14};
 *         int[] outputShape = new int[]{1, 3, 1};
 *
 *         NeuralNet2 engine = new NeuralNet2(inputShape, outputShape, arch, new MeanSquareError());
 *
 * */

public class Launcher {
    public static void main(String[] args) {
        NeuralNet2 engine = NeuralNetReader.loadNetwork("Aaron");
        int epoch = 4;
        for (int i = 0; i < epoch; i++){
            System.out.println("Epoch " + i);
            int games = 6;
            // We use an epoch of 'games' games, we update parameters 'epoch' times in 'engine'
            TrainRandom.sim(games, engine, 0.1f);
        }
        // We save the engine
        engine.saveNetwork("Aaron");


    }
}
