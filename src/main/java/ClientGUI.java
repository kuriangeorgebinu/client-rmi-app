import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientGUI {
    private JFrame frame;
    private JButton sendButton;
    private JButton connectButton;
    private JTextField nameField;
    private JTextField ageField;
    private JLabel statusLabel;

    private DataLogger dataLogger; // Interface for the RMI server

    public ClientGUI() {
        frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        sendButton = new JButton("Send Data");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData();
            }
        });

        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        nameField = new JTextField(20);
        ageField = new JTextField(20);
        statusLabel = new JLabel("Disconnected");

        frame.setLayout(new FlowLayout());
        frame.add(sendButton);
        frame.add(connectButton);
        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Age:"));
        frame.add(ageField);
        frame.add(statusLabel);

        frame.setVisible(true);
    }

    private void sendData() {
        try {
            String name = nameField.getText();
            String ageStr = ageField.getText();
            if (name != null && !name.isEmpty() && ageStr != null && !ageStr.isEmpty()) {
                int age = Integer.parseInt(ageStr);
                if (dataLogger != null) {
                    SerializableObject dataObject = new SerializableObject(name, age); // Create a serializable DataObject with the name and age
                    dataLogger.logData(dataObject); // Call the logData() method on the remote dataLogger object with the serialized dataObject
                    statusLabel.setText("Data sent: " + name + ", " + age);
                } else {
                    statusLabel.setText("Not connected to server");
                }
            } else {
                statusLabel.setText("Enter name and age to send");
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid age. Age must be a number.");
        } catch (RemoteException ex) {
            statusLabel.setText("Error sending data: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Connect to RMI registry on localhost at default port 1099
            dataLogger = (DataLogger) registry.lookup("DataLogger"); // Lookup the RMI server by its registered name
            statusLabel.setText("Connected to server");
        } catch (RemoteException | NotBoundException ex) {
            statusLabel.setText("Error connecting to server: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }
}