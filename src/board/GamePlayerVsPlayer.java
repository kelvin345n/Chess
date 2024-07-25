package board;

import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GamePlayerVsPlayer {

    public GamePlayerVsPlayer(ChessBoard b, List<String> moves, boolean orientWhite){
        Game g = new Game(b, moves);
        Gamelogic gl = g.getGamelogic();
        ChessBoardDrawer drawer = new ChessBoardDrawer(b, orientWhite);
        Scanner scanner = new Scanner(System.in);
        while(!g.isGameOver()){
            drawer.draw();
            String color = "Black";
            if (gl.isWhiteTurn()){
                color = "White";
            }
            System.out.println(color + "'s turn to move. Enter here: ");
            String move = scanner.nextLine().toLowerCase();
            while (!g.move(move)){
                System.out.println("Still " + color + "'s turn. Enter move: ");
                move = scanner.nextLine().toLowerCase();
            }
        }
        drawer.draw();
        scanner.close();  // Close the scanner
        System.out.println("Game Over!");
    }
    public GamePlayerVsPlayer(){
        this(new ChessBoard(), new ArrayList<>(), true);
    }
}