import java.nio.file.Paths;
import java.util.*;

public class Emulator {
    public static void main(String[] args) throws Exception {
        Map<String, Client> clients = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        List<String> input;
        boolean run = true;
        Client currentClient = null;

        System.out.println("Hello");
        while (run) {
            try {
                input = List.of(scanner.nextLine().split(" "));
                switch (input.get(0)) {
                    case "print":
                        System.out.println(String.join(" ", input.subList(1, input.size())));
                        break;
                    case "exit":
                        run = false;
                        break;
                    case "run":
                        var path = Paths.get("src\\main\\resources\\" + input.get(1));
                        scanner = new Scanner(path);
                        break;
                    case "default_input":
                        scanner = new Scanner(System.in);
                        break;
                    case "create":
                        String name = input.get(1);
                        String inputNodeIp = input.get(2);
                        clients.put(name,
                                Client.Builder.create().setName(name).setDefaultNode().boot(Integer.parseInt(inputNodeIp)).build());
                        break;
                    case "create_with_ip":
                        name = input.get(1);
                        inputNodeIp = input.get(2);
                        String preIp = input.get(3);
                        clients.put(name,
                                Client.Builder.create().setName(name).setPremadeNode(Integer.parseInt(preIp)).boot(Integer.parseInt(inputNodeIp)).build());
                        break;
                    case "root":
                        name = input.get(1);
                        clients.put(name,
                                Client.Builder.create().setName(name).setDefaultNode().boot().build());
                        break;
                    case "root_with_ip":
                        name = input.get(1);
                        preIp = input.get(2);
                        clients.put(name,
                                Client.Builder.create().setName(name).setPremadeNode(Integer.parseInt(preIp)).boot().build());
                        break;
                    case "client":
                        currentClient = clients.get(input.get(1));
                        break;
                    case "get":
                        System.out.println(currentClient.getResource(input.get(1)));
                        break;
                    case "publish":
                        currentClient.publishResource(new Resource(input.get(1), input.get(2)));
                        break;
                    case "view":
                        System.out.println(Network.getInstance());
                        System.out.println("Clients : ");
                        for (Client client : clients.values()) {
                            System.out.println(client);
                        }
                        break;
                    case "range_view":
                        rangeView(clients.values());
                        break;
                    default:
                        System.out.println("unsupported");
                }
            } catch (Exception e) {
                System.out.println(e);
                throw e;
            }
        }
    }

    private static void rangeView(Collection<Client> values) {
        Set<Client> clients = new HashSet<>(values);
        for (Client client : clients) {
            System.out.println("Client : " + Utils.toDegree(client.getId()) + " resources : " + client.getResources());
        }
    }
}
