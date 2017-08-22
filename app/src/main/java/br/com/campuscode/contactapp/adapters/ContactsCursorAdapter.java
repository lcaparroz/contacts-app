package br.com.campuscode.contactapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import br.com.campuscode.contactapp.R;
import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.provider.ContactModel;

/**
 * Created by campuscode02 on 8/21/17.
 */

public class ContactsCursorAdapter extends CursorAdapter {
    OnDeleted activity;

    public ContactsCursorAdapter(Context context, Cursor c, boolean autoRequery, OnDeleted activity) {
        super(context, c, autoRequery);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.contacts_item_layout, null);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        View viewHolder = view;

        TextView tvContactName = viewHolder.findViewById(R.id.tv_contact_item_name);
        TextView tvContactPhone = viewHolder.findViewById(R.id.tv_contact_item_phone);
        ImageButton btnDeleteContact = viewHolder.findViewById(R.id.btn_delete_contact);

        tvContactName.setText(cursor.getString(cursor.getColumnIndex(ContactModel.NAME)));
        tvContactPhone.setText(cursor.getString(cursor.getColumnIndex(ContactModel.PHONE)));
        final String id = cursor.getString(cursor.getColumnIndex(ContactModel._ID));

        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.withAppendedPath(ContactModel.CONTENT_URI, id);
                context.getContentResolver().delete(uri, null, null);

                activity.RefreshListOnDelete();

                Toast.makeText(view.getContext(), "Contato excluido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnDeleted {
        void RefreshListOnDelete();
    }
}
