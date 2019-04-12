package com.example.naughtsandcrosses

enum class TileState {
    Empty,
    Naught,
    Cross
}

enum class GameState {
    Playing,
    Finished
}

enum class Player {
    Naughts,
    Crosses,
}

data class AppState(
    val winHistory: MutableList<Player?>,
    var gameState: GameState,
    var player: Player,
    val boardState: List<MutableList<TileState>>
){
    fun advanceGameState() {
        if (player == Player.Crosses) {
            player = Player.Naughts
        } else {
            player = Player.Crosses
        }
        if (playerWon(TileState.Naught)) {
            gameState = GameState.Finished
            winHistory.add(Player.Naughts)
        } else if (playerWon(TileState.Cross)) {
            gameState = GameState.Finished
            winHistory.add(Player.Naughts)
        } else {
            for (row in 0..2) {
                for (col in 0..2) {
                    if (boardState[row][col] == TileState.Empty) {
                        return
                    }
                }
            }
            gameState = GameState.Finished
            winHistory.add(null)
        }
    }

    fun resetBoard() {
        if (winHistory.any()) {
            if (winHistory.last() == Player.Naughts) {
                player = Player.Crosses
            } else {
                player = Player.Naughts
            }
        } else {
            player = Player.Naughts
        }
        gameState = GameState.Playing
        for (row in 0..2) {
            for (col in 0..2) {
                boardState[row][col] = TileState.Empty
            }
        }
    }


    fun playerWon(player: TileState): Boolean {
        for (num in 0..2) {
            if (boardState[num][0] == player &&
                boardState[num][1] == player &&
                boardState[num][2] == player
            ) {
                return true
            }
            if (boardState[0][num] == player &&
                boardState[1][num] == player &&
                boardState[2][num] == player
            ) {
                return true
            }
        }
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

    fun tileClicked(row: Int, col: Int) {
        if (gameState == GameState.Finished) {
            return
        }
        if (boardState[row - 1][col - 1] != TileState.Empty) {
            return
        }
        if (player == Player.Crosses) {
            boardState[row - 1][col - 1] = TileState.Cross
        } else {
            boardState[row - 1][col - 1] = TileState.Naught
        }
        advanceGameState()
    }
}
