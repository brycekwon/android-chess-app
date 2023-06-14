package com.cs301.chessapp.gamestate.pieces;

import java.util.ArrayList;

import com.cs301.chessapp.gameframework.players.GamePlayer;

import com.cs301.chessapp.gamestate.ChessGameState;
import com.cs301.chessapp.gamestate.checkmate.CheckAlgorithm;
import com.cs301.chessapp.gamestate.chessboard.ChessMove;

/**
 * King class
 * <p>
 * This class represents a king in the game of chess. The king can move one
 * square in any direction. It cannot jump over other pieces. It captures
 * pieces by moving to their square. It is a subclass of Piece.
 *
 * @author Bryce Kwon
 * @author Christopher Yee
 * @author Magnus Graham
 * @author Marshall Zhang
 * @version Spring 2023
 */
public class King extends Piece {

    // this variable keeps track of whether the king is in check
    private final CheckAlgorithm _check;

    /**
     * King default constructor
     * <p>
     * This constructor initializes a king assigned to a player.
     *
     * @param playerId      the player who owns the king
     */
    public King(int playerId) {
        // invoke superclass constructor
        super(playerId, "King");

        // initialize check algorithm for this king
        _check = new CheckAlgorithm(playerId);
    }

    /**
     * getMoves
     * <p>
     * This method calculates all valid moves for the king at its current
     * position on the chessboard.
     *
     * @param gamestate     current state of the game
     * @param player        player assigned to this piece
     * @return              valid moves for the king
     */
    @Override
    public ArrayList<ChessMove> getMoves(ChessGameState gamestate, GamePlayer player) {
        // initialize an empty list of valid moves
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // traverse through all directions
        for (int i = -1; i <= 1; i++) {
            for (int j = - 1; j <= 1; j++) {
                if (hasValidBounds(_row + i, _col + j) && _check.moveIsCheck(_row + i, _col + j)) {
                    if (gamestate.getPiece(_row + i, _col + j) == null) {
                        validMoves.add(new ChessMove(player, _row, _col, _row + i, _col + j));
                    } else if (gamestate.getPiece(_row + i, _col + j).getPlayerId() != _playerId) {
                        validMoves.add(new ChessMove(player, _row, _col, _row + i, _col + j));
                    }
                }
            }
        }

        // check for castling for white player
        if (_playerId == ChessGameState.PLAYER_1) {
            Piece kingSideRook = gamestate.getPiece(7, 7);
            Piece queenSideRook = gamestate.getPiece(7, 0);

            if (_row == 7 && _col == 4) {
                if (kingSideRook instanceof Rook && kingSideRook.getPlayerId() == _playerId && (gamestate.getPiece(7, 5) == null && gamestate.getPiece(7, 6) == null)) {
                        validMoves.add(new ChessMove(player, _row, _col, 7, 7));

                }
                if (queenSideRook instanceof Rook && queenSideRook.getPlayerId() == _playerId && (gamestate.getPiece(7, 1) == null && gamestate.getPiece(7, 2) == null && gamestate.getPiece(7, 3) == null)) {
                        validMoves.add(new ChessMove(player, _row, _col, 7, 0));

                }
            }
        }

        // check for castling for black player
        else if (_playerId == ChessGameState.PLAYER_2) {
            Piece kingSideRook = gamestate.getPiece(0, 7);
            Piece queenSideRook = gamestate.getPiece(0, 0);

            if (_row == 0 && _col == 4) {
                if (kingSideRook instanceof Rook && kingSideRook.getPlayerId() == _playerId && (gamestate.getPiece(0, 5) == null && gamestate.getPiece(0, 6) == null)) {
                        validMoves.add(new ChessMove(player, _row, _col, 0, 7));

                }
                if (queenSideRook instanceof Rook && queenSideRook.getPlayerId() == _playerId && (gamestate.getPiece(0, 1) == null && gamestate.getPiece(0, 2) == null && gamestate.getPiece(0, 3) == null)) {
                        validMoves.add(new ChessMove(player, _row, _col, 0, 0));

                }
            }
        }

        // return list of valid moves
        return validMoves;
    }

    /**
     * getChecks
     * <p>
     * This methods calculates all checkable moves for the king at its
     * current position on the chessboard. This method is used to determine
     * whether the king is putting the opposing player's king in check.
     *
     * @param gamestate     current state of the game
     * @return              checkable moves for the king
     */
    @Override
    public ArrayList<ChessMove> getChecks(ChessGameState gamestate) {
        // initialize an empty list of valid moves
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // traverse through all directions
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (hasValidBounds(_row + i, _col + j) && _check.moveIsCheck(_row + i, _col + j)) {
                    validMoves.add(new ChessMove(null, _row, _col, _row + i, _col + j));
                }
            }
        }

        // check for castling for white player
        if (_playerId == ChessGameState.PLAYER_1) {
            Piece kingSideRook = gamestate.getPiece(7, 7);
            Piece queenSideRook = gamestate.getPiece(7, 0);

            if (_row == 7 && _col == 4) {
                if (kingSideRook instanceof Rook && kingSideRook.getPlayerId() == _playerId && (gamestate.getPiece(7, 5) == null && gamestate.getPiece(7, 6) == null)) {
                        validMoves.add(new ChessMove(null, _row, _col, 7, 7));

                }
                if (queenSideRook instanceof Rook && queenSideRook.getPlayerId() == _playerId && (gamestate.getPiece(7, 1) == null && gamestate.getPiece(7, 2) == null && gamestate.getPiece(7, 3) == null)) {
                        validMoves.add(new ChessMove(null, _row, _col, 7, 0));

                }
            }
        }

        // check for castling for black player
        else if (_playerId == ChessGameState.PLAYER_2) {
            Piece kingSideRook = gamestate.getPiece(0, 7);
            Piece queenSideRook = gamestate.getPiece(0, 0);

            if (_row == 0 && _col == 4) {
                if (kingSideRook instanceof Rook && kingSideRook.getPlayerId() == _playerId && (gamestate.getPiece(0, 5) == null && gamestate.getPiece(0, 6) == null)) {
                        validMoves.add(new ChessMove(null, _row, _col, 0, 7));

                }
                if (queenSideRook instanceof Rook && queenSideRook.getPlayerId() == _playerId && (gamestate.getPiece(0, 1) == null && gamestate.getPiece(0, 2) == null && gamestate.getPiece(0, 3) == null)) {
                        validMoves.add(new ChessMove(null, _row, _col, 0, 0));

                }
            }
        }

        // return list of valid moves
        return validMoves;
    }

    /**
     * inCheck
     * <p>
     * This method determines whether the king is in check.
     *
     * @return      true if king is in check, false otherwise
     */
    public boolean inCheck() {
        return _check.kingInCheck();
    }

    /**
     * update
     * <p>
     * This method updates the gamestate for the check object.
     *
     * @param gamestate     current state of the game
     */
    public void update(ChessGameState gamestate) {
        _check.setPosition(gamestate, _row, _col);
    }
}
