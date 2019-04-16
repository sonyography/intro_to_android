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
        if (!currentlyPlaying) {
            return
        }
        if (boardState.getPlayerAtPosition(row, col) != null) {
            return
        }
        boardState.setPlayerAtPosition(row, col, currentPlayer)

        if (boardState.playerHasWon(currentPlayer)) {
            currentlyPlaying = false
            lastWinner = currentPlayer
        } else if (boardState.noFreeTiles()) {
            currentlyPlaying = false
            lastWinner = null
        }

        currentPlayer = nextPlayer(currentPlayer)
    }

    private fun nextPlayer(player: Player): Player {
        if (player == Player.Crosses) {
            return Player.Naughts
        } else {
            return Player.Crosses
        }
    }

    private fun startingPlayer(): Player {
        val last = lastWinner
        if (last == null) {
            return nextPlayer(currentPlayer)
        } else {
            return nextPlayer(last)
        }
    }
}
