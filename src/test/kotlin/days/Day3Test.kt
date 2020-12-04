package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Test

class Day3Test {

    private val dayOne = Day3()

    @Test
    fun testPartOne() {

        assertThat(dayOne.partOne(), `is`(7))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(336L))

    }
}
