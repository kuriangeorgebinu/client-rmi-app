import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Create and export the DataLoggerImpl object
            DataLogger dataLogger = new DataLoggerImpl();

            // Bind the DataLoggerImpl object to the RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("DataLogger", dataLogger);

            System.out.println("Server is running and waiting for client requests...");
        } catch (Exception e) {
            System.err.println("Failed to start the server: " + e.getMessage());
        }
    }
}