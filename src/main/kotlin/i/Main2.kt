package i

const val JACK = "Jack"

fun main() {
    val person = Person(JACK, 2)
    person.isAdult

    val personPoor = PersonPoor(JACK, 2)
    personPoor.isAdult
}

data class Person(
    val name: String,
    val age: Int
) {
    val isAdult: Boolean
        get() = age >= 18
}

data class PersonPoor(
    val name: String,
    val age: Int
)

val PersonPoor.isAdult: Boolean
    get() = age >= 18