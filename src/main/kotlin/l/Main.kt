package l

import i.encodeUrl
import java.io.OutputStream
import java.io.PrintWriter
import javax.net.ssl.SSLSocketFactory

fun main() {
//    SocketFactory.getDefault().createSocket("localhost", 8080).use { socket ->
//        socket.getOutputStream().bufferedWriter().use { originalRequestWriter ->
//            PrintWriter(originalRequestWriter).apply {
//                println("GET /mock/chunked HTTP/1.1")
//                println("Host: localhost")
//                println("Connection: close")
//                println()
//                flush()
//            }
//
//            socket.getInputStream().buffered().use { responseReader ->
//                responseReader.transferTo(System.out)
//            }
//        }
//    }

    SSLSocketFactory.getDefault().createSocket("api.mrgrd56.ru", 443).use { socket ->
        MultiOutputStream(socket.getOutputStream().buffered(), System.out).use { originalRequestStream ->
            PrintWriter(originalRequestStream).apply {
//                println("GET /proxy?url=${"https://st.mrgrd56.ru/util/lorem.txt".encodeUrl()} HTTP/1.1")
                println("GET /proxy?url=${"http://localhost:8080/mock/ro?size=1".encodeUrl()} HTTP/1.1")
                println("Host: api.mrgrd56.ru")
                println("Connection: close")
//                println("Referer: mrgrd56.ru")
//                println("Origin: mrgrd56.ru")
                println()
                flush()
            }

            socket.getInputStream().buffered().use { responseReader ->
                responseReader.transferTo(System.out)
            }
        }
    }

//    SocketFactory.getDefault().createSocket("st.mrgrd56.ru", 80).use { socket ->
//        socket.getOutputStream().bufferedWriter().use { originalRequestWriter ->
//            PrintWriter(originalRequestWriter).apply {
//                println("GET /util/lorem.txt HTTP/1.1")
//                println("Host: st.mrgrd56.ru")
//                println("X-Enforce-Protocol: HTTP")
//                println("Connection: close")
//                println()
//                flush()
//            }
//
//            socket.getInputStream().buffered().use { responseReader ->
//                responseReader.transferTo(System.out)
//            }
//        }
//    }
}

class MultiOutputStream(private vararg val outputStreams: OutputStream) : OutputStream() {
    override fun write(b: Int) {
        outputStreams.forEach { it.write(b) }
    }

    override fun write(b: ByteArray) {
        outputStreams.forEach { it.write(b) }
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        outputStreams.forEach { it.write(b, off, len) }
    }

    override fun flush() {
        outputStreams.forEach { it.flush() }
    }

    override fun close() {
        outputStreams.forEach { it.close() }
    }
}