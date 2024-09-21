package board;

import FrameworkML.Matrix;
import FrameworkML.NeuralNet2;
import FrameworkML.NeuralNetReader;
import FrameworkML.Operations;
import bots.Abby;
import bots.AdvRand;
import bots.AlphaBota;
import bots.RandomBot;
import edu.princeton.cs.algs4.Alphabet;
import engineStuff.PositionEncoder;
import pieces.Nothing;
import pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Launcher {
    public static void main(String[] strings){
//        GamePlayerVsPlayer pvp = new GamePlayerVsPlayer();
        GameBotVsBot bvb = new GameBotVsBot(new RandomBot(), new RandomBot());

//        GamePlayerVsBot pvb = new GamePlayerVsBot(true, new AdvRand());
//        NeuralNet2 alulu = NeuralNetReader.loadNetwork("Aluluuuuuuu");
//        NeuralNet2 alulu2 = NeuralNetReader.loadNetwork("Aluluuuu");
//        GameBotVsBot bvb = new GameBotVsBot(new AlphaBota(alulu, 2, 0f), new AlphaBota(alulu2, 2, 0f));
////
//        GamePlayerVsBot pvb = new GamePlayerVsBot(false, new AlphaBota(alulu, 3, 0f));
//        GameBotVsBot no = new GameBotVsBot(new AdvRand(), new AlphaBota(alulu, 1, 0.1f));

//        ChessBoard cb = new ChessBoard();
//        for (int i = 0; i < 8; i++){
//            for (int j = 0; j < 8; j++){
//                cb.setPieceAt(new Nothing(true), cb.convertToIndex(j, i));
//            }
//        }
//        NeuralNet2 abby = NeuralNetReader.loadNetwork("Alulu");
//        Game g = new Game(cb, new ArrayList<>());
//        Matrix[] p = PositionEncoder.encode(g);
//
//        for (Matrix s : p){
//            Operations.printMatrix(s);
//            System.out.println();
//        }
//
//        Matrix[] infer = abby.inference(p);
//        for (Matrix s : infer){
//            Operations.printMatrix(s);
//            System.out.println();
//        }
//        g = new Game();
//        System.out.println();
//        System.out.println();
//        p = PositionEncoder.encode(g);
//        for (Matrix s : p){
//            Operations.printMatrix(s);
//            System.out.println();
//        }
//        infer = abby.inference(p);
//        for (Matrix s : infer){
//            Operations.printMatrix(s);
//            System.out.println();
//        }
//
//        g.move("e2 -> e4");
//        g.move("d7 -> d5");
//        g.move("e1 -> e2");
//        System.out.println();
//        System.out.println();
//        p = PositionEncoder.encode(g);
//        for (Matrix s : p){
//            Operations.printMatrix(s);
//            System.out.println();
//        }
//        infer = abby.inference(p);
//        for (Matrix s : infer){
//            Operations.printMatrix(s);
//            System.out.println();
//        }


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

    public static void changeStr(String s){
        s = "s";
    }

}
