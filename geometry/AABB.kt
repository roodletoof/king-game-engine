package king_game_engine.geometry

/**
 * Axis aligned bounding box.
 * Tracks position, width and height of a rectangle that does not rotate.
 * Useful for simple collision detection
 * @param position The position of the AABB.
 * @param size The dimensions of the AABB.
 * @param anchor Where on the AABB should be where the position represents.
 */
class AABB(var position: Vector2, var size: Vector2, private val anchor: Vector2 = size / 2 + 0.5) {

    /**
     * Axis aligned bounding box.
     * Tracks position, width and height of a rectangle that does not rotate.
     * Useful for simple collision detection
     * @param x Position of the AABB.
     * @param y Position of the AABB.
     * @param width dimension of the AABB.
     * @param height dimension of the AABB.
     * @param anchorX Where on the AABB should be where the position represents.
     * @param anchorX Where on the AABB should be where the position represents.
     */
    constructor(
        x: Number,
        y: Number,
        width: Number,
        height: Number,
        anchorX: Number = width.toDouble() / 2.0 + 0.5,
        anchorY: Number = height.toDouble() / 2.0 + 0.5
    ): this( Vector2( x, y ), Vector2( width, height ), Vector2( anchorX, anchorY ) )

    override fun toString() = "northWest = $northWest, southEast = $southEast"

    infix fun overlaps(other: AABB): Boolean {
        return  northWest bothLesserOrEqualThan  other.southEast &&
                southEast bothGreaterOrEqualThan other.northWest
    }

    /**
     * The left x coordinate of this AABB. Setting a new value will update the position of this AABB.
     */
    var west: Double
        get() = position.x - anchor.x
        set(value) {position.x = value + anchor.x}

    /**
     * The right x coordinate of this AABB. Setting a new value will update the position of this AABB.
     */
    var east: Double
        get() = position.x + size.x - anchor.x - 1
        set(value) {
            position.x = value - size.x + anchor.x + 1
        }

    /**
     * The top y coordinate of this AABB. Setting a new value will update the position of this AABB.
     */
    var north: Double
        get() = position.y - anchor.y
        set(value) {position.y = value + anchor.y}

    /**
     * The bottom y coordinate of this AABB. Setting a new value will update the position of this AABB.
     */
    var south: Double
        get() = position.y + size.y - anchor.y - 1
        set(value) {
            position.y = value - size.y + anchor.y + 1
        }

    /**
     * The top-left coordinate of this AABB. Setting a new value will update the position of this AABB.
     * Changing only the x or y coordinate of the returned Vector2 will not update the position.
     * Use the north or west AABB member for that.
     */
    var northWest: Vector2
        get() = position - anchor
        set(value) {
            position = value + anchor
        }

    /**
     * The top-right coordinate of this AABB. Setting a new value will update the position of this AABB.
     * Changing only the x or y coordinate of the returned Vector2 will not update the position.
     * Use the north or west AABB member for that.
     */
    var northEast: Vector2
        get() = Vector2(east, north)
        set(value) {
            east = value.x
            north = value.y
        }

    /**
     * The bottom-right coordinate of this AABB. Setting a new value will update the position of this AABB.
     * Changing only the x or y coordinate of the returned Vector2 will not update the position.
     * Use the south or east AABB member for that.
     */
    var southEast: Vector2
        get() = position + size - anchor - 1
        set(value) {
            position = value - size + anchor + 1
        }


    /**
     * The bottom-left coordinate of this AABB. Setting a new value will update the position of this AABB.
     * Changing only the x or y coordinate of the returned Vector2 will not update the position.
     * Use the south or east AABB member for that.
     */
    var southWest: Vector2
        get() = Vector2(west, south)
        set(value) {
            west = value.x
            south = value.y
        }

    private infix fun Vector2.bothGreaterOrEqualThan(other: Vector2) = this.x >= other.x && this.y >= other.y
    private infix fun Vector2.bothLesserOrEqualThan(other: Vector2) = this.x <= other.x && this.y <= other.y
}
