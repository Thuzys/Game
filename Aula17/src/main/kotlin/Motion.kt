/**
 * represents the motion towards the position
 * @property dx is the width
 * @property dy is the height
 */
data class Motion(val dx: Int, val dy: Int)

operator fun Position.minus(pos: Position): Motion =
    Motion(width - pos.width, height - pos.height)