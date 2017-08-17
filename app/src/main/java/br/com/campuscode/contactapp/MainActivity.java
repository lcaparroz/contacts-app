package br.com.campuscode.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.campuscode.contactapp.adapters.ContactsAdapter;
import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.ContactModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
}
