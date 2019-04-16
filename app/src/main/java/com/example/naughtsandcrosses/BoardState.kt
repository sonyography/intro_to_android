package com.example.naughtsandcrosses

class BoardState {
    // We represent the board as a 2-dimensional array of Players
    private val boardState = arrayOf(
        arrayOf<Player?>(null, null, null),
        arrayOf<Player?>(null, null, null),
        arrayOf<Player?>(null, null, null)
    )

    fun getPlayerAtPosition(row: Int, col: Int): Player? {
        // We subtract 1 on each, because array indexes start at 0, not 1
        return boardState[row - 1][col - 1]
    }

    fun setPlayerAtPosition(row: Int, col: Int, player: Player) {
        // We subtract 1 on each, because array indexes start at 0, not 1
        boardState[row - 1][col - 1] = player
    }

    fun playerHasWon(player: Player): Boolean {
        return winningHorizontally(player) || winningVertically(player)
        // But what about diagonally!
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

    fun clearBoard() {
        // We loop over all tiles to set the value to empty
        for (row in boardState) {
            for (cellIndex in 0..2) {
                row[cellIndex] = null
            }
        }
    }

    fun noFreeTiles(): Boolean {
        // We loop over all tiles to see if unclaimed
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