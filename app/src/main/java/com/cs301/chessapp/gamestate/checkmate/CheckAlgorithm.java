package com.cs301.chessapp.gamestate.checkmate;

import com.cs301.chessapp.gameframework.players.GamePlayer;
import com.cs301.chessapp.gamestate.ChessGameState;
import com.cs301.chessapp.gamestate.chessboard.ChessMove;
import com.cs301.chessapp.gamestate.chessboard.ChessTile;
import com.cs301.chessapp.gamestate.pieces.Piece;

import java.util.ArrayList;

public class CheckAlgorithm {
    private final int _playerId;
    private int _kingRow;
    private int _kingCol;
    private ArrayList<ChessMove> _checkedMoves;

    public CheckAlgorithm(int playerId) {
        this._playerId = playerId;
        this._checkedMoves = new ArrayList<>();
    }

    /**
     * moveIsCheck
     *
     * This method checks if a provided move will place the king in check. It
     * references the list of checkable moves for the current position of the
     * king.
     *
     * @param row
     * @param col
     * @return
     */
    public boolean moveIsCheck(int row, int col) {
        for (ChessMove move : _checkedMoves) {
            if (move.getEndRow() == row && move.getEndCol() == col) {
                return false;
            }
        }
        return true;
    }

    /**
     * kingInCheck
     *
     * This method checks if the current position of the king is in check. It
     * references the list of checkable moves for the current position of the
     * king.
     *
     * @return
     */
    public boolean kingInCheck() {
        for (ChessMove move : _checkedMoves) {
            if (move.getEndRow() == _kingRow && move.getEndCol() == _kingCol) {
                return true;
            }
        }
        return false;
    }

    /**
     * setPosition
     *
     * This method resets the position of the king and recalculates checkable
     * moves.
     *
     * @param gamestate
     * @param kingRow
     * @param kingCol
     */
    public void setPosition(ChessGameState gamestate, int kingRow, int kingCol) {
        _kingRow = kingRow;
        _kingCol = kingCol;
        _checkedMoves.clear();

        Piece currPiece;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                currPiece = gamestate.getPiece(i, j);
                if (currPiece != null && currPiece.getPlayerId() != _playerId) {
                    _checkedMoves.addAll(currPiece.getChecks(gamestate));
                }
            }
        }
    }

    // todo: redo test move for check

    public static boolean testMove(ChessGameState gamestate, ChessMove move) {
        ChessGameState tempState = new ChessGameState(gamestate);

        ChessTile tempFrom = tempState.getTile(move.getStartRow(), move.getStartCol());
        ChessTile tempTo = tempState.getTile(move.getEndRow(), move.getEndCol());

        tempFrom.setPiece(null);
        tempTo.setPiece(tempFrom.getPiece());

        return tempState.inCheck(move.getPlayer().getPlayerTurn());
    }


    // todo: redo checkmate checking

    public static boolean isCheckmate(ChessGameState gamestate, GamePlayer player) {
        Piece currPiece;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                currPiece = gamestate.getPiece(i, j);
                if (currPiece != null && currPiece.getPlayerId() == player.getPlayerTurn()) {
                    for (ChessMove m : currPiece.getMoves(gamestate, player)) {
                        if (!testMove(gamestate, m)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
