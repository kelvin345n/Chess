package board;

import bots.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameBotVsBot {
    public GameBotVsBot(ChessBoard b, List<String> moves, boolean orientWhite, Bot oneBot, Bot twoBot){
        Game g = new Game(b, moves);
        Gamelogic gl = g.getGamelogic();
        ChessBoardDrawer drawer = new ChessBoardDrawer(b, orientWhite);
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        boolean oneBotIsWhite;
        Random rand = new Random();
        if (rand.nextInt(2) == 1){
            oneBotIsWhite = true;
        } else {
            oneBotIsWhite = false;
        }
        while(!g.isGameOver()){
            drawer.draw();
            String color = "Black";
            if (gl.isWhiteTurn()){
                color = "White";
            }
            System.out.println(color + "'s turn to move.");
            String move;
            if (gl.isWhiteTurn() == oneBotIsWhite){
                move = oneBot.nextMove(g);
            } else {
                move = twoBot.nextMove(g);
            }
            g.move(move);
            count++;
            System.out.println(color + " made the move: " + move);
        }
        drawer.draw();
        scanner.close();  // Close the scanner
        System.out.println("Game Over! " + count + " moves made" );
    }
    public GameBotVsBot(Bot oneBot, Bot twoBot){
        this(new ChessBoard(), new ArrayList<>(), true, oneBot, twoBot);
    }

}
