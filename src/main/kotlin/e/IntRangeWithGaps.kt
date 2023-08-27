package e

fun main() {
    for (i in 0..5 and 10..15) {
        print("$i ")
    }
}

class IntRangeWithGaps(private vararg val ranges: ClosedRange<Int>) : ClosedRange<Int>, Iterable<Int> {
    override fun iterator(): Iterator<Int> {
        return object : Iterator<Int> {
            private var rangeIndex: Int = 0
            private var rangeValue: Int = ranges.first().start

            override fun hasNext(): Boolean {
                if (rangeValue > ranges[rangeIndex].endInclusive) {
                    if (rangeIndex > ranges.size - 2) {
                        return false
                    }
                }

                return true
            }

            override fun next(): Int {
                if (rangeValue > ranges[rangeIndex].endInclusive) {
                    if (rangeIndex > ranges.size - 2) {
                        throw NoSuchElementException()
                    }
                    rangeIndex++
                    rangeValue = ranges[rangeIndex].start
                }

                return rangeValue++;
            }

        }
    }

    override val start: Int
        get() = ranges.first().start

    override val endInclusive: Int
        get() = ranges.last().endInclusive
}

infix fun ClosedRange<Int>.and(that: ClosedRange<Int>): IntRangeWithGaps {
    if (this.endInclusive > that.start) {
        throw IllegalArgumentException("Second range must start after the first range")
    }

    return IntRangeWithGaps(this, that)
}