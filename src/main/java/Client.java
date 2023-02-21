import java.util.Objects;
import java.util.Set;

public class Client {
    private String name;
    private Node node;

    public Resource getResource(String name) {
        Node targetNode = node.findNodeById(name.hashCode());
        return targetNode.getResourceByName(name);
    }

    public Resource publishResource(Resource resource) {
        Node targetNode = node.findNodeById(resource.getName().hashCode());
        targetNode.publishResource(resource);
        return resource;
    }

    public int getId() {
        return node.getId();
    }

    public Set<Resource> getResources() {
        return node.getAllResources();
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", node=" + node +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(node, client.node);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

    public static class Builder {
        private Client client;

        public static Builder create(){
            return new Builder();
        }

        public Builder() {
            client = new Client();
        }

        public Builder setName(String name) {
            client.name = name;
            return this;
        }

        public Builder setDefaultNode() {
            client.node = new Node();
            return this;
        }

        public Builder setPremadeNode(int ip) {
            client.node = new Node(ip, true);
            return this;
        }

        public Builder boot() throws Exception {
            if (client.node == null) {
                throw new Exception("Нода не установлена");
            }
            client.node.boot();

            return this;
        }

        public Builder boot(int ip) throws Exception {
            if (client.node == null) {
                throw new Exception("Нода не установлена");
            }
            client.node.boot(Network.getInstance().getNodeByIp(ip));

            return this;
        }

        public Client build() {
            return client;
        }
    }
}
