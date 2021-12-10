package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day22Test {

    private val dayOne = Day22()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(306))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(291))
    }
}
