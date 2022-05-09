package king_game_engine.swing_extensions

import king_game_engine.geometry.Vector2
import java.awt.geom.AffineTransform

fun AffineTransform.transform(source: Vector2): Vector2 {
    val src = source.toDoubleArray()
    val dst = Vector2().toDoubleArray()

    transform(src, 0, dst, 0, 1)

    return Vector2(dst)
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