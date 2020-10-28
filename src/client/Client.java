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

        joinButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                m.join();
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
