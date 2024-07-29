package tests;

import board.*;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.util.ArrayList;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class GameTester {

    @Test
    public void tempMoveTest(){

        ChessBoard cbb = new ChessBoard();
        Game gg = new Game(cbb, new ArrayList<>());
        gg.move("b2 -> b4");
        assertThat(cbb.getEnPassant()).isEqualTo("b4");
        gg.tempMove("g7 -> g5");
        assertThat(Piece.isPawn(cbb.getPieceAt("g5"))).isEqualTo(true);
        assertThat(cbb.getEnPassant()).isEqualTo("g5");
        gg.reverseMove();
        assertThat(Piece.isPawn(cbb.getPieceAt("g7"))).isEqualTo(true);
        assertThat(cbb.getEnPassant()).isEqualTo("b4");
        gg.move("g7 -> g5");
        gg.move("c1 -> b2");
        gg.move("g5 -> g4");
        assertThat(cbb.getEnPassant()).isEqualTo("");
        gg.tempMove("h2 -> h4");
        assertThat(cbb.getEnPassant()).isEqualTo("h4");
        assertThat(Piece.isPawn(cbb.getPieceAt("h4"))).isEqualTo(true);
        gg.reverseMove();
        assertThat(cbb.getEnPassant()).isEqualTo("");
        assertThat(Piece.isPawn(cbb.getPieceAt("h2"))).isEqualTo(true);
        assertThat(Piece.isPawn(cbb.getPieceAt("h4"))).isEqualTo(false);
        gg.move("h2 -> h4");
        assertThat(cbb.getEnPassant()).isEqualTo("h4");
        gg.tempMove("g4 -> h3");
        assertThat(cbb.getEnPassant()).isEqualTo("");
        assertThat(Piece.isPawn(cbb.getPieceAt("h3"))).isEqualTo(true);
        assertThat(Piece.isPawn(cbb.getPieceAt("g4"))).isEqualTo(false);
        assertThat(Piece.isPawn(cbb.getPieceAt("h4"))).isEqualTo(false);
        gg.reverseMove();
        assertThat(cbb.getEnPassant()).isEqualTo("h4");
        assertThat(Piece.isPawn(cbb.getPieceAt("h3"))).isEqualTo(false);
        assertThat(Piece.isPawn(cbb.getPieceAt("g4"))).isEqualTo(true);
        assertThat(Piece.isPawn(cbb.getPieceAt("h4"))).isEqualTo(true);
        assertThat(gg.move("g4 -> h3")).isEqualTo(true);

        Game g = new Game();
        ChessBoard cb = g.getGamelogic().getChessBoard();
        cb.setPieceAt(new Nothing(true), "d1");
        cb.setPieceAt(new Nothing(true), "c1");
        cb.setPieceAt(new Nothing(true), "b1");
        g.tempMove("e1 -> c1");
        assertThat(Piece.isKing(cb.getPieceAt("c1"))).isEqualTo(true);
        assertThat(Piece.isRook(cb.getPieceAt("d1"))).isEqualTo(true);
        g.reverseMove();
        assertThat(Piece.isKing(cb.getPieceAt("e1"))).isEqualTo(true);
        assertThat(Piece.isRook(cb.getPieceAt("a1"))).isEqualTo(true);
        assertThat(g.move("e1 -> c1")).isEqualTo(true);


        g = new Game();
        cb = g.getGamelogic().getChessBoard();
        ChessBoardDrawer dr = new ChessBoardDrawer(cb, true);
        cb.setPieceAt(new Nothing(true), "d8");
        cb.setPieceAt(new Nothing(true), "c8");
        cb.setPieceAt(new Nothing(true), "b8");
        g.move("e2 -> e4");
        g.tempMove("e8 -> c8");
        assertThat(Piece.isKing(cb.getPieceAt("c8"))).isEqualTo(true);
        assertThat(Piece.isRook(cb.getPieceAt("d8"))).isEqualTo(true);
        g.reverseMove();
        assertThat(Piece.isKing(cb.getPieceAt("e8"))).isEqualTo(true);
        assertThat(Piece.isRook(cb.getPieceAt("a8"))).isEqualTo(true);
        assertThat(g.move("e8 -> c8")).isEqualTo(true);

        g = new Game();
        cb = g.getGamelogic().getChessBoard();
        g.tempMove("e2 -> e4");
        assertThat(cb.getEnPassant()).isEqualTo("e4");
        g.reverseMove();
        assertThat(cb.getPieceAt("e2").isMoved()).isEqualTo(false);
        assertThat(cb.getEnPassant()).isEqualTo("");
        g.move("e2 -> e4");
        assertThat(cb.getEnPassant()).isEqualTo("e4");
        assertThat(cb.getPieceAt("e4").isMoved()).isEqualTo(true);

        cb = new ChessBoard();
        cb.setPieceAt(new Pawn(true), "g7");
        g = new Game(cb, new ArrayList<>());
        g.tempMove("g7 -> h8=q");
        assertThat(Piece.isQueen(cb.getPieceAt("h8"))).isEqualTo(true);
        assertThat(cb.getPieceAt("h8").isWhiteTeam()).isEqualTo(true);
        g.reverseMove();
        assertThat(Piece.isRook(cb.getPieceAt("h8"))).isEqualTo(true);
        assertThat(cb.getPieceAt("h8").isWhiteTeam()).isEqualTo(false);
        assertThat(Piece.isPawn(cb.getPieceAt("g7"))).isEqualTo(true);


        g = new Game();
        cb = g.getGamelogic().getChessBoard();
        g.move("e2 -> e4");
        g.move("g8 -> f6");
        g.move("e4 -> e5");
        g.move("d7 -> d5");
        assertThat(cb.getEnPassant()).isEqualTo("d5");
        g.tempMove("e5 -> d6");
        assertThat(cb.getEnPassant()).isEqualTo("");
        assertThat(Piece.isPawn(cb.getPieceAt("d6"))).isEqualTo(true);
        assertThat(cb.getPieceAt("d6").isWhiteTeam()).isEqualTo(true);
        g.reverseMove();
        assertThat(cb.getEnPassant()).isEqualTo("d5");
        assertThat(Piece.isPawn(cb.getPieceAt("e5"))).isEqualTo(true);
        assertThat(cb.getPieceAt("e5").isWhiteTeam()).isEqualTo(true);

        g = new Game();
        cb = g.getGamelogic().getChessBoard();
        g.move("e2 -> e4");
        g.move("d7 -> d5");
        g.move("f1 -> e2");
        g.move("c8 -> e6");
        g.move("g1 -> f3");
        g.move("d8 -> d6");
        g.tempMove("e1 -> g1");
        assertThat(Piece.isKing(cb.getPieceAt("g1"))).isEqualTo(true);
        assertThat(Piece.isRook(cb.getPieceAt("f1"))).isEqualTo(true);
        g.reverseMove();
        assertThat(Piece.isKing(cb.getPieceAt("e1"))).isEqualTo(true);
        assertThat(Piece.isRook(cb.getPieceAt("h1"))).isEqualTo(true);
        g.move("e1 -> g1");
        assertThat(Piece.isKing(cb.getPieceAt("g1"))).isEqualTo(true);
        assertThat(Piece.isRook(cb.getPieceAt("f1"))).isEqualTo(true);

        g.tempMove("g8 -> f6");
        g.reverseMove();
        g.move("g8 -> f6");
        g.move("h2 -> h3");
        g.move("f6 -> g4");
        g.tempMove("g1 -> h1");
        g.reverseMove();
        g.move("f3 -> e1");
        assertThat(g.isGameOver()).isEqualTo(false);
        g.tempMove("d6 -> h2");
        assertThat(g.isGameOver()).isEqualTo(true);
        assertThat(g.getPoints(true)).isEqualTo(0f);
        assertThat(g.getPoints(false)).isEqualTo(1f);
        g.reverseMove();
        assertThat(g.isGameOver()).isEqualTo(false);
        assertThat(g.getPoints(true)).isEqualTo(0f);
        assertThat(g.getPoints(false)).isEqualTo(0f);

// Smothered Mate
        Game game = new Game();
        game.move("e2 -> e4");
        game.move("c7 -> c6");
        game.move("d2 -> d4");
        game.move("d7 -> d5");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.move("b1 -> c3");
        game.move("d5 -> e4");
        game.move("c3 -> e4");
        game.move("b8 -> d7");
        game.move("d1 -> e2");
        game.move("g8 -> f6");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.tempMove("e4 -> d6");
        assertThat(game.tempGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(1f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.reverseMove();
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);

        ChessBoard b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(true), "e2");
        b.setPieceAt(new King(false), "e5");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        b.setPieceAt(new Pawn(false), "e3");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.tempMove("e2 -> e3");
        assertThat(Piece.isKing(b.getPieceAt("e3"))).isEqualTo(true);
        assertThat(game.tempGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        game.reverseMove();
        assertThat(Piece.isPawn(b.getPieceAt("e3"))).isEqualTo(true);
        assertThat(Piece.isKing(b.getPieceAt("e2"))).isEqualTo(true);
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);

        // 3-fold rep
        game = new Game();
        game.move("e2 -> e4");
        game.move("g7 -> g6");
        game.move("e4 -> e5");
        game.move("d7 -> d5");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> e2");
        game.move("c8 -> d7");
        game.move("e2 -> f1");
        game.move("d7 -> c8");
        game.move("f1 -> e2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("c8 -> d7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e2 -> f1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d7 -> c8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.tempMove("f1 -> e2");
        assertThat(game.tempGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        game.reverseMove();
        assertThat(game.isGameOver()).isEqualTo(false);
        game.tempMove("f1 -> e2");
        assertThat(game.tempGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        game.reverseMove();
        assertThat(game.isGameOver()).isEqualTo(false);
        game.tempMove("f1 -> e2");
        assertThat(game.tempGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        game.reverseMove();
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> e2");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
    }

    @Test
    public void pawnPromotionTest(){
        Game game = new Game();
        ChessBoard b = game.getGamelogic().getChessBoard();
        b.setPieceAt(new Pawn(true), "a7");
        b.setPieceAt(new Pawn(false), "b2");
        boolean move = game.move("a7 -> a8");
        assertThat(move).isEqualTo(false);
        move = game.move("a7 -> b8");
        assertThat(move).isEqualTo(false);
        move = game.move("a7 -> b8=n");
        assertThat(move).isEqualTo(true);
        assertThat(Piece.isKnight(b.getPieceAt("b8")));

        move = game.move("b2 -> a1=q");
        assertThat(move).isEqualTo(true);
        assertThat(Piece.isQueen(b.getPieceAt("a1")));

        move = game.move("e2 -> e4=q");
        assertThat(move).isEqualTo(false);
        assertThat(!b.getPieceAt("e4").isPiece());

        move = game.move("g1 -> f6=q");
        assertThat(move).isEqualTo(false);
    }

    @Test
    public void enPassantTest(){
        Game game = new Game();
        ChessBoard cb = game.getGamelogic().getChessBoard();
        game.move("e2 -> e4");
        game.move("f7 -> f5");
        game.move("e4 -> e5");
        game.move("d7 -> d5");
        game.move("e5 -> f6");
        game.move("e5 -> d6");
        assertThat(Piece.isPawn(cb.getPieceAt("f6"))).isEqualTo(false);
        assertThat(Piece.isPawn(cb.getPieceAt("d6"))).isEqualTo(true);
        assertThat(Piece.isPawn(cb.getPieceAt("d5"))).isEqualTo(false);
    }

    @Test
    public void kingMoveTest(){
        ChessBoard b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(false), "a2");
        b.setPieceAt(new King(true), "f8");
        b.setPieceAt(new Queen(false), "g2");
        Game game = new Game(b, new ArrayList<>());
        assertThat(game.move("f8 -> g8")).isEqualTo(false);

        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(false), "c8");
        b.setPieceAt(new King(true), "h2");
        b.setPieceAt(new Rook(true), "h6");
        game = new Game(b, new ArrayList<>());
        assertThat(game.move("h6 -> h8")).isEqualTo(true);
        boolean move = game.move("c8 -> b8");

        assertThat(move).isEqualTo(false);


    }

    @Test
    public void isGameOverTest(){
        // Basic tests on a new board
        Game game = new Game();
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e2 -> e4");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);

        // Tests on checkmate.
        game = new Game();
        game.move("f2 -> f3");
        game.move("e7 -> e5");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g2 -> g4");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d8 -> h4");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(1f);
        // New test
        ChessBoard b = new ChessBoard();
        b.setPieceAt(new Queen(true), "f3");
        game = new Game(b, new ArrayList<>());
        game.move("f3 -> f7");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        // n
        b = new ChessBoard();
        b.setPieceAt(new Queen(true), "f3");
        b.setPieceAt(new Queen(true), "f4");
        game = new Game(b, new ArrayList<>());
        game.move("f4 -> f7");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(1f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        // Smothered Mate
        game = new Game();
        game.move("e2 -> e4");
        game.move("c7 -> c6");
        game.move("d2 -> d4");
        game.move("d7 -> d5");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.move("b1 -> c3");
        game.move("d5 -> e4");
        game.move("c3 -> e4");
        game.move("b8 -> d7");
        game.move("d1 -> e2");
        game.move("g8 -> f6");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.move("e4 -> d6");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(1f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        // Englund Gambit mate
        game = new Game();
        game.move("d2 -> d4");
        game.move("e7 -> e5");
        game.move("d4 -> e5");
        game.move("d8 -> e7");
        game.move("g1 -> f3");
        game.move("b8 -> c6");
        game.move("c1 -> f4");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.move("e7 -> b4");
        game.move("f4 -> d2");
        game.move("b4 -> b2");
        game.move("d2 -> c3");
        game.move("f8 -> b4");
        game.move("d1 -> d2");
        game.move("b4 -> c3");
        game.move("d2 -> c3");
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.move("b2 -> c1");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(1f);

        // Tests on insufficient material.
        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(true), "e2");
        b.setPieceAt(new King(false), "e5");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        b.setPieceAt(new Pawn(false), "e3");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        game.move("e2 -> e3");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        b.setPieceAt(new Knight(true), "a1");
        b.setPieceAt(new Bishop(false), "h8");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        b.setPieceAt(new Knight(true), "a2");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);

        b.setPieceAt(new Pawn(false), "a2");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);

        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(true), "e2");
        b.setPieceAt(new King(false), "e5");
        b.setPieceAt(new Rook(false), "e3");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);

        b.setPieceAt(new Bishop(true), "e3");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        b.setPieceAt(new Pawn(true), "e4");
        game = new Game(b, new ArrayList<>());
        boolean over = game.isGameOver();
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);

        // More insufficient material tests
        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(true), "e4");
        b.setPieceAt(new King(false), "e7");

        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        b.setPieceAt(new Knight(true), "a2");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        b.setPieceAt(new Bishop(true), "a3");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);
        // Lone king versus two knights is draw. But has to be lone king
        b.setPieceAt(new Knight(true), "a3");
        game = new Game(b, new ArrayList<>());
        game.isGameOver();
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        b.setPieceAt(new Bishop(false), "h8");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        assertThat(game.getPoints(true)).isEqualTo(0f);
        assertThat(game.getPoints(false)).isEqualTo(0f);


        // Tests on stalemate
        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        // https://www.youtube.com/watch?v=qWVnm9AgxOU
        b.setPieceAt(new King(false), "h5");
        b.setPieceAt(new Pawn(true), "h4");
        b.setPieceAt(new Bishop(true), "g5");
        b.setPieceAt(new Pawn(false), "g6");
        b.setPieceAt(new Rook(true), "d4");
        b.setPieceAt(new Knight(false), "c5");
        b.setPieceAt(new Pawn(true), "b2");
        b.setPieceAt(new Pawn(true), "b3");
        b.setPieceAt(new King(true), "a3");
        b.setPieceAt(new Pawn(true), "a4");
        b.setPieceAt(new Pawn(false), "a5");
        game = new Game(b, new ArrayList<>());
        game.move("b3 -> b4");
        game.move("c5 -> e4");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d4 -> e4");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("a5 -> b4");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("a3 -> a2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("b4 -> b3");
        // Invalid move b/c king is in check.
        boolean move = game.move("g5 -> d8");
        assertThat(move).isEqualTo(false);
        assertThat(game.isGameOver()).isEqualTo(false);
        // Valid move but black has no more valid moves.
        game.move("a2 -> b1");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        // More stalemate tests
        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(true), "h7");
        b.setPieceAt(new King(false), "g5");
        b.setPieceAt(new Rook(true), "h6");
        b.setPieceAt(new Rook(false), "g4");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("h7 -> h8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g5 -> h6");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        // More stalemate tests
        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(true), "g1");
        b.setPieceAt(new Queen(false), "f8");
        b.setPieceAt(new King(false), "g8");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g1 -> h1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f8 -> f2");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        // More stalemate tests
        b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Replace all pieces with nothing.
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }

        b.setPieceAt(new King(true), "a1");
        b.setPieceAt(new Rook(true), "a2");
        b.setPieceAt(new King(false), "g1");
        b.setPieceAt(new Knight(true), "h5");
        game = new Game(b, new ArrayList<>());
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("h5 -> g3");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        // Tests on three-fold repetition
        game = new Game();
        game.move("e2 -> e4");
        game.move("d7 -> d5");
        game.move("e1 -> e2");
        game.move("d8 -> d7");
        game.move("e2 -> e1");
        game.move("d7 -> d8");
        game.move("e1 -> e2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d8 -> d7");
        game.move("e2 -> e1");
        game.move("d7 -> d8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e1 -> e2");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        // More 3-fold repetition tests
        game = new Game();
        game.move("g1 -> f3");
        game.move("b8 -> c6");
        game.move("h2 -> h4");
        game.move("a7 -> a5");
        game.move("f3 -> g1");
        game.move("c6 -> b8");
        game.move("g1 -> f3");
        game.move("b8 -> c6");
        game.move("f3 -> g1");
        game.move("c6 -> b8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g1 -> f3");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("b8 -> c6");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        // More 3-fold repetition tests
        game = new Game();
        game.move("e2 -> e3");
        game.move("e7 -> e6");
        game.move("e1 -> e2");
        game.move("e8 -> e7");
        game.move("e2 -> e1");
        game.move("e7 -> e8");
        game.move("e1 -> e2");
        game.move("e8 -> e7");
        game.move("e2 -> e1");
        game.move("e7 -> e8");
        game.move("e1 -> e2");
        game.move("e8 -> e7");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        // More 3-fold rep tests
        game = new Game();
        game.move("e2 -> e3");
        game.move("e7 -> e6");
        game.move("d2 -> d3");
        game.move("d7 -> d6");
        game.move("e1 -> e2");
        game.move("e8 -> e7");
        game.move("e2 -> e1");
        game.move("e7 -> e8");
        game.move("e1 -> d2");
        game.move("e8 -> e7");
        game.move("d2 -> e1");
        game.move("e7 -> e8");
        game.move("e1 -> e2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e8 -> d7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e2 -> e1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d7 -> e8");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
        // More 3-fold repetition tests
        game = new Game();
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("c2 -> c4");
        game.move("g7 -> g6");
        game.move("b1 -> c3");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f8 -> g7");
        game.move("e2 -> e4");
        game.move("g8 -> f6");
        game.move("g1 -> f3");
        game.move("e8 -> g8");
        game.move("f3 -> g5");
        game.move("h7 -> h6");
        game.move("h2 -> h4");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("h6 -> g5");
        game.move("d1 -> f3");
        game.move("f8 -> e8");
        game.move("h4 -> g5");
        game.move("e7 -> e5");
        game.move("d2 -> d3");
        game.move("d7 -> d6");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("c1 -> e3");
        game.move("d8 -> d7");
        // Move 11
        game.move("h1 -> h6");
        game.move("g8 -> f8");
        // M12
        game.move("h6 -> h1");
        game.move("f8 -> g8");
        //M13
        game.move("h1 -> h6");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g8 -> f8");
        // M14
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("h6 -> h1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f8 -> g8");

        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f3 -> e2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d7 -> e6");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e2 -> f3");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e6 -> d7");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        // More 3-fold repetition tests
        game = new Game();
        game.move("e2 -> e4");
        game.move("g7 -> g6");
        game.move("e4 -> e5");
        game.move("d7 -> d5");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> e2");
        game.move("c8 -> d7");
        game.move("e2 -> f1");
        game.move("d7 -> c8");
        game.move("f1 -> e2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("c8 -> d7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e2 -> f1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d7 -> c8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> e2");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        // More three-fold repetition tests
        game = new Game();
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g2 -> g3");
        game.move("g7 -> g6");
        game.move("f2 -> f4");
        game.move("f8 -> g7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> g2");
        game.move("g7 -> f8");
        game.move("g2 -> f1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f8 -> g7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> g2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g7 -> f8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g2 -> f1");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);

        // More 3-fold rep tests
        game = new Game();
        game.move("g2 -> g3");
        game.move("g7 -> g6");
        game.move("f1 -> g2");
        game.move("f8 -> g7");
        game.move("d2 -> d3");
        game.move("d7 -> d6");
        game.move("g2 -> f1");
        game.move("g7 -> f8");
        game.move("f1 -> g2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f8 -> g7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g2 -> f1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g7 -> f8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e1 -> d2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("e8 -> d7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d2 -> e1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("d7 -> e8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> g2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f8 -> g7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g2 -> f1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g7 -> f8");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f1 -> g2");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("f8 -> g7");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g2 -> f1");
        assertThat(game.isGameOver()).isEqualTo(false);
        game.move("g7 -> f8");
        assertThat(game.isGameOver()).isEqualTo(true);
        assertThat(game.getPoints(true)).isEqualTo(0.5f);
        assertThat(game.getPoints(false)).isEqualTo(0.5f);
    }
}
