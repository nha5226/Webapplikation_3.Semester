package Webapplication.Assignments04;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WebServer {

    public static void main( String[] args ) throws Exception {
        //Hier erstellen wir einen neuen Serversocket
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                //Hier müssen wir der Server accepten. Er wird blockiert, solange er keine antwort bekommt
                try (Socket socketClient = serverSocket.accept()) {
                    clientHandler(socketClient);
                }
            }
        }
    }

    //Hier implementieren wir den client handler (die wir zwar optional waren ich jedoch trotzdem probieren wollte)
    private static void clientHandler(Socket socketclient) throws IOException {
        //Hier können wir den InputStream vom ClientSocket
        BufferedReader br = new BufferedReader(new InputStreamReader(socketclient.getInputStream()));

        StringBuilder request = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isBlank()) {
            request.append(line + "\r\n");
        }
//Bis hierher können wir nun eine gesamte Abfrage protokollieren-----------------------------------------


        //Hier analysieren wir die Anfrage
        String s = request.toString();
        String[] requestsLines = s.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        //GET
        String method = requestLine[0];
        //URL oder pathname
        String pathname = requestLine[1];
        //HTTP Version (HTTP/1.1)
        String httpversion = requestLine[2];
        //Host ist einfach Host: 2
        String host = requestsLines[1].split(" ")[1];


        //Und hier machen wir den localhost also Host: localhost
        List<String> headers = new ArrayList<>();
        for (int h = 2; h < requestsLines.length; h++) {
            String header = requestsLines[h];
            headers.add(header);
        }

        //Hier wird alles ausgegeben
        String accessLog = String.format("Client %s, method %s, pathname %s, httpversion %s, host %s, headers %s",
                socketclient.toString(), method, pathname, httpversion, host, headers.toString());
        System.out.println(accessLog);


        //Hier schauen wir, ob es den Pfad gibt und Returnen die File
        Path filePath = getFilePath(pathname);
        if (Files.exists(filePath)) {
            //wenn es die File findet passiert das
            String contentType = guessContentType(filePath);
            sendResponse(socketclient, "200 OK", contentType, Files.readAllBytes(filePath));
        } else {
            //wenn es die File nicht findet, kommt ein Not Found Errer
            byte[] notFoundContent = "<h1>File not found :(</h1>".getBytes();
            sendResponse(socketclient, "404 Not Found", "text/html", notFoundContent);
        }

    }

    //Hier erstellen wir einen Path und den genauen Ort zu zeige
    private static Path getFilePath(String path) {
        if ("/".equals(path)) {
            path = "C:\\Users\\ninah\\Desktop\\FH Dornbirn\\Programming\\src\\Webapplication\\Assignments04\\Webroot\\index2.html";
        }

        return Path.of(path);
    }

    //Hier senden wir eine Antwort
    private static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException {
        OutputStream clientOutput = client.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
        clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(content);
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        client.close();
    }



    //Hier sagen wir dem Browser was für eine Art von Content wir ihm senden
    private static String guessContentType(Path filePath) throws IOException {
        return Files.probeContentType(filePath);
    }

}