package br.com.campuscode.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.campuscode.contactapp.adapters.ContactsAdapter;
import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.ContactModel;
import br.com.campuscode.contactapp.tasks.GetContactsTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GetContactsTask.OnContactsSynced {

    ListView lvContactList;
    List<Contact> contactList;
    FloatingActionButton addContact;
    ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContactList = (ListView) findViewById(R.id.lv_contact_list);
        addContact = (FloatingActionButton) findViewById(R.id.fab_add_contact);

        contactList = new ArrayList<>();

        adapter = new ContactsAdapter(this, contactList);

        lvContactList.setAdapter(adapter);

        addContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent addContactIntent = new Intent(this, AddContactActivity.class);
        startActivity(addContactIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetContactsTask getContacts = new GetContactsTask(this);
        getContacts.execute();

        refreshList();
    }

    private void refreshList() {

        Cursor contactCursor = getContentResolver().query(ContactModel.CONTENT_URI,
                null, null, null, null);

        if (contactCursor != null) {
            contactList.clear();
            while (contactCursor.moveToNext()) {
                Contact contact = new Contact();
                contact.setName(contactCursor.getString(contactCursor.getColumnIndex(ContactModel.NAME)));
                contact.setPhone(contactCursor.getString(contactCursor.getColumnIndex(ContactModel.PHONE)));
                contact.setId(contactCursor.getLong(contactCursor.getColumnIndex(ContactModel._ID)));
                contactList.add(contact);
            }
            contactCursor.close();
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public void FinishSync(List<Contact> contactList) {
        Toast.makeText(this, String.valueOf(contactList.size()) + " contatos sincronizados com a web", Toast.LENGTH_SHORT).show();
    }
}
