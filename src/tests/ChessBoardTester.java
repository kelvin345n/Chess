package tests;

import board.ChessBoard;
import board.ChessBoardDrawer;
import board.Tile;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/** This class holds the tests for the ChessBoard class */
public class ChessBoardTester {
    @Test
    public void possibleTileTest(){
        ChessBoard b = new ChessBoard();
        assertThat(b.possibleTile(0, 7)).isEqualTo(true);
        assertThat(b.possibleTile(2, 5)).isEqualTo(true);
        assertThat(b.possibleTile(7, 7)).isEqualTo(true);
        assertThat(b.possibleTile(0, 0)).isEqualTo(true);
        assertThat(b.possibleTile(1, 8)).isEqualTo(false);
        assertThat(b.possibleTile(8, 0)).isEqualTo(false);
        assertThat(b.possibleTile(-1, 7)).isEqualTo(false);
        assertThat(b.possibleTile(0, -1)).isEqualTo(false);
    }

    @Test
    public void convertToIndexTest(){
        ChessBoard b = new ChessBoard();
        assertThat(b.convertToIndex(0, 0)).isEqualTo("a1");
        assertThat(b.convertToIndex(1, 0)).isEqualTo("b1");
        assertThat(b.convertToIndex(7, 7)).isEqualTo("h8");
        assertThat(b.convertToIndex(5, 6)).isEqualTo("f7");
        assertThat(b.convertToIndex(2, 4)).isEqualTo("c5");
        assertThat(b.convertToIndex(4, 7)).isEqualTo("e8");
        assertThat(b.convertToIndex(3, 3)).isEqualTo("d4");
    }

    @Test
    public void convertToColAndRowTest(){
        ChessBoard b = new ChessBoard();
        assertThat(b.convertToCol("g4")).isEqualTo(6);
        assertThat(b.convertToRow("g4")).isEqualTo(3);

        assertThat(b.convertToCol("h8")).isEqualTo(7);
        assertThat(b.convertToRow("h8")).isEqualTo(7);

        assertThat(b.convertToCol("e6")).isEqualTo(4);
        assertThat(b.convertToRow("e6")).isEqualTo(5);

        assertThat(b.convertToCol("c1")).isEqualTo(2);
        assertThat(b.convertToRow("c1")).isEqualTo(0);

        assertThat(b.convertToCol("g1")).isEqualTo(6);
        assertThat(b.convertToRow("g1")).isEqualTo(0);

        assertThat(b.convertToCol("g7")).isEqualTo(6);
        assertThat(b.convertToRow("g7")).isEqualTo(6);

        assertThat(b.convertToCol("d8")).isEqualTo(3);
        assertThat(b.convertToRow("d8")).isEqualTo(7);

        assertThat(b.convertToCol("d5")).isEqualTo(3);
        assertThat(b.convertToRow("d5")).isEqualTo(4);

        assertThat(b.convertToCol("e5")).isEqualTo(4);
        assertThat(b.convertToRow("e5")).isEqualTo(4);

    }

    @Test
    public void opposingTeamsTest(){
        ChessBoard b = new ChessBoard();
        assertThat(b.opposingTeams("a2", "a1")).isEqualTo(false);
        assertThat(b.opposingTeams("a3", "a1")).isEqualTo(false);
        assertThat(b.opposingTeams("e1", "d1")).isEqualTo(false);
        assertThat(b.opposingTeams("e8", "h8")).isEqualTo(false);
        assertThat(b.opposingTeams("e8", "h6")).isEqualTo(false);
        assertThat(b.opposingTeams("a4", "h6")).isEqualTo(false);

        assertThat(b.opposingTeams("a2", "a7")).isEqualTo(true);
        assertThat(b.opposingTeams("e1", "h8")).isEqualTo(true);
        assertThat(b.opposingTeams("e8", "e1")).isEqualTo(true);
        assertThat(b.opposingTeams("e8", "d1")).isEqualTo(true);
        assertThat(b.opposingTeams("e2", "d7")).isEqualTo(true);
    }

    @Test
    public void sameTeamsTest(){
        ChessBoard b = new ChessBoard();

        assertThat(b.sameTeams("a2", "a1")).isEqualTo(true);
        assertThat(b.sameTeams("a3", "a1")).isEqualTo(false);
        assertThat(b.sameTeams("e1", "d1")).isEqualTo(true);
        assertThat(b.sameTeams("e8", "h8")).isEqualTo(true);
        assertThat(b.sameTeams("e8", "h6")).isEqualTo(false);
        assertThat(b.sameTeams("a4", "h6")).isEqualTo(false);

        assertThat(b.sameTeams("a2", "a7")).isEqualTo(false);
        assertThat(b.sameTeams("e1", "h8")).isEqualTo(false);
        assertThat(b.sameTeams("e8", "e1")).isEqualTo(false);
        assertThat(b.sameTeams("e8", "d1")).isEqualTo(false);
        assertThat(b.sameTeams("e2", "h2")).isEqualTo(true);
        assertThat(b.sameTeams("d2", "h1")).isEqualTo(true);

    }

