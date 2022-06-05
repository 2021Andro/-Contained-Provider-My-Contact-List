package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvConditionMassage;

    private ListView lvContactList;
    private ArrayList<String> contactList;
    private ArrayAdapter myArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvConditionMassage = findViewById(R.id.tvConditionMassage);

        lvContactList = findViewById(R.id.lvContactList);

        contactList = new ArrayList<>();

        myArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList);

        lvContactList.setAdapter(myArrayAdapter);

        getContactList();


    }

    private void getContactList() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
        } else {


            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

            String[] projections = null;
            String selection = null;
            String[] selectionAreas = null;
            String oder = null;

            ContentResolver contentResolver = getContentResolver();

            Cursor cursorQuery = contentResolver.query(uri, projections, selection, selectionAreas, oder);

            if (cursorQuery.getCount() > 0) {

                tvConditionMassage.setVisibility(View.GONE);
                lvContactList.setVisibility(View.VISIBLE);

                while (cursorQuery.moveToNext()) {

                    @SuppressLint("Range") String name = cursorQuery.getString(cursorQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    @SuppressLint("Range") String phoneNumber = cursorQuery.getString(cursorQuery.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    String contactInfo = name + "\n" + phoneNumber;

                    contactList.add(contactInfo);

                }


            } else {

                tvConditionMassage.setVisibility(View.VISIBLE);
                lvContactList.setVisibility(View.GONE);

            }


        }


    }
}