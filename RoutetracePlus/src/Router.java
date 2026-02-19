import java.util.*;

public class Router {
    String IPAddress = "";
    int packetSize = 0;
    List<String> packetTypes;
    List<Router> connectedRouters;
    boolean source = false;
    boolean target = false;
    int id = 0;
    int ipv = 0;

    QOL f = new QOL();

    public Router(String IPAddress, int packetSize, int id) {
        this.IPAddress = IPAddress;
        this.packetSize = packetSize;
        this.packetTypes = new ArrayList<>();
        this.connectedRouters = new ArrayList<>();
        this.id = id;
        this.ipv = determineIPVersion(IPAddress);
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

    public void setSource(boolean isSource) {
        this.source = isSource;
    }

    public void setTarget(boolean isTarget) {
        this.target = isTarget;
    }

    public void displayInfo() {
        f.print("[" + this.id + "] Router IP Address: " + IPAddress);
        if (source) {f.print(" (Source)");}
        if (target) {f.print(" (Target)");}
        f.blank();
        f.println("Version: IPv" + ipv);

        f.println("Packet Size: " + packetSize);
        f.println("Supported Packet Types: " + String.join(", ", packetTypes));
        f.println("Connected Routers: ");
        for (Router router : connectedRouters) {
            f.println("[" + router.id + "] " + router.IPAddress);
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
    public boolean hasPacketType(String packetType) {
        return packetTypes.contains(packetType);
    }

    public List<Router> getConnectedRouters() {
        return connectedRouters;
    }

    public int getID() {
        return id;
    }

    public boolean isSource() {
        return source;
    }

    public boolean isTarget() {
        return target;
    }

    public int determineIPVersion(String ipAddress) {
        if (ipAddress.contains(":")) {
            return 6; // IPv6
        } else if (ipAddress.contains(".")) {
            return 4; // IPv4
        } else {
            return 0; // Unknown format
        }
    }

    public int getIPVersion() {
        return ipv;
    }

    public void setIPVersion(int version) {
        this.ipv = version;
    }
}
