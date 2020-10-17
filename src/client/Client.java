package client;

import client.view.ViewClient;

public class Client {
    public static void main(String[] args) {
        ViewClient dialog = new ViewClient();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
