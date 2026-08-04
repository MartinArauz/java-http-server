import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) throws IOException {

        try (ServerSocket server = new ServerSocket(8080)) {

            System.out.println("Server started on port 8080");

            while (true) {

                try (Socket socket = server.accept();
                     InputStream inputStream = socket.getInputStream();
                     OutputStream outputStream = socket.getOutputStream()) {

                    System.out.println("Client accepted from: " + socket.getRemoteSocketAddress());

                    byte[] buffer = new byte[1024];
                    int read = inputStream.read(buffer);
                    String message = new String(buffer, 0, read, StandardCharsets.UTF_8);
                    System.out.println(message);

                    String body = "Hello! How are you doing?";
                    byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
                    String headers =
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: text/plain\r\n" +
                                    "Content-Length: " + bodyBytes.length + "\r\n" +
                                    "\r\n";

                    outputStream.write(headers.getBytes(StandardCharsets.UTF_8));
                    outputStream.write(bodyBytes);
                    outputStream.flush();

                }
            }
        }

    }
}