package days

import days.Action.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day15Test {

    private val dayOne = Day15()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(1L))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(2578L))
    }
}
