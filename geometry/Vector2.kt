package king_game_engine.geometry

import king_game_engine.swing_extensions.performRevert
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Polygon
import kotlin.math.*


/**
 * Exists to represent 2D vectors and coordinates
 * @param x Coordinate of the x-axis
 * @param y Coordinate of hte y-axis
 */
open class Vector2(x: Number, y: Number) {
    constructor(both: Number) : this(both, both)
    constructor() : this(0)
    constructor(immutableVector2: ImmutableVector2) : this(immutableVector2.x, immutableVector2.y)
    constructor(array: DoubleArray) : this(array[0], array[1])

    open var x = x.toDouble()
    open var y = y.toDouble()

    var xInt: Int
        get() = x.toInt()
        set(value) {x = value.toDouble()}

    var yInt: Int
        get() = y.toInt()
        set(value) {y = value.toDouble()}

    var xFloat: Float
        get() = x.toFloat()
        set(value) {x = value.toDouble()}

    var yFloat: Float
        get() = y.toFloat()
        set(value) {y = value.toDouble()}

    fun copy() = Vector2(x, y)
    fun round() = Vector2(round(x), round(y))
    fun ceil() = Vector2(ceil(x), ceil(y))

    fun floor() = Vector2(floor(x), floor(y))
    override fun toString() = "Vector2[$x, $y]"

    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)

    operator fun plus(other: Number) = plus(Vector2(other))
    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)

    operator fun minus(other: Number) = minus(Vector2(other))
    operator fun times(other: Vector2) = Vector2(x * other.x, y * other.y)

    operator fun times(other: Number) = times(Vector2(other))
    operator fun div(other: Vector2) = Vector2(x / other.x, y / other.y)

    operator fun div(other: Number) = div(Vector2(other))
    operator fun unaryPlus() = this

    operator fun unaryMinus() = this * -1
    operator fun inc() = this + 1

    operator fun dec() = this - 1
    operator fun rem(other: Vector2) = Vector2(x % other.x, y % other.y)

    operator fun rem(other: Number) = rem(Vector2(other))
    fun lenSquared() = x * x + y * y

    fun len() = sqrt(lenSquared())
    infix fun longerThan(other: Vector2) = lenSquared() > other.lenSquared()
    infix fun longerThan(other: Number) = lenSquared() > other.toDouble().pow(2)
    infix fun shorterThan(other: Vector2) = lenSquared() < other.lenSquared()
    infix fun shorterThan(other: Number) = lenSquared() < other.toDouble().pow(2)
    fun dot(other: Vector2) = x * other.x + y * other.y


    fun angle() = atan2(y, x)
    fun angleTo(other: Vector2) = acos(dot(other) / len() * other.len())
    fun angleToPoint(other: Vector2) = (other - this).angle()
    fun normalized() = this / len()
    fun rotatedBy(radians: Double) = fromAngle(angle() + radians, len())
    fun rotatedAt(radians: Double) = fromAngle(radians, len())
    override operator fun equals(other: Any?): Boolean {
        if (other is Vector2) {
            return (x == other.x) and (y == other.y)
        }

        return false
    }

    fun toDoubleArray() = doubleArrayOf(x, y)

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    /**
     * Draw the vector for debugging purposes.
     * @param canvas The canvas to draw to.
     * @param position Where on the canvas this vector should be drawn from.
     */
    fun draw(canvas: Graphics2D, position: Vector2 = Vector2(), color: Color = Color.WHITE) {
        canvas.stroke = BasicStroke(1.0F)

        val end = position + this

        val tip = Polygon()
        tip.addPoint(end.x.toInt(), end.y.toInt())
        val tipHeight = 20.0

        for (factor in -1..1 step 2) {
            val point = fromAngle(angle() + PI / 1.2 * factor, tipHeight) + end
            tip.addPoint(point.x.toInt(), point.y.toInt())
        }

        fun drawLine(color: Color) {
            canvas.color = color
            canvas.drawLine(position.x.toInt(), position.y.toInt(), end.x.toInt(), end.y.toInt())
            canvas.fillPolygon(tip)
        }

        canvas.performRevert { // Shadow to make the arrow more visible in bright environments.
            canvas.translate(2, 2)
            drawLine(Color.BLACK)
        }

        drawLine(color)


    }

    companion object{
        fun fromAngle(radians: Double, length: Double = 1.0) = Vector2(cos(radians), sin(radians)) * length
    }

}