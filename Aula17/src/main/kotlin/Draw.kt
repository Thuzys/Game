
import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE
import pt.isel.canvas.YELLOW

private const val GRAY = 0x555555
private const val TEXT_BASE = 10
private const val SPRITE_DIV = 64
private const val SPRITE_CELLS = 4

private fun Canvas.drawGrid() {
    (CELL_SIDE..width step CELL_SIDE).forEach {
        drawLine(it, 0, it, height, WHITE, 1) // vertical
    }
    (CELL_SIDE..height step CELL_SIDE).forEach {
        drawLine(0, it, width, it, WHITE, 1) // horizontal
    }
}

private fun Canvas.drawCharacter(character: Character, name: String, game: Game) {
    if (game.status != Status.LOSE) {
        val width = character.pos.width * CELL_SIDE - game.stepAnim * character.motion.width()
        val height = character.pos.height * CELL_SIDE - game.stepAnim * character.motion.height()
        val yImg = SPRITE_DIV * when (character.motion) {
            Direction.DOWN, null -> 0
            Direction.LEFT, Direction.UP_LEFT, Direction.DOWN_LEFT -> 1
            Direction.RIGHT, Direction.UP_RIGHT, Direction.DOWN_RIGHT -> 2
            Direction.UP -> 3
        }
        val xImg = SPRITE_DIV * ((game.stepAnim / (CELL_SIDE / ANIM_STEPS)) % SPRITE_CELLS)
        drawImage("$name|${xImg},${yImg},$SPRITE_DIV,$SPRITE_DIV", width, height, CELL_SIDE, CELL_SIDE)
    }
    else
        drawDeadCharacters(character.pos)
}

private fun Canvas.drawDeadCharacters(pos: Position) =
    drawImage("grave",pos.width*CELL_SIDE, pos.height*CELL_SIDE, CELL_SIDE, CELL_SIDE)

private fun Canvas.drawStatus(game: Game) {
    drawRect(0, height-STATUS_BAR, width, STATUS_BAR, GRAY)
    drawText(CELL_SIDE, height-TEXT_BASE, "Enemies: ${game.enemies.size}", WHITE)
    if (game.status != Status.PLAYING)
        drawText(width/2, height-TEXT_BASE,"You ${game.status.name}", YELLOW)
}
/**
 * use the auxiliary functions to draw the game
 */
fun Canvas.drawArena(game: Game) {
    erase()
    drawGrid()
    drawCharacter(game.hero, "Man", game)
    game.enemies.forEach { drawCharacter(it, "enemy", game) }
    game.deadEnemies.forEach { drawDeadCharacters(it) }
    drawStatus(game)
}