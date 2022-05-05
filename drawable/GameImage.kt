package sge.drawable

import sge.geometry.Vector2
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class GameImage(filename: String){
    private var img: BufferedImage? = null
    private val affineTransform = AffineTransform()

    init {
        try {
            img = ImageIO.read(File("src/main/resources/$filename"))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    fun draw(canvas: Graphics2D, position: Vector2 = Vector2()) {
        canvas.drawImage(img, position.x.toInt(), position.y.toInt(), null)
    }

    fun draw(canvas: Graphics2D, x: Number = 0, y: Number = 0) {
        draw(canvas, Vector2(x, y))
    }

}