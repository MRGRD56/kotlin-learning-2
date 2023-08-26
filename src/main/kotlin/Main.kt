fun `if`(c: Boolean, action: () -> Unit): Unit {
    if (c) action()
}

fun test(user: User?): User {
    user?.also {
        println(user)
        return user
    }

    throw RuntimeException("NO USER!!!")
}

fun main() {
    val people: MutableList<Person> = ArrayList();

    for (i in 0..10) {
        people.add(Person("Ivan", 20 + i))
    }

    for (person in people) {
        println(person)
    }

    val obj = object {
        val hello = 3
    }

    val hel2 = obj
        .takeIf { it.hello > 2 }
        ?.let { it.hello }
        ?.let { it * 2 }

    println(hel2)

    `if`(4 > 2) {
        println("4 is gt 2")
    }
}

data class Person(
    val name: String,
    val age: Int
)