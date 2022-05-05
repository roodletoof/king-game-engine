package king_game_engine.core

import java.awt.Graphics2D


/**
 * The superclass of all King games.
 */
abstract  class King (val screenWidth: Int, val screenHeight: Int, val fps: Int) {
    private var backend: Backend? = null


    /**
     * Called once per frame before drawing.
     * @param dt Number of seconds since last update call.
     * Use this to get movement speeds independent of framerate.
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
        return this
    }
}