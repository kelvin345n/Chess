package board;

import pieces.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Sets up the Chessboard and places all the pieces in starting position. */
public class ChessBoard {
    //
    private final int TILE_COUNT = 8;
    // Board that holds all the floor pieces
    // Each tile will hold a piece.
    // Important: The board is in column major order so board[col][row]
    private Tile[][] board;

    // This holds the string index of the tile that is en passant-able.
    // Meaning that a starting pawn moved two tiles forward.
    private String enPassant;

    private Set<String> whitePieces;
    private Set<String> blackPieces;

    // Useful in determining insufficient material
    private int whitePieceScore;
    private int blackPieceScore;


    /** Constructor. Sets up all pieces in starting position. */
    public ChessBoard(){
        board = new Tile[TILE_COUNT][TILE_COUNT];
        for (int i = 0; i < TILE_COUNT; i++){
            for (int j = 0; j < TILE_COUNT; j++){
                // Alternating tiles depending on position in board
                board[i][j] = new Tile((i + j) % 2 == 0);
                if (j == 1){
                    //Place white pawns
                    board[i][j].setPiece(new Pawn(true));
                } else if (j == 6) {
                    // Place black pawns
                    board[i][j].setPiece(new Pawn(false));
                } else {
                    // O means that there should be no piece
                    board[i][j].setPiece(new Nothing(true));
                }
            }
        }
        enPassant = "";
        placePieces(true);
        placePieces(false);
        // All pieces have been placed. Now we assign each tile if a
        // white or black thing can attack it.
        attackOnTiles();
        whitePieceScore = 0;
        blackPieceScore = 0;
        updatePieceSets();
    }
    /** Places all pieces other than pawns in starting position. */
    private void placePieces(boolean isWhite){
        int offset = 7;
        if (isWhite){
            offset = 0;
        }
        // Places rooks
        board[0][offset].setPiece(new Rook(isWhite));
        board[7][offset].setPiece(new Rook(isWhite));
        // Places knights
        board[1][offset].setPiece(new Knight(isWhite));
        board[6][offset].setPiece(new Knight(isWhite));
        // Places bishops
        board[2][offset].setPiece(new Bishop(isWhite));
        board[5][offset].setPiece(new Bishop(isWhite));
        //Places Queens
        board[3][offset].setPiece(new Queen(isWhite));
        //Places King
        board[4][offset].setPiece(new King(isWhite));
    }

    /** Set every tile's black and white attacking to false */
    private void setAllTilesAttackFalse(){
        for (int c = 0; c < 8; c++){
            for (int r = 0; r < 8; r++){
                Tile currTile = getTile(c, r);
                // Set the curr tiles attacks to both false.
                // Will change them later.
                currTile.setBlackAttacking(false);
                currTile.setWhiteAttacking(false);
            }
        }
    }

    /** Sets every tile whether a white or black piece is attacking it. */
    public void attackOnTiles(){
        setAllTilesAttackFalse();
        for (int c = 0; c < 8; c++){
            for (int r = 0; r < 8; r++){
                Tile currTile = getTile(c, r);
                Piece currPiece = currTile.getPiece();

                Set<String> attackSet = attacksSet(convertToIndex(c, r));

                for (String attacked : attackSet){
                    Tile attackedTile = getTile(attacked);
                    if (currPiece.isWhiteTeam()){
                        attackedTile.setWhiteAttacking(true);
                    } else {
                        attackedTile.setBlackAttacking(true);
                    }
                }
            }
        }
    }

