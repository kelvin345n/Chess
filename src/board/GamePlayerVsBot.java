package board;

import bots.Bot;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GamePlayerVsBot {

    public GamePlayerVsBot(ChessBoard b, List<String> moves, boolean orientWhite, boolean isPlayerWhite, Bot bot){
        Game g = new Game(b, moves);
        Gamelogic gl = g.getGamelogic();
        ChessBoardDrawer drawer = new ChessBoardDrawer(b, orientWhite);
        Scanner scanner = new Scanner(System.in);

        String playerColor;
        String botColor;
        if (isPlayerWhite){
            playerColor = "White";
            botColor = "Black";
        } else {
            playerColor = "Black";
            botColor = "White";
        }

        while(!g.isGameOver()){
            drawer.draw();
            if (gl.isWhiteTurn() == isPlayerWhite){
                System.out.println(playerColor + "'s turn to move. Enter here: ");
                String move = scanner.nextLine().toLowerCase();
                while (!g.move(move)){
                    System.out.println("Still " + playerColor + "'s turn. Enter move: ");
                    move = scanner.nextLine().toLowerCase();
                }
            } else {
                String botMove = bot.nextMove(g);
                System.out.println(botColor + " made the move: " + botMove);
                g.move(botMove);
            }
        }
        drawer.draw();
        scanner.close();  // Close the scanner
        System.out.println("Game Over!");
    }
    public GamePlayerVsBot(boolean isPlayerWhite, Bot bot){
        this(new ChessBoard(), new ArrayList<>(), isPlayerWhite, isPlayerWhite, bot);
    }
}