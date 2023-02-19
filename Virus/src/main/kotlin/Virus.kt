
import pt.isel.canvas.*

const val CELL_SIDE = 64
const val GRID_WIDTH = 12
const val GRID_HEIGHT = 10
private const val PERIOD1 = 25
private const val ANIM_STEPS1 = 8 //multiple of CELL_SIDE

/**
 * @property width final grid position in horizontal
 *  @property height final grid position in vertical
 *  @property stepAnim step of animation 0..CELL_SIDE (0 -> final position, CELL_SIDE -> start position)
 *  @property dWidth delta width position (-1, 0, 1)
 *  @property dHeight delta height position (-1, 0, 1)
 */
data class Virus(val width: Int, val height: Int, val stepAnim: Int = 0, val dWidth: Int = 0, val dHeight: Int = 0)

fun Virus.move(code: Int) =
    when(code){
        UP_CODE -> if (height-1>=0) copy(height = height-1, stepAnim = CELL_SIDE, dWidth = 0, dHeight = -1)
        else this
        DOWN_CODE -> if (height+1<GRID_HEIGHT) copy(height = height+1, stepAnim = CELL_SIDE, dWidth = 0, dHeight = 1)
        else this
        RIGHT_CODE -> if (width+1<GRID_WIDTH) copy(width = width+1, stepAnim = CELL_SIDE, dWidth = 1, dHeight = 0)
        else this
        LEFT_CODE -> if (width-1>=0) copy(width = width-1, stepAnim = CELL_SIDE, dWidth = -1, dHeight = 0)
        else this
        else -> this
    }

fun main(){
    onStart {
        val screen = Canvas(CELL_SIDE*GRID_WIDTH, CELL_SIDE*GRID_HEIGHT, BLACK)
        var virus = Virus(GRID_WIDTH/2, GRID_HEIGHT/2)
        screen.drawVirus(virus)
        screen.onKeyPressed { keyEvent ->
            virus = virus.move(keyEvent.code)
            screen.drawVirus(virus)
        }
        screen.onTimeProgress(PERIOD1){
            if (virus.stepAnim>0){
                virus = virus.copy(stepAnim = virus.stepAnim-(CELL_SIDE/ANIM_STEPS1))
                screen.drawVirus(virus)
            }
        }
    }
    onFinish {  }
}

private fun Canvas.drawVirus(virus: Virus) {
    fun Canvas.drawGrid() {
        (CELL_SIDE..width step CELL_SIDE).forEach {
            drawLine(it, 0, it, height, WHITE, 1) // vertical
        }
        (CELL_SIDE..height step CELL_SIDE).forEach {
            drawLine(0, it, width, it, WHITE, 1) // horizontal
        }
    }
    erase()
    drawGrid()
    val width = virus.width*CELL_SIDE-virus.stepAnim*virus.dWidth
    val height = virus.height*CELL_SIDE-virus.stepAnim*virus.dHeight
    drawImage("coronavirus", width, height, CELL_SIDE, CELL_SIDE)
}