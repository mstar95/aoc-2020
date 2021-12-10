package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day26Test {

    private val dayOne = Day26()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(10))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(2208))
    }
}
