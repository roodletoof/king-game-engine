package king_game_engine.swing_extensions

import king_game_engine.geometry.Vector2
import java.awt.Point
import java.awt.geom.AffineTransform

fun AffineTransform.transform(source: Vector2): Vector2 {
    val src = Point(source.xInt, source.yInt)
    val dst = Point()
    transform(src, dst)
    return Vector2(dst.x, dst.y)
}
fun AffineTransform.translate(amount: Vector2) {
    translate(amount.x, amount.y)
}
fun AffineTransform.scale(amount: Vector2) {
    scale(amount.x, amount.y)
}
fun AffineTransform.shear(amount: Vector2) {
    shear(amount.x, amount.y)
}