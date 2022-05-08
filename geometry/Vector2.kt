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
 * @param isMutable Whether this Vector2 is mutable.
 *                  An immutable Vector2 can not be turned into a mutable one.
 *                  Use the copy method to get a mutable version instead.
 */
class Vector2(x: Number, y: Number,  isMutable: Boolean = true) {
    private var name = "Vector2"
    var isMutable = isMutable
        private set

    /**
     * Makes this Vector2 immutable.
     * An immutable Vector2 cannot be converted to a mutable one.
     * Use the copy method instead.
     * @return This Vector2
     */
    fun makeImmutable(): Vector2 {
        isMutable = false
        return this
    }


    private var nameHasBeenSet = false
    /**
     * Can only be called once for each Vector2.
     * @param name Will be printed along with coordinates in this Vector2's toString() method.
     * Setting a unique and descriptive name will give a better exception message if someone tries
     * to alter the x or y field of this Vector2 while it is immutable.
     * @return This Vector2
     */
    fun setName(name: String): Vector2 {
        if (nameHasBeenSet) {
            throw IllegalCallerException(
                "$this already has a name, but you tried to set the name to $name.\n" +
                "You can not set a name twice for the same Vector2."
            )
        }

        nameHasBeenSet = true
        this.name = name
        return this
    }

    private fun throwImmutableException() {
        throw IllegalStateException(
            "You tried to modify one of the values of this Vector2 ($this).\n" +
            "But this Vector2 is immutable"
        )
    }

    var x = x.toDouble()
        set(value) {
            if (!isMutable) { throwImmutableException() }
            field = value
        }

    var y = y.toDouble()
        set(value) {
            if (!isMutable) { throwImmutableException() }
            field = value
        }



    constructor(both: Number, isMutable: Boolean = true) : this(both, both, isMutable)
    constructor(isMutable: Boolean = true) : this(0, isMutable)

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
        get() = x.toFloat()
        set(value) {x = value.toDouble()}

    fun copy(isMutable: Boolean = true) = Vector2(x, y, isMutable)
    fun round() = Vector2(round(x), round(y))
    fun ceil() = Vector2(ceil(x), ceil(y))

    fun floor() = Vector2(floor(x), floor(y))
    override fun toString() = "$name[$x, $y]"

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