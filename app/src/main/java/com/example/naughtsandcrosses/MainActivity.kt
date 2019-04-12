package com.example.naughtsandcrosses

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

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

class MainActivity : AppCompatActivity() {

    private var winHistory = mutableListOf<Player?>()

    private var gameState = GameState.Playing
    private var player = Player.Naughts

    private val boardState = listOf(
        mutableListOf(TileState.Empty, TileState.Empty, TileState.Empty),
        mutableListOf(TileState.Empty, TileState.Empty, TileState.Empty),
        mutableListOf(TileState.Empty, TileState.Empty, TileState.Empty)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        row1Col1.setOnClickListener { tileClicked(1, 1) }
        row1Col2.setOnClickListener { tileClicked(1, 2) }
        row1Col3.setOnClickListener { tileClicked(1, 3) }
        row2Col1.setOnClickListener { tileClicked(2, 1) }
        row2Col2.setOnClickListener { tileClicked(2, 2) }
        row2Col3.setOnClickListener { tileClicked(2, 3) }
        row3Col1.setOnClickListener { tileClicked(3, 1) }
        row3Col2.setOnClickListener { tileClicked(3, 2) }
        row3Col3.setOnClickListener { tileClicked(3, 3) }

        restart.setOnClickListener { newGame() }
        invalidate()
    }

    private fun newGame() {
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
        invalidate()
    }

    private fun tileClicked(row: Int, col: Int) {
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
        invalidate()
    }

    private fun advanceGameState() {
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

    private fun playerWon(player: TileState): Boolean {
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

    private fun invalidate() {
        if (gameState == GameState.Playing) {
            restart.visibility = View.INVISIBLE
            message.text = when (player) {
                Player.Naughts -> getString(R.string.naught_turn)
                Player.Crosses -> getString(R.string.cross_turn)
            }

        } else {
            restart.visibility = View.VISIBLE
            message.text = when (winHistory.last()) {
                Player.Naughts -> getString(R.string.naught_win)
                Player.Crosses -> getString(R.string.cross_win)
                null -> getString(R.string.draw)
            }
        }
        setImage(row1Col1, getImage(1,1))
        setImage(row1Col2, getImage(1,2))
        setImage(row1Col3, getImage(1,3))
        setImage(row2Col1, getImage(2,1))
        setImage(row2Col2, getImage(2,2))
        setImage(row2Col3, getImage(2,3))
        setImage(row3Col1, getImage(3,1))
        setImage(row3Col2, getImage(3,2))
        setImage(row3Col3, getImage(3,3))
    }

    private fun setImage(tile: CardView, image: Drawable?) {
        tile.removeAllViews()
        val imageView = ImageView(this).apply {
            setImageDrawable(image)
            setPadding(15,15,15,15)
        }
        tile.addView(imageView)
    }

    private fun getImage(row: Int, col: Int): Drawable? = when (boardState[row - 1][col - 1]) {
        TileState.Empty -> getDrawable(R.color.white)
        TileState.Naught -> getDrawable(R.drawable.ic_circle)
        TileState.Cross -> getDrawable(R.drawable.ic_cross)
    }
}
