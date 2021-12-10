package days

import days.Action.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day18Test {

    private val dayOne = Day18()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(13632))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(2578L))
    }
}
