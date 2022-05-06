package king_game_engine

import king_game_engine.geometry.Vector2
import java.awt.Color
import java.awt.Graphics2D

/**
 * Stores current transform, performs given function, and finally sets transform to original state.
 * @param lambda The function to perform.
 */
fun Graphics2D.performRevert(lambda: func) {
    val originalTransform = transform
    lambda()
    transform = originalTransform
}


// Color parameter extensions //////////////
fun Graphics2D.fillRect(x: Int, y: Int, width: Int, height: Int, color: Color) {
    setColor(color)
    fillRect(x, y, width, height)
}


// Vector2 support /////////////////////////
fun Graphics2D.fillRect(position: Vector2, size: Vector2) {
    fillRect(position.xInt, position.yInt, size.xInt, size.yInt)
}
fun Graphics2D.fillRect(position: Vector2, size: Vector2, color: Color) {
    fillRect(position.xInt, position.yInt, size.xInt, size.yInt, color)
}

// Helper functions ////////////////////////

private typealias func = () -> Unit

/**
 * Helper function with intended use of performing some transformation on a graphics object,
 * then calling a given lambda, and finally revert the transformation.
 */
private fun Graphics2D.performWith(lambda: func, transformFunc: func) {
    val originalTransform = transform
    transformFunc()
    lambda()
    transform = originalTransform
}

