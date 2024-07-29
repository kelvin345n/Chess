package board;

import FrameworkML.Matrix;
import FrameworkML.Operations;
import bots.AdvRand;
import bots.RandomBot;
import engineStuff.PositionEncoder;
import pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Launcher {
    public static void main(String[] strings){
//        GamePlayerVsPlayer pvp = new GamePlayerVsPlayer();
//        GamePlayerVsBot pvb = new GamePlayerVsBot(true, new RandomBot());
        GameBotVsBot bvb = new GameBotVsBot(new AdvRand(), new AdvRand());

//
//        Game g = new Game();
//        g.move("e2 -> e4");
//        g.move("e7 -> e5");
//        g.move("e1 -> e2");
//        g.move("d8 -> h4");
//        g.move("g1 -> f3");
//        g.move("f8 -> c5");
//        g.move("b2 -> b4");
//        g.move("g8 -> f6");
//        g.move("c2 -> c4");
//        g.move("h8 -> g8");
//        g.move("d1 -> c2");
//
//        Game n = new Game(new ChessBoard(), g.getGamelogic().movesList());
//        ChessBoardDrawer d = new ChessBoardDrawer(n.getGamelogic().getChessBoard(), true);
//        System.out.println(n.getGamelogic().getPositionString().equals(n.getGamelogic().getPositionString()));
//
//        Matrix[] m = PositionEncoder.encode(g);
//        int count = 0;
//        for (Matrix a : m){
//            System.out.println(count++);
//            Operations.printMatrix(a);
//        }

    }
}
