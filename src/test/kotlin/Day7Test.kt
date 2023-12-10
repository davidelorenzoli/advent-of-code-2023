import Day07.part1
import Day07.part2
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day7Test {
    @Test
    fun `part 1`() {
        val test1Input = readInput("Day07_test1")
        part1(test1Input) shouldBe 6440
    }

    @Test
    fun `part 2`() {
        val test2Input = readInput("Day07_test2")
        part2(test2Input) shouldBe 5905
    }
}