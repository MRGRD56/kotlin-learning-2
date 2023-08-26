package a

import a.TaskInvoker.TaskValue.Multi
import a.TaskInvoker.TaskValue.Single
import a.TaskInvoker.TaskValue.Void
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Supplier
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Used for executing a specific set of tasks, distributing them among threads using [ExecutorService].<br></br><br></br>
 * Main methods:<br></br>
 *
 *  * [.submit] - accepts a task. The task *does not* start executing;
 *  * [.completeAll] - executes all accepted tasks using [.invokeAllTasks], waits for their completion, and returns the results.
 *
 *
 * @param <T> The type of value returned by the executed tasks.
 * @since 1.0
</T> */
class TaskInvoker<T>(private val executor: ExecutorService) {
    private val tasks = Collections.synchronizedList(ArrayList<InvokerCallable<T>>())

    /**
     * Accepts a task by adding it to the list for execution. The task *does not* start executing.<br></br>
     * The task accepted returns a single value which will be added to the output list.
     */
    fun submit(task: Callable<T>) {
        tasks.add(InvokerCallable.ofCallable(task))
    }

    /**
     * Accepts a task with no return value, adding it to the list for execution. The task *does not* start executing.<br></br>
     * No values will be added to the output list.
     *
     * <br></br><br></br>
     *
     *
     * Since v1.6.0 it takes [ExceptionalRunnable] instead of regular [Runnable].<br></br>
     * Since v2.0.0 `null` is not added to the output list.
     */
    fun submit(task: ExceptionalRunnable) {
        tasks.add(InvokerCallable.ofRunnable(task))
    }
    /**
     * Accepts a task by adding it to the list for execution. The task *does not* start executing.<br></br>
     * The values that are passed to the `consumer` will be added to the output list.<br></br>
     * These values are firstly collected to the [List] newly created from the provided `listFactory`.
     *
     * @param listFactory The factory used to create a [List] to which the accepted values will be collected.<br></br>
     * <br></br>
     * It's recommended to use this method only if you need to add multiple values by one task,
     * otherwise consider using [.submit] instead.
     * @since 2.0.0
     */
    /**
     * Accepts a task by adding it to the list for execution. The task *does not* start executing.<br></br>
     * The values that are passed to the `consumer` will be added to the output list.<br></br>
     * These values are firstly collected to the newly created *synchronized* [ArrayList].<br></br>
     * <br></br>
     * It's recommended to use this method only if you need to add multiple values by one task,
     * otherwise consider using [.submit] instead.
     *
     * @since 2.0.0
     */
    @JvmOverloads
    fun submit(
        task: ExceptionalConsumer<MultiConsumer<T>?>,
        listFactory: Supplier<MutableList<T>> = Supplier { Collections.synchronizedList(ArrayList()) }
    ) {
        tasks.add(InvokerCallable {
            val appliedValues = listFactory.get()
            val valueConsumer: MultiConsumer<T> = CollectionConsumer(appliedValues)
            task.accept(valueConsumer)
            Multi(appliedValues)
        })
    }

    /**
     * Accepts tasks by adding them to the list for execution. The tasks <u>do not</u> start executing.
     *
     * @see .submit
     */
    fun submitAll(tasks: Collection<Callable<T>>) {
        this.tasks.addAll(tasks.map { callable: Callable<T> -> InvokerCallable.ofCallable(callable) })
    }

    /**
     * Accepts tasks with no return value, adding them to the list for execution. The tasks <u>do not</u> start executing.<br></br>
     * No values will be added to the output list.
     *
     * <br></br><br></br>
     *
     *
     * Since v1.6.0 it takes a list of [ExceptionalRunnable]s instead of regular [Runnable]s.
     * <br></br>
     * Since v2.0.0 `null`s are not added to the output list.
     *
     * @see .submit
     * @see .submitAll
     */
    fun submitAllVoid(tasks: Collection<ExceptionalRunnable>) {
        this.tasks.addAll(tasks.map { InvokerCallable.ofRunnable(it) })
    }

    /**
     * Executes all still uncompleted tasks using [.invokeAllTasks], waits for their completion, and returns the results.<br></br>
     * Calling this method clears the list of accepted tasks.
     *
     * @throws CancellationException May be thrown if the [.cancelAll] method was called while the current tasks were being executed.
     */
    @Throws(CancellationException::class)
    fun completeAll(): List<T> {
        return if (tasks.isEmpty()) {
            emptyList()
        } else {
            completeFutures(invokeAllTasks())
        }
    }

    /**
     * Executes all still uncompleted tasks using [.invokeAllTasks], waits for their completion, and returns the results.<br></br>
     * Calling this method clears the list of accepted tasks.<br></br>
     * Uses the provided timeout.
     *
     * @throws CancellationException May be thrown if the [.cancelAll] method was called while the current tasks were being executed.<br></br>
     * It is also thrown if the timeout has been exceeded.
     * @since 1.6.0
     */
    @Throws(CancellationException::class)
    fun completeAll(timeout: Long, unit: TimeUnit): List<T> {
        return if (tasks.isEmpty()) {
            emptyList()
        } else {
            completeFutures(invokeAllTasks(timeout, unit))
        }
    }

