/**
 * @property width is the x of the grid
 * @property height is the y of the grid
 */
data class Position(val width: Int, val height: Int)

operator fun Position.plus(dir: Direction): Position =
    Position(width+dir.width, height+dir.height)

fun Position.isNotValid(maxWidth: Int, maxHeight: Int, game: Game): Boolean =
    width !in 0 until maxWidth || height !in 0 until maxHeight || game.stepAnim != 0 ||
            this in game.deadEnemies || game.status != Status.PLAYING
/**
 * return a list with no repeated positions
 */
fun List<Position>.overlapped(): List<Position> =
    filterIndexed { idx, pos -> lastIndexOf(pos) > idx && indexOf(pos)==idx }