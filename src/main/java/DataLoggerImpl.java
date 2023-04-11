import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataLoggerImpl extends UnicastRemoteObject implements DataLogger {
    private static final String LOG_FILE_PATH = "data.log";

    public DataLoggerImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void logData(SerializableObject data) throws RemoteException {
        try (FileOutputStream fileOut = new FileOutputStream(LOG_FILE_PATH, true);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            // Timestamp the data
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String timestamp = sdf.format(new Date());

            // Write the data and timestamp to the log file
            String logEntry = "[" + timestamp + "] " + data.toString();
            objectOut.writeObject(logEntry);
            objectOut.writeObject(System.lineSeparator());
            System.out.println("Data logged: " + logEntry);
        } catch (IOException e) {
            System.err.println("Failed to log data: " + e.getMessage());
        }
    }
}