    /**
     * Executes all still uncompleted tasks using [.invokeAllTasks], waits for their completion, and returns the results.<br></br>
     * Calling this method clears the list of accepted tasks, which means that the results can't be obtained afterward.<br></br>
     *
     * @throws CancellationException May be thrown if the [.cancelAll] method was called while the current tasks were being executed.
     *
     * @since 3.0.0
     */
    @Throws(CancellationException::class)
    fun completeAllVoid() {
        completeFuturesVoid(invokeAllTasks())
    }

    /**
     * Executes all still uncompleted tasks using [.invokeAllTasks] and waits for their completion.<br></br>
     * Calling this method clears the list of accepted tasks, which means that the results can't be obtained afterward.<br></br>
     * Uses the provided timeout.
     *
     * @throws CancellationException May be thrown if the [.cancelAll] method was called while the current tasks were being executed.<br></br>
     * It is also thrown if the timeout has been exceeded.
     * @since 3.0.0
     */
    @Throws(CancellationException::class)
    fun completeAllVoid(timeout: Long, unit: TimeUnit) {
        completeFuturesVoid(invokeAllTasks(timeout, unit))
    }

    /**
     * Cancels all the accepted tasks. If attempted to execute, these tasks will throw a [CancellationException].<br></br>
     * Multiple calls to the method for the same tasks will not lead to anything.<br></br>
     * Clears the accepted tasks list.
     */
    fun cancelAll() {
        if (tasks.isNotEmpty()) {
            synchronized(tasks) {
                if (tasks.isNotEmpty()) {
                    try {
                        tasks.forEach { it.cancel() }
                    } finally {
                        tasks.clear()
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return StringJoiner(", ", TaskInvoker::class.java.getSimpleName() + "[", "]")
            .add("tasks=" + tasks.size)
            .add("executor=$executor")
            .toString()
    }

    private fun invokeAllTasks(): List<Future<TaskValue<T>>> {
        if (tasks.isEmpty()) {
            return emptyList()
        }

        try {
            return executor.invokeAll(tasks)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } finally {
            tasks.clear()
        }
    }

    private fun invokeAllTasks(timeout: Long, unit: TimeUnit): List<Future<TaskValue<T>>> {
        if (tasks.isEmpty()) {
            return emptyList();
        }

        try {
            return executor.invokeAll(tasks, timeout, unit)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } finally {
            tasks.clear()
        }
    }

    private class InvokerCallable<T>(private val callable: Callable<TaskValue<T>>) : Callable<TaskValue<T>> {
        private val isCancelled = AtomicBoolean(false)
        fun cancel() {
            isCancelled.set(true)
        }

        @Throws(Exception::class)
        override fun call(): TaskValue<T> {
            if (isCancelled.get()) {
                throw CancellationException("The task has been cancelled")
            }
            return callable.call()
        }

        companion object {
            fun <T> ofCallable(callable: Callable<T>): InvokerCallable<T> {
                return InvokerCallable { Single(callable.call()) }
            }

            @Suppress("UNCHECKED_CAST")
            fun <T> ofRunnable(runnable: ExceptionalRunnable): InvokerCallable<T> {
                return InvokerCallable {
                    runnable.run()
                    Void.instance as Void<T>
                }
            }
        }
    }

    private interface TaskValue<T> {
        class Void<T> : TaskValue<T> {
            companion object {
                val instance: Void<*> by lazy { Void<Any?>() }
            }
        }

        class Single<T>(val value: T) : TaskValue<T>

        class Multi<T>(val values: List<T>) : TaskValue<T>
    }

    companion object {
        @Throws(CancellationException::class)
        private fun <T> getFutureResult(future: Future<T>): T {
            try {
                return future.get()
            } catch (e: Exception) {
                val cause: Throwable? = e.cause

                throw when (e) {
                    is InterruptedException, is ExecutionException -> {
                        if (cause is CancellationException) cause
                        else RuntimeException(e)
                    }
                    else -> e
                }
            }
        }

        private fun <T> completeFutures(futures: List<Future<TaskValue<T>>>): List<T> {
            return futures.asSequence()
                .map { getFutureResult(it) }
                .filter { it !is Void }
                .flatMap { taskValue: TaskValue<T> ->
                    return@flatMap when (taskValue) {
                        is Single<T> -> sequenceOf(taskValue.value)
                        is Multi<T> -> taskValue.values.asSequence()
                        else -> {
                            throw IllegalStateException("taskValue is an instance of an unsupported class: ${taskValue.javaClass.getName()}")
                        }
                    }
                }
                .toList()
        }

        private fun <T> completeFuturesVoid(futures: List<Future<TaskValue<T>>>) {
            futures.forEach { getFutureResult(it) }
        }
    }
}
