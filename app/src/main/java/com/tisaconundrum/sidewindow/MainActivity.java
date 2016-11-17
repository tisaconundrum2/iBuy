package com.tisaconundrum.sidewindow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseHandler dba;
    private ArrayList<Item> dbItems = new ArrayList<>();
    private CustomListviewAdapter ItemAdapter;
    private ListView listView;

    private Item myItem;
    private TextView totalCost, totalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNewItem.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ==========================

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
        ItemAdapter = new CustomListviewAdapter(MainActivity.this, R.layout.list_row, dbItems);
        listView.setAdapter(ItemAdapter);
        ItemAdapter.notifyDataSetChanged();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
