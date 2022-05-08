package king_game_engine.drawable

import king_game_engine.geometry.Vector2
import king_game_engine.swing_extensions.*
import java.awt.Graphics2D
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
        anchor: Vector2 = Vector2(img.width, img.height) / 2.0 + 0.5
    ) {

        val at = AffineTransform()
        at.rotate(rotation)
        at.scale(scale)
        at.shear(shear)

        canvas.performRevert {
            canvas.translate(position - at.transform(anchor) )
            canvas.drawImage(img, at, null)
        }
    }
}