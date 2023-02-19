
import pt.isel.canvas.BLACK
import pt.isel.canvas.Canvas
import pt.isel.canvas.onFinish
import pt.isel.canvas.onStart

private const val PERIOD = 50
const val CELL_SIDE = 64
const val GRID_WIDTH = 12
const val GRID_HEIGHT = 10
const val STATUS_BAR = CELL_SIDE
const val ANIM_STEPS = 4

fun main(){
    onStart {
        val screen = Canvas(CELL_SIDE * GRID_WIDTH, STATUS_BAR + CELL_SIDE * GRID_HEIGHT, BLACK)
        var game = createGame()
        screen.drawArena(game)
        screen.onKeyPressed { keyEvent ->
            game = game.move(keyEvent)
        }
        screen.onTimeProgress(PERIOD) {
            if (game.stepAnim > 0) {
                game = game.copy(stepAnim = game.stepAnim - (CELL_SIDE / ANIM_STEPS))
                screen.drawArena(game)
            }
            else screen.drawArena(game)
        }
    }
    onFinish { }
}