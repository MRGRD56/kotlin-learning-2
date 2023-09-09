package j

fun main() {
    val fullPath = "/Users/yole/kotlin-book/chapter.adoc"

    val regex = """(.*)/(.+)\.(.+)$""".toRegex()
    val match = regex.matchEntire(fullPath)

//    val groupValues = match!!.groupValues
//    val (_, path, fileName, fileExtension) = groupValues
    val (path, fileName, fileExtension) = match!!.destructured

    println("path: $path")
    println("name: $fileName")
    println("extension: $fileExtension")
}