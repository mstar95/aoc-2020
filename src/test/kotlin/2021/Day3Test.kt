package `2021`

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day3Test {

    private val dayOne = Day3()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(198))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(230))
    }
}
