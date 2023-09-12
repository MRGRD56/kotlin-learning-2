package n

import java.util.*

fun main() {
    val people = listOf<Person?>(
        Person("Ivan", 10, Gender.MALE),
        Person("John", 21, Gender.MALE),
        null,
        null,
        Person("Rachel", 19, Gender.FEMALE),
        Person("Olga", 35, Gender.FEMALE),
        Person("Greg", 35, Gender.MALE),
        Person("Vlad", 35, Gender.MALE),
        null,
        Person("Grigory", 9, Gender.MALE),
        Person("", -1, Gender.FEMALE),
        Person("Mark", 27, Gender.MALE)
    )

    val peopleByGender = people.asSequence()
        .filterNotNull()
        .filterNot { it.name.isEmpty() || it.age < 0 }
        .groupBy { it.gender }
        .mapValues { (_, value) ->
            value.asSequence()
                .map { it.age.toDouble() }
                .average()
        }

//    val peopleByGender = people.asSequence()
//        .filterNotNull()
//        .filterNot { it.name.isEmpty() || it.age < 0 }
//        .groupingBy { it.gender }
//        .fold(0.0 to 0.0) { (sum, count), person ->
//            sum + person.age to count + 1
//        }
//        .mapValues { (_, value) ->
//            if (value.second == 0.0) 0.0
//            else value.first / value.second
//        }
//        .mapValuesTo(TreeMap()) { (_, value) ->
//            value.groupBy { it.age }
//        }

//    ByteArray(0).binarySearch()

    val arr = arrayOfNulls<String>(10)

    println(peopleByGender)
}

data class Person(
    val name: String,
    val age: Int,
    val gender: Gender
)

enum class Gender {
    MALE,
    FEMALE
}