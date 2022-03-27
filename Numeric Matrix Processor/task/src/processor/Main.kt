package processor

typealias MOption = Menu.MenuOption


class Menu(val options: List<MenuOption>) {
    val menu = options.map { it.optionString }.joinToString("\n")
    fun display() {
        println(menu)
    }

    fun matchedOption(choice: String) = options.firstOrNull { it.choiceMatch(choice) }

    class MenuOption(val optionString: String, private val choice: String, private val action: () -> Unit) {

        fun choiceMatch(otherChoice: String) = choice == otherChoice
        fun doAction() {
            action()
        }
    }


}

//class Matrix(val rows: Int, val cols: Int, val initMaxtix: (row: Int) -> MutableList<Int>) {
//    companion object {
//        fun dotProduct(row: List<Int>, col: List<Int>): Int {
////            println(row.joinToString(" ") + " X " + col.joinToString(" "))
//            return row.mapIndexed { index, i -> i * col[index] }.sum()
//        }
//    }
//
//    private val mat = MutableList(rows) { initMaxtix(it) }
//
//    fun getRow(idx: Int) = mat[idx]
//    fun getCol(idx: Int) = mat.map { it[idx] }
//
//    operator fun plus(m2: Matrix): Matrix {
//        if (rows != m2.rows || cols != m2.cols)
//            throw IllegalArgumentException("ERROR")
//        return Matrix(rows, cols) { x ->
//            MutableList(cols) { getRow(x)[it] + m2.getRow(x)[it] }
//        }
//    }
//
//    operator fun times(m2: Matrix): Matrix {
////        println("Matrix mul")
//        if (cols != m2.rows)
//            throw IllegalArgumentException("ERROR")
//
//        return Matrix(rows, m2.cols) { x ->
//            getRow(x).mapIndexed { index, _ -> dotProduct(getRow(x), m2.getCol(index)) }.toMutableList()
//        }
//
//    }
//
//    operator fun times(num: Int): Matrix {
//        return Matrix(rows, cols) { x ->
//            MutableList(cols) { getRow(x)[it] * num }
//        }
//    }
//
//
//    override fun toString(): String {
//        return mat.joinToString("\n") {
//            it.joinToString(" ")
//        }
//    }
//}
fun enterMatrix(sizeMessage: String, matrixMessage: String): Matrix {
//    try {


    print(sizeMessage)
    val (m1Rows, m1Cols) = readln().split(" ").map { it.toInt() }
    println(matrixMessage)
    val m1 = Matrix(m1Rows, m1Cols) { readln().split(" ").map { it.toDouble() }.toMutableList() }

    return m1
//    } catch (ex: Exception) {
//        return Matrix.EmptyMatrix
//    }
}

fun performAdd() {
    val m1 = enterMatrix("Enter size of first matrix:", "Enter first matrix:")
//    print()
//    val (m2Rows, m2Cols) = readln().split(" ").map { it.toInt() }
//    println()

    val m2 = enterMatrix("Enter size of second matrix:", "Enter second matrix:")
    try {
        println("The result is:")
        println(m1 + m2)
    } catch (ex: IllegalArgumentException) {
        println(ex.message)
    }
}

fun performMultiplyByConst() {
    val m1 = enterMatrix("Enter size of first matrix:", "Enter first matrix:")
    print("Enter constant:")
    val num = readln().toInt()
    println("The result is:")
    println(m1 * num)
}

fun performMultiply() {
    val m1 = enterMatrix("Enter size of first matrix:", "Enter first matrix:")
    val m2 = enterMatrix("Enter size of second matrix:", "Enter second matrix:")
    try {
        println("The result is:")
        println(m1 * m2)
    } catch (ex: IllegalArgumentException) {
        println(ex.message)
    }
}

fun performHorizontalTransposition() {

    val m1 = enterMatrix("Enter matrix size: ", "Enter matrix:")
    val mT = m1.transpose(Matrix.TranspositionType.Horizontal)
    println("The result is:")
    println(mT)
}

fun performVerticalTransposition() {
    val m1 = enterMatrix("Enter matrix size: ", "Enter matrix:")
    val mT = m1.transpose(Matrix.TranspositionType.Vertical)
    println("The result is:")
    println(mT)
}

fun performSideDiagonalTransposition() {
    val m1 = enterMatrix("Enter matrix size: ", "Enter matrix:")
    val mT = m1.transpose(Matrix.TranspositionType.SideDiagonal)
    println("The result is:")
    println(mT)
}

fun performMainDiagonalTransposition() {

    val m1 = enterMatrix("Enter matrix size: ", "Enter matrix:")
    val mT = m1.transpose(Matrix.TranspositionType.Diagonal)
    println("The result is:")
    println(mT)
}

fun performDeterminant() {

    val m1 = enterMatrix("Enter matrix size: ", "Enter matrix:")

    println("The result is:")
    println(m1.determinant())
}
fun performInverse() {

    val m1 = enterMatrix("Enter matrix size: ", "Enter matrix:")

    println("The result is:")
    println(m1.inverse())
}

fun main() {
    var running = true
//    println(Matrix.dotProduct(listOf(1, 1), listOf(2, 2)))
    val optionsList = listOf(
        MOption("1. Add matrices", "1") { performAdd() },
        MOption("2. Multiply matrix by a constant", "2") { performMultiplyByConst() },
        MOption("3. Multiply matrices", "3") { performMultiply() },
        MOption("4. Transpose matrix", "4") {
            val subMenu = Menu(listOf(MOption("1. Main diagonal", "1") { performMainDiagonalTransposition() },
                MOption("2. Side diagonal", "2") { performSideDiagonalTransposition() },
                MOption("3. Vertical line", "3") { performVerticalTransposition() },
                MOption("4. Horizontal line", "4") { performHorizontalTransposition() }
            ))

            subMenu.display()
            val choice = readln()
            val selectedOption = subMenu.matchedOption(choice)
            if (selectedOption != null) {
                selectedOption.doAction()
            } else {
                println("Invalid Option")
            }
        },
        MOption("5. Calculate a determinant", "5") { performDeterminant() },
        MOption("6. Inverse matrix", "6") { performInverse() },

        MOption("0. Exit", "0") {
//            println("Bye!!")
            running = false
        }
    )

    val menu = Menu(optionsList)
    try {
        while (running) {
            menu.display()
            val choice = readln()
            val selectedOption = menu.matchedOption(choice)
            if (selectedOption != null) {
                selectedOption.doAction()
            } else {
                println("Invalid Option")
            }

        }
    } catch (ex: Exception) {
        println("${ex.message}")
//        ex.printStackTrace()
    }
}

