package com.example.studentdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Table extends AppCompatActivity {
    String[] rec={"ID","Name","Classname","Marks"};
    String[][] data;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        myDb = new DatabaseHelper(this);
        //Intent i = getIntent();
        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        populateData();
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this,rec));
        tb.setDataAdapter(new SimpleTableDataAdapter(this,data));
    }
    private void populateData()
    {
        Records records = new Records();
        ArrayList<Records> reclist = new ArrayList<>();
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        //Add Data
        while (res.moveToNext()) {
            records.setID(res.getString(0));
            records.setName(res.getString(1));
            records.setCName(res.getString(2));
            records.setMarks(res.getString(3));
            reclist.add(records);
        }
            data = new String[reclist.size()][4];
            for (int i = 0; i < reclist.size(); i++) {
                Records r = reclist.get(i);
                data[i][0] = r.getID();
                data[i][1] = r.getName();
                data[i][2] = r.getCName();
                data[i][3] = r.getMarks();
            }
        }
        public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}