package com.example.naughtsandcrosses

class AppState {
    val boardState = BoardState()

    var lastWinner: Player? = null
        private set

    var currentPlayer = Player.Naughts
        private set

    var currentlyPlaying = true
        private set

    fun startNewGame() {
        currentPlayer = startingPlayer()
        currentlyPlaying = true
        boardState.clearBoard()
    }

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
            // If the current player has won, we can stop playing, and set the curent player as the last winner
            currentlyPlaying = false
            lastWinner = currentPlayer
        } else if (boardState.noFreeTiles()) {
            // Otherwise, if there are no tiles left to claim, it is a draw
            currentlyPlaying = false
            lastWinner = null
        }

        currentPlayer = nextPlayer(currentPlayer)
    }

    private fun nextPlayer(player: Player): Player {
        // Here we swap the current player
        if (player == Player.Crosses) {
            return Player.Naughts
        } else {
            return Player.Crosses
        }
    }

    private fun startingPlayer(): Player {
        // Here we choose a starting player based on the last winner, the previous winner goes second
        val last = lastWinner
        if (last == null) {
            return nextPlayer(currentPlayer)
        } else {
            return nextPlayer(last)
        }
    }
}
