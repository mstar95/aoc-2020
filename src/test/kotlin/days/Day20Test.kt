package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day20Test {

    private val dayOne = Day20()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(20899048083289))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(273))
    }
}
