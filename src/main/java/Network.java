import java.util.HashMap;
import java.util.Map;

public class Network {
    private Map<Integer, Node> ipNodeMap;

    private static Network network;

    private Network() {
        ipNodeMap = new HashMap<>();
    }

    public static Network getInstance() {
        if (network == null) {
            network = new Network();
        }
        return network;
    }

    public Node register(Node node){
        ipNodeMap.put(node.getIp(), node);
        return node;
    }

    public Node getNodeByIp(int ip){
        return ipNodeMap.get(ip);
    }

    @Override
    public String toString() {
        return "Network{" +
                "ipNodeMap=" + ipNodeMap +
                '}';
    }
}
