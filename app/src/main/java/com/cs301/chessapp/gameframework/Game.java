package com.cs301.chessapp.gameframework;

import com.cs301.chessapp.gameframework.actionMessage.GameAction;
import com.cs301.chessapp.gameframework.infoMessage.GameState;
import com.cs301.chessapp.gameframework.players.GamePlayer;

/**
 * To support remote play, this game framework has two types of Games: local
 * games and remote games that are represented by a proxy. Both types adhere to
 * this common interface.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Eric Imperio
 * @version January 2020
 * @see LocalGame
 * @see ProxyGame
 */

public interface Game {
    /**
     * starts the game
     *
     * @param players
     * 			the players who are in the game
     */
    void start(GamePlayer[] players);

    /**
     * sends the given action to the Game object.
     *
     * @param action
     *            the action to send
     */
    void sendAction(GameAction action);

    /**
     * returns the current gameState.
     *
     * @return GameState
     */
    GameState getGameState();

}
