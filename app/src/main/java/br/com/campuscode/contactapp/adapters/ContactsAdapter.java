package br.com.campuscode.contactapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.campuscode.contactapp.R;
import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.ContactModel;

/**
 * Created by campuscode02 on 8/14/17.
 */

public class ContactsAdapter extends BaseAdapter {

    private Context context;
    private List<Contact> contactsList;

    public ContactsAdapter(Context context, List<Contact> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View viewHolder = view;

        if (viewHolder == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewHolder = inflater.inflate(R.layout.contacts_item_layout, null);
        }

        TextView tvContactName = viewHolder.findViewById(R.id.tv_contact_item_name);
        TextView tvContactPhone = viewHolder.findViewById(R.id.tv_contact_item_phone);
        ImageButton btnDeleteContact = viewHolder.findViewById(R.id.btn_delete_contact);
        final Contact contact = (Contact) getItem(i);

        tvContactName.setText(contact.getName());
        tvContactPhone.setText(contact.getPhone());
        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.withAppendedPath(ContactModel.CONTENT_URI, String.valueOf(contact.getId()));
                context.getContentResolver().delete(uri, null, null);
                contactsList.remove(i);
                notifyDataSetChanged();
                Toast.makeText(view.getContext(), "Contato com id " + contact.getId().toString() + " excluido", Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }
}
