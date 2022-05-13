package king_game_engine.drawable

/**
 * Using a LayeredAsepriteImage allows you to play an ase
 */


/**
 * For this method of reading the sprite-sheet json data you must do these things in the export sprite-sheet menu:
 *  Layout section
 *      - Not ignore empty frames.
 *  Sprite section
 *      - Select split layers.
 *  Output section
 *      - Set the type to array.
 *      - Check the "Output File", "JSON Data", "Layers" and "tags" checkboxes.
 *      - Set the output filename to "{layer}" without the quotes.
 */


// I decided to get the frame-data this way to avoid any external dependencies, and because I can't think of an easier way atm.

// matches every layer name in order
// value is the entire match
// "(?<=name\": \").*(?=\", \"opacity)"gm

// replace layerName with layer name to find x, y, w, h of sprite-sheet for each frame.
// values are in groups: x, y, w and h
// "(?:\"layerName\"\D*frame\D*)(?<x>\d+)(?:\D*)(?<y>\d+)(?:\D*)(?<w>\d+)(?:\D*)(?<h>\d+)"gm

// replace layerName with layer name to find frame duration for each frame.
// values are in groups: ms
// "(?:\"layerName\"[^n]*duration\D*)(?<ms>\d+)"gm

// Extract animation tag names and the frame range associated with the tag
// values are in groups: name, from, to
// "(?:name\": \")(?<name>[^\"]*)(?:\", \"from\D*)(?<from>\d+)(?:\D*)(?<to>\d+)"gm
class LayeredAnimatedAseprite(private val jsonPath: String, private val spriteSheetPath: String){
    private val img = GameImage(spriteSheetPath)



}