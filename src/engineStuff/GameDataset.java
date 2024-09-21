package engineStuff;

import FrameworkML.Matrix;

public class GameDataset {
    private Matrix[] pos;
    private Matrix[] val;
    // Stores the position and its associated value.
    public GameDataset(Matrix[] position, Matrix[] value){
        pos = position;
        val = value;
    }

    public Matrix[] getPosition(){
        return pos;
    }
    public Matrix[] getValue(){
        return val;
    }
}
