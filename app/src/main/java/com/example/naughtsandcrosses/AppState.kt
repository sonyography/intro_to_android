package com.example.naughtsandcrosses

class AppState {
    val boardState = BoardState()

    var lastWinner: Player? = null
        private set

    var currentPlayer = Player.Naughts
        private set

    var currentlyPlaying = true
        private set

    fun tileClicked(row: Int, col: Int) {
        // If we aren't playing right now, do nothing
        if (!currentlyPlaying) {
            return
        }
        // If a player has already claimed this tile, do nothing
        if (boardState.getPlayerAtPosition(row, col) != null) {
            return
        }
        // Otherwise, the current player can claim this tile
        boardState.setPlayerAtPosition(row, col, currentPlayer)

        // Now that we have updated the board we can query it's current state
        if (boardState.playerHasWon(currentPlayer)) {
            // If the current player has won, we can stop playing, and set the current player as the last winner
            currentlyPlaying = false
            lastWinner = currentPlayer
        } else if (boardState.noFreeTiles()) {
            // Otherwise, if there are no tiles left to claim, it is a draw
            currentlyPlaying = false
            lastWinner = null
        }

        currentPlayer = nextPlayer()
    }

    private fun nextPlayer(): Player {
        // Write code to switch to the next player
        return currentPlayer
    }
}
