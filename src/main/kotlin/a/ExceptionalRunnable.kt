package a

/**
 * Represents a [Runnable] task that can throw an [Exception].
 * @since 1.6.0
 */
interface ExceptionalRunnable {
    @Throws(Exception::class)
    fun run()
}
