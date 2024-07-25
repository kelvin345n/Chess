package tests;

import board.ChessBoard;
import board.ChessBoardDrawer;
import board.Gamelogic;
import board.Tile;
import org.junit.jupiter.api.Test;
import pieces.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;


public class GamelogicTester {

    @Test
    public void doMoveTest(){
        Gamelogic g = new Gamelogic();
        ChessBoard b = g.getChessBoard();

        // En passant testing
        assertThat(b.getEnPassant()).isEqualTo("");
        g.doMove("h7", "h5");
        assertThat(b.getEnPassant()).isEqualTo("h5");
        g.doMove("h5", "h4");
        g.doMove("g2", "g4");

        assertThat(Piece.isPawn(b.getPieceAt("g4"))).isEqualTo(true);
        assertThat(b.getPieceAt("g2").isPiece()).isEqualTo(false);
        assertThat(b.getEnPassant()).isEqualTo("g4");
        g.doMove("h4", "g3");
        assertThat(Piece.isPawn(b.getPieceAt("g3"))).isEqualTo(true);
        assertThat(b.getPieceAt("h4").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("g4").isPiece()).isEqualTo(false);

        g.doMove("e2", "e4");
        assertThat(b.getEnPassant()).isEqualTo("e4");
        g.doMove("e4", "e5");
        assertThat(b.getEnPassant()).isEqualTo("");

        g.doMove("d7", "d5");
        assertThat(Piece.isPawn(b.getPieceAt("d5"))).isEqualTo(true);
        assertThat(Piece.isPawn(b.getPieceAt("e5"))).isEqualTo(true);
        g.doMove("e5", "d6");
        assertThat(Piece.isPawn(b.getPieceAt("d6"))).isEqualTo(true);
        assertThat(b.getPieceAt("d5").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("e5").isPiece()).isEqualTo(false);

        g.doMove("a1", "a2");
        assertThat(Piece.isRook(b.getPieceAt("a2"))).isEqualTo(true);
        assertThat(b.getPieceAt("a1").isPiece()).isEqualTo(false);

        g.doMove("b1", "b8");
        assertThat(b.getPieceAt("b1").isPiece()).isEqualTo(false);
        assertThat(Piece.isKnight(b.getPieceAt("b8"))).isEqualTo(true);

        g.doMove("h8", "a8");
        assertThat(Piece.isRook(b.getPieceAt("a8"))).isEqualTo(true);
        assertThat(b.getPieceAt("h8").isPiece()).isEqualTo(false);

        // Castling tests.
        g = new Gamelogic();
        b = g.getChessBoard();

        b.setPieceAt(new Nothing(true), "f1");
        b.setPieceAt(new Nothing(true), "g1");
        g.doMove("e1", "g1");

        assertThat(b.getPieceAt("e1").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("h1").isPiece()).isEqualTo(false);
        assertThat(Piece.isKing(b.getPieceAt("g1"))).isEqualTo(true);
        assertThat(Piece.isRook(b.getPieceAt("f1"))).isEqualTo(true);

        b.setPieceAt(new Nothing(true), "d8");
        b.setPieceAt(new Nothing(true), "c8");
        b.setPieceAt(new Nothing(true), "b8");

        g.doMove("e8", "c8");
        assertThat(b.getPieceAt("e8").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("a8").isPiece()).isEqualTo(false);
        assertThat(Piece.isKing(b.getPieceAt("c8"))).isEqualTo(true);
        assertThat(Piece.isRook(b.getPieceAt("d8"))).isEqualTo(true);

        g = new Gamelogic();
        b = g.getChessBoard();

        b.setPieceAt(new Nothing(true), "c1");
        b.setPieceAt(new Nothing(true), "d1");
        b.setPieceAt(new Nothing(true), "b1");
        g.doMove("e1", "c1");

        assertThat(b.getPieceAt("e1").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("a1").isPiece()).isEqualTo(false);
        assertThat(Piece.isKing(b.getPieceAt("c1"))).isEqualTo(true);
        assertThat(Piece.isRook(b.getPieceAt("d1"))).isEqualTo(true);

        b.setPieceAt(new Nothing(true), "g8");
        b.setPieceAt(new Nothing(true), "f8");

        g.doMove("e8", "g8");

        assertThat(b.getPieceAt("e8").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("h8").isPiece()).isEqualTo(false);
        assertThat(Piece.isKing(b.getPieceAt("g8"))).isEqualTo(true);
        assertThat(Piece.isRook(b.getPieceAt("f8"))).isEqualTo(true);

        // Pawn Promotion tests
        g = new Gamelogic();
        b = g.getChessBoard();

        b.setPieceAt(new Pawn(true), "b7");

        g.doMove("b7", "a8", "q");
        assertThat(Piece.isQueen(b.getPieceAt("a8"))).isEqualTo(true);
        assertThat(b.getPieceAt("a8").isWhiteTeam()).isEqualTo(true);
        assertThat(b.getPieceAt("b7").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("a7").isPiece()).isEqualTo(true);

        b.setPieceAt(new Pawn(false), "c2");
        g.doMove("c2", "b1", "n");

        assertThat(Piece.isKnight(b.getPieceAt("b1"))).isEqualTo(true);
        assertThat(b.getPieceAt("b1").isWhiteTeam()).isEqualTo(false);
        assertThat(b.getPieceAt("b2").isPiece()).isEqualTo(true);
        assertThat(b.getPieceAt("a2").isPiece()).isEqualTo(true);

        g = new Gamelogic();
        b = g.getChessBoard();

        b.setPieceAt(new Pawn(true), "b7");
        g.doMove("b7", "a8", "r");

        assertThat(Piece.isRook(b.getPieceAt("a8"))).isEqualTo(true);
        assertThat(b.getPieceAt("a8").isWhiteTeam()).isEqualTo(true);
        assertThat(b.getPieceAt("b7").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("a7").isPiece()).isEqualTo(true);

        b.setPieceAt(new Pawn(false), "c2");
        b.setPieceAt(new Nothing(true), "c1");
        g.doMove("c2", "c1", "b");

        assertThat(Piece.isBishop(b.getPieceAt("c1"))).isEqualTo(true);
        assertThat(b.getPieceAt("c1").isWhiteTeam()).isEqualTo(false);
        assertThat(b.getPieceAt("c2").isPiece()).isEqualTo(false);

        // Captures testing
        g = new Gamelogic();
        b = g.getChessBoard();

        b.setPieceAt(new Nothing(true), "d2");

        g.doMove("d1", "d7");
        assertThat(Piece.isQueen(b.getPieceAt("d7"))).isEqualTo(true);
        assertThat(b.getPieceAt("d1").isPiece()).isEqualTo(false);

        g.doMove("d8", "d7");
        assertThat(Piece.isQueen(b.getPieceAt("d7"))).isEqualTo(true);
        assertThat(b.getPieceAt("d8").isPiece()).isEqualTo(false);

        g.doMove("c1", "f4");
        assertThat(Piece.isBishop(b.getPieceAt("f4"))).isEqualTo(true);
        assertThat(b.getPieceAt("c1").isPiece()).isEqualTo(false);
        g.doMove("f4", "c7");
        assertThat(Piece.isBishop(b.getPieceAt("c7"))).isEqualTo(true);
        assertThat(b.getPieceAt("f4").isPiece()).isEqualTo(false);
        g.doMove("d7", "c7");
        assertThat(Piece.isQueen(b.getPieceAt("c7"))).isEqualTo(true);
        assertThat(b.getPieceAt("d7").isPiece()).isEqualTo(false);
    }

