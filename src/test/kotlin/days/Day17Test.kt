package days

import days.Action.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day17Test {

    private val dayOne = Day17()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(112))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(2578L))
    }
}
