package sge.geometry

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Polygon
import kotlin.math.*

class Vector2(x: Number, y: Number) {
    var x = x.toDouble()
    var y = y.toDouble()

    fun clone() = Vector2(x, y)

    /**
     * Draw the vector for debugging purposes.
     * @param canvas The canvas to draw to.
     * @param position Where on the canvas this vector should be drawn from.
     */
    fun draw(canvas: Graphics2D, position: Vector2 = Vector2()) {
        canvas.color = Color.WHITE
        canvas.stroke = BasicStroke(1.0F)

        val end = position + this
        canvas.drawLine(position.x.toInt(), position.y.toInt(), end.x.toInt(), end.y.toInt())

        val tip = Polygon()
        tip.addPoint(end.x.toInt(), end.y.toInt())
        val tipHeight = 20.0

        for (factor in -1..1 step 2) {
            val point = fromAngle(angle() + PI / 1.2 * factor, tipHeight) + end
            tip.addPoint(point.x.toInt(), point.y.toInt())
        }

        canvas.fillPolygon(tip)


    }

    private constructor(both: Number) : this(both, both)
    constructor() : this(0)

    override fun toString() = "[$x, $y]"

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
    infix fun longerThan(other: Number) = len() > other.toDouble()
    infix fun shorterThan(other: Vector2) = lenSquared() < other.lenSquared()
    infix fun shorterThan(other: Number) = len() < other.toDouble()


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

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    companion object{
        fun fromAngle(radians: Double, length: Double = 1.0) = Vector2(cos(radians), sin(radians)) * length
    }

}