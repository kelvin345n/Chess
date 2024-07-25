package board;

import pieces.*;

import java.util.*;

public class Gamelogic {
    private ChessBoard cb;
    // true if it is currently white's turn and false if black's turn.
    private boolean whiteTurn;

    private List<String> movesList;

    // Keeps track off all the positions and possible moves of that position.
    // Useful for three-fold repetition.
    // For every position we check if it's been reached in the hashmap. If not
    // we add it to the hashmap and the number of times this position has been reached.
    // "1". If it has been reached then increment the counter. If the counter is 3,
    // then the game ends in a draw.
    // Must also include all possible moves and castling privileges.
    private HashMap<String, Integer> positions;

    public Gamelogic(ChessBoard cb, List<String> moves){
        this.cb = cb;
        whiteTurn = true;
        movesList = moves;
        positions = new HashMap<>();
        cb.updatePieceSets();
        // Add current position
        addPosition(getPositionString());
        if (!moves.isEmpty()){
            Gamelogic g = new Gamelogic();
            for (String move : moves){
                g.movePiece(move);
            }
            // Positions hashmap is set to the new one with all the prev moves.
            positions = g.getPositionsMap();
        }
    }

    public Gamelogic(){
        this(new ChessBoard(), new ArrayList<>());
    }

    public HashMap<String, Integer> getPositionsMap(){
        return positions;
    }

    /** adds the given position to the positions hashmap if not already there. */
    public void addPosition(String position){
        if (positions.containsKey(position)){
            positions.replace(position, positions.get(position) + 1);
        } else {
            positions.put(position, 1);
        }
    }

    /** Returns the current position in String form. Does not add to
     * the hashmap. */
    public String getPositionString(){
        StringBuilder position = new StringBuilder();
        // Must take into account next player to move.
        position.append(whiteTurn);
        position.append(getPositionHelper(cb.getPiecesSet(true), true));
        position.append(getPositionHelper(cb.getPiecesSet(false), false));
        return position.toString();
    }
    /** returns a string of that color's position, and with castling privileges and
     * all the possible moves those pieces can make.  */
    private String getPositionHelper(Set<String> piecesSet, boolean forWhite){
        StringBuilder builder = new StringBuilder();
        List<String> piecesList = new ArrayList<>(piecesSet);
        Collections.sort(piecesList);
        for (String piecePos : piecesList){
            Piece p = cb.getPieceAt(piecePos);
            // Append the type of piece
            builder.append(p.getImageRep());
            builder.append(piecePos);
        }
        // Append the moves.
        List<String> moves = allPossibleMoves(forWhite);
        Collections.sort(moves);
        builder.append(moves);
        // Append castling privileges
        builder.append(castlingPrivilege(true));
        builder.append(castlingPrivilege(false));

        return builder.toString();
    }

    /** Determines the castling privileges of a given color translated to a string */
    private String castlingPrivilege(boolean forWhite){
        String privilege = "b";
        int row = 7;
        if (forWhite){
            row = 0;
            privilege = "w";
        }
        Piece king = cb.getPieceAt(cb.convertToIndex(4, row));
        // Checking king side castling
        Piece kingRook = cb.getPieceAt(cb.convertToIndex(7, row));
        if (Piece.isKing(king) && !king.isMoved()){
            if (Piece.isRook(kingRook) && !kingRook.isMoved()){
                privilege += "k-yes";
            }
        }
        // Checking queen side castling
        Piece queenRook = cb.getPieceAt(cb.convertToIndex(0, row));
        if (Piece.isKing(king) && !king.isMoved()){
            if (Piece.isRook(queenRook) && !queenRook.isMoved()){
                privilege += "q-yes";
            }
        }
        return privilege;
    }

