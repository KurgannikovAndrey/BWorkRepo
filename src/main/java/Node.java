import java.util.*;
import java.util.stream.Collectors;

public class Node {
    private final int ip;
    private int id;
    private final Set<Node> pool;
    private final Map<Integer, Integer> resourceIdToSourceIdMap;
    private final Map<String, Resource> resourceTable;
    private Node next;
    private Node prev;

    public Node(){//client node
        this(Math.abs(new Random().nextInt()), true);
    }

    public Node(int ip, boolean register){//external node
        this.ip = ip;
        this.id = Utils.hash(ip);
        this.pool = new TreeSet<>(Comparator.comparingInt(Node::hashCode));
        this.resourceIdToSourceIdMap = new HashMap<>();
        this.resourceTable = new HashMap<>();

        if (register) {
            Network.getInstance().register(this);
        }
    }

    public Node boot(Node node){
        this.next = node.findNodeById(this.id);
        this.prev = next.prev;
        for (Integer resourceId : next.resourceIdToSourceIdMap.keySet()) {
            if (resourceId < this.id) {
                this.resourceIdToSourceIdMap.put(resourceId, next.resourceIdToSourceIdMap.get(resourceId));
            }
        }
        next.prev = this;
        refillPool();
        prev.next = this;

        node.refillPool();

        return this;
    }

    public Node boot(){
        this.next = this;
        this.prev = this;
        refillPool();

        return this;
    }

    public Node findNodeById(int id){
//        if (id > prev.getId() && id <= this.id) {
//            return this;
//        } else {
//            for (Node node : pool) {
//                if (id <= node.getId()) {
//                    return node;
//                }
//            }
//            return this.id >= id ? this : next.id >= id ? next : this;
//        }
//
        var find = pool.stream().filter(x -> x.id >= id).min(Comparator.comparingInt(x -> x.id));
        var result = find.orElseGet(() -> pool.stream().min(Comparator.comparingInt(x -> x.id)).get());

//        var toFind = new Node(id, false);
//        var poolTree = ((TreeSet<Node>)pool);
//        var find = poolTree.contains(toFind) ? poolTree.floor(toFind) : poolTree.higher(toFind);
//        var result = find != null ? find : poolTree.first();

        return result == this ? this : result.findNodeById(id);
    }

    public Node refillPool(){
        pool.clear();
        pool.add(this);
        pool.add(prev);
        pool.add(next);
        System.out.println("refill start : " + this.id + " " + Utils.toDegree(this.id));
        for (int i = 0; i < 32; i++){
            int targetId = Utils.sumWithOutOverflow(this.id, (int) Math.pow(2, i));
            var target = findNodeById(targetId);
            pool.add(target);
        }
        return this;
    }

    public int getIp() {
        return ip;
    }

    public int getId() {
        return id;
    }


    public Resource getResourceByName(String name) {
        var toFound = new Resource(name, null);
        var target = findNodeById(toFound.getId());
        return Network.getInstance().getNodeByIp(target.resourceIdToSourceIdMap.get(toFound.getId()))
                .resourceTable.get(name);
    }
    
    public void publishResource(Resource resource) {
        var target = findNodeById(resource.getId());
        target.resourceIdToSourceIdMap.put(resource.getId(), this.id);//put
        resourceTable.put(resource.getName(), resource);//this
    }

    public Set<Resource> getAllResources(){
        return new HashSet<>(resourceTable.values());
    }

    public Map<Integer, Integer> getResourceIdToSourceIdMap() {
        return resourceIdToSourceIdMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return ip == node.ip && id == node.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "ip=" + ip +
                ", id=" + Utils.toDegree(id) +
                ", next=" + Utils.toDegree(next.ip) +
                ", prev=" + Utils.toDegree(prev.ip) +
                ", pool=" + pool.stream().map(x -> Utils.toDegree(x.getId())).collect(Collectors.toList()) +
                ", resourceIdToSourceIdMap=" + resourceIdToSourceIdMap +
                ", resourceTable=" + resourceTable +
                '}';
    }
}
