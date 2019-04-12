package com.example.naughtsandcrosses

class BoardState {
    private val boardState = arrayOf(
        arrayOf<Player?>(null, null, null),
        arrayOf<Player?>(null, null, null),
        arrayOf<Player?>(null, null, null)
    )

    fun getPlayerAtPosition(row: Int, col: Int): Player? {
        return boardState[row - 1][col - 1]
    }

    fun setPlayerAtPosition(row: Int, col: Int, player: Player) {
        boardState[row - 1][col - 1] = player
    }

    fun playerHasWon(player: Player): Boolean {
        return winningHorizontally(player)
                || winningVertically(player)
                || winningDiagonally(player)
    }

    private fun winningHorizontally(player: Player): Boolean {
        for (num in 0..2) {
            if (boardState[num][0] == player &&
                boardState[num][1] == player &&
                boardState[num][2] == player
            ) {
                return true
            }
        }
        return false
    }

    private fun winningVertically(player: Player): Boolean {
        for (num in 0..2) {
            if (boardState[0][num] == player &&
                boardState[1][num] == player &&
                boardState[2][num] == player
            ) {
                return true
            }
        }
        return false
    }

    private fun winningDiagonally(player: Player): Boolean {
        if (boardState[0][0] == player &&
            boardState[1][1] == player &&
            boardState[2][2] == player
        ) {
            return true
        }
        if (boardState[0][2] == player &&
            boardState[1][1] == player &&
            boardState[2][0] == player
        ) {
            return true
        }
        return false
    }

    fun clearBoard() {
        for (row in boardState) {
            for (cellIndex in 0..2) {
                row[cellIndex] = null
            }
        }
    }

    fun noFreeTiles(): Boolean {
        for (row in boardState) {
            for (player in row) {
                if (player == null) {
                    return false
                }
            }
        }
        return true
    }
}