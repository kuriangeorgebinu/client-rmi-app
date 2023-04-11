import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Locate the RMI registry
            Registry registry = LocateRegistry.getRegistry(1099);

            // Look up the DataLogger remote object
            DataLogger dataLogger = (DataLogger) registry.lookup("DataLogger");

            // Create and send a SerializableObject to the server for logging
            SerializableObject data = new SerializableObject("John Doe", 30);
            dataLogger.logData(data);

        } catch (Exception e) {
            System.err.println("Failed to communicate with the server: " + e.getMessage());
        }
    }
}