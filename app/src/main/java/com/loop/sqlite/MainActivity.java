package com.loop.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.loop.sqlite.models.CustomerModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_viewAll,btn_Add;
    private Switch isActive;
    private EditText et_name,et_age;
    private ListView lv_customerList;
    SQLiteDatabase sqLiteDatabase;
    ArrayAdapter customerAdapter;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        btn_Add = findViewById(R.id.btn_add);
        isActive = findViewById(R.id.sw_active);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        lv_customerList = findViewById(R.id.lv_customerList);
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        ShowCustomersOnListView(dataBaseHelper);

        // button listeners

         btn_Add.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {

                 CustomerModel customerModel;

                 try {
                     customerModel = new CustomerModel(1,et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()),isActive.isChecked());
                     Toast.makeText(MainActivity.this,customerModel.toString(),Toast.LENGTH_LONG).show();

                 }catch (Exception e) {

                     Toast.makeText(MainActivity.this,"you dumb ass you did something stupid",Toast.LENGTH_LONG).show();
                     customerModel = new CustomerModel(-1,"error",0,false);

                 }

                 DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                 boolean success = dataBaseHelper.addOne(customerModel);
                 ShowCustomersOnListView(dataBaseHelper);
                 Toast.makeText(MainActivity.this, "success" + success, Toast.LENGTH_SHORT).show();
             }
         });

         btn_viewAll.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {

                 DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                 ShowCustomersOnListView(dataBaseHelper);
             }
         });

         lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 CustomerModel customerModel = (CustomerModel) adapterView.getItemAtPosition(i);
                 dataBaseHelper.deleteOne(customerModel);
                 ShowCustomersOnListView(dataBaseHelper);
                 Toast.makeText(MainActivity.this, "deleted successfully", Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void ShowCustomersOnListView(DataBaseHelper dataBaseHelper2) {
        customerAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        lv_customerList.setAdapter(customerAdapter);
    }
}
