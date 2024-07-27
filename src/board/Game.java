package board;


import org.checkerframework.checker.units.qual.C;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/** This class runs the game and determines the winner. Such as checkmate, or
 * draw due to insufficient material. */
public class Game {
    private Gamelogic gamelogic;
    // You get 1 point for a win and 0.5 for a loss.
    float whitePoints;
    float blackPoints;

    public Game(ChessBoard cb, List<String> moves){
        whitePoints = 0f;
        blackPoints = 0f;
        gamelogic = new Gamelogic(cb, moves);
    }
    public Game(){
        this(new ChessBoard(), new ArrayList<>());
    }

    /** When given points for both white and black, sets those as for each respective
     * player */
    private void setPoints(boolean forWhite, float points){
        if (forWhite){
            whitePoints = points;
        } else {
            blackPoints = points;
        }
    }
    /** When given a color, returns the points that color has so far. */
    public float getPoints(boolean forWhite){
        if (forWhite){
            return whitePoints;
        }
        return blackPoints;
    }
    /** Given a string of the wanted move in the format "e2 -> e4". Makes
     * that move on the chessboard */
    public boolean move(String move){
        return gamelogic.movePiece(move);
    }
    /** Sets the color to make the next move */
    public void setTurn(boolean whiteTurn){
        gamelogic.setTurn(whiteTurn);
    }

    /** Checks if the game is over in its current state. */
    public boolean isGameOver(){
        boolean whiteTurn = gamelogic.isWhiteTurn();
        String color = "Black";
        String otherColor = "White";
        if (whiteTurn) {
            color = "White";
            otherColor = "Black";
        }

        // Insufficient material
        ChessBoard b = gamelogic.getChessBoard();
        if (b.getPieceScore(true) < 4 && b.getPieceScore(false) < 4){
            if (b.getPieceScore(true) == 2 && b.getPieceScore(false) > 0){
                // White has two knights vs king and anything else means no draw.
            } else if (b.getPieceScore(false) == 2 && b.getPieceScore(true) > 0) {

            } else {
                setPoints(whiteTurn, 0.5f);
                setPoints(!whiteTurn, 0.5f);
                System.out.println("Insufficient material. Game ends in a draw!");
                return true;
            }
        }

        // 3-fold repetition
        String currPosition = gamelogic.getPositionString();
        int counter = gamelogic.getPositionsMap().get(currPosition);
        if (counter == 3){
            setPoints(whiteTurn, 0.5f);
            setPoints(!whiteTurn, 0.5f);
            System.out.println("Three-fold repetition. Game ends in a draw!");
            return true;
        }

        // The color up to move has no moves to make.
        if (gamelogic.allPossibleMoves(whiteTurn).isEmpty()){
            if (gamelogic.getChessBoard().isKingChecked(whiteTurn)){
                // Checkmate
                setPoints(whiteTurn, 0f);
                setPoints(!whiteTurn, 1f);
                System.out.println(color + " has been checkmated. " + otherColor + " wins!");
            } else {
                // Stalemate
                setPoints(whiteTurn, 0.5f);
                setPoints(!whiteTurn, 0.5f);
                System.out.println("Stalemate. Game ends in a draw!");
            }
            return true;
        }
        return false;
    }

    public Gamelogic getGamelogic(){
        return gamelogic;
    }
}
