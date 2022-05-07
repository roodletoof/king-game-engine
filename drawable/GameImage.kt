package king_game_engine.drawable

import king_game_engine.geometry.Vector2
import java.awt.Graphics2D
import java.awt.Point
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class GameImage(filename: String){
    private var img: BufferedImage

    init {
        try {
            img = ImageIO.read(File("src/main/resources/$filename"))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    fun draw(
        canvas: Graphics2D,
        position: Vector2 = Vector2(),
        rotation: Double = 0.0,
        shear: Vector2 = Vector2(),
        scale: Vector2 = Vector2(1, 1),
        anchor: Vector2 = Vector2(img.width, img.height) / 2
    ) {
        fun AffineTransform.transform(source: Vector2): Vector2 {
            val src = Point(source.xInt, source.yInt)
            val dst = Point()
            transform(src, dst)
            return Vector2(dst.x, dst.y)
        }
        fun AffineTransform.translate(amount: Vector2) {
            translate(amount.x, amount.y)
        }

        val at = AffineTransform()
        at.translate(position - anchor + anchor * (scale - 1))

        at.rotate(rotation, anchor.x, anchor.y)
        at.scale(scale.x, scale.y)
        at.translate(anchor)
        at.shear(shear.x, shear.y)
        at.translate(-anchor)


        canvas.drawImage(img, at, null)
    }



}