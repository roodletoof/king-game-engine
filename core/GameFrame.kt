package king_game_engine.core

import king_game_engine.communication.Signal
import king_game_engine.geometry.Vector2
import king_game_engine.swing_extensions.fillRect
import king_game_engine.swing_extensions.performRevert
import king_game_engine.swing_extensions.scale
import king_game_engine.swing_extensions.translate
import java.awt.*
import java.awt.RenderingHints.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.min


/**
 * Uses swing library to draw to call update and draw methods in fixed intervals of given King game.
 * Also provides game with easier to use input methods.
 * Handles window scaling by assuming the user always wants to keep the same aspect ratio as the original screen resolution.
 */
class GameFrame(width: Int, height: Int, private val fps: Int, private val game: King) : JFrame(), ActionListener, KeyListener {
    private val panel = GamePanel(width, height, game)
    private val timer = javax.swing.Timer(1000 / fps, this)
    val mousePositionCalculated = Signal<(newPosition: Vector2) -> Unit>()
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        add(panel)
        addKeyListener(this)
        pack()
        setLocationRelativeTo(null)
        isVisible = true
        timer.start()
    }

    fun keyIsDown(keyCode: Int) = thisFrameKeys.getOrDefault(keyCode, false)
    fun keyJustPressed(keyCode: Int) =  keyIsDown(keyCode) && !keyWasDown(keyCode)
    fun keyJustReleased(keyCode: Int) = !keyIsDown(keyCode) && keyWasDown(keyCode)

    override fun actionPerformed(e: ActionEvent?) {
        if (e == null) { return }
        if (e.source == timer) {
            mousePositionCalculated.emit { it( panel.getMouseVector().makeImmutable() ) }

            game.update(calculateDt())
            panel.repaint()

            for ((keyCode, value) in thisFrameKeys.entries) { // Needed to make Input methods work.
                lastFrameKeys[keyCode] = value
            }
        }
    }

    private fun calculateDt(): Double {
        // Temporary solution. Seems to work better than using nanoTime or whatever it's called.
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

/**
 * Resizes drawing operations and moves the view to the middle of the screen.
 * Calls King.draw(canvas) Where canvas is a Graphics2D object that draws on this GamePanel.
 * Finally, draws black bars over sections of the GamePanel if the window aspect ratio is off.
 */
private class GamePanel(
    private val preferredWidth: Int,
    private val preferredHeight: Int,
    private val game: King
) : JPanel() {
    operator fun Point.minus(other: Point) = Vector2(this.x - other.x, this.y - other.y)

    /**
     * Get mouse location on screen.
     * Accounts for whether the window is repositioned or scaled.
     */
    fun getMouseVector(): Vector2 {
        val mouseLocation = MouseInfo.getPointerInfo().location
        val panelLocation = locationOnScreen
        val mouseLocationFromGamePanel = Vector2(
            mouseLocation.x - panelLocation.x,
            mouseLocation.y - panelLocation.y
        )
        val gameViewStats = calculateAndGetGameViewStats()

        val mouseLocationFromOrigin = mouseLocationFromGamePanel - gameViewStats.origin
        return mouseLocationFromOrigin / gameViewStats.scale
    }

    private val RENDER_HINTS = mapOf(
        KEY_ANTIALIASING to VALUE_ANTIALIAS_OFF,
        KEY_ALPHA_INTERPOLATION to VALUE_ALPHA_INTERPOLATION_SPEED,
        KEY_COLOR_RENDERING to VALUE_COLOR_RENDER_SPEED,
        KEY_DITHERING to VALUE_DITHER_ENABLE,
        KEY_INTERPOLATION to VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
        KEY_RENDERING to VALUE_RENDER_SPEED
    )


    override fun getPreferredSize(): Dimension {
        return Dimension(preferredWidth, preferredHeight)
    }


    /**
     * Three Vector2's representing different stats of the current game view
     * @param scale The scale of the game view compared to its original resolution.
     * @param scaledSize The actual size of the game view after scaling.
     * @param origin The position that becomes [0.0, 0.0] when drawing to the game view.
     */
    private data class GameViewStats(val scale: Vector2, val scaledSize: Vector2, val origin: Vector2)
    private fun calculateAndGetGameViewStats(): GameViewStats {
        val scale = Vector2(
            min(
                width / preferredWidth.toDouble(),
                height / preferredHeight.toDouble()
            )
        )
        val middleCord = Vector2(width, height) / 2
        val scaledSize = Vector2(preferredWidth, preferredHeight) * scale
        val origin = middleCord - scaledSize / 2
        return GameViewStats(scale, scaledSize, origin)
    }
    override fun paint(canvas: Graphics) {
        canvas as Graphics2D
        canvas.setRenderingHints(RENDER_HINTS)

        val (scale, scaledSize, newOrigin) = calculateAndGetGameViewStats()

        canvas.performRevert {
            canvas.translate(newOrigin)
            canvas.scale(scale)
            game.draw(canvas)
        }

        // Then draw black bars
        if (newOrigin.x == newOrigin.y) {
            return
        }

        canvas.color = Color.BLACK

        if (newOrigin.x > newOrigin.y) {
            newOrigin.yInt = height
            canvas.fillRect(Vector2(), newOrigin.ceil())
            canvas.fillRect(Vector2(newOrigin.x + scaledSize.x, 0), newOrigin.ceil())
            return
        }

        newOrigin.xInt = width
        canvas.fillRect(Vector2(), newOrigin.ceil())
        canvas.fillRect(Vector2(0, newOrigin.y + scaledSize.y), newOrigin.ceil())
    }
}
