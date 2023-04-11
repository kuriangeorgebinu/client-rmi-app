import java.io.Serializable;

public class SerializableObject implements Serializable {
    // Implement your data structure here
    // Example:
    private String name;
    private int age;

    public SerializableObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}