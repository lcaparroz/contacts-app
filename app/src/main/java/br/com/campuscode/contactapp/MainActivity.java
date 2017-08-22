package br.com.campuscode.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.campuscode.contactapp.adapters.ContactsAdapter;
import br.com.campuscode.contactapp.adapters.ContactsCursorAdapter;
import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.ContactModel;
import br.com.campuscode.contactapp.tasks.GetContactsTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GetContactsTask.OnContactsSynced, SwipeRefreshLayout.OnRefreshListener,
        ContactsCursorAdapter.OnDeleted {

    ListView lvContactList;
    List<Contact> contactList;
    FloatingActionButton addContact;
//    ContactsAdapter adapter;
    CursorAdapter adapter;
    SwipeRefreshLayout swp_contact_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContactList = (ListView) findViewById(R.id.lv_contact_list);
        addContact = (FloatingActionButton) findViewById(R.id.fab_add_contact);
        swp_contact_list = (SwipeRefreshLayout) findViewById(R.id.swp_contact_list);

        contactList = new ArrayList<>();

        swp_contact_list.setOnRefreshListener(this);
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
        adapter = new ContactsCursorAdapter(this, contactCursor, true, this);

        lvContactList.setAdapter(adapter);
    }


    @Override
    public void FinishSync(List<Contact> contactList) {
        Toast.makeText(this, String.valueOf(contactList.size()) + " contatos sincronizados com a web", Toast.LENGTH_SHORT).show();
        swp_contact_list.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        GetContactsTask getContacts = new GetContactsTask(this, this);
        getContacts.execute();

        refreshList();
    }

    @Override
    public void RefreshListOnDelete() {
        refreshList();
    }
}
