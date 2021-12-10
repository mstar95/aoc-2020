package days

import days.Action.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class Day12Test {

    private val dayOne = Day12()

    @Test
    fun testPartOne() {
        assertThat(dayOne.partOne(), `is`(25))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(286))
    }

    @Test
    fun turn() {
        val waypoint = Waypoint(Point(1,2))
        assertThat(waypoint.turn(R, 90), `is`(Waypoint(Point(2,-1))))
        assertThat(waypoint.turn(R, 180), `is`(Waypoint(Point(-1,-2))))
        assertThat(waypoint.turn(R,270), `is`(Waypoint(Point(-2,1))))
        assertThat(waypoint.turn(R, 360), `is`(Waypoint(Point(1,2))))

        assertThat(waypoint.turn(L, 90), `is`(Waypoint(Point(-2,1))))
        assertThat(waypoint.turn(L, 180), `is`(Waypoint(Point(-1,-2))))
        assertThat(waypoint.turn(L,270), `is`(Waypoint(Point(2,-1))))
        assertThat(waypoint.turn(L, 360), `is`(Waypoint(Point(1,2))))
    }
}
