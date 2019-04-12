package com.example.naughtsandcrosses

class AppState {
    private val winHistory = mutableListOf<Player?>()
    val boardState = BoardState()
    var currentPlayer = startingPlayer(getLastWinner())
    var currentlyPlaying = true
        private set

    fun getLastWinner(): Player? {
        if (winHistory.any()) {
            return winHistory.last()
        } else {
            return null
        }
    }

    fun startNewGame() {
        currentPlayer = startingPlayer(getLastWinner())
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
            winHistory.add(currentPlayer)
        } else if (boardState.noFreeTiles()) {
            currentlyPlaying = false
            winHistory.add(null)
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

    private fun startingPlayer(lastWinner: Player?): Player {
        if (lastWinner == null) {
            return Player.Naughts
        } else {
            return nextPlayer(lastWinner)
        }
    }
}
