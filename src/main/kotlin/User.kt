open class User(var firstName: String?, var lastName: String?) {
    operator fun plus(that: User): User {
        return User(this.firstName + that.firstName, this.lastName + that.lastName)
    }

    companion object Companion {
        @JvmStatic
        fun of(): User {
            throw RuntimeException("Damn it")
        }
    }
}

fun hello(): User {
    val user = object : User(
        firstName = "333",
        lastName = null
    ) {
        fun isPerson(): Boolean {
            return true
        }
    }

    synchronized(user) {
        synchronized(user) {

        }
    }

    user.let { it.isPerson() }

    val user2 = User("e", "f")

    return user + user2
}