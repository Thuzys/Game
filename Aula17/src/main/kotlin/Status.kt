/**
 * @property WIN
 * @property LOSE
 * @property PLAYING
 * are all the tree possible status
 */
enum class Status{
    WIN,
    LOSE,
    PLAYING,
}
/**
 * make the next status based in the current position, enemies and position of moved enemies
 */
fun Position.statusValidation(enemies: List<Character>, movedPos: List<Character>): Status =
    when{
        this in movedPos.map { it.pos } -> Status.LOSE
        enemies.isEmpty() -> Status.WIN
        else -> Status.PLAYING
    }