enum class Result {
    Win, Loss, Draw;

    fun toPoints(): Int = when (this) {
        Win -> 6
        Draw -> 3
        Loss -> 0
    }

    companion object {
        fun fromCharacter(input: Char): Result = when (input) {
            'X' -> Loss
            'Y' -> Draw
            'Z' -> Win
            else -> error("Bad character")
        }
    }
}


enum class Option {
    Rock, Paper, Scissor;

    infix fun challenge(other: Option): Result = when (this) {
        Rock -> when (other) {
            Rock -> Result.Draw
            Paper -> Result.Loss
            Scissor -> Result.Win
        }

        Paper -> when (other) {
            Rock -> Result.Win
            Paper -> Result.Draw
            Scissor -> Result.Loss
        }

        Scissor -> when (other) {
            Rock -> Result.Loss
            Paper -> Result.Win
            Scissor -> Result.Draw
        }
    }

    fun pointsForPlayingIt(): Int = when (this) {
        Rock -> 1
        Paper -> 2
        Scissor -> 3
    }


    companion object {
        fun fromOpponentCharacter(input: Char): Option {
            return when (input) {
                'A' -> Rock
                'B' -> Paper
                'C' -> Scissor
                else -> error("Bad character")
            }
        }

        fun fromMyCharacter(input: Char): Option {
            return when (input) {
                'X' -> Rock
                'Y' -> Paper
                'Z' -> Scissor
                else -> error("Bad character")
            }
        }

        fun whichToPlayForResult(neededResult: Result, opponentOption: Option): Option {
            return when (neededResult) {
                Result.Win -> when (opponentOption) {
                    Rock -> Paper
                    Paper -> Scissor
                    Scissor -> Rock
                }

                Result.Loss -> when (opponentOption) {
                    Rock -> Scissor
                    Paper -> Rock
                    Scissor -> Paper
                }

                Result.Draw -> opponentOption
            }
        }

    }
}


fun interface Strategy {
    fun apply(letters: List<Char>): Int
}

val firstStrategy = Strategy { (firstLetter, secondLetter) ->
    val opponentOption = Option.fromOpponentCharacter(firstLetter)
    val myOption = Option.fromMyCharacter(secondLetter)

    myOption.pointsForPlayingIt() + myOption.challenge(opponentOption).toPoints()
}

val secondStrategy = Strategy { (firstLetter, secondLetter) ->
    val opponentOption = Option.fromOpponentCharacter(firstLetter)
    val neededResult = Result.fromCharacter(secondLetter)
    val myOption = Option.whichToPlayForResult(neededResult, opponentOption)

    myOption.pointsForPlayingIt() + myOption.challenge(opponentOption).toPoints()
}


fun runGameUsingStrategy(strategy: Strategy): Int = loadFile("Day02Input.txt")
    .readLines()
    .sumOf { line ->
        line
            .split(' ')
            .map { it.toCharArray().first() }
            .let(strategy::apply)
    }


fun printGameResult(result: Int) = println("Game result: $result")

fun main() {
    listOf(firstStrategy, secondStrategy)
        .map(::runGameUsingStrategy)
        .forEach(::printGameResult)
}