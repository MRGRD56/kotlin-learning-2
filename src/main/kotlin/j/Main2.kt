package j

fun main() {
    val fullPath = "/Users/yole/kotlin-book/chapter.adoc"

    val path = fullPath.substringBeforeLast('/')
    val file = fullPath.substringAfterLast('/')
    val fileName = file.substringBefore('.')
    val fileExtension = file.substringAfter('.')

    println("path: $path")
    println("name: $fileName")
    println("extension: $fileExtension")
}