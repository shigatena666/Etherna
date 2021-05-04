package Server;

import Game.Characters.Character;
import Game.Managers.CharacterManager;
import ServerAPI.EventCode;
import ServerAPI.NetworkPlayer;
import ServerAPI.Packets.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server extends Thread {
    public final List<NetworkPlayer> networkPlayers = new ArrayList<>();
    private DatagramSocket socket;
    private Eterna eterna;

    //region Constuctors

    /**
     * Default ctor.
     * Allows you to create a server for a client to connect on with an IpAdress.
     *
     * @param port The port to listen to. Care on localhost port should be different for the server and the client.
     */
    public Server(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region Methods

    //TODO: Optimize, due to the networking system, not optimized at all.
    //Preferably use ID instead but needs serializing.
    public NetworkPlayer getPlayer(InetAddress ipAddress, int port) {
        return networkPlayers.stream()
                .filter(x -> x.getIpAddress().equals(ipAddress) && x.getPort() == port)
                .findFirst()
                .orElse(null);
    }

    /**
     * This method is overrided from Thread.
     * Allows you to receive data from a client
     */
    @Override
    public void run() {
        eterna = new Eterna();
        while (true) {
            byte[] data = new byte[4096]; //TODO: Make size changeable later
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    /**
     * Execute a function depending on the packet that has been received.
     * If the packet isn't recognized, Unknown case will be executed.
     *
     * @param data The data that has been filled by the sender.
     * @param address The sender's address whom sent the packet.
     * @param port The sender's port whom sent the packet.
     */
    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        EventCode code = Packet.getPacket(message);
        switch (code) {

            case Unknown:
                break;

            case UpdateMap: {
                //TODO: Useless for server (?), remove.
                MapPacket mapPacket = new MapPacket(data);
                System.out.println("[Received by server] [" + address.getHostAddress() + ":" + port + "] " +
                        " received map update");
                sendToAllClients(data);
                break;
            }

            case Move: {
                MovePacket movePacket = new MovePacket(data);
                NetworkPlayer sender = getPlayer(address, port);

                //TODO: Check if tile is empty, bug happens that you can override a wall.
                //If player is on Map, move him
                if (Arrays.stream(CharacterManager.getPlayers()).anyMatch(x -> x.getID() == sender.getID())) {
                    Character character = Arrays.stream(CharacterManager.getPlayers())
                            .filter(x -> x.getID() == sender.getID())
                            .findFirst()
                            .get();

                    if (eterna.getMap().isEmpty(movePacket.getDirection(), character)) {
                        eterna.getMap().move(character, movePacket.getDirection());

                        //Send back map to everyone
                        MapPacket mapPacket = new MapPacket(eterna.getMap());
                        sendToAllClients(mapPacket.getData());

                        System.out.println("[Received by server] [" + address.getHostAddress() + ":" + port + "] "
                                + sender.getUsername() + " moved to: " + movePacket.getDirection());
                    }
                    else {
                        send(new MessagePacket("Try again, something is on the way").getData(), address, port);
                        System.out.println("[Received by server] [" + address.getHostAddress() + ":" + port + "] "
                                + sender.getUsername() + " couldn't move to: " + movePacket.getDirection());
                    }
                    //Send player possible actions
                    String possibleActions = "You can :\n" +
                            "1 - move (2PA)\n" +
                            "2 - attack (3PA)\n" +
                            "3 - use an object (Variable)\n" +
                            "4 - take/drop an item (2PA)\n" +
                            "5 - skip turn and keep PAs\n" +
                            "\n" +
                            "Your choice: ";
                    MessagePacket messagePacket = new MessagePacket(possibleActions);
                    send(messagePacket.getData(), address, port);
                }
                break;
            }

            case Disconnect: {
                DisconnectPacket disconnectPacket = new DisconnectPacket(data);
                NetworkPlayer sender = getPlayer(address, port);
                System.out.println("[Received by server] [" + address.getHostAddress() + ":" + port + "] " +
                        disconnectPacket.getUsername() + " has left the server.");
                Arrays.stream(CharacterManager.getPlayers())
                        .filter(x -> x.getID() == sender.getID())
                        .findFirst()
                        .ifPresent(CharacterManager.characters::remove);
                break;
            }

            case Connect: {
                LoginPacket loginPacket = new LoginPacket(data);

                //Create new instance of networkplayer when a player join and add him to the list.
                NetworkPlayer networkPlayer = new NetworkPlayer(networkPlayers.size() + 1, loginPacket.getUsername(), address, port);
                networkPlayers.add(networkPlayer);

                System.out.println("[Received by server] [" + address.getHostAddress() + ":" + port + "] " +
                       "ID: " + networkPlayer.getID() + " " + loginPacket.getUsername() + " has joined the server.");

                //Add the new player to the map.
                eterna.OnPlayerConnected(networkPlayer);

                //Create a map packet that has the player informations initialized.
                MapPacket mapPacket = new MapPacket(eterna.getMap());
                send(mapPacket.getData(), networkPlayer.getIpAddress(), networkPlayer.getPort());

                //Send player name formatted as [name]
                MessagePacket messagePacket = new MessagePacket("[" + networkPlayer.getUsername() + "]");
                send(messagePacket.getData(), networkPlayer.getIpAddress(), networkPlayer.getPort());

                //Send player informations
                messagePacket = new MessagePacket(eterna.getPlayer(networkPlayer).toString());
                send(messagePacket.getData(), networkPlayer.getIpAddress(), networkPlayer.getPort());

                //Send player possible actions
                String possibleActions = "You can :\n" +
                        "1 - move (2PA)\n" +
                        "2 - attack (3PA)\n" +
                        "3 - use an object (Variable)\n" +
                        "4 - take/drop an item (2PA)\n" +
                        "5 - skip turn and keep PAs\n" +
                        "\n" +
                        "Your choice: ";
                messagePacket = new MessagePacket(possibleActions);
                send(messagePacket.getData(), networkPlayer.getIpAddress(), networkPlayer.getPort());
                break;
            }

            case Message: {
                MessagePacket messagePacket = new MessagePacket(data);
                break;
            }
        }
    }

    /**
     * Send some data to a specified client.
     *
     * @param data The byte[] as data to send.
     * @param ipAddress The IpAddress from the client to send the data to.
     * @param port The port that the client uses with its IpAdress.
     */
    public void send(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send some data to every clients connected in-game.
     *
     * @param data The byte[] as data to send.
     */
    public void sendToAllClients(byte[] data) {
        for (NetworkPlayer player : networkPlayers) {
            send(data, player.getIpAddress(), player.getPort());
        }
    }

    //endregion
}
