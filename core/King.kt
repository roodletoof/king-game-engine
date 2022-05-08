package king_game_engine.core

import king_game_engine.geometry.Vector2
import java.awt.Graphics2D


/**
 * The superclass of all King games.
 * Goals:
 *  - Only expose the bare minimum from swing that is needed or useful to make a 2D game.
 *  - Let the user make the game without worrying about things like resizing the game view.
 *  - Giving easy access to keyboard and mouse input reading.
 */
abstract  class King (val screenWidth: Int, val screenHeight: Int, val fps: Int) {
    private var backend: Backend? = null

    /**
     * The current position of the mouse. Accounts for the current game-view scale and position.
     * The value will be [[screenWidth], [screenHeight]] if the mouse in the bottom-right corner
     * of the drawable section of the game panel.
     * The value will be [[0.0, 0.0]] if the mouse is in the top-left corner of the drawable section
     * of the game panel.
     */
    var mousePosition: Vector2 = Vector2()
        private set

    /**
     * Called once per frame before drawing.
     * @param dt Number of seconds since last update call.
     * Use this to get movement speeds independent of the chosen framerate.
     */
    abstract fun update(dt: Double)


    /**
     * Called once per frame after update.
     * @param canvas The canvas to draw to.
     * Use all the normal Graphics2D methods,
     * or one of the king_game_engine.graphics classes.
     */
    abstract fun draw(canvas: Graphics2D)

    private val gameNotRunning = java.lang.IllegalStateException("Can not check input while the game is not running.")
    fun keyIsDown(keyCode: Int) = backend?.keyIsDown(keyCode) ?: throw gameNotRunning
    fun keyJustPressed(keyCode: Int) = backend?.keyJustPressed(keyCode) ?: throw gameNotRunning
    fun keyJustReleased(keyCode: Int) = backend?.keyJustReleased(keyCode) ?: throw gameNotRunning

    /**
     * Starts the game.
     */
    fun run(): King {
        backend = Backend(screenWidth, screenHeight, fps, this)
        backend!!.mousePositionRead.connect { this.mousePosition = it }
        return this
    }
}