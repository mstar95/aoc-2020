package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day11Test {

    private val dayOne = Day11()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(37))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(26))
    }
}
