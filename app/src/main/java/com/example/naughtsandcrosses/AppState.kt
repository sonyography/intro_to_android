package com.example.naughtsandcrosses

data class AppState(
    private val winHistory: MutableList<Player?> = mutableListOf()
) {
    val boardState = BoardState()
    var currentPlayer = startingPlayer(getLastWinner())
    var currentlyPlaying = true
        private set

    fun getLastWinner() = if (winHistory.any()) winHistory.last() else null

    fun startNewGame() {
        currentPlayer = startingPlayer(winHistory.last())
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
        } else if (boardState.noTilesRemaining) {
            currentlyPlaying = false
            winHistory.add(null)
        }

        currentPlayer = otherPlayer(currentPlayer)
    }
}