    /** When given an index of a tile, returns the all the moves the piece at
     * that tile can do. */
    public Set<String> movesSet(String index){
        // If not a possible index then return nothing.
        if (!possibleTile(index)) return new HashSet<>();
        attackOnTiles();
        Set<String> realMoves = new HashSet<>();
        Set<String> moves = getPieceAt(index).movesSet(index, this);

        Piece startPiece = getPieceAt(index);
        for (String move : moves){
            // Check if each move puts the king in check, or if the king is
            // already in check, then puts the king out of check.
            Map<String, Tile> changedTiles = movesSetHelper(index, move);
            updatePieceSets();
            // The move has been made.
            if (!isKingChecked(startPiece.isWhiteTeam())){
                realMoves.add(move);
            }
            // Put the cloned tile back.
            for (String tileIndex : changedTiles.keySet()){
                insertTileAt(tileIndex, changedTiles.get(tileIndex));
            }
        }
        updatePieceSets();
        attackOnTiles();
        return realMoves;
    }

    /** Returns a map of indices to its cloned tile that would be changed if,
     * the move from start index to end index were to be made, Note: the move
     * is actually made. but the clones of each tile modified are saved
     * in the hash map. */
    private Map<String, Tile> movesSetHelper(String start, String end){
        Map<String, Tile> changed = new HashMap<>();
        Piece startPiece = getPieceAt(start);
        Tile endTile = getTile(end);
        if (Piece.isPawn(startPiece) && !startPiece.isMoved()){
            // Means that the pawn is moving diagonally.
            if (start.charAt(0) != end.charAt(0)){
                // Means we have to be capturing en passant.
                if (!endTile.getPiece().isPiece()){
                    // Remove the either below or in front of the end tile depending on the team.
                    int col = convertToCol(end);
                    int row = convertToRow(end);
                    String enpass;
                    if (startPiece.isWhiteTeam()){
                        enpass = convertToIndex(col, row - 1);
                    } else {
                        enpass = convertToIndex(col, row + 1);
                    }
                    changed.put(enpass, getTile(enpass).clone());
                    setPieceAt(new Nothing(true), enpass);
                }
            }
        }
        changed.put(start, getTile(start).clone());
        changed.put(end, getTile(end).clone());
        setPieceAt(getPieceAt(start), end);
        setPieceAt(new Nothing(true), start);
        return changed;
    }

    /** When given the index of a tile, returns all the tiles that the piece at
     * index is attacking. */
    public Set<String> attacksSet(String index){
        // If not a possible index then return nothing.
        if (!possibleTile(index)) return new HashSet<>();
        return getPieceAt(index).attackSet(index, this);
    }

    /** Inserts the given tile at the given index on the chessboard */
    public void insertTileAt(String index, Tile t){
        int col = convertToCol(index);
        int row = convertToRow(index);
        board[col][row] = t;
    }

    /** Assumes that col and row are possible tiles on the board.
     * Example: converts col 0, and row 0 to "a1". col 1 row 0 = "b2" */
    public String convertToIndex(int col, int row){
        char charCol = (char) ('a' + col);
        char charRow = (char) ('1' + row);
        return "" + charCol + charRow;
    }
    /** Converts the string index to the specified column on the board.
     * If the index is not in the correct format then we return a negative number. */
    public int convertToCol(String index){
        if (index.length() != 2){
            return -1;
        }
        char charCol = index.charAt(0);
        return (int)charCol - 'a';
    }

    /** Converts the string index to the specified row on the board */
    public int convertToRow(String index){
        if (index.length() != 2){
            return -1;
        }
        char charRow = index.charAt(1);
        return (int)charRow - '1';
    }

    /** Determines if the given tile at the given row and column is a possible
     * position on the chess board */
    public boolean possibleTile(int col, int row){
        return col >= 0 && col < TILE_COUNT && row >= 0 && row < TILE_COUNT;
    }
    public boolean possibleTile(String index){
        return possibleTile(convertToCol(index), convertToRow(index));
    }

    /** Returns true if this is a valid position and there is a piece here */
    public boolean isPieceHere(int col, int row){
        if (possibleTile(col, row)){
            Tile t = getTile(col, row);
            Piece p = t.getPiece();
            // There is a piece at this position
            return p.isPiece();
        }
        return false;
    }
    public boolean isPieceHere(String index){
        int col = convertToCol(index);
        int row = convertToRow(index);
        return isPieceHere(col, row);
    }

