package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day23Test {

    private val dayOne = Day23()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(67384529))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(291))
    }
}
