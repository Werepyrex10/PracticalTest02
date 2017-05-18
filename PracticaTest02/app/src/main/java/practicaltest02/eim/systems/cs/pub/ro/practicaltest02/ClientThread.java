package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by malvo.
 */

public class ClientThread extends Thread{
    private String query;
    private int port;
    private Socket socket;
    EditText text;

    public ClientThread(String query, int port, EditText text) {
        this.query = query;
        this.port = port;
        this.text = text;
    }

    @Override
    public void run() {
        try {
            Log.d("Client Thread", "Started Client Thread");
            socket = new Socket("localhost", port);
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            printWriter.println(query);
            printWriter.flush();
            String queryResult;
            while ((queryResult = bufferedReader.readLine()) != null) {
                final String result = queryResult;
                text.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(result);
                    }
                });
                break;
            }

        } catch(Exception e) { e.printStackTrace(); }
    }
}
