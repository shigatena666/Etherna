package Game;

import Server.Server;
import java.util.Scanner;

public class ServerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to start a server ?");
        String response = scanner.next();
        switch (response)
        {
            case "y":
            case "yes":
                Server server = new Server(4567);
                server.start();
                break;
            case "n":
            case "no":
                break;
        }
    }
}
