package `2021`

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day5Test {

    private val dayOne = Day5()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(5))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(12))
    }
}
