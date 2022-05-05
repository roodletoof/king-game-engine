package sge

import java.awt.*
import java.awt.font.FontRenderContext
import java.awt.font.GlyphVector
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageOp
import java.awt.image.ImageObserver
import java.awt.image.RenderedImage
import java.awt.image.renderable.RenderableImage
import java.text.AttributedCharacterIterator

/**
 * Expanded Graphics2D that adds the ability to temporarily apply a translation/rotation/scale/sheer,
 * perform the given lambda, and then revert the translation/rotation/scale/sheer
 */
class ExGraphics2D(private val g: Graphics2D) {
    //TODO CHECK THESE METHODS AND FIX THEM.
    //TODO FIND THE JAVADOC FOR Graphics2D AND PASTE THEM HERE.
    fun withTranslate(x: Int, y: Int, lambda: () -> Unit) {
        translate(x, y)
        lambda()
        translate(-x, -y)
    }

    fun withTranslate(tx: Double, ty: Double, lambda: () -> Unit) {
        translate(tx, ty)
        lambda()
        translate(-tx, -ty)
    }

    fun withRotate(theta: Double, lambda: () -> Unit) {
        rotate(theta)
        lambda()
        rotate(-theta)
    }

    fun withRotate(theta: Double, x: Double, y: Double, lambda: () -> Unit) {
        rotate(theta, x, y)
        lambda()
        rotate(-theta, -x, -y)
    }

    fun withScale(sx: Double, sy: Double, lambda: () -> Unit) {
        scale(sx, sy)
        lambda()
        scale(1.0/sx, 1.0/sy)
    }

    fun withShear(shx: Double, shy: Double, lambda: () -> Unit) {
        g.shear(shx, shy)
    }

    fun draw3DRect(x: Int, y: Int, width: Int, height: Int, raised: Boolean) {
        g.draw3DRect(x, y, width, height, raised)
    }

    fun fill3DRect(x: Int, y: Int, width: Int, height: Int, raised: Boolean) {
        g.fill3DRect(x, y, width, height, raised)
    }

    fun draw(s: Shape?) {
        g.draw(s)
    }

    fun drawImage(img: Image?, xform: AffineTransform?, obs: ImageObserver?): Boolean {
        return g.drawImage(img, xform, obs)
    }

    fun drawImage(img: BufferedImage?, op: BufferedImageOp?, x: Int, y: Int) {
        g.drawImage(img, op, x, y)
    }

    fun drawRenderedImage(img: RenderedImage?, xform: AffineTransform?) {
        g.drawRenderedImage(img, xform)
    }

    fun drawRenderableImage(img: RenderableImage?, xform: AffineTransform?) {
        g.drawRenderableImage(img, xform)
    }

    fun drawString(str: String?, x: Int, y: Int) {
        g.drawString(str, x, y)
    }

    fun drawString(str: String?, x: Float, y: Float) {
        g.drawString(str, x, y)
    }

    fun drawString(iterator: AttributedCharacterIterator?, x: Int, y: Int) {
        g.drawString(iterator, x, y)
    }

    fun drawString(iterator: AttributedCharacterIterator?, x: Float, y: Float) {
        g.drawString(iterator, x, y)
    }

    fun drawGlyphVector(g: GlyphVector?, x: Float, y: Float) {
        this.g.drawGlyphVector(g, x, y)
    }

    fun fill(s: Shape?) {
        g.fill(s)
    }

    fun hit(rect: Rectangle?, s: Shape?, onStroke: Boolean): Boolean {
        return g.hit(rect, s, onStroke)
    }

    val deviceConfiguration: GraphicsConfiguration
        get() = g.deviceConfiguration

    fun setRenderingHint(hintKey: RenderingHints.Key?, hintValue: Any?) {
        g.setRenderingHint(hintKey, hintValue)
    }

    fun getRenderingHint(hintKey: RenderingHints.Key?): Any {
        return g.getRenderingHint(hintKey)
    }

    fun addRenderingHints(hints: Map<*, *>?) {
        g.addRenderingHints(hints)
    }

    var renderingHints: Map<*, *>?
        get() = g.renderingHints
        set(hints) {
            g.setRenderingHints(hints)
        }

    fun translate(x: Int, y: Int) {
        g.translate(x, y)
    }

    fun translate(tx: Double, ty: Double) {
        g.translate(tx, ty)
    }

    fun rotate(theta: Double) {
        g.rotate(theta)
    }

    fun rotate(theta: Double, x: Double, y: Double) {
        g.rotate(theta, x, y)
    }

    fun scale(sx: Double, sy: Double) {
        g.scale(sx, sy)
    }

    fun shear(shx: Double, shy: Double) {
        g.shear(shx, shy)
    }

    fun transform(Tx: AffineTransform?) {
        g.transform(Tx)
    }

    var transform: AffineTransform?
        get() = g.transform
        set(Tx) {
            g.transform = Tx
        }
    var paint: Paint?
        get() = g.paint
        set(paint) {
            g.paint = paint
        }
    var composite: Composite?
        get() = g.composite
        set(comp) {
            g.composite = comp
        }
    var background: Color?
        get() = g.background
        set(color) {
            g.background = color
        }
    var stroke: Stroke?
        get() = g.stroke
        set(s) {
            g.stroke = s
        }

