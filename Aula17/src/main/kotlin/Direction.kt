
import pt.isel.canvas.DOWN_CODE
import pt.isel.canvas.LEFT_CODE
import pt.isel.canvas.RIGHT_CODE
import pt.isel.canvas.UP_CODE

private const val HOME_CODE = 36
private const val END_CODE = 35
private const val PAGE_UP_CODE = 33
private const val PAGE_DOWN_CODE = 34

/**
 * represents all the possibles directions
 */
enum class Direction(val width: Int, val height: Int, val key: Int){
    DOWN(0, 1, DOWN_CODE), LEFT(-1, 0, LEFT_CODE),
    RIGHT(1, 0, RIGHT_CODE), UP(0, -1, UP_CODE),
    UP_LEFT(-1,-1, HOME_CODE), UP_RIGHT(1,-1, PAGE_UP_CODE),
    DOWN_LEFT(-1,1, END_CODE), DOWN_RIGHT(1,1, PAGE_DOWN_CODE),
}

fun Motion.toDirection(): Direction {
    val dxu = dx.toSign()
    val dyu = dy.toSign()
    return Direction.values().first{ it.width == dxu && it.height == dyu }
}

private fun Int.toSign(): Int =
    when{
        this == 0 -> 0
        this < 0 -> -1
        else -> 1
    }

fun Direction?.width(): Int =
    this?.width ?: 0

fun Direction?.height(): Int =
    this?.height ?: 0

fun directionOf(key: Int): Direction? =
    Direction.values().firstOrNull{ it.key == key }