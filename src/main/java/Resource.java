public class Resource {
    private int id;
    private String name;
    private String value;
    
    public Resource(String name, String value) {
        this.name = name;
        this.value = value;
        this.id = Math.abs(name.hashCode());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + Utils.toDegree(id) +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
