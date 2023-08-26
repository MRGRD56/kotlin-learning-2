package a

import java.util.function.Consumer

/**
 * Represents a [java.util.function.Consumer] that can accept multiple items at once, as well as single item.
 * @since 2.0.0
 */
interface MultiConsumer<T> : Consumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    override fun accept(t: T)

    /**
     * Performs this operation on the given items.
     *
     * @param items the input items
     */
    fun acceptAll(items: Collection<T>)
}
