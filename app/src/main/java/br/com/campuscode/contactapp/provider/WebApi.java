package br.com.campuscode.contactapp.provider;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by campuscode02 on 8/16/17.
 */

public class WebApi {

    private static final String CONTACTS_URL = "https://contatostreinamento.herokuapp.com/contacts";

    public String postContact(String name, String phone) throws IOException {
        OutputStreamWriter writer = null;
        String contentAsString = "";
        try {
            URL url = new URL(CONTACTS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* miliseconds */);
            conn.setConnectTimeout(15000 /* miliseconds */);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject data = new JSONObject();
            data.put("name", name);
            data.put("phone", phone);
            JSONObject contact = new JSONObject();
            contact.put("contact", data);

            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(contact.toString());
            writer.flush();

            StringBuilder builder = new StringBuilder();
            int httpResult = conn.getResponseCode();
            if (httpResult == HttpURLConnection.HTTP_CREATED || httpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                        String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                reader.close();
                contentAsString = builder.toString();
            }
            else {
                contentAsString = conn.getResponseMessage();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
        return contentAsString;
    }

}
