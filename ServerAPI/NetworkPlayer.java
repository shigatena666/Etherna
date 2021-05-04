package ServerAPI;

import java.net.InetAddress;

public class NetworkPlayer {
    private int ID;
    private String username;
    private InetAddress ipAddress;
    private int port;

    public NetworkPlayer() {
        this.ID = -1;
        this.username = "empty";
        this.ipAddress = null;
        this.port = 0;
    }

    public NetworkPlayer(int ID, String username, InetAddress ipAddress, int port) {
        this.ID = ID;
        this.username = username;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
