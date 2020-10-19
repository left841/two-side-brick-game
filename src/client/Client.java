package client;

import client.view.ViewClient;

public class Client {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewClient vc = new ViewClient();
                vc.setVisible(true);
                vc.setSize(800, 600);
            }
        });
    }
}
