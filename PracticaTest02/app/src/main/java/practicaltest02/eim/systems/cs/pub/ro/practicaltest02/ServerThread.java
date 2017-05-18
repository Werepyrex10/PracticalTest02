package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by malvo.
 */

public class ServerThread extends Thread{
    private int port;
    private ServerSocket serverSocket = null;


    public ServerThread(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public ServerSocket getServerSocket() { return serverSocket; }

    @Override
    public void run() {
        try {
            Log.d("Server Thread", "Started Server Thread");
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                CommunicationThread communicationThread = new CommunicationThread(socket);
                communicationThread.start();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

}
