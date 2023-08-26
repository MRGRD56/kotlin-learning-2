package a

import java.util.*
import java.util.function.Supplier
import kotlin.concurrent.Volatile

/**
 * Thread-safe lazy initialization implementation. The value produced is always non-null.
 *
 *
 * Can be used to implement the Singleton pattern.<br></br>
 * Example:
 * <pre>
 * public class MyClass {
 * private static final Lazy&lt;MyClass&gt; instance = new Lazy&lt;&gt;(MyClass::new);
 *
 * private MyClass() { }
 *
 * public static MyClass getInstance() {
 * return instance.get();
 * }
 * }</pre>
 * @since 1.5.0
 */
class Lazy<T>(valueFactory: Supplier<T>) {
    @Volatile
    private var value: T? = null
    private var valueFactory: Supplier<T>?

    init {
        Objects.requireNonNull(valueFactory, "valueFactory is null")
        this.valueFactory = valueFactory
    }

    fun get(): T {
        if (value == null) {
            synchronized(this) {
                if (value == null) {
                    value = valueFactory!!.get()
                    Objects.requireNonNull(value, "the value returned by valueFactory equals to null but must not")
                    valueFactory = null
                }
            }
        }
        return value!!
    }
}
