package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day101Test {

    private val dayOne = Day101()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(listOf(
                "3,0,4,0,99"
        )))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(32))
    }
}
