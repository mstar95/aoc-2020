package days

import arrow.Kind
import arrow.extension
import arrow.typeclasses.Comonad
import days.StoreComonad.coflatMap
import days.StoreComonad.duplicate
import days.StoreComonad.experiment
import days.StoreComonad.extract


class ForStore private constructor() {
    companion object
}

typealias StoreOf<A> = Kind<ForStore, A>


@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> StoreOf<A>.fix(): Store<A> =
        this as Store<A>

@extension
object StoreComonad : Comonad<ForStore> {

    override fun <A> StoreOf<A>.extract(): A {
        return fix().peek(fix().pos)
    }

    override fun <A, B> StoreOf<A>.map(f: (A) -> B): Store<B> {
        return Store({ f(fix().peek(it)) }, fix().pos)
    }

    override fun <A> Kind<ForStore, A>.duplicate(): Store<Store<A>> {
        return Store({ Store(fix().peek, it) }, fix().pos)
    }

    override fun <A, B> StoreOf<A>.coflatMap(f: (StoreOf<A>) -> B): Store<B> {
        return duplicate().map(f)
    }

    fun <A> StoreOf<A>.experiment(fn: (Coord) -> List<Coord>): List<A> {
        val functor: List<Pair<Int, Int>> = fn(fix().pos)
        println(functor)
        return functor.map { fix().peek(it) }
    }

}

typealias Coord = Pair<Int, Int>

data class Store<A>(val peek: (Coord) -> A, val pos: (Coord)) : StoreOf<A> {
    fun seek(s: Coord) = duplicate().peek(s)

}

class Day26 : Day(26) {

    val glider = mapOf(
            (1 to 0) to true,
            (2 to 1) to true,
            (0 to 2) to true,
            (1 to 2) to true,
            (2 to 2) to true,
    )

    val initialState: Map<Coord, Boolean> = glider

    override fun partOne(): Any {
        val store = Store({ true }, 1 to 1)
//        println(store == store.duplicate().extract())
//        println(store.duplicate().extract() == store.coflatMap { it }.extract())
//        println(store.map({ it + 1 }).extract() == store.coflatMap { it.extract() + 1 }.extract())
        //   gameLoop()
        return 10
    }


    fun gameLoop(): Unit {
        var current = Store({ initialState[it] ?: false }, 0 to 0)

        println(render(current))
        println(current)
        while (true) {
            current = step(current)
            println(current)
            val rendered = render(current)
            println(rendered)
            return
        }
        return
    }


    fun render(plane: Store<Boolean>): String {
        val extent = 5

        val coords: List<Coord> = (0..extent).flatMap { x -> (0..extent).map { x to it }.toList() }

        val cells: List<String> = plane.experiment { coords }.map { if (it) " X " else " . " }
        return cells.chunked(extent).map { it.joinToString() }.joinToString("\n")
    }


    fun neighbourCoords(x: Int, y: Int): List<Coord> = listOf(
            x + 1 to y,
            x - 1 to y,
            x to y + 1,
            x to y - 1,
            x + 1 to y + 1,
            x + 1 to y - 1,
            x - 1 to y + 1,
            x - 1 to y - 1
    )

    fun conway(store: StoreOf<Boolean>): Boolean {
        val neighbours = store.experiment { neighbourCoords(it.first, it.second) }
        val liveCount = neighbours.count { it }
        println((store as Store).pos)
        println(neighbours)
        val e = store.extract()
        if (e == true) {
            if (liveCount < 2) {
                return false
            }
            if (liveCount == 2 || liveCount == 3) {
                return true
            }
            return false
        }
        if (liveCount == 3) {
            return true
        }
        return false
    }

    fun step(store: Store<Boolean>): Store<Boolean> =
            store.coflatMap { conway(it) }


    override fun partTwo(): Any {
        return 1
    }

}