    @Test
    public void moveTest(){
        Gamelogic g = new Gamelogic();
        ChessBoard b = g.getChessBoard();

        // invalid input
        boolean move = g.movePiece("a2 > a4");
        assertThat(move).isEqualTo(false);
        assertThat(b.getPieceAt("a4").isPiece()).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("a2"))).isEqualTo(true);

        // Invalid input
        move = g.movePiece("a2 ,> a4");
        assertThat(move).isEqualTo(false);
        assertThat(b.getPieceAt("a4").isPiece()).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("a2"))).isEqualTo(true);

        // Invalid input
        move = g.movePiece("a2 -> a45");
        assertThat(move).isEqualTo(false);
        assertThat(b.getPieceAt("a4").isPiece()).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("a2"))).isEqualTo(true);

        // Invalid input
        move = g.movePiece("aa2 -> a4");
        assertThat(move).isEqualTo(false);
        assertThat(b.getPieceAt("a4").isPiece()).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("a2"))).isEqualTo(true);

        // Valid move. Now it's black's turn
        assertThat(b.getEnPassant()).isEqualTo("");
        move = g.movePiece("a2 -> a4");
        assertThat(move).isEqualTo(true);
        assertThat(b.getPieceAt("a2").isPiece()).isEqualTo(false);
        assertThat(b.getPieceAt("a3").isPiece()).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("a4"))).isEqualTo(true);
        assertThat(b.getEnPassant()).isEqualTo("a4");
        // Advanced Testing

        // No piece at a2
        move = g.movePiece("a2 -> a4");
        assertThat(move).isEqualTo(false);
        // Moving white piece, when black's turn.
        move = g.movePiece("e2 -> e4");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("b2 -> b3");
        assertThat(move).isEqualTo(false);
        assertThat(b.getEnPassant()).isEqualTo("a4");

        // Moving black knight.
        assertThat(g.isWhiteTurn()).isEqualTo(false);
        // Incorrect movement
        move = g.movePiece("g8 -> g6");
        assertThat(g.isWhiteTurn()).isEqualTo(false);
        assertThat(move).isEqualTo(false);
        assertThat(g.isWhiteTurn()).isEqualTo(false);
        move = g.movePiece("g8 -> h6");
        assertThat(b.getEnPassant()).isEqualTo("");
        assertThat(move).isEqualTo((true));
        assertThat(b.getPieceAt("g8").isPiece()).isEqualTo(false);
        assertThat(Piece.isKnight(b.getPieceAt("h6"))).isEqualTo(true);
        assertThat(g.isWhiteTurn()).isEqualTo(true);

        // Moving a1 rook.
        move = g.movePiece("a1 -> a4");
        assertThat(move).isEqualTo(false);
        assertThat(Piece.isRook(b.getPieceAt("a1"))).isEqualTo(true);
        move = g.movePiece("a1 -> a3");
        assertThat(move).isEqualTo(true);
        assertThat(g.isWhiteTurn()).isEqualTo(false);
        assertThat(Piece.isRook(b.getPieceAt("a1"))).isEqualTo(false);
        assertThat(Piece.isRook(b.getPieceAt("a3"))).isEqualTo(true);
        move = g.movePiece("a1 -> a3");
        assertThat(move).isEqualTo(false);
        assertThat(g.isWhiteTurn()).isEqualTo(false);
        move = g.movePiece("a3 -> a1");
        assertThat(move).isEqualTo(false);

        // Moving e8 black king, but not really moving b/c invalid move.
        move = g.movePiece("e8 -> d7");
        assertThat(move).isEqualTo(false);
        assertThat(Piece.isKing(b.getPieceAt("e8"))).isEqualTo(true);
        // Moving d7 pawn
        move = g.movePiece("d7 -> d5");
        assertThat(b.getEnPassant()).isEqualTo("d5");
        assertThat(move).isEqualTo(true);
        assertThat(Piece.isPawn(b.getPieceAt("d7"))).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("d5"))).isEqualTo(true);

        // Moving e2 pawn
        move = g.movePiece("e2 -> e4");
        assertThat(b.getEnPassant()).isEqualTo("e4");
        assertThat(move).isEqualTo(true);
        assertThat(b.getPieceAt("e2").isPiece()).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("e4"))).isEqualTo(true);
        move = g.movePiece("e4 -> e5");
        assertThat(b.getEnPassant()).isEqualTo("e4");
        assertThat(move).isEqualTo(false);

        // Moving d5 pawn
        assertThat(g.isWhiteTurn()).isEqualTo(false);
        move = g.movePiece("d5 -> d7");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("d5 -> d3");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("d5 -> e4");
        assertThat(move).isEqualTo(true);
        assertThat(b.getEnPassant()).isEqualTo("");
        move = g.movePiece("d5 -> e4");
        assertThat(move).isEqualTo(false);
        assertThat(b.getPieceAt("d5").isPiece()).isEqualTo(false);
        assertThat(Piece.isPawn(b.getPieceAt("e4"))).isEqualTo(true);
        assertThat(b.getPieceAt("e4").isWhiteTeam()).isEqualTo(false);

        // Moving a3 rook. Now black's turn
        move = g.movePiece("a3 -> h3");
        assertThat(move).isEqualTo(true);

        // Moving g7 pawn. Now White's turn
        move = g.movePiece("g7 -> g6");
        assertThat(move).isEqualTo(true);
        assertThat(b.getEnPassant()).isEqualTo("");

        // Capturing Knight on h6 with h3 rook. Now black's turn
        move = g.movePiece("h3 -> h6");
        assertThat(move).isEqualTo(true);
        assertThat(Piece.isRook(b.getPieceAt("h6"))).isEqualTo(true);

        // Moving d8 queen. Now white's turn
        move = g.movePiece("d8 -> e4");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("d8 -> c7");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("d8 -> c8");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("d8 -> d2");
        assertThat(move).isEqualTo(true);
        assertThat(b.getPieceAt("d2").isWhiteTeam()).isEqualTo(false);

        // Moving anything that does not bring the white king out of check.
        move = g.movePiece("a4 -> a5");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("a3 -> c3");
        assertThat(move).isEqualTo(false);
        move = g.movePiece("e1 -> e2"); // Not possible b/c king would still be in check
        assertThat(move).isEqualTo(false);

        // e1 king captures black queen. Now black's turn
        move = g.movePiece("e1 -> d2");
        assertThat(move).isEqualTo(true);

    }

    @Test
    public void fischerVsSpassky(){
        // More tests: Fischer vs Spassky (1972) Game 6.
        // Fischer has white.
        Gamelogic g = new Gamelogic();
        ChessBoard b = g.getChessBoard();
        ChessBoardDrawer d = new ChessBoardDrawer(b, true);
        g.movePiece("c2 -> c4");
        assertThat(Piece.isPawn(b.getPieceAt("c4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e7 -> e6");
        assertThat(Piece.isPawn(b.getPieceAt("e6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("g1 -> f3");
        assertThat(Piece.isKnight(b.getPieceAt("f3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d7 -> d5");
        assertThat(Piece.isPawn(b.getPieceAt("d5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d2 -> d4");
        assertThat(Piece.isPawn(b.getPieceAt("d4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("g8 -> f6");
        assertThat(Piece.isKnight(b.getPieceAt("f6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b1 -> c3");
        assertThat(Piece.isKnight(b.getPieceAt("c3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f8 -> e7");
        assertThat(Piece.isBishop(b.getPieceAt("e7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c1 -> g5");
        assertThat(Piece.isBishop(b.getPieceAt("g5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e8 -> g8");
        assertThat(Piece.isKing(b.getPieceAt("g8"))).isEqualTo(true);
        assertThat(Piece.isRook(b.getPieceAt("f8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e2 -> e3");
        assertThat(Piece.isPawn(b.getPieceAt("e3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("h7 -> h6");
        assertThat(Piece.isPawn(b.getPieceAt("h6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("g5 -> h4");
        assertThat(Piece.isBishop(b.getPieceAt("h4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b7 -> b6");
        assertThat(Piece.isPawn(b.getPieceAt("b6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c4 -> d5");
        assertThat(Piece.isPawn(b.getPieceAt("d5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f6 -> d5");
        assertThat(Piece.isKnight(b.getPieceAt("d5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("h4 -> e7");
        assertThat(Piece.isBishop(b.getPieceAt("e7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d8 -> e7");
        assertThat(Piece.isQueen(b.getPieceAt("e7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c3 -> d5");
        assertThat(Piece.isKnight(b.getPieceAt("d5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e6 -> d5");
        assertThat(Piece.isPawn(b.getPieceAt("d5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a1 -> c1");
        assertThat(Piece.isRook(b.getPieceAt("c1"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c8 -> e6");
        assertThat(Piece.isBishop(b.getPieceAt("e6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d1 -> a4");
        assertThat(Piece.isQueen(b.getPieceAt("a4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c7 -> c5");
        assertThat(Piece.isPawn(b.getPieceAt("c5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a4 -> a3");
        assertThat(Piece.isQueen(b.getPieceAt("a3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f8 -> c8");
        assertThat(Piece.isRook(b.getPieceAt("c8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f1 -> b5");
        assertThat(Piece.isBishop(b.getPieceAt("b5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a7 -> a6");
        assertThat(Piece.isPawn(b.getPieceAt("a6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d4 -> c5");
        assertThat(Piece.isPawn(b.getPieceAt("c5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b6 -> c5");
        assertThat(Piece.isPawn(b.getPieceAt("c5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e1 -> g1");
        assertThat(Piece.isKing(b.getPieceAt("g1"))).isEqualTo(true);
        assertThat(Piece.isRook(b.getPieceAt("f1"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a8 -> a7");
        assertThat(Piece.isRook(b.getPieceAt("a7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b5 -> e2");
        assertThat(Piece.isBishop(b.getPieceAt("e2"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b8 -> d7");
        assertThat(Piece.isKnight(b.getPieceAt("d7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f3 -> d4");
        assertThat(Piece.isKnight(b.getPieceAt("d4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e7 -> f8");
        assertThat(Piece.isQueen(b.getPieceAt("f8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d4 -> e6");
        assertThat(Piece.isKnight(b.getPieceAt("e6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f7 -> e6");
        assertThat(Piece.isPawn(b.getPieceAt("e6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e3 -> e4");
        assertThat(Piece.isPawn(b.getPieceAt("e4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d5 -> d4");
        assertThat(Piece.isPawn(b.getPieceAt("d4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f2 -> f4");
        assertThat(Piece.isPawn(b.getPieceAt("f4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f8 -> e7");
        assertThat(Piece.isQueen(b.getPieceAt("e7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e4 -> e5");
        assertThat(Piece.isPawn(b.getPieceAt("e5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c8 -> b8");
        assertThat(Piece.isRook(b.getPieceAt("b8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e2 -> c4");
        assertThat(Piece.isBishop(b.getPieceAt("c4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("g8 -> h8");
        assertThat(Piece.isKing(b.getPieceAt("h8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a3 -> h3");
        assertThat(Piece.isQueen(b.getPieceAt("h3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d7 -> f8");
        assertThat(Piece.isKnight(b.getPieceAt("f8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b2 -> b3");
        assertThat(Piece.isPawn(b.getPieceAt("b3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a6 -> a5");
        assertThat(Piece.isPawn(b.getPieceAt("a5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f4 -> f5");
        assertThat(Piece.isPawn(b.getPieceAt("f5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e6 -> f5");
        assertThat(Piece.isPawn(b.getPieceAt("f5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f1 -> f5");
        assertThat(Piece.isRook(b.getPieceAt("f5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f8 -> h7");
        assertThat(Piece.isKnight(b.getPieceAt("h7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c1 -> f1");
        assertThat(Piece.isRook(b.getPieceAt("f1"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e7 -> d8");
        assertThat(Piece.isQueen(b.getPieceAt("d8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("h3 -> g3");
        assertThat(Piece.isQueen(b.getPieceAt("g3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a7 -> e7");
        assertThat(Piece.isRook(b.getPieceAt("e7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("h2 -> h4");
        assertThat(Piece.isPawn(b.getPieceAt("h4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b8 -> b7");
        assertThat(Piece.isRook(b.getPieceAt("b7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e5 -> e6");
        assertThat(Piece.isPawn(b.getPieceAt("e6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("b7 -> c7");
        assertThat(Piece.isRook(b.getPieceAt("c7"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("g3 -> e5");
        assertThat(Piece.isQueen(b.getPieceAt("e5"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d8 -> e8");
        assertThat(Piece.isQueen(b.getPieceAt("e8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("a2 -> a4");
        assertThat(Piece.isPawn(b.getPieceAt("a4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e8 -> d8");
        assertThat(Piece.isQueen(b.getPieceAt("d8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f1 -> f2");
        assertThat(Piece.isRook(b.getPieceAt("f2"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d8 -> e8");
        assertThat(Piece.isQueen(b.getPieceAt("e8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f2 -> f3");
        assertThat(Piece.isRook(b.getPieceAt("f3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e8 -> d8");
        assertThat(Piece.isQueen(b.getPieceAt("d8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("c4 -> d3");
        assertThat(Piece.isBishop(b.getPieceAt("d3"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d8 -> e8");
        assertThat(Piece.isQueen(b.getPieceAt("e8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e5 -> e4");
        assertThat(Piece.isQueen(b.getPieceAt("e4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("h7 -> f6");
        assertThat(Piece.isKnight(b.getPieceAt("f6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f5 -> f6");
        assertThat(Piece.isRook(b.getPieceAt("f6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("g7 -> f6");
        assertThat(Piece.isPawn(b.getPieceAt("f6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("f3 -> f6");
        assertThat(Piece.isRook(b.getPieceAt("f6"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("h8 -> g8");
        assertThat(Piece.isKing(b.getPieceAt("g8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("d3 -> c4");
        assertThat(Piece.isBishop(b.getPieceAt("c4"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("g8 -> h8");
        assertThat(Piece.isKing(b.getPieceAt("h8"))).isEqualTo(true);
        d.draw(500);
        g.movePiece("e4 -> f4");
        assertThat(Piece.isQueen(b.getPieceAt("f4"))).isEqualTo(true);
        d.draw(2000);
        // Spassky resigns
    }

    @Test
    public void getPositionStringTest(){
        Gamelogic g = new Gamelogic();
        assertThat(g.getPositionString()).isEqualTo(g.getPositionString());
        String startPos = g.getPositionString();

        g.movePiece("e2 -> e4");
        String pos2 = g.getPositionString();
        assertThat(startPos.equals(g.getPositionString())).isEqualTo(false);

        g.movePiece("b8 -> c6");
        String pos3 = g.getPositionString();
        assertThat(startPos.equals(pos3)).isEqualTo(false);
        assertThat(pos2.equals(pos3)).isEqualTo(false);
        assertThat(pos3.equals(g.getPositionString())).isEqualTo(true);

        g.movePiece("b1 -> c3");
        String pos4 = g.getPositionString();
        assertThat(startPos.equals(pos4)).isEqualTo(false);
        assertThat(pos2.equals(pos4)).isEqualTo(false);
        assertThat(pos3.equals(pos4)).isEqualTo(false);
        assertThat(pos4.equals(g.getPositionString())).isEqualTo(true);

        g.movePiece("c6 -> b8");
        String pos5 = g.getPositionString();
        assertThat(startPos.equals(pos5)).isEqualTo(false);
        assertThat(pos2.equals(pos5)).isEqualTo(false);
        assertThat(pos3.equals(pos5)).isEqualTo(false);
        assertThat(pos4.equals(pos5)).isEqualTo(false);

        g.movePiece("c3 -> b1");
        String pos6 = g.getPositionString();
        assertThat(startPos.equals(pos6)).isEqualTo(false);
        assertThat(pos2.equals(pos6)).isEqualTo(true);
        assertThat(pos3.equals(pos6)).isEqualTo(false);
        assertThat(pos4.equals(pos6)).isEqualTo(false);
        assertThat(pos5.equals(pos6)).isEqualTo(false);
    }

    @Test
    public void positionsTest(){
        Gamelogic g = new Gamelogic();
        assertThat(g.getPositionsMap().size()).isEqualTo(1);
        g.movePiece("c2 -> c4");
        assertThat(g.getPositionsMap().size()).isEqualTo(2);
        g.movePiece("e7 -> e6");
        assertThat(g.getPositionsMap().size()).isEqualTo(3);
        g.movePiece("g1 -> f3");
        assertThat(g.getPositionsMap().size()).isEqualTo(4);
        g.movePiece("d7 -> d5");
        assertThat(g.getPositionsMap().size()).isEqualTo(5);
        g.movePiece("f3 -> g1");
        assertThat(g.getPositionsMap().size()).isEqualTo(6);

        // More positions tests
        g = new Gamelogic();
        assertThat(g.getPositionsMap().size()).isEqualTo(1);
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(1);
        g.movePiece("e2 -> e3");
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(1);
        assertThat(g.getPositionsMap().size()).isEqualTo(2);
        g.movePiece("e7 -> e6");
        assertThat(g.getPositionsMap().size()).isEqualTo(3);
        g.movePiece("e1 -> e2");
        assertThat(g.getPositionsMap().size()).isEqualTo(4);
        g.movePiece("e8 -> e7");
        assertThat(g.getPositionsMap().size()).isEqualTo(5);
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(1);
        g.movePiece("e2 -> e1");
        assertThat(g.getPositionsMap().size()).isEqualTo(6);
        g.movePiece("e7 -> e8");
        assertThat(g.getPositionsMap().size()).isEqualTo(7);
        g.movePiece("e1 -> e2");
        assertThat(g.getPositionsMap().size()).isEqualTo(8);

        g.movePiece("e8 -> e7");
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(2);
        assertThat(g.getPositionsMap().size()).isEqualTo(8);

        g.movePiece("e2 -> e1");
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(2);
        assertThat(g.getPositionsMap().size()).isEqualTo(8);

        g.movePiece("e7 -> e8");
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(2);
        assertThat(g.getPositionsMap().size()).isEqualTo(8);

        g.movePiece("e1 -> e2");
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(2);
        assertThat(g.getPositionsMap().size()).isEqualTo(8);

        g.movePiece("e8 -> e7");
        assertThat(g.getPositionsMap().get(g.getPositionString())).isEqualTo(3);
        assertThat(g.getPositionsMap().size()).isEqualTo(8);
    }

}
