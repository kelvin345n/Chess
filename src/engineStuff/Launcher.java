package engineStuff;

import FrameworkML.ActivationFunctions.Relu;
import FrameworkML.ActivationFunctions.Sigmoud;
import FrameworkML.ActivationFunctions.Softmax;
import FrameworkML.CostFunctions.BinaryCrossEntropy;
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

/**First Architecture
 *
 *         Layer[] arch = new Layer[]{
 *                 new Conv3D(3, 3, 8, 1, 1, 8, new Relu()),
 *                 new Conv3D(3, 3, 2, 1, 1, 1, new Relu(), true),
 *                 new Dense(72, new Relu()),
 *                 new Dense(36, new Relu()),
 *                 new Dense(3, new Softmax())
 *         };
 *         int[] inputShape = new int[]{10, 10, 16};
 *         int[] outputShape = new int[]{1, 3, 1};
 *
 *         NeuralNet2 engine = new NeuralNet2(inputShape, outputShape, arch, new MeanSquareError());
 *
 * */

/** SECOND ARCHITECTURE:
 *
 * Layer[] arch = new Layer[]{
 *                   new Conv3D(3, 3, 8, 1, 1, 8, new Relu()),
 *                   new Conv3D(3, 3, 2, 1, 1, 1, new Relu(), true),
 *                   new Dense(168, new Relu()),
 *                   new Dense(84, new Relu()),
 *                   new Dense(128, new Relu()),
 *                   new Dense(3, new Softmax())
 *           };
 *           int[] inputShape = new int[]{10, 10, 16};
 *           int[] outputShape = new int[]{1, 3, 1};
 *           NeuralNet2 engine = new NeuralNet2(inputShape, outputShape, arch, new MeanSquareError());
 * */

/** THIRD ARCH:
 *
 * Layer[] arch = new Layer[]{
 *             new Conv3D(3, 3, 8, 1, 1, 8, new Relu()),
 *             new Conv3D(3, 3, 2, 1, 1, 1, new Relu(), true),
 *             new Dense(512, new Relu()),
 *             new Dense(1024, new Relu()),
 *             new Dense(2048, new Relu()),
 *                  new Dense(1024, new Relu()),
 *                  new Dense(512, new Relu()),
 *                  new Dense(256, new Relu()),
 *             new Dense(1, new Sigmoud())
 *         };
 *          int[] inputShape = new int[]{10, 10, 16};
 *             int[] outputShape = new int[]{1, 1, 1};
 *          NeuralNet2 engine = new NeuralNet2(inputShape, outputShape, arch, new BinaryCrossEntropy());
 * */

public class Launcher {
    public static void main(String[] args) {
//          // Used when making a new architecture and saved
//         Layer[] arch = new Layer[]{
//            new Conv3D(3, 3, 8, 1, 1, 8, new Relu()),
//            new Conv3D(3, 3, 2, 1, 1, 1, new Relu(), true),
//            new Dense(512, new Relu()),
//            new Dense(1024, new Relu()),
//            new Dense(2048, new Relu()),
//                 new Dense(1024, new Relu()),
//                 new Dense(512, new Relu()),
//                 new Dense(256, new Relu()),
//            new Dense(1, new Sigmoud())
//        };
//         int[] inputShape = new int[]{10, 10, 16};
//            int[] outputShape = new int[]{1, 1, 1};
//         NeuralNet2 engine = new NeuralNet2(inputShape, outputShape, arch, new BinaryCrossEntropy());
//
//        engine.saveNetwork("fourthInit");

//        System.out.println("Inferring");
//        NeuralNet2 load = NeuralNetReader.loadNetwork("fourthInit");
//        Game g = new Game();
//        System.out.println("Encoding");
//        Matrix[] input = PositionEncoder.encode(g);
//        System.out.println("Inferring");
//        Matrix[] infer = load.inference(input);
//        System.out.println("Printing");
//        for (Matrix m: infer){
//            Operations.printMatrix(m);
//        }
//        System.out.println();
//        System.out.println("Training");
//        Matrix[][] trainEx = new Matrix[][]{
//                input
//        };
//        Matrix[][] trainAct = new Matrix[][]{
//                new Matrix[]{new Matrix(1, 1, new float[]{1})}
//        };
//        System.out.println("Cost before: " + load.cost(trainEx, trainAct));
//        load.trainNetwork(trainEx, trainAct, 0.0001f);
//        System.out.println("Cost after: " + load.cost(trainEx, trainAct));
//
//        infer = load.inference(input);
//        System.out.println("Printing");
//        for (Matrix m: infer){
//            Operations.printMatrix(m);
//        }

        String name = "fourthInit";
        NeuralNet2 engine = NeuralNetReader.loadNetwork(name);
        System.out.println("Done loading...");
        int superepoch = 100;
        TrainEpsGreedy trainer = new TrainEpsGreedy(engine, 0.01f, 1f, 0.05f);
        for (int i = 0; i < superepoch; i++){
            int numOfGames = 100;
            for (int j = 0; j < numOfGames; j++){
                trainer.sim();
            }
            trainer.setEpsilon(trainer.getEpsilon() - 0.01f);
        }
        // We save the engine
        engine.saveNetwork("Samantha");

//        NeuralNet2 load = NeuralNetReader.loadNetwork("Aluluuuuuuuuuuu");
//        load.saveNetwork("Alulu");
//

    }
}
