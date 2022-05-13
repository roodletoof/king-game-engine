package king_game_engine.drawable

// The following regex captures all animation data from a spine .atlas file that is bound to a sprite-sheet export in groups.
// "(?<animation>.*)(?:_)(?<frame>\d+)(?:\D*)(?<boundX>\d+)(?:,)(?<boundY>\d+)(?:,)(?<boundW>\d+)(?:,)(?<boundH>\d+)(?:\D*)(?<offX>\d+)(?:,)(?<offY>\d+)(?:,)(?<offW>\d+)(?:,)(?<offH>\d+)(?:\nrotate:)?(?<rotDeg>\d+)?(?:\norigin:)?(?<origX>\d+)?(?:,)?(?<origY>\d+)?"gm
/** Groups ((?) means it might not be present)
 *  animation   :   animation name
 *  frame       :   frame number
 *  boundX      :   location of clip on sprite-sheet
 *  boundY      :   location of clip on sprite-sheet
 *  boundW      :   width of clip on sprite-sheet
 *  boundH      :   height of clip on sprite-sheet
 *  offX        :   offset x
 *  offY        :   offset y
 *  offW        :   offset width
 *  offH        :   offset height
 *  rotDeg(?)   :   how many degrees the image on the sprite-sheet is rotated.
 *  origX(?)    :   origin X
 *  origY(?)    :   origin Y
 */
// TODO look into the difference between offsets and origin. And see if I understood the offset values correctly.
class SpineSheetAnimation {
}