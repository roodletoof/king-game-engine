package king_game_engine.geometry

/**
 * Has all the functionality of Vector2, except the ability to set the x and y fields.
 * Everything that would have returned a Vector2 still does.
 * E.g. adding a number or vector, calling copy(), etc.
 * The only purpose of this class is that you can send an instance of it to a function/method and be assured
 * that the values won't change while you're not in control.
 */
class ImmutableVector2(x: Number, y: Number) : Vector2(x, y) {
    override var x: Double
        set(_) = throwIllegalAccess()
        get() = super.x

    override var y: Double
        set(_) = throwIllegalAccess()
        get() = super.y

    override var xInt: Int
        set(_) = throwIllegalAccess()
        get() = super.xInt

    override var yInt: Int
        set(_) = throwIllegalAccess()
        get() = super.yInt

    override var xFloat: Float
        set(_) = throwIllegalAccess()
        get() = super.xFloat

    override var yFloat: Float
        set(_) = throwIllegalAccess()
        get() = super.yFloat

    constructor(vector2: Vector2) : this(vector2.x, vector2.y)
    constructor(both: Number) : this(both, both)
    constructor() : this(0, 0)

    private fun throwIllegalAccess() {
        throw IllegalAccessError("You are not permitted to change the fields of an $this")
    }

    override fun toString(): String {
        return "ImmutableVector2[$x, $y]"
    }

}