package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day6Test {

    private val dayOne = Day6()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(11))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(6))
    }
}