    @Test
    public void attackOnTiles(){
        ChessBoard b = new ChessBoard();

        Tile t = b.getTile("a7");
        assertThat(t.isBlackAttacking()).isEqualTo(true);
        assertThat(t.isWhiteAttacking()).isEqualTo(false);

        t = b.getTile("a8");
        assertThat(t.isBlackAttacking()).isEqualTo(false);
        assertThat(t.isWhiteAttacking()).isEqualTo(false);

        t = b.getTile("e1");
        assertThat(t.isBlackAttacking()).isEqualTo(false);
        assertThat(t.isWhiteAttacking()).isEqualTo(true);

        // Place white queen on g2
        b.setPieceAt(new Queen(true), "g2");

        t = b.getTile("b7");
        assertThat(t.isWhiteAttacking()).isEqualTo(false);

        b.attackOnTiles(); // Reset attacks on each tile.
        assertThat(t.isBlackAttacking()).isEqualTo(true);
        assertThat(t.isWhiteAttacking()).isEqualTo(true);

        t = b.getTile("d5");
        assertThat(t.isBlackAttacking()).isEqualTo(false);
        assertThat(t.isWhiteAttacking()).isEqualTo(true);

        t = b.getTile("g7");
        assertThat(t.isBlackAttacking()).isEqualTo(true);
        assertThat(t.isWhiteAttacking()).isEqualTo(true);

        t = b.getTile("h2");
        assertThat(t.isBlackAttacking()).isEqualTo(false);
        assertThat(t.isWhiteAttacking()).isEqualTo(true);
    }

    @Test
    public void isKingCheckedTest(){
        ChessBoard b = new ChessBoard();
        // Checking white king
        assertThat(b.isKingChecked(true)).isEqualTo(false);
        // Checking black king
        assertThat(b.isKingChecked(false)).isEqualTo(false);

        // Advanced Testing
        b.setPieceAt(new Queen(false), "e2");
        // Checking white king
        assertThat(b.isKingChecked(true)).isEqualTo(true);
        // Checking black king
        assertThat(b.isKingChecked(false)).isEqualTo(false);

        b.setPieceAt(new Bishop(false), "b4");
        assertThat(b.isKingChecked(true)).isEqualTo(true);
        b.setPieceAt(new Queen(true), "e2");
        assertThat(b.isKingChecked(true)).isEqualTo(false);

        b.setPieceAt(new Nothing(true), "d2");
        assertThat(b.isKingChecked(true)).isEqualTo(true);

        b.setPieceAt(new Nothing(true), "e7");

        assertThat(b.isKingChecked(false)).isEqualTo(true);

        b.setPieceAt(new Pawn(false), "e2");
        assertThat(b.isKingChecked(false)).isEqualTo(false);

        b.setPieceAt(new Pawn(true), "d2");
        assertThat(b.isKingChecked(true)).isEqualTo(false);

        b.setPieceAt(new Pawn(true), "d7");
        assertThat(b.isKingChecked(false)).isEqualTo(true);

        b.setPieceAt(new Pawn(false), "d2");
        assertThat(b.isKingChecked(true)).isEqualTo(true);

        b.setPieceAt(new Knight(false), "f3");
        assertThat(b.isKingChecked(true)).isEqualTo(true);
    }

    @Test
    public void piecesSetTest(){
        ChessBoard b = new ChessBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                // Clearing board
                b.setPieceAt(new Nothing(true), b.convertToIndex(i, j));
            }
        }
        b.setPieceAt(new King(true), "e5");
        b.setPieceAt(new King(false), "g1");
        b.setPieceAt(new Pawn(false), "h7");
        b.setPieceAt(new Bishop(false), "e4");
        b.setPieceAt(new Bishop(false), "e6");
        b.setPieceAt(new Rook(true), "h8");

        b.updatePieceSets();
        assertThat(b.getPiecesSet(true)).isEqualTo(Set.of("e5", "h8"));
        assertThat(b.getPiecesSet(false)).isEqualTo(Set.of("g1", "h7", "e4", "e6"));
    }




}