    /** Parses the input to see if it is valid formatting, and then
     * determines if this is a valid move. If not, then return false. If
     * a move is made then return true, and player's turn is changed. */
    public boolean movePiece(String input){
        // Must check if the next move made puts the king that has the same turn
        // in check. If so then no move can be made.
        boolean move = movePieceHelper(input);
        if (move){
            cb.updatePieceSets();
            // The move was made, so we add that new position to the hashmap.
            addPosition(getPositionString());
            return true;
        }
        return false;
    }
    /** A move should have the format of "a1 -> a8". if it does not have this
     * format then no move is made and false is returned. A move is also not made
     * if the move is not a legal move. If a legal move is made then true is returned */
    public boolean movePieceHelper(String input){
        if (input.length() != 8 && input.length() != 10) {
            System.out.println(input + " is not a legal format or move. Format moves such as 'e2 -> e4' where the piece at e2 moves to e4.");
            return false;
        }
        input = input.strip().toLowerCase();
        // Grabs the starting position
        String start = input.substring(0, 2);
        // Makes sure the format is " -> "
        String arrow = input.substring(2, 6);
        // Grabs the ending position
        String end = input.substring(6, 8);

        if (!cb.possibleTile(start) || !cb.possibleTile(end) || !arrow.equals(" -> ")){
            System.out.println(input + " is not a legal format or move. Format moves such as 'e2 -> e4' Where the piece at e2 moves to e4.");
            return false;
        }

        Piece p = cb.getPieceAt(start);

        if ((!p.isWhiteTeam() && whiteTurn) || (p.isWhiteTeam() && !whiteTurn)){
            System.out.println("You cannot move this piece. " + input);
            return false;
        }

        String promotion = "";
        if (input.length() == 10){
            if (input.charAt(8) != '='){
                System.out.println("Incorrect format. Correct format: 'e7 -> e8=q'.");
                return false;
            }
            promotion = input.substring(9);
            Set<String> validPromotions = Set.of("q", "n", "b", "r");
            if (!validPromotions.contains(promotion)) {
                System.out.println(promotion + " is not a valid piece. Use either 'q', 'n', 'b', or 'r'.");
                return false;
            }
        }

        if (Piece.isPawn(p)){
            // Pawn promotion
            char promotionSquare = '1';
            if (p.isWhiteTeam()){
                promotionSquare = '8';
            }
            if (end.charAt(1) == promotionSquare){
                if (promotion.isEmpty()){
                    System.out.println("Pawn promotion must be supplied with a piece to promote to. Ex: 'a7 -> b8=r'");
                    return false;
                }
            } else {
                if (input.length() == 10){
                    System.out.println(input+ " is invalid move. Cannot promote with this move.");
                    return false;
                }
            }
        }

        Set<String> movesSet = cb.movesSet(start);
        if (!movesSet.contains(end)){
            System.out.println(input + " is not a valid move. Try again!");
            return false;
        }
        // Add move to the moves made.
        if (promotion.isEmpty()){
            movesList.addLast(input);
        } else {
            movesList.addLast(input + "=" + promotion);
        }
        doMove(start, end, promotion);
        p.setMoved();
        whiteTurn = !whiteTurn;
        cb.attackOnTiles();
        return true;
    }

