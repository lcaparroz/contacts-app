package br.com.campuscode.contactapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import br.com.campuscode.contactapp.provider.WebApi;

/**
 * Created by campuscode02 on 8/16/17.
 */

public class SendContactTask extends AsyncTask<Void, Integer, Integer> {

    private String name;
    private String phone;
    private Context context;

    public SendContactTask(String name, String phone, Context context) {
        this.name = name;
        this.phone = phone;
        this.context = context;
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
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        switch (result) {
            case 0:
                Toast.makeText(context, "Contato adicionado", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "Erro ao adicionar contato", Toast.LENGTH_SHORT).show();

        }
    }
}
