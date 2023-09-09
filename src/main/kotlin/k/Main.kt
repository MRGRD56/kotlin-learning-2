package k

import kotlinx.atomicfu.atomic
import java.io.InputStream
import java.io.PrintWriter
import javax.net.ssl.SSLSocketFactory

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE)
annotation class SuperDuper(
    val value: String
)

class CountingInputStream(private val inputStream: InputStream) : InputStream() {
    val count: Int
        get() = bytesRead.value

    private var bytesRead = atomic(0)

    override fun read(): Int {
        return inputStream.read().counted()
    }

    override fun read(b: ByteArray): Int {
        return inputStream.read(b).counted()
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        return inputStream.read(b, off, len).counted()
    }

    override fun readAllBytes(): ByteArray {
        return inputStream.readAllBytes().counted()
    }

    override fun readNBytes(len: Int): ByteArray {
        return inputStream.readNBytes(len).counted()
    }

    override fun readNBytes(b: ByteArray?, off: Int, len: Int): Int {
        return inputStream.readNBytes(b, off, len).counted()
    }

    private fun ByteArray.counted(): ByteArray {
        bytesRead += size
        return this
    }

    private fun Int.counted(): Int {
        bytesRead += this
        return this
    }
}

@SuperDuper("werwer")
fun main() {
    SSLSocketFactory.getDefault().createSocket("api.mrgrd56.ru", 443).use { socket ->
        socket.getOutputStream().bufferedWriter().use { originalRequestWriter ->
            PrintWriter(originalRequestWriter).apply {
                println("GET /mock/ro?size=1 HTTP/1.1")
                println("Host: api.mrgrd56.ru")
                println("Connection: close")
                println()
                flush()
            }

            socket.getInputStream().buffered().use { responseReader ->
                responseReader.transferTo(System.out)
            }
        }
    }
}