    /** Moves the piece at start to the tile at end. Replacing and deleting the piece
     * at the end tile. This does not check if start and end are valid tiles. The
     * checks should be done before. In the case of en passant, the piece next to the pawn
     * will be removed. If castles, then king and rook are moved. If pawn promotion then user
     * is prompted on what piece they want. */
    public void doMove(String start, String end){
        Piece startPiece = cb.getPieceAt(start);
        Tile endTile = cb.getTile(end);
        // Set en passant to default;
        cb.setEnPassant("");
        if (Piece.isPawn(startPiece)){
            // En passant
            if (!startPiece.isMoved()){
                if (Math.abs(start.charAt(1) - end.charAt(1)) == 2){
                    cb.setEnPassant(end);
                }
            }
            // Means that the pawn is moving diagonally.
            if (start.charAt(0) != end.charAt(0)){
                // Means we have to be capturing en passant.
                if (!endTile.getPiece().isPiece()){
                    // Remove the either below or in front of the end tile depending on the team.
                    int col = cb.convertToCol(end);
                    int row = cb.convertToRow(end);
                    String enpass;
                    if (startPiece.isWhiteTeam()){
                        enpass = cb.convertToIndex(col, row - 1);
                    } else {
                        enpass = cb.convertToIndex(col, row + 1);
                    }
                    removePieceAt(enpass);
                }
            }
        }
        // Castling
        // Assumes that the king and rook have not moved
        if (Piece.isKing(startPiece) && !startPiece.isMoved()){
            // If the king is trying to move two places to the right or left and
            // The king is not moving up or down, then they are trying to castle.
            if (Math.abs(start.charAt(0) - end.charAt(0)) == 2 && start.charAt(1) == end.charAt(1)){
                // We have to place the rook. The king will be placed later.
                // Represent the columns that the rooks are on.

                int row = 7;    // Means black is castling
                if (startPiece.isWhiteTeam()){
                    row = 0;    // Means white is castling
                }
                int col = 7;    // Means castle right.
                int mult = -2;
                if (start.charAt(0) - end.charAt(0) > 0){
                    col = 0;    // Means left side.
                    mult = 3;
                }
                // Do move on rook.
                cb.getPieceAt(cb.convertToIndex(col, row)).setMoved();
                doMove(cb.convertToIndex(col, row), cb.convertToIndex(col + mult, row));

            }
        }
        // Set ending tile's piece to the starting piece.
        endTile.setPiece(startPiece);
        removePieceAt(start);
    }

    /** The promotion string can either be 'q', 'n', 'b', 'r' indicating desired
     * piece to promote */
    public void doMove(String start, String end, String promotion){
        if (!promotion.isEmpty()){
            cb.setEnPassant("");
            Piece startPiece = cb.getPieceAt(start);
            Tile endTile = cb.getTile(end);
            startPiece = switch (promotion) {
                case "q" -> new Queen(startPiece.isWhiteTeam());
                case "b" -> new Bishop(startPiece.isWhiteTeam());
                case "r" -> new Rook(startPiece.isWhiteTeam());
                case "n" -> new Knight(startPiece.isWhiteTeam());
                default -> startPiece;
            };
            // Set ending tile's piece to the starting piece.
            endTile.setPiece(startPiece);
            removePieceAt(start);
        } else {
            doMove(start, end);
        }
    }

    /** Removes the piece and makes it nothing at the index. Does not
     * check if this is a valid index. The check should be done before. */
    private void removePieceAt(String index){
        cb.setPieceAt(new Nothing(true), index);
    }

    public ChessBoard getChessBoard(){
        return cb;
    }

    /** returns true if it is white's turn. False if black's turn. */
    public boolean isWhiteTurn(){
        return whiteTurn;
    }

    /** Get all possible moves for either white or black. */
    public List<String> allPossibleMoves(boolean forWhite){
        Set<String> piecesSet = cb.getPiecesSet(forWhite);
        List<String> possibleMoves = new ArrayList<>();
        for (String piece : piecesSet){
            for (String move : cb.movesSet(piece)){
                String moveString = piece + " -> " + move;

                if (Piece.isPawn(cb.getPieceAt(piece)) && (move.charAt(1) == '8' || move.charAt(1) == '1')){
                    List<String> promotionPieces = List.of("=q", "=n", "=b", "=r");
                    for (String promotion : promotionPieces){
                        possibleMoves.add(moveString + promotion);
                    }
                } else {
                    possibleMoves.add(moveString);
                }
            }
        }
        return possibleMoves;
    }

    /** Returns a list of all the moves made so far. */
    public List<String> movesList(){
        return movesList;
    }

    /** Sets who makes the next move on the chess board */
    public void setTurn(boolean whiteTurn){
        this.whiteTurn = whiteTurn;
    }

}
