package days

import days.Action.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day14Test {

    private val dayOne = Day14()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(165))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(208L))
    }
}
