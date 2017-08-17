package br.com.campuscode.contactapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.ContactModel;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddContact;
    EditText etContactName;
    EditText etContactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etContactName = (EditText) findViewById(R.id.et_contact_name);
        etContactPhone = (EditText) findViewById(R.id.et_contact_phone);
        btnAddContact= (Button) findViewById(R.id.btn_add_contact);

        btnAddContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent result = new Intent();
        Bundle contactBundle = new Bundle();
        Contact contact = new Contact();

        contact.setName(etContactName.getText().toString());
        contact.setPhone(etContactPhone.getText().toString());
        insertContact(contact);

        contactBundle.putSerializable("contact", contact);
        result.putExtra("contact", contactBundle);
        setResult(0, result);

        finish();
    }

    private void insertContact(Contact contact) {

        ContentValues content = new ContentValues();
        content.put(ContactModel.NAME, contact.getName());
        content.put(ContactModel.PHONE, contact.getPhone());

        Uri result = getContentResolver().insert(ContactModel.CONTENT_URI, content);
        if (result != null) {
            Toast.makeText(this, "Contato Adicionado com id: " + result.getLastPathSegment(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
