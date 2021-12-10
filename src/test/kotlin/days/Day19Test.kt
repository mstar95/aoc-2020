package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day19Test {

    private val dayOne = Day19()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(1))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(2578L))
    }
}
