
import pt.isel.canvas.KeyEvent

/**
 * @property hero is a character that contains a position and a direction
 * @property enemies is a list of characters
 * @property stepAnim is used in the animation
 * @property deadEnemies is the list that represents the collisions of the enemies
 * @property status contains the status of the game
 */
data class Game(
    val hero: Character, val enemies: List<Character>,
    val stepAnim: Int, val deadEnemies: List<Position>,
    val status: Status
)
/**
 * the move function is responsible for all the key interactions with the game
 */
fun Game.move(key: KeyEvent): Game {
    if (key.char == '*') return copy(hero = hero.jump(enemies, deadEnemies))
    val dir = directionOf(key.code) ?: return this
    val newPos = hero.pos + dir
    return if (newPos.isNotValid(GRID_WIDTH, GRID_HEIGHT, this) || enemies.hasCollisions(newPos))
        this
    else{
        val movedEnemies = enemies.moveTo(newPos)
        val overlappedPosition = movedEnemies.map { it.pos }.overlapped() + deadEnemies
        val newEnemies = movedEnemies.filter { it.pos !in overlappedPosition }
        val newStatus = newPos.statusValidation(newEnemies, movedEnemies)
        Game(Character(newPos, dir), newEnemies, CELL_SIDE, overlappedPosition, newStatus)
        }
}
/**
 * createGame is responsible for create the game in the initial position
 */
fun createGame(): Game =
    Game(
        hero = Character(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2), null),
        enemies = createEnemies(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)),
        stepAnim = 0,
        deadEnemies = emptyList(),
        status = Status.PLAYING
    )
/**
 * is capable of recognize when a collision occurs
 */
private fun List<Character>.hasCollisions(pos: Position): Boolean =
    any { it.pos == pos }