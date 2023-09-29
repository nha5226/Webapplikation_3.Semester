package Webapplication.Assignments03;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class WebBrowser {
    public static void main(String[] args) throws IOException {
        //Standarddomäne und -pfad
        String domain = "localhost";
        String path = "/";

        //Hier wird eine GET-Anfrage konstruiert
        String request = String.format("GET http://%s%s HTTP/1.0\r\n\r\n", domain, path);

        //Hier wird eine try-with-resources-Anweisung angegeben, damit sich der Socket auch wieder automatisch schließt
        try (Socket socket = new Socket(domain, 80)) {

            //Hier wird wieder der Writer mit einer try methode geschlossen
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
                //Hier wird die GET-Anfrage an den Socket geschrieben
                writer.write(request);
                writer.flush();

                //Hier wird wieder eine Try Anweisung genutzt um diesesmal den Reader zu schließen
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;

                    //Hier wird der Statuscode der Antwort geprüft
                    if ((line = reader.readLine()).contains("200")) {

                        //Lesen und Verwerfen der Kopfzeilen
                        while (!Objects.equals((line = reader.readLine()), "")) {
                            //Hier wird der Header ausgelassen
                        }

                        //Hier wird der Antworttext gelesen solange bis nichts null ist
                        while ((line = reader.readLine()) != null) {
                            response.append(line).append("\n");
                        }
                    } else {
                        //Hier werden die Statuszeile durch leerzeichen getrennt
                        String[] parts = line.split(" ", 2);
                        response.append(parts[1]);
                    }

                    //Hier wird die Antwort ausgegeben
                    System.out.println(response);
                }
            }
        }

        //Eingabe eines URLs vom User
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bitte geben sie eine gültige URL ein: ");
        String url = scanner.nextLine();
        InetAddress address = InetAddress.getByName(url);
        String host = address.getHostAddress();
        System.out.println(String.format("Verbunden mit: %s at %s", url, host));

        //Hier wird die URL aufgeteilt in Domainname und Domainreferenz
        if (url.contains("/")) {
            String[] split = url.split("/", 2);
            String hostPart = split[0];
            String pathPart = split[1];
            System.out.println(String.format("Host: %s", hostPart));
            System.out.println(String.format("Path: %s", pathPart));
        }

        scanner.close();
    }
}

