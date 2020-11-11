package client;

import client.model.BModelClient;
import client.model.IModelClient;
import client.view.SidePanel;
import client.view.ViewClient;

import javax.swing.*;
import java.awt.*;

public class Client extends JFrame {
    IModelClient m;
    ViewClient view;
    private SidePanel sidePanel;

    private JPanel contentPane;
    private JPanel mainPane;
    private JPanel sidePane;

    Client() {
        m = BModelClient.model();

        setContentPane(contentPane);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
    }

    void initComponents() {
        view = new ViewClient();
        mainPane.setLayout(new GridLayout());
        mainPane.add(view);

        sidePanel = new SidePanel();
        sidePane.setLayout(new GridLayout());
        sidePane.add(sidePanel);
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
}
