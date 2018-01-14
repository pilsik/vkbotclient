package by.SIvko.vk_bot_client;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String response;
        String request = "{\"id\":0, \"method\":\"getstat\"}";
        while (true) {
            try (Socket socket = new Socket("192.168.0.21", 42000);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
                while (true) {
                    response = "";
                    out.writeBytes(request + "\n");
                    out.flush();
                    response = in.readLine();
                    if (response != null && !response.isEmpty()) {
                        System.out.println("Sending stat to server " + new Date());
                        HttpClient httpclient = HttpClientBuilder.create().build();
                        HttpPost httppost = new HttpPost("https://vk-bot-miner.herokuapp.com/farm");
                        StringEntity entity = new StringEntity(response, ContentType.APPLICATION_JSON);
                        httppost.setEntity(entity);
                        httpclient.execute(httppost);
                    }
                    Thread.sleep(10000L);
                }
            } catch (InterruptedException | IOException e) {
               // e.printStackTrace();
            }
        }
    }
}
