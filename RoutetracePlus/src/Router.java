import java.util.*;

public class Router {
    String IPAddress = "";
    int packetSize = 0;
    List<String> packetTypes;
    List<Router> connectedRouters;

    public Router(String IPAddress, int packetSize) {
        this.IPAddress = IPAddress;
        this.packetSize = packetSize;
        this.packetTypes = new ArrayList<>();
        this.connectedRouters = new ArrayList<>();


    }

    public void connectRouter(Router router) {
        connectedRouters.add(router);
    }

    public void addPacketType(String packetType) {
        packetTypes.add(packetType);
    }

    public void displayInfo() {
        System.out.println("Router IP Address: " + IPAddress);
        System.out.println("Packet Size: " + packetSize);
        System.out.println("Supported Packet Types: " + String.join(", ", packetTypes));
        System.out.println("Connected Routers: ");
        for (Router router : connectedRouters) {
            System.out.println(" - " + router.IPAddress);
        }
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public int getPacketSize() {
        return packetSize;
    }

    public List<String> getPacketTypes() {
        return packetTypes;
    }

    public List<Router> getConnectedRouters() {
        return connectedRouters;
    }
}
