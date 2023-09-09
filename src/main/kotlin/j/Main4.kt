package j

import kotlin.math.max

open class User(val nickname : String)

class TwitterUser(nickname: String) : User(nickname) {
//    val age: Int

    constructor(nickname: String, age: Int) : this(nickname) {
//        this.age = age
    }
}

class FacebookUser : User {
    val age: Int

    constructor(nickname: String, age: Int) : super(nickname) {
        this.age = age
    }
}

class RedditUser(nickname: String, val age: Int) : User(nickname)

class Person {
    val firstName: String
    val lastName: String
    val age: Int

    constructor(firstName: String, lastName: String, age: Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.age = max(0, age)
    }

    val isAdult: Boolean
        get() = age >= 18
}

class Person2(val firstName: String, val lastName: String, age: Int) {
    val age: Int

    init {
        this.age = max(0, age)
    }

    constructor(firstName: String, lastName: String) : this(firstName, lastName, 0)

    val isAdult: Boolean
        get() = age >= 18
}