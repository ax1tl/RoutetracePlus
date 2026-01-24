import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class main {
    static QOL f = new QOL();
    static int id = 0;

    public static String ping(int packet_size, int ttl, String target) {
        try {
            ProcessBuilder pb;
            String cmd = String.format("ping -f -l %d -n 1 -i %d %s", packet_size, ttl, target); // build command

            pb = new ProcessBuilder("cmd.exe", "/c", cmd);

            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (id==6){
                    f.println(line);
                }
                if (line.contains("Reply from")) {
                    // extract the ip address from the line
                    String[] parts = line.split(" ");

                    String ipAddress = parts[2].replace(":", "");
                    return ipAddress;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    public static void run_ping(int packet_size, int max_hops, String target, List<Router> routerList) {
        
        for (int i = 1; i <= max_hops; i++) {

            Router router = createRouter(ping(packet_size, i, target), packet_size, "ICMP", routerList);  
            Router existingRouter = doesRouterExist(router.getIPAddress(), routerList);

            if (i == 1) {
                router.setSource(true);
            }
            if (i > 1) { // ensure not first router
                Router prevRouter = routerList.get(i - 2);

                // check if router already exists
                if (existingRouter != null) {
                    // check if connection already exists
                    if (doesConnectionExist(existingRouter, prevRouter) == null && !existingRouter.IPAddress.equals(prevRouter.IPAddress)) {
                        existingRouter.connectRouter(prevRouter); // connect to previous router if not connected
                    }
                    router = null; // prevent adding duplicate router
                    break;
                }

                router.connectRouter(prevRouter); // connect to previous router
            }
            if (router.getIPAddress().equals(target)) {
                router.setTarget(true);
            }

            routerList.add(router); // add new router to list

        }
    }

    public static String tcpPing(int ttl, String target) {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c","tcping -t " + ttl + " -n 1 " + target + " 443");

            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Reply from")) {
                    // extract the ip address from the line
                    String[] parts = line.split(" ");

                    String ipAddress = parts[2].replace(":", "");
                    return ipAddress;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String resolveIP(String hostname) {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "nslookup " + hostname);
            Process process = pb.start();

            QOL f = new QOL(); // ==============================================================================================================================

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            String[] parts = new String[3];
            while ((line = reader.readLine()) != null) {
                if (line.contains("Address")) {
                    // extract the ip address from the line
                    parts = line.split(" ");
                }
            }
            return parts[2];

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Router doesRouterExist(String ipAddress, List<Router> routerList) {
        for (Router router : routerList) {
            if (router.getIPAddress().equals(ipAddress)) {
                id--;
                return router;
            }
        }
        return null;
    }
    public static Router doesConnectionExist(Router routerA, Router routerB) {
        for (Router router : routerA.connectedRouters) {
            if (router.getIPAddress().equals(routerB.getIPAddress())) {
                return router;
            }
        }
        return null;
    }

    public static Router createRouter(String ipAddress, int packetSize, String packetType, List<Router> routerList) {
        if (doesRouterExist(ipAddress, routerList) != null) {
            return doesRouterExist(ipAddress, routerList);
        }
        Router router = new Router(ipAddress, packetSize, id);
        router.addPacketType(packetType);
        router.updatePacketSize(packetSize);
        id++;
        return router;
    }

    public static List<Router> removeDuplicateRouters(List<Router> routerList) {
        List<Router> uniqueRouters = new ArrayList<>();
        for (Router router : routerList) {
            if (doesRouterExist(router.getIPAddress(), uniqueRouters) == null) {
                uniqueRouters.add(router);
            }
        }
        return uniqueRouters;
    }

    public static void main(String[] args) throws Exception {
        QOL f = new QOL();
        Scanner scan = new Scanner(System.in);

        List<Router> routerList = new ArrayList<>();

        f.blank(10);
        // run a command to ping routers in traceroute fashion
            // retrieve the router ipaddress
            // create router object
            // get next router, create object, connect to previous router
        
        // get user input
        f.print("Enter target hostname or IP address: ");
        String target = resolveIP(scan.nextLine());

        f.println("Resolved target IP address: " + target);

        f.print("Enter packet size (in bytes): ");
        int packetSize = scan.nextInt();
        f.print("Enter max hops: ");
        int maxHops = scan.nextInt();

        f.bar(true);

        // run ping commands and get routers
        for (int i = 1; i <= 4; i++) {
            run_ping((packetSize/4)*i, maxHops, target, routerList);
        }

        routerList = removeDuplicateRouters(routerList);

        for (Router router : routerList) {
            router.displayInfo();
            f.blank();
        }





        
        scan.close();
    }
}
