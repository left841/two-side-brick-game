package client;

import client.model.BModelClient;
import client.model.IModelClient;
import client.view.IObserver;
import client.view.ViewClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Client extends JFrame implements IObserver {
    IModelClient m;
    ViewClient view;
    private JButton joinButton;
    private JPanel contentPane;
    private JPanel mainPane;
    private JButton settingsButton;
    private JTextField ipAddressField;
    private JTextField portField;
    private JLabel ipAddressLabel;
    private JLabel portLabel;

    boolean inEditSettingsState = false;

    Client() {
        m = BModelClient.model();
        m.addObserver(this);

        setContentPane(contentPane);
        setVisible(true);
        joinButton.setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
    }

    void initComponents() {
        view = new ViewClient();
        mainPane.setLayout(new GridLayout());
        mainPane.add(view);

        ipAddressLabel.setVisible(false);
        portLabel.setVisible(false);
        ipAddressField.setVisible(false);
        portField.setVisible(false);

        joinButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                m.join();
            }
        });

        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!inEditSettingsState) {
                    ipAddressLabel.setVisible(true);
                    ipAddressField.setVisible(true);
                    portLabel.setVisible(true);
                    portField.setVisible(true);
                    settingsButton.setText("Apply");
                    ipAddressField.setText(m.getIp().getHostAddress());
                    portField.setText(Integer.toString(m.getPort()));
                } else {
                    ipAddressLabel.setVisible(false);
                    portLabel.setVisible(false);
                    ipAddressField.setVisible(false);
                    portField.setVisible(false);
                    settingsButton.setText("Settings");
                    m.setIp(ipAddressField.getText());
                    m.setPort(portField.getText());
                    m.connect();
                }
                inEditSettingsState = !inEditSettingsState;
            }
        });
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Client vc = new Client();
                vc.setVisible(true);
                vc.setSize(800, 600);
            }
        });
    }

    @Override
    public void refresh() {
        joinButton.setText(m.getRoomNumber() == -1 ? "Join" : "Connected");
    }
}
