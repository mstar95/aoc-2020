package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test
import java.util.HashSet

class Day4Test {

    private val dayOne = Day4()

    @Test
    fun testPartOne() {
        val hashSet = HashSet<List<String>>()
        val list = mutableListOf<String>()
        val list2 = mutableListOf<String>()
        hashSet.add(list)
       // hashSet.add(list2)
        println(hashSet)
        println(list.hashCode())
        println(list2.hashCode())
        list.add("XD")
        hashSet.add(list2)
        list2.add(":P")
        println(hashSet)
        println(list.hashCode())
        println(list2.hashCode())
        assertThat(dayOne.partOne(), `is`(8))
    }

    @Test
    fun testPartTwo() {
        assertThat(dayOne.partTwo(), `is`(4))

    }
}
