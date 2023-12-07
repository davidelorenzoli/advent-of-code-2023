import Day05.part1
import Day05.part2
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day5Test {
    @Test
    fun `part 1`() {
        val test1Input = readInput("Day05_test1")
        part1(test1Input) shouldBe 35
    }

    @Test
    fun `part 2`() {
        val test2Input = readInput("Day05_test2")
        part2(test2Input) shouldBe 46
    }
}