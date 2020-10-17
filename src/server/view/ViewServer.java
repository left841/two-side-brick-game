package server.view;

import java.net.Socket;

class ViewServer implements IViewServer {
    Socket cs;

    public ViewServer(Socket s) {
        cs = s;
    }
}
