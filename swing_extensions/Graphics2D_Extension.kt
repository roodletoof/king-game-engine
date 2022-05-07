package king_game_engine.swing_extensions

import king_game_engine.geometry.AABB
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

fun Graphics2D.translate(amount: Vector2) {
    translate(amount.x, amount.y)
}

fun Graphics2D.scale(amount: Vector2) {
    scale(amount.x, amount.y)
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


// AABB support ///
fun Graphics2D.drawRect(aabb: AABB) {
    drawRect(
        aabb.west.toInt(),
        aabb.north.toInt(),
        aabb.size.xInt,
        aabb.size.yInt
    )
}



private typealias func = () -> Unit

