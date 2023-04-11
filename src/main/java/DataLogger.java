import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataLogger extends Remote {
    void logData(SerializableObject data) throws RemoteException;
}