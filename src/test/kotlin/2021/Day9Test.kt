package `2021`

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day9Test {

    private val dayOne = Day9()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(15))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(1134))
    }
}
