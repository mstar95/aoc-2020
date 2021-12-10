package days

import days.Action.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day13Test {

    private val dayOne = Day13()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(295))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(1068781L))
    }
}
