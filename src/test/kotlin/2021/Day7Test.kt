package `2021`

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day7Test {

    private val dayOne = Day7()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(37))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(168))
    }
}
