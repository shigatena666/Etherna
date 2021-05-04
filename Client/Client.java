package Client;

import ServerAPI.EventCode;
import ServerAPI.Packets.*;

import java.io.IOException;
import java.net.*;

public class Client extends Thread {
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private int port;
    private int ID;

    //region Constructors

    /**
     * Default ctor.
     *
     * @param ipAddress The ip address of the server to connect to.
     * @param port The port that the server has opened.
     */
    public Client(String ipAddress, int port) {
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
            this.socket = new DatagramSocket();
            this.port = port;
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region Methods

    /**
     * This method is overrided from Thread.
     * Allows you to receive data from the server by listening for incoming data.
     */
    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[4096]; //TODO: Make it changeable later
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            parsePacket(data);
        }
    }

    /**
     * Execute a function depending on the packet that has been received.
     * If the packet isn't recognized, Unknown case will be executed.
     *
     * @param data The data that has been filled by the server.
     */
    private void parsePacket(byte[] data) {
        String message = new String(data).trim();
        EventCode code = Packet.getPacket(message);
        switch (code) {

            case Unknown:
                break;

            case Move:
                break;

            case UpdateMap:
                MapPacket mapPacket = new MapPacket(data);
                System.out.println(mapPacket.getMap().toString());
                break;

            case Message:
                MessagePacket messagePacket = new MessagePacket(data);
                System.out.println(messagePacket.getMessage());
                break;

            case Disconnect:
                break;

            case Connect:
                break;
        }
    }

    /**
     * Allows you to send data to the server you're connected to.
     * @param data The data as a byte array to send
     */
    public void send(byte[] data) {
        //The port is the port opened on the server.
        //TODO: Serialize ID?
        //String message = new String(data);
        //String[] parts = message.split(",");
        //String reformatedMessage = parts[0] + ID + "," + parts[1];

        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region Accessors

    /**
     * @return The ip address of the client.
     */
    public InetAddress getIpAddress() {
        return ipAddress;
    }

    /**
     * @return The port on which the client is connected to (server).
     */
    public int getPort() {
        return port;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    //endregion
}
