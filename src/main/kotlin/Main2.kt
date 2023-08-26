fun main() {
    val student1 = Student("First")
    val student2 = Student("Second")
    student1.sayHello(listOf("f", "s", "t"))
    student1.sayHello(listOf(student1, student2))
}

class Student(val name: String) {
    @JvmName("sayHelloToNames")
    fun sayHello(students: Collection<String>) {
        println("Saying hello to strings $students")
    }

    @JvmName("sayHelloToStudents")
    fun sayHello(students: Collection<Student>) {
        println("Saying hello to objects $students")
    }
}