package a

import java.util.*

/**
 * Represents a [java.util.function.Consumer] that can throw an [Exception].
 * @since 2.0.0
 */
interface ExceptionalConsumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    @Throws(Exception::class)
    fun accept(t: T)

    /**
     * Returns a composed `ExceptionalConsumer` that performs, in sequence, this
     * operation followed by the `after` operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the `after` operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed `ExceptionalConsumer` that performs in sequence this
     * operation followed by the `after` operation
     * @throws NullPointerException if `after` is null
     */
    fun andThen(after: ExceptionalConsumer<in T>): ExceptionalConsumer<T>? {

//        Objects.requireNonNull(after);
//        return (T t) -> { accept(t); after.accept(t); };

        return { t: T ->
            accept(t)
            after.accept(t)
        } as ExceptionalConsumer<T>
    }
}
