package king_game_engine.geometry

class Rect(var position: Vector2, var dimensions: Vector2, val centered: Boolean = true) {
    constructor(x: Number, y:Number, width:Number, height: Number, centered: Boolean) :
            this(Vector2(x, y), Vector2(width, height), centered)


}