    fun clip(s: Shape?) {
        g.clip(s)
    }

    val fontRenderContext: FontRenderContext
        get() = g.fontRenderContext

    fun create(): Graphics {
        return g.create()
    }

    fun create(x: Int, y: Int, width: Int, height: Int): Graphics {
        return g.create(x, y, width, height)
    }

    var color: Color?
        get() = g.color
        set(c) {
            g.color = c
        }

    fun setPaintMode() {
        g.setPaintMode()
    }

    fun setXORMode(c1: Color?) {
        g.setXORMode(c1)
    }

    var font: Font?
        get() = g.font
        set(font) {
            g.font = font
        }
    val fontMetrics: FontMetrics
        get() = g.fontMetrics

    fun getFontMetrics(f: Font?): FontMetrics {
        return g.getFontMetrics(f)
    }

    val clipBounds: Rectangle
        get() = g.clipBounds

    fun clipRect(x: Int, y: Int, width: Int, height: Int) {
        g.clipRect(x, y, width, height)
    }

    fun setClip(x: Int, y: Int, width: Int, height: Int) {
        g.setClip(x, y, width, height)
    }

    var clip: Shape?
        get() = g.clip
        set(clip) {
            g.clip = clip
        }

    fun copyArea(x: Int, y: Int, width: Int, height: Int, dx: Int, dy: Int) {
        g.copyArea(x, y, width, height, dx, dy)
    }

    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        g.drawLine(x1, y1, x2, y2)
    }

    fun fillRect(x: Int, y: Int, width: Int, height: Int) {
        g.fillRect(x, y, width, height)
    }

    fun drawRect(x: Int, y: Int, width: Int, height: Int) {
        g.drawRect(x, y, width, height)
    }

    fun clearRect(x: Int, y: Int, width: Int, height: Int) {
        g.clearRect(x, y, width, height)
    }

    fun drawRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int) {
        g.drawRoundRect(x, y, width, height, arcWidth, arcHeight)
    }

    fun fillRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int) {
        g.fillRoundRect(x, y, width, height, arcWidth, arcHeight)
    }

    fun drawOval(x: Int, y: Int, width: Int, height: Int) {
        g.drawOval(x, y, width, height)
    }

    fun fillOval(x: Int, y: Int, width: Int, height: Int) {
        g.fillOval(x, y, width, height)
    }

    fun drawArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        g.drawArc(x, y, width, height, startAngle, arcAngle)
    }

    fun fillArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        g.fillArc(x, y, width, height, startAngle, arcAngle)
    }

    fun drawPolyline(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int) {
        g.drawPolyline(xPoints, yPoints, nPoints)
    }

    fun drawPolygon(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int) {
        g.drawPolygon(xPoints, yPoints, nPoints)
    }

    fun drawPolygon(p: Polygon?) {
        g.drawPolygon(p)
    }

    fun fillPolygon(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int) {
        g.fillPolygon(xPoints, yPoints, nPoints)
    }

    fun fillPolygon(p: Polygon?) {
        g.fillPolygon(p)
    }

    fun drawChars(data: CharArray?, offset: Int, length: Int, x: Int, y: Int) {
        g.drawChars(data, offset, length, x, y)
    }

    fun drawBytes(data: ByteArray?, offset: Int, length: Int, x: Int, y: Int) {
        g.drawBytes(data, offset, length, x, y)
    }

    fun drawImage(img: Image?, x: Int, y: Int, observer: ImageObserver?): Boolean {
        return g.drawImage(img, x, y, observer)
    }

    fun drawImage(img: Image?, x: Int, y: Int, width: Int, height: Int, observer: ImageObserver?): Boolean {
        return g.drawImage(img, x, y, width, height, observer)
    }

    fun drawImage(img: Image?, x: Int, y: Int, bgcolor: Color?, observer: ImageObserver?): Boolean {
        return g.drawImage(img, x, y, bgcolor, observer)
    }

    fun drawImage(
        img: Image?,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        bgcolor: Color?,
        observer: ImageObserver?
    ): Boolean {
        return g.drawImage(img, x, y, width, height, bgcolor, observer)
    }

    fun drawImage(
        img: Image?,
        dx1: Int,
        dy1: Int,
        dx2: Int,
        dy2: Int,
        sx1: Int,
        sy1: Int,
        sx2: Int,
        sy2: Int,
        observer: ImageObserver?
    ): Boolean {
        return g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
    }

    fun drawImage(
        img: Image?,
        dx1: Int,
        dy1: Int,
        dx2: Int,
        dy2: Int,
        sx1: Int,
        sy1: Int,
        sx2: Int,
        sy2: Int,
        bgcolor: Color?,
        observer: ImageObserver?
    ): Boolean {
        return g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
    }

    fun dispose() {
        g.dispose()
    }

    @get:Deprecated("")
    val clipRect: Rectangle
        get() = g.clipRect

    fun hitClip(x: Int, y: Int, width: Int, height: Int): Boolean {
        return g.hitClip(x, y, width, height)
    }

    fun getClipBounds(r: Rectangle?): Rectangle {
        return g.getClipBounds(r)
    }
}