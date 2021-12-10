package `2021`

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day10Test {

    private val dayOne = Day10()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(26397))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(288957))
    }
}
