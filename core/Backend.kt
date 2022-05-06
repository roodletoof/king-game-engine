package king_game_engine.core

import king_game_engine.fillRect
import king_game_engine.geometry.Vector2
import king_game_engine.performRevert
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.min


/**
 * Calls update and draw methods of given Game
 */
class Backend(width: Int, height: Int, private val fps: Int, private val game: King) : JFrame(), ActionListener, KeyListener {
    private val panel = GamePanel(width, height, game)
    private val timer = javax.swing.Timer(1000 / fps, this)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        add(panel)
        addKeyListener(this)
        pack()
        setLocationRelativeTo(null)
        isVisible = true
        //isResizable = false
        timer.start()
    }

    fun keyIsDown(keyCode: Int) = thisFrameKeys.getOrDefault(keyCode, false)
    fun keyJustPressed(keyCode: Int) =  keyIsDown(keyCode) && !keyWasDown(keyCode)
    fun keyJustReleased(keyCode: Int) = !keyIsDown(keyCode) && keyWasDown(keyCode)

    override fun actionPerformed(e: ActionEvent?) {
        if (e == null) { return }
        if (e.source == timer) {

            game.update(calculateDt())
            panel.repaint()

            for ((keyCode, value) in thisFrameKeys.entries) { // Needed to make Input methods work.
                lastFrameKeys[keyCode] = value
            }
        }
    }

    private class GamePanel(
        private val preferredWidth: Int,
        private val preferredHeight: Int,
        private val game: King
    ) : JPanel() {
        override fun getPreferredSize(): Dimension {
            return Dimension(preferredWidth, preferredHeight)
        }
        override fun paint(canvas: Graphics) {
            canvas as Graphics2D

            // First reposition and scale gave view
            val scale = min(
                width / preferredWidth.toDouble(),
                height / preferredHeight.toDouble()
            )

            val middleCord = Vector2(width, height) / 2.0
            val scaledSize = Vector2(preferredWidth, preferredHeight) * scale
            val newOrigin = middleCord - scaledSize / 2.0

            canvas.performRevert {
                canvas.translate(newOrigin.x, newOrigin.y)
                canvas.scale(scale, scale)
                game.draw(canvas)
            }

            // Then draw black bars
            canvas.color = Color.BLACK

            if (newOrigin.x == newOrigin.y) { return }

            if (newOrigin.x > newOrigin.y) {
                val sideBarSize = newOrigin.copy()
                sideBarSize.yInt = height
                canvas.fillRect(Vector2(), sideBarSize.ceil())
                canvas.fillRect(Vector2(sideBarSize.x + scaledSize.x, 0), sideBarSize.ceil())
                return
            }

            val topBottomBarSize = newOrigin.copy()
            topBottomBarSize.xInt = width
            canvas.fillRect(Vector2(), topBottomBarSize.ceil())
            canvas.fillRect(Vector2(0, topBottomBarSize.y + scaledSize.y), topBottomBarSize.ceil())
        }
    }

    private fun calculateDt(): Double {
        return 1.0 / fps
    }

    private fun keyWasDown(keyCode: Int) = lastFrameKeys.getOrDefault(keyCode, false)


    private val thisFrameKeys = HashMap<Int, Boolean>()
    private val lastFrameKeys = HashMap<Int, Boolean>()

    override fun keyPressed(e: KeyEvent?) {
        if (e == null) { return }
        val keyCode = e.keyCode
        thisFrameKeys[keyCode] = true
    }

    override fun keyReleased(e: KeyEvent?) {
        if (e == null) { return }
        val keyCode = e.keyCode
        thisFrameKeys[keyCode] = false
    }

    override fun keyTyped(e: KeyEvent?) { }
}
