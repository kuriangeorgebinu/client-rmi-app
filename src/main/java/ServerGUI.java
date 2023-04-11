import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerGUI {
    private JFrame frame;
    private JButton startButton;
    private JLabel statusLabel;

    public ServerGUI() {
        frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        startButton = new JButton("Start Server");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        statusLabel = new JLabel("Server not running");

        frame.setLayout(new FlowLayout());
        frame.add(startButton);
        frame.add(statusLabel);

        frame.setVisible(true);
    }

    private void startServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(1099); // RMI registry on default port 1099
            DataLoggerImpl dataLogger = new DataLoggerImpl(); // Your implementation of the RMI server
            registry.rebind("DataLogger", dataLogger);
            statusLabel.setText("Server running");
            startButton.setEnabled(false);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
    }
}