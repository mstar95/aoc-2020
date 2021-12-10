package days


class Day20 : Day(20) {
    val bodyRegex = Regex("#....##....##....###")
    val legsRegex = Regex(".#..#..#..#..#..#...")
    override fun partOne(): Any {
        val tiles = prepareInput(inputString)
        findConnected(tiles)
        val corners = tiles.filter { it.connections.size == 2 }.map { it.id }
        return corners.fold(1L) { a, b -> a * b }
    }

    override fun partTwo(): Any {
        val tiles = prepareInput(inputString)
        findConnected(tiles)
        val corner = tiles.filter { it.connections.size == 2 }.first()
        val image1 = Image(buildImage(tiles, rotateCorner(tiles, corner), 1))
        val image = Image(buildImage(tiles, rotateCorner(tiles, corner), 2))
        image.printImage()
//        println("NEW")
//        image.rotate().printImage()
        val monsters = image.rotations().map { findMonsters(it) }.filter { it != 0 }.first()
        val hashes = image.image.flatMap { it.filter { it == '#' }.toList() }.size
        val hashesInMonster = (bodyRegex.pattern + legsRegex.pattern).filter { it == '#' }.length + 1
        return hashes - hashesInMonster * monsters
    }

    fun findMonsters(image: Image): Int {
        //  println("NEW")
        // image.printImage()
        return image.image.mapIndexed { idx, row ->
            val bodies = bodyRegex.find(row)
            findMonsters(image.image, idx, bodies)
        }.filter { it != 0 }.sum()
    }

    fun findMonsters(image: List<String>, idx: Int, bodies: MatchResult?): Int {
        if (bodies == null || idx == 0 || idx + 1 == image.size) {
            return 0
        }
        val range = bodies.range
        val head = image[idx - 1][range.last - 1]
        //  println(head)
        if (head != '#') {
            return findMonsters(image, idx, bodies.next())
        }
        val legs = image[idx + 1].substring(range)
        //    println(legs)
        if (legsRegex.matches(legs)) {
            return 1 + findMonsters(image, idx, bodies.next())
        }
        return findMonsters(image, idx, bodies.next())
    }

    fun findConnected(tiles: List<Tile>) {
        return tiles.forEach { t1 -> tiles.forEach { t2 -> connectImages(t1, t2) } }
    }

    fun buildImage(tiles: List<Tile>, current: Tile, v: Int): List<String> {
        val side = current.bottom
        val bottomConnection = current.connections.map { c -> getTile(tiles, c) }
                .mapNotNull { rotateToConnect(side, it, 0) }.firstOrNull()
        if (bottomConnection == null) {
            return buildRow(tiles, current, v)
        }
        return buildRow(tiles, current, v) + buildImage(tiles, bottomConnection, v)
    }

    fun buildRow(tiles: List<Tile>, current: Tile, v: Int): List<String> {
        val side = current.right
        val rightConnection = current.connections.map { c -> getTile(tiles, c) }
                .mapNotNull { rotateToConnect(side, it, 3) }.firstOrNull()

        if (rightConnection == null) {
            return if (v == 2) current.withoutBorders() else current.image
        }
        return concatRows(if (v == 2) current.withoutBorders() else current.image, buildRow(tiles, rightConnection, v))
    }

    fun concatRows(r1: List<String>, r2: List<String>): List<String> {
        return r1.mapIndexed { idx, it -> it + r2[idx] }
    }

    fun rotateCorner(tiles: List<Tile>, corner: Tile): Tile {
        val rotations = corner.rotations()
        val connections = listOf(0, 1)
        return rotations.first { r -> connections.map { c -> findSide(tiles, r, c) }.toSet() == setOf(1, 2) }
    }

    fun findSide(tiles: List<Tile>, corner: Tile, connection: Int): Int {
        val tile = tiles.first { it.id == corner.connections[connection] }
        val side = corner.sides.mapIndexed { id, it -> id to it }.first { c -> tile.permutations.any { c.second == it } }
        return side.first
    }

    private fun getTile(tiles: List<Tile>, c: Int) = tiles.first { it.id == c }

    fun rotateToConnect(sideToConnect: String, tile: Tile, fromSide: Int): Tile? {
        val rotations = tile.rotations()
        return rotations.firstOrNull { it.sides[fromSide] == sideToConnect }
    }

    fun connectImages(a: Tile, b: Tile) {
        if (a == b) {
            return
        }
        if (a.connections.contains(b.id)) {
            return
        }
        if (a.permutations.any { p1 -> b.permutations.any { p2 -> p1 == p2 } }) {
            a.connections.add(b.id)
            b.connections.add(a.id)
        }
    }


    private fun prepareInput(input: String): List<Tile> {
        return input.split("\n\n").map { prepareTile(it.split("\n")) }
    }

    private fun prepareTile(input: List<String>): Tile {
        val id = input.first().drop("Tile ".length).dropLast(":".length).toInt()
        val body = input.drop(1)
        return Tile(id, body)
    }

}

data class Tile(val id: Int, val image: List<String>, val connections: MutableList<Int> = mutableListOf()) {
    val sides = buildSides()
    val permutations = sides + sides.map { it.reversed() }
    val right: String
        get() = sides[1]

    val bottom: String
        get() = sides[2]

    fun printImage() {
        image.forEach { println(it) }
    }

    fun withoutBorders(): List<String> {
        return image.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }
    }

    fun rotate(): Tile {
        val rows = mutableListOf<String>()
        image.forEach { row ->
            row.forEachIndexed { columntId, char ->
                val r = rows.getOrElse(columntId) {
                    rows.add("")
                    ""
                }
                rows[columntId] = (char + r)
            }
        }
        return copy(image = rows)
    }

    fun rotate2() = this.rotate().rotate()
    fun rotate3() = this.rotate2().rotate()

    fun flip() = copy(image = image.reversed())

    fun rotations() = listOf(this, this.rotate(), this.rotate2(), this.rotate3(), this.flip(),
            this.flip().rotate(), this.flip().rotate2(), this.flip().rotate3())
            .distinct()


    private fun buildSides(): List<String> {
        val a: String = image.first()
        val b: String = image.map { it.last() }.joinToString("")
        val c: String = image.last()
        val d: String = image.map { it.first() }.joinToString("")
        return listOf(a, b, c, d)
    }
}

data class Image(val image: List<String>) {
    fun printImage() {
        image.forEach { println(it) }
    }

    fun flip() = copy(image = image.reversed())

    fun rotate(): Image {
        val rows = mutableListOf<String>()
        image.forEach { row ->
            row.forEachIndexed { columntId, char ->
                val r = rows.getOrElse(columntId) {
                    rows.add("")
                    ""
                }
                rows[columntId] = (char + r)
            }
        }
        return copy(image = rows)
    }

    fun rotate2() = this.rotate().rotate()
    fun rotate3() = this.rotate2().rotate()
    fun rotations() = listOf(this, this.rotate(), this.rotate2(), this.rotate3(), this.flip(),
            this.flip().rotate(), this.flip().rotate2(), this.flip().rotate3())
            .distinct()
}
