package tests;
import board.ChessBoard;
import board.ChessBoardDrawer;
import board.Tile;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class PiecesTest {
        // g
    @Test
    public void nothingMovesSetTest(){
        ChessBoard b = new ChessBoard();
        assertThat(b.movesSet("a3")).isEqualTo(Set.of());
        assertThat(b.movesSet("a5")).isEqualTo(Set.of());
        assertThat(b.movesSet("c6")).isEqualTo(Set.of());
        assertThat(b.movesSet("d5")).isEqualTo(Set.of());
        assertThat(b.movesSet("f4")).isEqualTo(Set.of());

        b.setPieceAt(new Nothing(true), "e1");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of());
        b.setPieceAt(new Nothing(true), "d8");
        assertThat(b.movesSet("d8")).isEqualTo(Set.of());
        assertThat(b.movesSet("z8")).isEqualTo(Set.of());
        assertThat(b.movesSet("a9")).isEqualTo(Set.of());
    }
        // g
    @Test
    public void nothingAttacksSetTest(){
        ChessBoard b = new ChessBoard();
        assertThat(b.movesSet("a4")).isEqualTo(Set.of());
        assertThat(b.movesSet("a6")).isEqualTo(Set.of());
        assertThat(b.movesSet("c5")).isEqualTo(Set.of());
        assertThat(b.movesSet("d4")).isEqualTo(Set.of());
        assertThat(b.movesSet("f3")).isEqualTo(Set.of());

        b.setPieceAt(new Nothing(true), "h2");
        assertThat(b.movesSet("h2")).isEqualTo(Set.of());
        b.setPieceAt(new Nothing(true), "d7");
        assertThat(b.movesSet("d7")).isEqualTo(Set.of());
        assertThat(b.movesSet("j8")).isEqualTo(Set.of());
        assertThat(b.movesSet("g9")).isEqualTo(Set.of());
        b.setPieceAt(new Nothing(true), "z5");
    }
        // g
    @Test
    public void pawnAttacksSetTest(){
        ChessBoard b = new ChessBoard();
        assertThat(b.attacksSet("a2")).isEqualTo(Set.of("b3"));
        assertThat(b.attacksSet("b2")).isEqualTo(Set.of("a3", "c3"));
        assertThat(b.attacksSet("f2")).isEqualTo(Set.of("e3", "g3"));
        assertThat(b.attacksSet("a7")).isEqualTo(Set.of("b6"));
        assertThat(b.attacksSet("h7")).isEqualTo(Set.of("g6"));

        assertThat(b.attacksSet("g7")).isEqualTo(Set.of("h6", "f6"));
        assertThat(b.attacksSet("e7")).isEqualTo(Set.of("d6", "f6"));
        assertThat(b.attacksSet("c7")).isEqualTo(Set.of("b6", "d6"));
        assertThat(b.attacksSet("g2")).isEqualTo(Set.of("f3", "h3"));

        // Advanced Tests
        b.setPieceAt(new Pawn(true), "a5");
        assertThat(b.attacksSet("a5")).isEqualTo(Set.of("b6"));
        b.setPieceAt(new Pawn(false), "a6");
        assertThat(b.attacksSet("a6")).isEqualTo(Set.of("b5"));

        b.setPieceAt(new Pawn(true), "e4");
        b.setPieceAt(new Pawn(true), "d4");
        b.setPieceAt(new Pawn(false), "e5");
        b.setPieceAt(new Pawn(false), "d5");

        assertThat(b.attacksSet("e4")).isEqualTo(Set.of("d5", "f5"));
        assertThat(b.attacksSet("d4")).isEqualTo(Set.of("e5", "c5"));
        assertThat(b.attacksSet("e5")).isEqualTo(Set.of("d4", "f4"));
        assertThat(b.attacksSet("d5")).isEqualTo(Set.of("e4", "c4"));

        b.setPieceAt(new Pawn(false), "g2");
        assertThat(b.attacksSet("g2")).isEqualTo(Set.of("h1", "f1"));


    }
        // g
    @Test
    public void pawnMovesTest(){
        ChessBoard b = new ChessBoard();
        // Testing starting pawns
        assertThat(b.movesSet("a2")).isEqualTo(Set.of("a3", "a4"));
        assertThat(b.movesSet("f7")).isEqualTo(Set.of("f6", "f5"));
        assertThat(b.movesSet("e2")).isEqualTo(Set.of("e3", "e4"));
        assertThat(b.movesSet("c3")).isEqualTo(Set.of());
        assertThat(b.movesSet("a2")).isEqualTo(Set.of("a3", "a4"));
        assertThat(b.movesSet("h7")).isEqualTo(Set.of("h6", "h5"));
        assertThat(b.movesSet("d7")).isEqualTo(Set.of("d6", "d5"));

        b.setPieceAt(new Pawn(true), "a3");
        Piece p = b.getPieceAt("a3");
        p.setMoved();
        assertThat(b.movesSet("a3")).isEqualTo(Set.of("a4"));
        assertThat(b.movesSet("a2")).isEqualTo(Set.of());

        b.setPieceAt(new Pawn(false), "a4");
        p = b.getPieceAt("a4");
        p.setMoved();
        assertThat(b.movesSet("a4")).isEqualTo(Set.of());
        assertThat(b.movesSet("a3")).isEqualTo(Set.of());

        b.setPieceAt(new Pawn(false), "b3");
        p = b.getPieceAt("b3");
        p.setMoved();
        assertThat(b.movesSet("a4")).isEqualTo(Set.of());
        assertThat(b.movesSet("a3")).isEqualTo(Set.of());
        assertThat(b.movesSet("b3")).isEqualTo(Set.of("a2", "c2"));
        assertThat(b.movesSet("a2")).isEqualTo(Set.of("b3"));
        assertThat(b.movesSet("c2")).isEqualTo(Set.of("b3", "c3", "c4"));
        b.setPieceAt(new Pawn(false), "d3");
        p = b.getPieceAt("d3");
        p.setMoved();
        assertThat(b.movesSet("c2")).isEqualTo(Set.of("b3", "c3", "c4", "d3"));
        p = b.getPieceAt("c2");
        p.setMoved();
        assertThat(b.movesSet("c2")).isEqualTo(Set.of("b3", "c3", "d3"));

        assertThat(b.movesSet("b7")).isEqualTo(Set.of("b6", "b5"));

        //Advanced Testing
        b.setPieceAt(new Pawn(true), "e5");
        p = b.getPieceAt("e5");
        p.setMoved();
        b.setPieceAt(new Pawn(false), "c5");
        p = b.getPieceAt("c5");
        p.setMoved();
        b.setPieceAt(new Pawn(false), "d5");
        p = b.getPieceAt("d5");
        p.setMoved();
        b.setEnPassant("d5");
        assertThat(b.movesSet("e5")).isEqualTo(Set.of("d6", "e6"));
        b.setPieceAt(new Pawn(false), "f6");
        p = b.getPieceAt("f6");
        p.setMoved();
        assertThat(b.movesSet("e5")).isEqualTo(Set.of("d6", "e6", "f6"));
        assertThat(b.movesSet("d5")).isEqualTo(Set.of("d4"));
        assertThat(b.movesSet("f6")).isEqualTo(Set.of("f5", "e5"));

        b = new ChessBoard();
        b.setPieceAt(new Pawn(false), "d4");
        p = b.getPieceAt("d4");
        p.setMoved();
        b.setPieceAt(new Pawn(true), "e4");
        p = b.getPieceAt("e4");
        p.setMoved();
        b.setEnPassant("e4");
        assertThat(b.movesSet("e4")).isEqualTo(Set.of("e5"));
        assertThat(b.movesSet("d4")).isEqualTo(Set.of("d3", "e3"));

        b.setPieceAt(new Pawn(false), "f4");
        assertThat(b.movesSet("f4")).isEqualTo(Set.of("f3", "e3"));
        p = b.getPieceAt("f4");
        p.setMoved();
        b.setPieceAt(new Pawn(true), "h6");
        p = b.getPieceAt("h6");
        p.setMoved();
        b.setPieceAt(new Pawn(true), "g6");
        p = b.getPieceAt("g6");
        p.setMoved();

        assertThat(b.movesSet("g6")).isEqualTo(Set.of("h7", "f7"));
        assertThat(b.movesSet("h6")).isEqualTo(Set.of("g7"));
        assertThat(b.movesSet("h7")).isEqualTo(Set.of("g6"));
        assertThat(b.movesSet("g7")).isEqualTo(Set.of("h6"));

        b.setPieceAt(new Pawn(true), "a5");
        assertThat(b.movesSet("a7")).isEqualTo(Set.of("a6"));
        b.setPieceAt(new Pawn(false), "a5");
        assertThat(b.movesSet("a7")).isEqualTo(Set.of("a6"));

        b = new ChessBoard();
        assertThat(b.movesSet("d2")).isEqualTo(Set.of("d3", "d4"));
        b.setPieceAt(new Bishop(false), "b4");
        assertThat(b.movesSet("d2")).isEqualTo(Set.of());
        b.setPieceAt(new Pawn(false), "e3");
        assertThat(b.movesSet("d2")).isEqualTo(Set.of());
        b.setPieceAt(new Pawn(false), "c3");
        assertThat(b.movesSet("d2")).isEqualTo(Set.of("c3", "e3", "d3", "d4"));
        b.setPieceAt(new Pawn(true),"d7");

        assertThat(b.movesSet("d7")).isEqualTo(Set.of("e8", "c8"));
        assertThat(b.movesSet("c7")).isEqualTo(Set.of());
        assertThat(b.movesSet("a7")).isEqualTo(Set.of());
        assertThat(b.movesSet("e3")).isEqualTo(Set.of());

        b.setPieceAt(new Pawn(false), "d3");
        assertThat(b.movesSet("e2")).isEqualTo(Set.of("d3"));
        b.setPieceAt(new Queen(true), "e6");
        assertThat(b.movesSet("e2")).isEqualTo(Set.of("d3"));
        b.setPieceAt(new Queen(false), "e6");
        b.setPieceAt(new Nothing(false), "e3");
        assertThat(b.movesSet("e2")).isEqualTo(Set.of("e3", "e4"));

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "e2");
        b.setPieceAt(new Pawn(true), "e5");
        b.setPieceAt(new Pawn(false), "d5");
        b.setEnPassant("d5");
        assertThat(b.movesSet("e5")).isEqualTo(Set.of("d6", "e6"));
        b.setPieceAt(new Queen(false), "e7");
        assertThat(b.movesSet("e5")).isEqualTo(Set.of("e6"));
    }
        // g
    @Test
    public void knightAttacksSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing the starting knights
        assertThat(b.attacksSet("b1")).isEqualTo(Set.of("a3", "c3", "d2"));
        assertThat(b.attacksSet("b8")).isEqualTo(Set.of("a6", "c6", "d7"));
        assertThat(b.attacksSet("g1")).isEqualTo(Set.of("h3", "f3", "e2"));
        assertThat(b.attacksSet("g8")).isEqualTo(Set.of("h6", "f6", "e7"));

        // Advanced Testing
        assertThat(b.attacksSet("c3")).isEqualTo(Set.of());
        b.setPieceAt(new Knight(true), "c3");
        assertThat(b.attacksSet("c3")).isEqualTo(Set.of("b1", "a2", "a4", "b5",
                                                              "d5", "e4", "e2", "d1"));
        b.setPieceAt(new Knight(false), "c3");
        assertThat(b.attacksSet("c3")).isEqualTo(Set.of("b1", "a2", "a4", "b5",
                                                              "d5", "e4", "e2", "d1"));

        b.setPieceAt(new Knight(false), "e7");
        b.setPieceAt(new Knight(true), "e6");
        assertThat(b.attacksSet("e7")).isEqualTo(Set.of("d5", "c6", "c8", "g8",
                                                            "g6", "f5"));
        assertThat(b.attacksSet("e6")).isEqualTo(Set.of("d4", "c5", "c7", "d8",
                                                            "f8", "g7", "g5", "f4"));

        b.setPieceAt(new Knight(false), "a5");
        assertThat(b.attacksSet("a5")).isEqualTo(Set.of("b7", "c6", "c4", "b3"));

        b.setPieceAt(new Knight(true), "h1");
        assertThat(b.attacksSet("h1")).isEqualTo(Set.of("f2", "g3"));
    }
        // g
    @Test
    public void knightMovesSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing starting knights
        assertThat(b.movesSet("b1")).isEqualTo(Set.of("a3", "c3"));
        assertThat(b.movesSet("b8")).isEqualTo(Set.of("a6", "c6"));
        assertThat(b.movesSet("g1")).isEqualTo(Set.of("h3", "f3"));
        assertThat(b.movesSet("g8")).isEqualTo(Set.of("h6", "f6"));

        // Advanced Testing
        b.setPieceAt(new Nothing(true), "b1");
        b.setPieceAt(new Knight(true), "c3");
        assertThat(b.movesSet("c3")).isEqualTo(Set.of("b1", "a4", "b5", "d5", "e4"));

        b.setPieceAt(new Knight(false), "c3");
        assertThat(b.movesSet("c3")).isEqualTo(Set.of("b1", "a2", "a4", "b5",
                                                        "d5", "e4", "e2", "d1"));

        b.setPieceAt(new Knight(false), "f5");
        assertThat(b.movesSet("f5")).isEqualTo(Set.of("e3", "d4", "d6", "h6",
                                                        "h4", "g3"));

        b.setPieceAt(new Knight(true), "f5");
        assertThat(b.movesSet("f5")).isEqualTo(Set.of("e3", "d4", "d6", "h6",
                                                         "h4", "g3", "e7", "g7"));

        b.setPieceAt(new Knight(false), "a8");
        assertThat(b.movesSet("a8")).isEqualTo(Set.of("b6"));

        b.setPieceAt(new Knight(true), "d3");
        b.setPieceAt(new Knight(false), "b4");
        b.setPieceAt(new Knight(false), "c5");
        assertThat(b.movesSet("d3")).isEqualTo(Set.of("b4", "c5", "e5", "f4"));

        b.setPieceAt(new Knight(true), "b4");
        b.setPieceAt(new Knight(true), "c5");
        b.setPieceAt(new Knight(true), "e5");
        b.setPieceAt(new Knight(true), "f4");
        assertThat(b.movesSet("d3")).isEqualTo(Set.of());

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "d2");
        b.setPieceAt(new Knight(false), "c3");
        b.setPieceAt(new Queen(false), "a5");
        assertThat(b.movesSet("c3")).isEqualTo(Set.of("b1", "a2", "a4", "b5",
                                                    "d5", "e4", "e2", "d1"));

        b.setPieceAt(new Nothing(true), "c3");

        assertThat(b.movesSet("b1")).isEqualTo(Set.of("c3", "d2"));
        assertThat(b.movesSet("g1")).isEqualTo(Set.of());
        b.setPieceAt(new Knight(true), "c3");

        b.setPieceAt(new Nothing(true), "d7");
        b.setPieceAt(new Knight(false), "c6");
        b.setPieceAt(new Bishop(true), "b5");
        assertThat(b.movesSet("c6")).isEqualTo(Set.of());
    }
        // g
    @Test
    public void rookAttacksSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing starting rooks.
        assertThat(b.attacksSet("a1")).isEqualTo(Set.of("a2", "b1"));
        assertThat(b.attacksSet("h8")).isEqualTo(Set.of("h7", "g8"));
        assertThat(b.attacksSet("h1")).isEqualTo(Set.of("h2", "g1"));
        assertThat(b.attacksSet("a8")).isEqualTo(Set.of("a7", "b8"));

        // Advanced Testing
        b.setPieceAt(new Rook(true), "f3");
        // Sets white rook on f3
        assertThat(b.attacksSet("f3")).isEqualTo(Set.of("g3", "h3", "e3", "d3",
                "c3", "b3", "a3", "f2",
                "f4", "f5", "f6", "f7"));

        b.setPieceAt(new Rook(true), "c2");
        // Sets white rook on c2
        assertThat(b.attacksSet("c2")).isEqualTo(Set.of("d2", "b2", "c1", "c3",
                "c4", "c5", "c6", "c7"));

        b.setPieceAt(new Rook(false), "h6");
        // Sets black rook on h6
        assertThat(b.attacksSet("h6")).isEqualTo(Set.of("h7", "h5", "h4", "h3",
                "h2", "g6", "f6", "e6", "d6",
                "c6", "b6", "a6"));

        // Black king on h5
        b.setPieceAt(new King(false), "h5");
        // White Queen on g6
        b.setPieceAt(new Queen(true), "g6");
        assertThat(b.attacksSet("h6")).isEqualTo(Set.of("h7", "h5", "g6"));

        // White rook on g5
        b.setPieceAt(new Rook(true), "g5");
        // Surround rook with random pieces
        b.setPieceAt(new Rook(true), "h5");
        b.setPieceAt(new Pawn(false), "f5");
        b.setPieceAt(new Knight(false), "g4");
        b.setPieceAt(new Rook(false), "g6");
        assertThat(b.attacksSet("g5")).isEqualTo(Set.of("h5", "f5", "g4", "g6"));

        assertThat(b.attacksSet("h5")).isEqualTo(Set.of("h4", "h3", "h2", "g5", "h6"));

        b = new ChessBoard();
        Set<String> set = new HashSet<>();
        int col = b.convertToCol("a1");
        int row = b.convertToRow("a1");
        for (int i = 1; i < 8; i++){
            b.setPieceAt(new Nothing(true), b.convertToIndex(col, row + i));
            b.setPieceAt(new Nothing(true), b.convertToIndex(col + i, row));
            set.add(b.convertToIndex(col, row + i));
            set.add(b.convertToIndex(col + i, row));
        }
        assertThat(b.attacksSet("a1")).isEqualTo(set);

    }
        // g
    @Test
    public void rookMovesSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing Starting rooks
        assertThat(b.movesSet("a1")).isEqualTo(Set.of());
        assertThat(b.movesSet("h1")).isEqualTo(Set.of());
        assertThat(b.movesSet("h8")).isEqualTo(Set.of());
        assertThat(b.movesSet("a8")).isEqualTo(Set.of());

        // Remove a2 pawn
        b.setPieceAt(new Nothing(true), "a2");

        // Removed a2 pawn so now rook should have a clear path straight ahead
        // to the black a7 pawn.
        assertThat(b.movesSet("a1")).isEqualTo(Set.of("a2", "a3", "a4",
                "a5", "a6", "a7"));

        // Set h7 pawn to nothing
        b.setPieceAt(new Nothing(true), "h7");
        assertThat(b.movesSet("h8")).isEqualTo(Set.of("h7", "h6", "h5",
                "h4", "h3", "h2"));

        // Advanced Testing

        // Add white rook to d5
        b.setPieceAt(new Rook(true), "d5");
        assertThat(b.movesSet("d5")).isEqualTo(
                Set.of("d4", "d3", "d6", "d7", "a5", "b5",
                        "c5", "e5", "f5", "g5", "h5"));
        // Add black rook to d5
        b.setPieceAt(new Rook(false), "d5");
        assertThat(b.movesSet("d5")).isEqualTo(
                Set.of("d4", "d3", "d6", "d2", "a5", "b5",
                        "c5", "e5", "f5", "g5", "h5"));

        // Surrounding black rook with white pieces
        b.setPieceAt(new Rook(true), "d4");
        b.setPieceAt(new Pawn(true), "d6");
        b.setPieceAt(new Pawn(true), "c5");
        b.setPieceAt(new Pawn(true), "e5");
        assertThat(b.movesSet("d5")).isEqualTo(
                Set.of("d4", "d6", "c5", "e5"));
        b.setPieceAt(new Knight(true), "d6");
        assertThat(b.movesSet("d5")).isEqualTo(
                Set.of("d6"));

        // Set d5 rook to white, therefore it should attack no piece
        b.setPieceAt(new Rook(true), "d5");
        assertThat(b.movesSet("d5")).isEqualTo(Set.of());
        // Remove the white piece at c5
        b.setPieceAt(new Nothing(true), "c5");
        assertThat(b.movesSet("d5")).isEqualTo(Set.of("c5", "b5", "a5"));

        // Place black rook on c1
        b.setPieceAt(new Rook(false), "c1");
        assertThat(b.movesSet("c1")).isEqualTo(Set.of());

        // Place white rook on d1
        b.setPieceAt(new Rook(true), "d1");
        assertThat(b.movesSet("d1")).isEqualTo(Set.of("c1"));

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "f7");
        b.setPieceAt(new Nothing(true), "a7");
        b.setPieceAt(new Nothing(true), "f8");
        assertThat(b.movesSet("a8")).isEqualTo(Set.of("a7", "a6", "a5", "a4",
                "a3", "a2"));
        b.setPieceAt(new Rook(true), "g8");
        assertThat(b.movesSet("a8")).isEqualTo(Set.of());
        b.setPieceAt(new Nothing(true), "h7");
        assertThat(b.movesSet("h8")).isEqualTo(Set.of("g8"));
        b.setPieceAt(new Rook(false), "f8");
        assertThat(b.movesSet("a8")).isEqualTo(Set.of("a7", "a6", "a5", "a4",
                "a3", "a2"));
        assertThat(b.movesSet("f8")).isEqualTo(Set.of("g8"));
    }
        // g
    @Test
    public void bishopAttacksSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing starting bishops
        assertThat(b.attacksSet("c1")).isEqualTo(Set.of("b2", "d2"));
        assertThat(b.attacksSet("c8")).isEqualTo(Set.of("b7", "d7"));
        assertThat(b.attacksSet("f8")).isEqualTo(Set.of("e7", "g7"));
        assertThat(b.attacksSet("f1")).isEqualTo(Set.of("e2", "g2"));

        // Advanced Testing
        b.setPieceAt(new Bishop(true), "c3");
        // Place white bishop on c3
        assertThat(b.attacksSet("c3")).isEqualTo(Set.of("b2", "d2", "b4",
                "a5", "d4", "e5", "f6", "g7"));
        // Place black bishop on d4
        b.setPieceAt(new Bishop(false), "d4");
        // Place white bishop on b4
        b.setPieceAt(new Bishop(true), "b4");
        assertThat(b.attacksSet("c3")).isEqualTo(Set.of("b2", "d2", "b4", "d4"));
        assertThat(b.attacksSet("d4")).isEqualTo(Set.of("c3", "c5", "b6", "a7",
                "e3", "f2", "e5", "f6", "g7"));
        // Place black bishop on h5
        b.setPieceAt(new Bishop(false), "h5");
        // Place black bishop on f3
        b.setPieceAt(new Bishop(false), "f3");
        assertThat(b.attacksSet("h5")).isEqualTo(Set.of("g6", "f7", "g4", "f3"));

        b = new ChessBoard();
        b.setPieceAt(new Bishop(true), "a1");
        b.setPieceAt(new Nothing(true), "b2");
        b.setPieceAt(new Nothing(true), "g7");
        b.setPieceAt(new Nothing(true), "h8");
        assertThat(b.attacksSet("a1")).isEqualTo(Set.of("b2", "c3", "d4", "e5", "f6",
                "g7", "h8"));

        b.setPieceAt(new Bishop(false), "d4");
        assertThat(b.attacksSet("a1")).isEqualTo(Set.of("b2", "c3", "d4"));

        assertThat(b.attacksSet("d4")).isEqualTo(Set.of("a1", "b2", "c3", "e5", "f6",
                "g7", "h8", "b6", "c5", "a7", "e3", "f2"));
    }
        // g
    @Test
    public void bishopMovesSetTest(){
        ChessBoard b = new ChessBoard();

        // Testing Starting Bishop
        assertThat(b.movesSet("c1")).isEqualTo(Set.of());
        assertThat(b.movesSet("f1")).isEqualTo(Set.of());
        assertThat(b.movesSet("c8")).isEqualTo(Set.of());
        assertThat(b.movesSet("f8")).isEqualTo(Set.of());

        // Advanced Testing
        b.setPieceAt(new Nothing(true), "b2");
        // set b2 to nothing
        // set a1 to white bishop
        b.setPieceAt(new Bishop(true), "a1");
        // set g7 to nothing
        b.setPieceAt(new Nothing(true), "g7");
        // set h8 to nothing
        b.setPieceAt(new Nothing(true), "h8");
        assertThat(b.movesSet("a1")).isEqualTo(Set.of("b2", "c3", "d4",
                "e5", "f6", "g7", "h8"));
        // set g7 to black pawn
        b.setPieceAt(new Pawn(false), "g7");
        // set b2 to white pawn
        b.setPieceAt(new Pawn(true), "b2");
        // Removes d2 pawn
        b.setPieceAt(new Nothing(true), "d2");
        assertThat(b.movesSet("c1")).isEqualTo(Set.of("d2", "e3", "f4",
                "g5", "h6"));

        // Place white bishop on b2
        b.setPieceAt(new Bishop(true), "b2");
        assertThat(b.movesSet("b2")).isEqualTo(Set.of("a3", "c3", "d4",
                "e5", "f6", "g7"));
        // Place black bishop on d5
        b.setPieceAt(new Bishop(false), "d5");
        assertThat(b.movesSet("d5")).isEqualTo(Set.of("c6", "e6", "c4",
                "e4", "b3", "f3", "g2", "a2"));

        // Place black bishop on e2
        b.setPieceAt(new Bishop(false), "e2");
        // Place black pawn on g4
        b.setPieceAt(new Pawn(false), "g4");
        assertThat(b.movesSet("e2")).isEqualTo(Set.of("f1", "d1", "d3",
                "f3", "c4", "b5", "a6"));
        // Place white bishop on a6
        b.setPieceAt(new Bishop(true), "a6");
        // Place white queen on b5
        b.setPieceAt(new Queen(true), "b5");
        assertThat(b.movesSet("a6")).isEqualTo(Set.of("b7"));
        // Place white bishop on h4
        b.setPieceAt(new Bishop(true), "h4");
        assertThat(b.movesSet("h4")).isEqualTo(Set.of("g3", "g5", "f6", "e7"));

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "d7");
        b.setPieceAt(new Nothing(true), "e7");

        b.setPieceAt(new Bishop(true), "c6");
        assertThat(b.movesSet("c8")).isEqualTo(Set.of("d7"));

        b.setPieceAt(new Rook(true), "e2");
        assertThat(b.movesSet("c8")).isEqualTo(Set.of());
        assertThat(b.movesSet("f8")).isEqualTo(Set.of());
    }
        // g
    @Test
    public void queenAttacksSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing starting queens
        assertThat(b.attacksSet("d1")).isEqualTo(Set.of("c2", "d2", "e2", "c1",
                "e1"));
        assertThat(b.attacksSet("d8")).isEqualTo(Set.of("c7", "d7", "e7", "c8",
                "e8"));

        // Advanced Testing

        // place white queen on h3
        b.setPieceAt(new Queen(true), "h3");
        assertThat(b.attacksSet("h3")).isEqualTo(Set.of("h2", "g2", "g3", "f3", "e3",
                "d3", "c3", "b3", "a3", "g4",
                "f5", "e6", "d7", "h4", "h5",
                "h6", "h7"));

        // place white queen on g6
        b.setPieceAt(new Queen(true), "g6");
        // place black queen on f5
        b.setPieceAt(new Queen(false), "f5");
        // place white queen on f6
        b.setPieceAt(new Queen(true), "f6");
        assertThat(b.attacksSet("g6")).isEqualTo(Set.of("g5", "g4", "g3", "g2", "f5",
                "f6", "f7", "g7", "h7", "h6", "h5"));
        // place black queen on f7
        b.setPieceAt(new Queen(false), "f7");
        assertThat(b.attacksSet("f7")).isEqualTo(Set.of("f6", "e6", "d5", "c4", "b3",
                "a2", "e7", "e8", "f8", "g8", "g7", "g6"));
    }
        // g
    @Test
    public void queenMovesSetTest(){
        ChessBoard b = new ChessBoard();

        // Testing starting queens
        assertThat(b.movesSet("d1")).isEqualTo(Set.of());
        assertThat(b.movesSet("d8")).isEqualTo(Set.of());

        // Advanced Testing

        // Remove d2 pawn
        b.setPieceAt(new Nothing(true), "d2");
        assertThat(b.movesSet("d1")).isEqualTo(Set.of("d2", "d3", "d4",
                "d5", "d6", "d7"));

        // Place black queen on c4
        b.setPieceAt(new Queen(false), "c4");
        assertThat(b.movesSet("c4")).isEqualTo(Set.of("c3", "c2", "b4", "a4", "b3", "a2",
                "b5", "a6", "c5", "c6", "d5", "e6", "d4",
                "e4", "f4", "g4", "h4", "d3", "e2"));

        // Place white queen on e6
        b.setPieceAt(new Queen(true), "e6");
        assertThat(b.movesSet("e6")).isEqualTo(Set.of("e5", "e4", "e3", "d5", "c4",
                "d6", "c6", "b6", "a6", "d7", "e7", "f7",
                "f6", "g6", "h6", "f5", "g4", "h3"));

        // Place white queen on h1
        b.setPieceAt(new Queen(false), "h1");
        assertThat(b.movesSet("h1")).isEqualTo(Set.of("h2", "g2", "g1"));

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "e2");
        b.setPieceAt(new Queen(true), "d2");
        b.setPieceAt(new Queen(false), "e7");
        b.setPieceAt(new Queen(true), "e5");
        b.setPieceAt(new Queen(false), "b4");

        assertThat(b.movesSet("e7")).isEqualTo(Set.of("e6", "e5"));
        assertThat(b.movesSet("d2")).isEqualTo(Set.of("c3", "b4"));
        assertThat(b.movesSet("e5")).isEqualTo(Set.of("e6", "e7", "e4", "e3", "e2"));
    }
        // g
    @Test
    public void kingAttacksSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing starting kings
        assertThat(b.attacksSet("e1")).isEqualTo(Set.of("d1", "d2", "e2", "f2", "f1"));
        assertThat(b.attacksSet("e8")).isEqualTo(Set.of("d8", "d7", "e7", "f7", "f8"));

        // Advanced Testing

        // Place white king on a6
        b.setPieceAt(new King(true), "a6");
        assertThat(b.attacksSet("a6")).isEqualTo(Set.of("a7", "b7", "b6", "b5", "a5"));

        // Place white king on g4
        b.setPieceAt(new King(true), "g4");
        assertThat(b.attacksSet("g4")).isEqualTo(Set.of("f3", "f4", "f5", "g5",
                "h5", "h4", "h3", "g3"));

        // Place black king on d4
        b.setPieceAt(new King(false), "d4");
        assertThat(b.attacksSet("d4")).isEqualTo(Set.of("c5", "d5", "e5", "c4",
                                                            "e4", "c3", "d3", "e3"));
    }
        // g
    @Test
    public void kingMovesSetTest(){
        ChessBoard b = new ChessBoard();
        // Testing starting king
        assertThat(b.movesSet("e1")).isEqualTo(Set.of());
        assertThat(b.movesSet("e8")).isEqualTo(Set.of());

        // Advanced Testing

        // Place white king on e3
        b.setPieceAt(new King(true), "e3");
        b.attackOnTiles();
        assertThat(b.movesSet("e3")).isEqualTo(Set.of("d3", "d4", "e4", "f4", "f3"));
        // Place white king on a5
        b.setPieceAt(new King(true), "a5");
        b.attackOnTiles();
        assertThat(b.movesSet("a5")).isEqualTo(Set.of("b5", "b4", "a4"));

        // Place black king on c5
        b.setPieceAt(new King(false), "c5");
        b.attackOnTiles();
        assertThat(b.movesSet("c5")).isEqualTo(Set.of("c6", "d6", "d5", "c4"));

        assertThat(b.attacksSet("c5")).isEqualTo(Set.of("b4", "b5", "b6", "c6",
                "d6", "d5", "d4", "c4"));
        assertThat(b.getTile("b4").isBlackAttacking()).isEqualTo(true);
        // Recheck white king on a5
        assertThat(b.movesSet("a5")).isEqualTo(Set.of("a4"));
        // Remove d2 pawn
        b.setPieceAt(new Nothing(true), "d2");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("d2"));

        // Remove d7 pawn
        b.setPieceAt(new Nothing(true), "d7");
        b.attackOnTiles();
        assertThat(b.movesSet("e1")).isEqualTo(Set.of());
        assertThat(b.movesSet("c5")).isEqualTo(Set.of("c6", "c4"));

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "e2");
        b.setPieceAt(new Nothing(true), "d2");
        b.setPieceAt(new Queen(false), "f2");

        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f2"));
        b.setPieceAt(new Pawn(false), "f3");
        b.attackOnTiles();
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f2"));

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "e7");
        b.setPieceAt(new Nothing(true), "d7");
        b.setPieceAt(new Nothing(true), "f7");
        b.setPieceAt(new Nothing(true), "d8");
        b.setPieceAt(new Nothing(true), "f8");

        b.setPieceAt(new Rook(true), "e3");
        b.attackOnTiles();
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("d8", "d7", "f8", "f7"));
        b.setPieceAt(new Queen(true), "d3");
        b.attackOnTiles();
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8", "f7"));
        b.setPieceAt(new Bishop(true), "g6");
        b.attackOnTiles();
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8"));

        // Castling tests
        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "f1");
        b.setPieceAt(new Nothing(true), "d1");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "d1"));

        b.setPieceAt(new Nothing(true), "g1");
        b.setPieceAt(new Nothing(true), "c1");
        b.setPieceAt(new Nothing(true), "b1");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "g1", "d1", "c1"));

        b.setPieceAt(new Nothing(true), "e2");
        b.setPieceAt(new Queen(false), "e5");
        // King in check
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "d1"));
        b.setPieceAt(new Nothing(true), "e5");
        b.setPieceAt(new Nothing(true), "f2");

        b.setPieceAt(new Bishop(false), "a6");

        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f2", "d1", "c1"));

        b.setPieceAt(new Nothing(true), "a6");
        b.setPieceAt(new Bishop(false), "b6");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "e2", "c1", "d1"));

        b.setPieceAt(new Nothing(true), "b6");
        b.setPieceAt(new Nothing(true), "d2");
        b.setPieceAt(new Nothing(true), "d7");

        assertThat(b.movesSet("e8")).isEqualTo(Set.of("d7"));
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "e2", "f2", "g1"));
        b.getPieceAt("h1").setMoved();
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "e2", "f2"));
        b.setPieceAt(new Nothing(true), "h1");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "e2", "f2"));
        b.setPieceAt(new Queen(true), "h1");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "e2", "f2"));
        // Castling for black king
        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "g8");
        assertThat(b.movesSet("e8")).isEqualTo(Set.of());
        b.setPieceAt(new Nothing(true), "f8");
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8", "g8"));

        b.setPieceAt(new Nothing(true), "g7");
        b.setPieceAt(new Rook(true), "g2");

        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8"));

        b.setPieceAt(new Nothing(true), "d8");
        b.setPieceAt(new Nothing(true), "c8");
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8", "d8"));

        b.setPieceAt(new Nothing(true), "b8");
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8", "d8", "c8"));

        b.setPieceAt(new Knight(true), "a6");
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8", "d8", "c8"));

        b.setPieceAt(new Knight(true), "b6");
        assertThat(b.movesSet("e8")).isEqualTo(Set.of("f8", "d8"));

        b = new ChessBoard();
        b.setPieceAt(new Nothing(true), "f1");
        b.setPieceAt(new Nothing(true), "g1");
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1", "g1"));
        b.getPieceAt("e1").setMoved();
        assertThat(b.movesSet("e1")).isEqualTo(Set.of("f1"));
    }

}
