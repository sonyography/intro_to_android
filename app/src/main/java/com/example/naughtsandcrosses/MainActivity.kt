package com.example.naughtsandcrosses

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
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
        if (state.currentlyPlaying) {
            restart.visibility = View.INVISIBLE
            message.text = when (state.currentPlayer) {
                Player.Naughts -> getString(R.string.naught_turn)
                Player.Crosses -> getString(R.string.cross_turn)
            }

        } else {
            restart.visibility = View.VISIBLE
            message.text = when (state.getLastWinner()) {
                Player.Naughts -> getString(R.string.naught_win)
                Player.Crosses -> getString(R.string.cross_win)
                null -> getString(R.string.draw)
            }
        }
        setImage(row1Col1, getImage(1, 1))
        setImage(row1Col2, getImage(1, 2))
        setImage(row1Col3, getImage(1, 3))
        setImage(row2Col1, getImage(2, 1))
        setImage(row2Col2, getImage(2, 2))
        setImage(row2Col3, getImage(2, 3))
        setImage(row3Col1, getImage(3, 1))
        setImage(row3Col2, getImage(3, 2))
        setImage(row3Col3, getImage(3, 3))
    }

    private fun setImage(tile: CardView, image: Drawable?) {
        tile.removeAllViews()
        val imageView = ImageView(this).apply {
            setImageDrawable(image)
            setPadding(15, 15, 15, 15)
        }
        tile.addView(imageView)
    }

    private fun getImage(row: Int, col: Int): Drawable? = when (state.boardState.getPlayerAtPosition(row, col)) {
        null -> getDrawable(R.color.white)
        Player.Naughts -> getDrawable(R.drawable.ic_circle)
        Player.Crosses -> getDrawable(R.drawable.ic_cross)
    }
}
