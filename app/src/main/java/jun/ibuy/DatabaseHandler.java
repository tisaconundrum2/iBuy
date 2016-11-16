package jun.ibuy;

/**
 * Created by raojun on 11/7/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;



public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Item> itemList = new ArrayList<>();


    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.Item_NAME +
                " TEXT, " + Constants.Item_Cost + " INT, " + Constants.DATE_NAME + " LONG);";

        db.execSQL(CREATE_TABLE);

    }
    //Create our Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //create a new table
        onCreate(db);

    }


    //Get total items saved
    public int getTotalItems() {

        int totalItems = 0;

        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query, null);

        totalItems = cursor.getCount();

        cursor.close();

        return totalItems;


    }

    //get total calories consumed
    public int totalCost() {
        int cost = 0;

        SQLiteDatabase dba = this.getReadableDatabase();

        String query = "SELECT SUM( " + Constants.Item_Cost + " ) " +
                "FROM " + Constants.TABLE_NAME;

        Cursor cursor = dba.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cost = cursor.getInt(0);
        }

        cursor.close();
        dba.close();


        return cost;
    }


    //delete food item
    public void deleteFood(int id) {

        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        dba.close();


    }

    //add content to db - add Item
    public void addItem(Item item) {

        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Item_NAME, item.getItemName());
        values.put(Constants.Item_Cost, item.getCost());
        values.put(Constants.DATE_NAME, System.currentTimeMillis());

        dba.insert(Constants.TABLE_NAME, null, values);

        Log.v("Added item", "Yesss!!");

        dba.close();
    }


    //Get all foods
    public ArrayList<Item> getFoods(){

        itemList.clear();

        SQLiteDatabase dba = this.getReadableDatabase();

        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.Item_NAME, Constants.Item_Cost,
                        Constants.DATE_NAME}, null, null, null, null, Constants.DATE_NAME + " DESC ");

        //loop through...
        if (cursor.moveToFirst()) {
            do {

                Item item = new Item();
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.Item_NAME)));
                item.setCost(cursor.getInt(cursor.getColumnIndex(Constants.Item_Cost)));
                item.setItemID(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));


                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                item.setDate(date);


                itemList.add(item);

            }while (cursor.moveToNext());



        }

        cursor.close();
        dba.close();





        return itemList;

    }












}

