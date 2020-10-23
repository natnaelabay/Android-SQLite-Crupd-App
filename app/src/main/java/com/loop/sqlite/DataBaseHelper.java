package com.loop.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loop.sqlite.models.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement =
                "CREATE TABLE " + CUSTOMER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CUSTOMER_NAME +
                        " TEXT," + COLUMN_CUSTOMER_AGE + " INT," + COLUMN_ACTIVE_CUSTOMER + " bOOL )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME,customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE,customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER,customerModel.isActive());
        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if(insert == -1) return false;
        return true;
    }
    public List<CustomerModel> getEveryone() {

        List<CustomerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()) {
            do{
                int CustomerID = cursor.getInt(0);
                String CustomerName = cursor.getString(1);
                int CustomerAge = cursor.getInt(2);
                boolean CustomerActive = (cursor.getInt(3)) == 1 ? true : false;
                CustomerModel customerModel = new CustomerModel(CustomerID,CustomerName,CustomerAge,CustomerActive);
                returnList.add(customerModel);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        return returnList;
    }

    public boolean deleteOne(CustomerModel customerModel) {
        SQLiteDatabase db = getWritableDatabase();
//        String queryString = "DELETE FROM " + CUSTOMER_TABLE + "WHERE " + COLUMN_ID  + " = " + customerModel.getId() + ";";
        int cursor = db.delete(CUSTOMER_TABLE,COLUMN_ID + "=" + customerModel.getId(), null);
        if (cursor ==1 ) return true;
        else return false;
//        System.out.println(cursor);
//        return true;
    }

}
