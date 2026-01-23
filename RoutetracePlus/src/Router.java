import java.util.*;

public class Router {
    String IPAddress = "";
    int packetSize = 0;
    List<String> packetTypes;
    List<Router> connectedRouters;
    boolean client = false;
    boolean target = false;

    QOL f = new QOL();

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
        String type = packetType.toUpperCase();

        if (type == "ICMP" || type == "TCP" || type == "UDP"){
            packetTypes.add(packetType);
        } else {
            f.println("Unsupported packet type: " + packetType);
        }
    }
    public void removePacketType(String packetType) {
        packetTypes.remove(packetType);
    }

    public void updatePacketSize(int newSize) {
        if (newSize > packetSize){
            packetSize = newSize;
        }
    }

    public void setClient(boolean isClient) {
        this.client = isClient;
    }

    public void setTarget(boolean isTarget) {
        this.target = isTarget;
    }

    public void displayInfo() {
        f.print("Router IP Address: " + IPAddress);
        if (client) {f.print(" (Client)");}
        if (target) {f.print(" (Target)");}
        f.blank();

        f.println("Packet Size: " + packetSize);
        f.println("Supported Packet Types: " + String.join(", ", packetTypes));
        f.println("Connected Routers: ");
        for (Router router : connectedRouters) {
            f.println(" - " + router.IPAddress);
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

    public boolean isClient() {
        return client;
    }

    public boolean isTarget() {
        return target;
    }
}
