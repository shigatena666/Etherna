package Game;

import Client.Client;
import Game.Utilitaries.Direction;
import ServerAPI.Packets.LoginPacket;
import ServerAPI.Packets.MovePacket;

import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the server's IP address ?");
        String response = scanner.next();

        System.out.println("What is the server's port ?");
        int port = scanner.nextInt();

        Client client = new Client(response, port);
        client.start();

        LoginPacket login = new LoginPacket("shigatena");
        client.send(login.getData());

        while (true) {
            var command = scanner.next();
            switch (command) {
                case "1":
                    System.out.println("In which direction? (Up, Down, Left, Right)");
                    //TODO: Fix exception "right" and not "Right"
                    var direction = Direction.valueOf(scanner.next());
                    MovePacket move = new MovePacket(direction);
                    client.send(move.getData());
                    break;

                case "2":
                    break;

                case "3":
                    break;

                case "4":
                    break;

                case "5":
                    break;
            }
        }

        //MovePacket move = new MovePacket(Direction.randomDirection());
        //client.send(move.getData());

        //DisconnectPacket disconnect = new DisconnectPacket("shigatena");
        //client.send(disconnect.getData());

        //MapPacket map = new MapPacket(new Map());
        //client.send(map.getData());
    }
}
