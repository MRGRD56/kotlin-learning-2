class Program {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            this::class
        }
    }
}

class Main3 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val people: List<Person> = mutableListOf(
                Person("Ivan", 18, Gender.MALE),
                Person("John", 25, Gender.MALE),
                Person("Maria", 12, Gender.FEMALE),
                Person("Marta", 10, Gender.FEMALE),
                Person("Jack", 65, Gender.MALE),
                Person("Sakura", 30, Gender.FEMALE),
                Person("Mamiya", 17, Gender.MALE),
                Person("Michael", 10, Gender.MALE)
            )

            val peopleByGenders = people.asSequence()
                .filter { it.age >= 18 }
                .groupBy({ it.gender }, { it })

            for ((gender, genderPeople) in peopleByGenders) {
                println("People of the $gender gender:")

                for (person in genderPeople) {
                    println("  $person")
                }
            }
        }
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
}