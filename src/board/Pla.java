package board;

import java.util.Random;

public class Pla {
    public static void main(String[] args) {
        Random random = new Random();
        int n_in = 100; // Example number of input units
        double stdDev = Math.sqrt(2.0 / n_in);

        for (int i = 0; i < 100; i++){
            double weight = stdDev * random.nextGaussian();
            System.out.println(weight);
        }



    }
}
