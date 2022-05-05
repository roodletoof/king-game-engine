package sge.core

import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.JPanel


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
        isResizable = false
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
        override fun paint(g: Graphics) {
            game.draw(g as Graphics2D)
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
