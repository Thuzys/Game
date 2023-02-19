/**
 * @property pos has a position in the Grid of the game
 * @property motion has the current direction of the character
 */
data class Character(val pos: Position, val motion: Direction?)
/**
 * is a list with all the possibles positions in the game
 * uses the Grid width and height as params
 */
private val positionList = (0 until GRID_WIDTH*GRID_HEIGHT).map {
    Position(it%GRID_WIDTH, it/GRID_WIDTH)
}
/**
 * create 10 enemies in random positions of the game grid
 */
fun createEnemies(heroPos: Position): List<Character> =
    (positionList - heroPos).shuffled().take(10).map { Character(it, null) }
/**
 * put the hero into a random valid position
 */
fun Character.jump(enemies: List<Character>, deadEnemies: List<Position>): Character =
    Character(
        pos = (positionList - enemies.map { it.pos }.toSet() - deadEnemies.toSet() - pos).random(),
        motion = null
    )
/**
 * moves all the enemies into the hero direction
 */
fun List<Character>.moveTo(target: Position): List<Character> =
    map {
        val dir = (target - it.pos).toDirection()
        Character(
            pos = it.pos + dir,
            motion = dir
        )
    }