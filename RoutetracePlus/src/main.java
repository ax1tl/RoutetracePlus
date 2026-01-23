import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class main {

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

            Router router = createRouter(ping(packet_size, i, target), packet_size, "ICMP");  

            if (i > 1) { // ensure not first router

                Router existingRouter = doesRouterExist(router.getIPAddress(), routerList);
                Router prevRouter = routerList.get(i - 2);

                // check if router already exists
                if (existingRouter != null) {
                    // check if connection already exists
                    if (doesConnectionExist(existingRouter, prevRouter) == null) {
                        existingRouter.connectRouter(prevRouter); // connect to previous router if not connected
                    }
                    router = null; // prevent adding duplicate router
                    break;
                }

                router.connectRouter(prevRouter); // connect to previous router
            }

            routerList.add(router); // add new router to list

        }
    }

    public static Router doesRouterExist(String ipAddress, List<Router> routerList) {
        for (Router router : routerList) {
            if (router.getIPAddress().equals(ipAddress)) {
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


    public static Router createRouter(String ipAddress, int packetSize, String packetType) {
        Router router = new Router(ipAddress, packetSize);
        router.addPacketType(packetType);
        router.updatePacketSize(packetSize);
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
        String target = scan.nextLine();
        f.print("Enter packet size (in bytes): ");
        int packetSize = scan.nextInt();
        f.print("Enter max hops: ");
        int maxHops = scan.nextInt();

        f.bar(true);

        for (int i = 1; i <= 4; i++) {
            run_ping((packetSize/4)*i, maxHops, target, routerList);
        }
        
        routerList = removeDuplicateRouters(routerList);
        
        for (Router router : routerList) {
            router.displayInfo();
            f.blank();
        }

    }
}
