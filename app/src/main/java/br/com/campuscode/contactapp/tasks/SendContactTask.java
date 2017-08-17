package br.com.campuscode.contactapp.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import br.com.campuscode.contactapp.provider.WebApi;

/**
 * Created by campuscode02 on 8/16/17.
 */

public class SendContactTask extends AsyncTask<Void, Integer, Integer> {

    private String name;
    private String phone;

    public SendContactTask(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        WebApi web = new WebApi();
        int result = -1;

        try {
            web.postContact(name, phone);
            result = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

    }
}
