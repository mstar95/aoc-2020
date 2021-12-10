package `2021`

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day2Test {

    private val dayOne = Day2()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(150))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(900))
    }
}
