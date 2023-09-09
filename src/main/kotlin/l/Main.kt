package l

import i.encodeUrl
import java.io.PrintWriter
import javax.net.SocketFactory

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

    SocketFactory.getDefault().createSocket("localhost", 8080).use { socket ->
        socket.getOutputStream().bufferedWriter().use { originalRequestWriter ->
            PrintWriter(originalRequestWriter).apply {
//                println("GET /proxy?url=${"https://st.mrgrd56.ru/util/lorem.txt".encodeUrl()} HTTP/1.1")
                println("GET /proxy?url=${"http://localhost:8080/proxy?url=http://localhost:8080/mock/ro?page=50".encodeUrl()} HTTP/1.1")
                println("Host: localhost")
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