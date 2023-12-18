import Day10.part1
import Day10.part2
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day10Test {
    @Test
    fun `part 1`() {
        val test1Input = readInput("Day10_test1")
        part1(test1Input) shouldBe 8
    }

    @Test
    fun `part 2`() {
        val test2Input = readInput("Day10_test2")
        part2(test2Input) shouldBe 4
    }
}
