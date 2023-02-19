private data class Date(val year: Int, val month: Int, val day: Int)

fun isLeapYear(year: Int): Boolean =
    year%4 == 0 && year%100 != 0 || year%400 == 0

fun maxDaysOfMonth(month: Int, year: Int): Int =
    when(month){
        4, 6, 9, 11 -> 30
        2 -> if(isLeapYear(year)) 29 else 28
        else -> 31
    }

private fun Date.isValid(): Boolean =
    year in 1583..3000 && month in 1..12 && day in 0..maxDaysOfMonth(month, year)

private fun Date.toDayOfYear(): Int =
    (1 until  month).fold(day){ acc, i -> acc + maxDaysOfMonth(i, year) }

fun main (){
    println(Date(1980, 6, 89).isValid())
    println(Date(2023, 2, 6).toDayOfYear())
}