    /** Returns the tile at col and row on the chess board. If the col and row are
     * not valid positions, return null. */
    public Tile getTile(int col, int row){
        if (possibleTile(col, row)){
            return board[col][row];
        }
        return null;
    }
    /** Returns the tile at the given index on the chess board. If the index is
     * not a valid position, return null. */
    public Tile getTile(String index){
        int col = convertToCol(index);
        int row = convertToRow(index);
        return getTile(col, row);
    }

    /** given a valid index on the chessboard, this func
     * returns the piece at that index. Else returns null */
    public Piece getPieceAt(String index){
        int col = convertToCol(index);
        int row = convertToRow(index);
        if (possibleTile(col, row)){
            Tile t = getTile(index);
            return t.getPiece();
        }
        return null;
    }

    public Tile[][] getBoard(){
        return board;
    }

    public String getEnPassant(){
        return enPassant;
    }
    public void setEnPassant(String index){
        enPassant = index;
    }

    /** Updates the white and black piece sets. And their associated
     * score of all that color's pieces. */
    public void updatePieceSets(){
        int whiteScore = 0;
        int blackScore = 0;
        Set<String> white = new HashSet<>();
        Set<String> black = new HashSet<>();
        // Adding pieces to the set.
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (possibleTile(i, j)){
                    String index = convertToIndex(i, j);
                    Piece p = getPieceAt(index);
                    if (p.isPiece()){
                        if (p.isWhiteTeam()){
                            whiteScore += p.points();
                            white.add(index);
                        } else {
                            blackScore += p.points();
                            black.add(index);
                        }

                    }
                }
            }
        }
        whitePieceScore = whiteScore;
        blackPieceScore = blackScore;
        whitePieces = white;
        blackPieces = black;
    }
    /** Returns a set of strings of all the pieces for that player. */
    public Set<String> getPiecesSet(boolean forWhite){
        if (forWhite){
            return whitePieces;
        }
        return blackPieces;
    }

    /** When given a team, checks if that king is currently in check. */
    public boolean isKingChecked(boolean whiteTeam){
        attackOnTiles();
        Set<String> pieceSet = blackPieces;
        if (whiteTeam){
            pieceSet = whitePieces;
        }

        for (String piece : pieceSet){
            if (Piece.isKing(getPieceAt(piece))){
                Tile t = getTile(piece);
                if (whiteTeam){
                    if (t.isBlackAttacking()){
                        return true;
                    }
                } else {
                    if (t.isWhiteAttacking()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Checks if the two pieces at the two indices are indeed pieces,
     * and if they are on opposing teams. */
    public boolean opposingTeams(String indexOne, String indexTwo){
        // Checks if both pieces at these indices are not-nothing.
        if (isPieceHere(indexOne) && isPieceHere(indexTwo)){
            Piece one = getPieceAt(indexOne);
            Piece two = getPieceAt(indexTwo);
            // Checking if they are on different teams.
            if (one.isWhiteTeam() && !two.isWhiteTeam()){
                return true;
            }
            if (!one.isWhiteTeam() && two.isWhiteTeam()){
                return true;
            }
        }
        return false;
    }

    public boolean sameTeams(String indexOne, String indexTwo){
        // Checks if both pieces at these indices are not-nothing.
        if (isPieceHere(indexOne) && isPieceHere(indexTwo)){
            Piece one = getPieceAt(indexOne);
            Piece two = getPieceAt(indexTwo);
            // Checking if they are on the same teams.
            if (one.isWhiteTeam() == two.isWhiteTeam()){
                return true;
            }
        }
        return false;
    }

    /** Sets the piece at the index on the chess board. Overriding the previous
     * piece at that tile. */
    public void setPieceAt(Piece piece, String index){
        if (possibleTile(index)) getTile(index).setPiece(piece);
    }


    /** Returns the total piece score of the specified color */
    public int getPieceScore(boolean forWhite){
        if (forWhite){
            return whitePieceScore;
        }
        return blackPieceScore;
    }

}
