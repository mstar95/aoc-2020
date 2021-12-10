package deduplication

import arrow.core.extensions.set.foldable.exists
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class DeduplicationTest {

    val counter = AtomicInteger()
    val db = mutableSetOf<String>()

    @Test
    fun `should deduplicate message`() {
        //given
        // TODO implement given

        //when:
        // TODO implement when
        val message = "Gawron to maczo, dupy wokol niego skaczo"
        val keyExtractor: (String) -> String = { it }
        val businessFunc: (String) -> Int = fun(it: String): Int {
            println("Wykonuje operacje")
            counter.incrementAndGet()
            return 1
        }

        val saveMessage : (String) -> Unit = { db.add(it)}
        val getMessage : (String) -> String? = { if(db.contains(it)) it else null }


        val runInTransaction: (() -> Int) -> Int = {
            it()
        }
        var once: Int = Deduplication<Int>(runInTransaction,saveMessage, getMessage ).once(message, keyExtractor, businessFunc, { 0 })
        Deduplication<Int>(runInTransaction, saveMessage, getMessage ).once(message, keyExtractor, businessFunc, { 0 })
        //then:
        // TODO implement then
        assert(counter.get() == 1)

    }
}