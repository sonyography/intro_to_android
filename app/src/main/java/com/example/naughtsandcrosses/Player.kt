package com.example.naughtsandcrosses

enum class Player {
    Naughts,
    Crosses
}

fun otherPlayer(player: Player) = when (player) {
    Player.Crosses -> Player.Naughts
    Player.Naughts -> Player.Crosses
}

fun startingPlayer(lastWinner: Player?) = when (lastWinner) {
    null -> Player.Naughts
    else -> otherPlayer(lastWinner)
}