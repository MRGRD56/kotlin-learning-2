package a

/**
 * Represents a [MultiConsumer] that can accept items adding them to the `collection`.
 * @since 2.0.0
 */
class CollectionConsumer<T>(private val collection: MutableCollection<T>) : MultiConsumer<T> {
    override fun accept(t: T) {
        collection.add(t)
    }

    override fun acceptAll(items: Collection<T>) {
        collection.addAll(items)
    }
}
