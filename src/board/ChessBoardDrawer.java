package board;

import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;

/** This class draws the chess board from ChessBoard */
public class ChessBoardDrawer {
    // Each square tile on the board will be 50 pixels by 50 pixels
    private static final int TILE_SIZE = 60;
    // 8 square tiles vertically and horizontally in a chess board
    private static final int TILE_COUNT = 8;
    // Should the bottom of the board be white? true or false.
    private boolean isWhite;

    private static final Color backgroundColor = new Color(62, 35, 16);
    private static final Color penColor = new Color(86, 73, 86);

    private ChessBoard board;

    public ChessBoardDrawer(ChessBoard board, boolean isWhite){
        this.isWhite = isWhite;
        this.board = board;
        initialize(board);
    }

    public void initialize(ChessBoard board) {
        Tile[][] tileBoard = board.getBoard();
        // TILE_SIZE * 2 represents the border around the board
        StdDraw.setCanvasSize((TILE_SIZE * 2) + TILE_COUNT * TILE_SIZE,
                (TILE_SIZE * 2) + TILE_COUNT * TILE_SIZE);
        Font font = new Font("Monaco", Font.PLAIN, TILE_SIZE-10);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, TILE_COUNT + 2); // Add two because the outer tiles are the border
        StdDraw.setYscale(0, TILE_COUNT + 2); // 0 to 1 = border, 1 to 2 = 1st tile

        // Background Color
        StdDraw.clear(backgroundColor);

        for (int x = 0; x < TILE_COUNT; x++) {
            // Draws the letters on the side
            char letter;
            if (isWhite){
                letter = (char)('A' + x);
            } else {
                letter = (char)('H' - x);
            }
            StdDraw.setPenColor(penColor);
            StdDraw.text(x + 1 + 0.5, 0.4, Character.toString(letter));

            for (int y = 0; y < TILE_COUNT; y++) {
                // Draws the actual chess board
                if (isWhite){
                    tileBoard[x][y].draw(x + 1, y + 1);
                } else {
                    tileBoard[x][y].draw(TILE_COUNT - x, TILE_COUNT - y);
                }
            }
        }

        // Draws the numbers on the side of the board
        for (int y = 0; y < TILE_COUNT; y++){
            char number;
            if (isWhite){
                number = (char)('1' + y);
            } else {
                number = (char)('8' - y);
            }
            StdDraw.setPenColor(penColor);
            StdDraw.text(0.5, y + 1.4, Character.toString(number));
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    public void draw(int pauseMs){
        Tile[][] tileBoard = board.getBoard();
        // Background Color
        StdDraw.clear(backgroundColor);

        for (int x = 0; x < TILE_COUNT; x++) {
            // Draws the letters on the side
            char letter;
            if (isWhite){
                letter = (char)('A' + x);
            } else {
                letter = (char)('H' - x);
            }
            StdDraw.setPenColor(penColor);
            StdDraw.text(x + 1 + 0.5, 0.4, Character.toString(letter));

            for (int y = 0; y < TILE_COUNT; y++) {
                // Draws the actual chess board
                if (isWhite){
                    tileBoard[x][y].draw(x + 1, y + 1);
                } else {
                    tileBoard[x][y].draw(TILE_COUNT - x, TILE_COUNT - y);
                }
            }
        }

        // Draws the numbers on the side of the board
        for (int y = 0; y < TILE_COUNT; y++){
            char number;
            if (isWhite){
                number = (char)('1' + y);
            } else {
                number = (char)('8' - y);
            }
            StdDraw.setPenColor(penColor);
            StdDraw.text(0.5, y + 1.4, Character.toString(number));
        }
        StdDraw.show();
        StdDraw.pause(pauseMs);
    }
    public void draw(){
        Tile[][] tileBoard = board.getBoard();
        // Background Color
        StdDraw.clear(backgroundColor);

        for (int x = 0; x < TILE_COUNT; x++) {
            // Draws the letters on the side
            char letter;
            if (isWhite){
                letter = (char)('A' + x);
            } else {
                letter = (char)('H' - x);
            }
            StdDraw.setPenColor(penColor);
            StdDraw.text(x + 1 + 0.5, 0.4, Character.toString(letter));

            for (int y = 0; y < TILE_COUNT; y++) {
                // Draws the actual chess board
                if (isWhite){
                    tileBoard[x][y].draw(x + 1, y + 1);
                } else {
                    tileBoard[x][y].draw(TILE_COUNT - x, TILE_COUNT - y);
                }
            }
        }

        // Draws the numbers on the side of the board
        for (int y = 0; y < TILE_COUNT; y++){
            char number;
            if (isWhite){
                number = (char)('1' + y);
            } else {
                number = (char)('8' - y);
            }
            StdDraw.setPenColor(penColor);
            StdDraw.text(0.5, y + 1.4, Character.toString(number));
        }
        StdDraw.show();
    }
}