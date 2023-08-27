package i

import i.HttpMethod.GET
import java.io.InputStream
import java.io.PrintStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun main() {
    val stringBuilder = StringBuilder()
    stringBuilder
        .V { append("ewr") }
        .V { }

    val request = HttpRequest<Unit>(
        GET, "/search" query mapOf("q" to "hello world", "lang" to "en-US"),
        headers = LinkedHashMap<String, String>().also { headers ->
            headers["Host"] = "google.com"
            headers["Content-Type"] = "application/json"
        })

    println(request)
    request.print(System.out)
}

fun getValue(items: List<Int>): Int =
    items.asSequence()
        .filter { it > 7 }
        .firstOrNull() ?: -1

fun <T> T.V(action: T.() -> Any?): T {
    action(this)
    return this
}

data class HttpRequest<T>(
    val method: HttpMethod = GET,
    val path: String,
    val httpVersion: String = "HTTP/1.1",
    val headers: Map<String, String> = mapOf(),
    val body: InputStream? = null
) {
    fun print(out: PrintStream) {
        out.println("${method.name} $path $httpVersion")
        headers.forEach { out.println("${it.key}: ${it.value}") }
        out.println()
        body?.transferTo(out)
    }
}

enum class HttpMethod {
    GET, POST, PUT, DELETE
}

fun String.encodeUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8)

infix fun String.query(params: Map<String, String>): String =
    params.map { "${it.key.encodeUrl()}=${it.value.encodeUrl()}" }
        .joinToString(separator = "&", prefix = "?")
        .let { this + it }