import Day01.part1
import Day01.part2
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day1Test {
    @Test
    fun `part 1`() {
        val test1Input = readInput("Day01_test1")
        check(part1(test1Input) == 142)
    }

    @Test
    fun `part 2`() {
        val test2Input = readInput("Day01_test2")
        check(part2(test2Input) == 281)
    }
}