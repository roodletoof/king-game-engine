package king_game_engine.drawable

import king_game_engine.geometry.ImmutableVector2
import king_game_engine.geometry.Vector2
import king_game_engine.swing_extensions.*
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

/**
 * Exists to make drawing images with transforms easier.
 */
class GameImage(filePath: String){
    private var img: BufferedImage

    init {
        try {
            img = ImageIO.read(File(filePath))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    val size = ImmutableVector2(img.width, img.height)

    /**
     * Draw this GameImage to given canvas.
     * All if you use any of the transformations, the images anchor will still be drawn at the given position.
     * @param canvas The Graphics2D object to draw to.
     * @param position Where you want the image to be drawn.
     * @param rotation How much should the image be rotated in radians.
     * @param shear How much should the image be sheared in the x- and y-axis.
     * @param scale The scale of the image in the x- and y-axis.
     * @param anchor The location within the image that should be placed on the given position.
     */
    fun draw(
        canvas: Graphics2D,
        position: Vector2 = ImmutableVector2(),
        rotation: Double = 0.0,
        shear: Vector2 = ImmutableVector2(),
        scale: Vector2 = ImmutableVector2(1, 1),
        anchor: Vector2 = ImmutableVector2(size / 2 + 0.5)
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