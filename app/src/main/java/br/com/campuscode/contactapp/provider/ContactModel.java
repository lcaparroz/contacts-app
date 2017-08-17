package br.com.campuscode.contactapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by campuscode02 on 8/15/17.
 */

public class ContactModel implements BaseColumns {

    public static final String TABLE_NAME = "contacts";
    public static final String NAME = "name";
    public static final String PHONE = "phone";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Contacts.AUTHORITY + "/contact");
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.contacts.contact";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.contacts.contact";

    public static final String CREATE_CONTACTS_TABLE = "CREATE TABLE "
            + TABLE_NAME + "("
            + _ID + " integer primary key autoincrement, "
            + NAME + " text not null, "
            + PHONE + " text not null);";
}
