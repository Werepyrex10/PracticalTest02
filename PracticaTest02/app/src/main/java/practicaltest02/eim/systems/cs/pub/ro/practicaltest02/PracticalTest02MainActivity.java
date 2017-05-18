package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {
    Button queryButton = null;
    Button stopButton = null;
    Button startButton = null;
    EditText portText = null;
    EditText queryText = null;
    ServerThread serverThread = null;
    ClientThread clientThread = null;
    EditText resultText = null;

    public void onStartClick(View view) {
        String serverPort = portText.getText().toString();
        serverThread = new ServerThread(Integer.parseInt(serverPort));

        if (serverThread.getServerSocket() == null) {
            Toast.makeText(PracticalTest02MainActivity.this, "Unable to create server", Toast.LENGTH_SHORT).show();
        }

        serverThread.start();
    }

    public void onQueryClick(View view) {
        if (serverThread == null) {
            Toast.makeText(PracticalTest02MainActivity.this, "Server not started", Toast.LENGTH_SHORT).show();
            return;
        }

        clientThread = new ClientThread(queryText.getText().toString(),
                Integer.parseInt(portText.getText().toString()), resultText);

        clientThread.start();
    }

    public void onStopClick(View view) {
        if (serverThread != null) {
            serverThread.stopThread();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        queryButton = (Button) findViewById(R.id.queryButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        startButton = (Button) findViewById(R.id.startButton);
        portText = (EditText) findViewById(R.id.portText);
        queryText = (EditText) findViewById(R.id.queryText);
        resultText = (EditText) findViewById(R.id.resultText);
    }

    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopThread();
        }

        super.onDestroy();
    }

    public void showToast(String text) {
        Toast.makeText(PracticalTest02MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
