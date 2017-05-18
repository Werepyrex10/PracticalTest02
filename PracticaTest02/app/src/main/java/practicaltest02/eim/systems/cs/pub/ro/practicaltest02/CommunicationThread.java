package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by malvo.
 */

public class CommunicationThread extends Thread{
    private Socket socket = null;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Log.d("Communication Thread", "Started Communication thread");
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            String query = bufferedReader.readLine();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://autocomplete.wunderground.com/aq");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("query", query));
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String pageSourceCode = httpClient.execute(httpPost, responseHandler);

            Document document = Jsoup.parse(pageSourceCode);
            Element element = document.child(0);
            String text = element.text().substring(element.text().indexOf("name"), 40);

            printWriter.println(text);
            printWriter.flush();

        } catch (Exception e) { e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) { e.printStackTrace(); }
            }
        }


    }

}
