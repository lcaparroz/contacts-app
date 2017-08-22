package br.com.campuscode.contactapp.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.ContactModel;
import br.com.campuscode.contactapp.provider.WebApi;

/**
 * Created by campuscode02 on 8/17/17.
 */

public class GetContactsTask extends AsyncTask<Void, List<Contact>, List<Contact>> {

    private OnContactsSynced activity;
    private Context context;

    public GetContactsTask(OnContactsSynced activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    protected List<Contact> doInBackground(Void... voids) {

        WebApi web = new WebApi();
        List<Contact> result = new ArrayList<>();

        try {
            String json = web.getContacts();
            Gson contacts = new Gson();
            result = Arrays.asList(contacts.fromJson(json, Contact[].class));

            Uri uri = ContactModel.CONTENT_URI;
            context.getContentResolver().delete(uri, null, null);

            for (Contact contact : result) {
                ContentValues content = new ContentValues();
                content.put(ContactModel.NAME, contact.getName());
                content.put(ContactModel.PHONE, contact.getPhone());

                context.getContentResolver().insert(ContactModel.CONTENT_URI, content);
            }
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
