package client.view;

import client.model.BModelClient;
import client.model.IModelClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidePanel extends JPanel implements IObserver {
    IModelClient m;

    private final JButton joinButton = new JButton();
    private final JButton settingsButton = new JButton();
    private final JTextField ipAddressField = new JTextField();
    private final JTextField portField = new JTextField();
    private final JLabel ipAddressLabel = new JLabel();
    private final JLabel portLabel = new JLabel();


    boolean inEditSettingsState = false;

    public SidePanel() {
        m = BModelClient.model();
        m.addObserver(this);

        initComponents();
    }

    void initComponents() {
        joinButton.setMaximumSize(new Dimension(200, 30));
        settingsButton.setMaximumSize(new Dimension(200, 30));

        setLayout(new GridLayout(20, 1));
        add(joinButton);
        add(settingsButton);
        add(ipAddressLabel);
        add(ipAddressField);
        add(portLabel);
        add(portField);

        joinButton.setText("Join");
        settingsButton.setText("Settings");
        ipAddressLabel.setText("IP address:");
        portLabel.setText("Port:");


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

    @Override
    public void refresh() {
        joinButton.setText(m.getRoomNumber() == -1 ? "Join" : "Connected");
    }
}
