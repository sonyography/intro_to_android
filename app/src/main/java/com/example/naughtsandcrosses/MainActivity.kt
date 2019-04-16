package com.example.naughtsandcrosses

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val state = AppState()

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

        restart.setOnClickListener { startNewGame() }

        invalidate()
    }

    private fun startNewGame() {
        state.startNewGame()

        invalidate()
    }

    private fun tileClicked(row: Int, col: Int) {
        state.tileClicked(row, col)

        invalidate()
    }

    private fun invalidate() {
        setMessage()

        showOrHideStartButton()

        setTileImage(row1Col1, 1, 1)
        setTileImage(row1Col2, 1, 2)
        setTileImage(row1Col3, 1, 3)
        setTileImage(row2Col1, 2, 1)
        setTileImage(row2Col2, 2, 2)
        setTileImage(row2Col3, 2, 3)
        setTileImage(row3Col1, 3, 1)
        setTileImage(row3Col2, 3, 2)
        setTileImage(row3Col3, 3, 3)
    }

    private fun showOrHideStartButton() {
        if (state.currentlyPlaying) {
            restart.visibility = View.INVISIBLE
        } else {
            restart.visibility = View.VISIBLE
        }
    }

    private fun setMessage() {
        if (state.currentlyPlaying) {
            message.text = getCurrentPlayerMessage()
        } else {
            message.text = getWinnerText()
        }
    }

    private fun getCurrentPlayerMessage(): String {
        if (state.currentPlayer == Player.Naughts) {
            return getString(R.string.naught_turn)
        } else {
            return getString(R.string.cross_turn)
        }
    }

    private fun getWinnerText(): String {
        if (state.getLastWinner() == Player.Naughts) {
            return getString(R.string.naught_win)
        } else if (state.getLastWinner() == Player.Crosses) {
            return getString(R.string.cross_win)
        } else {
            return getString(R.string.draw)
        }
    }

    private fun setTileImage(tile: ImageView, row: Int, col: Int) {
        val imageToUse = getPlayerImageForTile(row, col)

        tile.setImageDrawable(imageToUse)
    }

    private fun getPlayerImageForTile(row: Int, col: Int): Drawable? {
        val player = state.boardState.getPlayerAtPosition(row, col)
        if (player == Player.Naughts) {
            return getDrawable(R.drawable.ic_circle)
        } else if (player == Player.Crosses) {
            return getDrawable(R.drawable.ic_cross)
        } else {
            return getDrawable(R.color.white)
        }
    }
}
