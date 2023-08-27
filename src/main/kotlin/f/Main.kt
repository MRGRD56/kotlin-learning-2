package f

import org.apache.commons.lang3.time.StopWatch

fun main() {
    val limit = 10_000_000_000L

    measure("while") {
        var i = 1L;
        while (i < limit) {
            // noop
            i++
        }
    }

    measure("for-range") {
        for (i in 1..<limit) {
            // noop
        }
    }
}

inline fun measure(name: String, action: () -> Unit) {
    StopWatch.createStarted().let {
        action()
        it.stop()
        println("[$name] completed in ${it.formatTime()}")
    }
}