package jun.ibuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        setContentView(R.layout.activity_display_item);

        listView = (ListView) findViewById(R.id.list);
        totalCost = (TextView) findViewById(R.id.totalAmountTextView);
        totalItems = (TextView) findViewById(R.id.totalItemsTextView);

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

        for (int i = 0; i < foodsFromDB.size(); i++){

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

