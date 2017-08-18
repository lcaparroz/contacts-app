package br.com.campuscode.contactapp.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.WebApi;

/**
 * Created by campuscode02 on 8/17/17.
 */

public class GetContactsTask extends AsyncTask<Void, List<Contact>, List<Contact>> {

    private OnContactsSynced activity;

    public GetContactsTask(OnContactsSynced activity) {
        this.activity = activity;
    }

    @Override
    protected List<Contact> doInBackground(Void... voids) {

        WebApi web = new WebApi();
        List<Contact> result = new ArrayList<>();

        try {
            String json = web.getContacts();
            Gson contacts = new Gson();
            result = Arrays.asList(contacts.fromJson(json, Contact[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Contact> contactsList) {
        super.onPostExecute(contactsList);
        activity.FinishSync(contactsList);
    }

    public interface OnContactsSynced {
        void FinishSync(List<Contact> contactList);
    }
}
