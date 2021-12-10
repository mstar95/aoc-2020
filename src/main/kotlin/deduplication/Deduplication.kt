package deduplication

data class Deduplication<R>(private val runInTransaction: (() -> R) -> R,
                            private val saveMessage: (String) -> Unit,
                            private val getMessage: (String) -> String?,
)  {


    /**
     * 1. runInTransaction + nasze connectory
     * 2.
     */
    fun <T> once(message: T, keyExtractor: (T) -> String, f: (T) -> R, onDuplicate: () -> R): R {
        val key = keyExtractor(message)
        return runInTransaction {
            if(getMessage(key) != null) {
                return@runInTransaction onDuplicate()
            }
            saveMessage(key)
            f(message)
        }
    }

}
