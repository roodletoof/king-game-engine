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


/**
 * I Decided to read the animation data this way because:
 *      1.  Considering the fact that Aseprite's json data format is complete ass;
 *          If the format of the exported json data were to change, it would probably change significantly in its structure.
 *          And if that was the case, this code would probably have to change anyway.
 *      2.  I want to avoid any external libraries.
 */


// matches every layer name, and its opacity in order from lowest z-index to highest.
// values are in groups: name, opacity
// "(?<=name\": \")(?<name>.*)(?:\", \"opacity\D*)(?<opacity>\d+)"gm

// replace layerName with layer name to find x, y, w, h of sprite-sheet for each frame.
// values are in groups: x, y, w and h
// "(?:\"layerName\"\D*frame\D*)(?<x>\d+)(?:\D*)(?<y>\d+)(?:\D*)(?<w>\d+)(?:\D*)(?<h>\d+)"gm

// replace layerName with layer name to find frame duration for each frame.
// values are in groups: ms
// "(?:\"layerName\"[^n]*duration\D*)(?<ms>\d+)"gm

// Extract animation tag names and the frame range associated with the tag
// values are in groups: name, from, to
// "(?:name\": \")(?<name>[^\"]*)(?:\", \"from\D*)(?<from>\d+)(?:\D*)(?<to>\d+)"gm
class LayeredAsepriteSheetAnimation(private val jsonPath: String, private val spriteSheetPath: String){
    private val img = GameImage(spriteSheetPath)



}