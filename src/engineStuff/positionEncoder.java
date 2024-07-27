package engineStuff;
import Ep2.FrameworkML.NeuralNet2;
import Ep2.FrameworkML.Matrix;
import board.*;
/** Used to encode a position of a current chess game into a tensor to use
 * in neural network training. */
public class positionEncoder {

    /** Given a Game we encode the current position into a 10x10x16 tensor
     *
     * Layer 1: current color's one-hot encoding of pawns
     * Layer 2: curr color one-hot of knights
     * Layer 3: curr color one-hot of bishops
     * Layer 4: curr color one-hot of rooks
     * Layer 5: curr color 1-hot of queen
     * Layer 6: curr color 1-hot of king
     * Layer 7: curr color castling privileges and en-passant squares
     *          - bottom row will right side will either be 0000, or 1111 depending if right side castling is possible same as left side.
     *          - since en passant can't be on the bottom row, then it won't interfere with this.
     * Layer 8: curr color squares it is attacking
     * */
    public static Matrix[] encode(Game chessGame){
        Gamelogic gl = chessGame.getGamelogic();
        board.ChessBoard b = gl.getChessBoard();

        return new Matrix[1];
    }

}
