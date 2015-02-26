package com.jam.ksm.cupworthy;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.BaseAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Button;
/**
 * Created by mj5545 on 2/26/15.
 */
public class CustomListViewAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> contacts = new ArrayList<String>();
    private Context context;
    private Hashtable<String, String> blacklist = new Hashtable<String, String>();

    public CustomListViewAdapter(ArrayList<String> contacts, Context context, Hashtable<String, String> blacklist){
        this.contacts = contacts;
        this.context = context;
        this.blacklist = blacklist;
    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View contact_view = view;
        if (contact_view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contact_view = inflater.inflate(R.layout.contact_list, null);
        }

        TextView contact_text = (TextView) contact_view.findViewById(R.id.blacklist_textview);
        contact_text.setText(contacts.get(i));

        Button deleteBtn = (Button) contact_view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View test){
                //do the deleting here!
                blacklist.remove(contacts.get(i));
                saveHash(blacklist);
                contacts.remove(i);

                notifyDataSetChanged();
            }
        });

        return contact_view;
    }

    public void saveHash(Serializable object){
        try{
            File file = new File(context.getFilesDir(), "contacts_hash.txt");
            file.delete();
            //File outputFile = new File(context.getFilesDir(), "test.txt");
            FileOutputStream out = new FileOutputStream(context.getFilesDir().toString() + "/contacts_hash.txt");
            ObjectOutputStream hash = new ObjectOutputStream(out);

            hash.writeObject(object);
            hash.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
