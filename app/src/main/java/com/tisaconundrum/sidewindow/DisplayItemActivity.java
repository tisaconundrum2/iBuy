package com.tisaconundrum.sidewindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class DisplayItemActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Item> dbItems = new ArrayList<>();
    private CustomListviewAdapter ItemAdapter;
    private ListView listView;

    private Item myItem;
    private TextView totalCost, totalItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_items);

        listView = (ListView) findViewById(R.id.list);
        totalCost = (TextView) findViewById(R.id.totalAmountTextView);
        totalItems = (TextView) findViewById(R.id.totalItemsTextView);
        listView.setDivider(null);
        refreshData();
    }

    private void refreshData() {
        dbItems.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Item> foodsFromDB = dba.getFoods();

        int CostValue = dba.totalCost();
        int total_Item = dba.getTotalItems();

        String formattedValue = Utils.formatNumber(CostValue);
        String formattedItems = Utils.formatNumber(total_Item);

        totalCost.setText("Total Cost: " + formattedValue);
        totalItems.setText("Total Items: " + formattedItems);

        for (int i = 0; i < foodsFromDB.size(); i++) {

            String name = foodsFromDB.get(i).getItemName();
            String dateText = foodsFromDB.get(i).getDate();
            int cals = foodsFromDB.get(i).getCost();
            int itemId = foodsFromDB.get(i).getItemID();

            Log.v("FOOD IDS: ", String.valueOf(itemId));


            myItem = new Item();
            myItem.setItemName(name);
            myItem.setDate(dateText);
            myItem.setCost(cals);

            myItem.setItemID(itemId);

            dbItems.add(myItem);


        }
        dba.close();

        //setup adapter
        ItemAdapter = new CustomListviewAdapter(DisplayItemActivity.this, R.layout.list_row, dbItems);
        listView.setAdapter(ItemAdapter);
        ItemAdapter.notifyDataSetChanged();


    }


}

