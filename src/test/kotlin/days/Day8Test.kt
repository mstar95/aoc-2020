package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day8Test {

    private val dayOne = Day8()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(5))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(8))
    }
}
