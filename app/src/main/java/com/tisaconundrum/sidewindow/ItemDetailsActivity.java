package com.tisaconundrum.sidewindow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ItemDetailsActivity extends AppCompatActivity {

    private TextView itemName, costs, dateTaken;
    private int item_id;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemName = (TextView) findViewById(R.id.detailsItemName);
        costs = (TextView) findViewById(R.id.detsCostValue);
        dateTaken = (TextView) findViewById(R.id.detailsDateText);
        shareButton = (Button) findViewById(R.id.detailsShareButton);


        Item food = (Item) getIntent().getSerializableExtra("userObj");

        itemName.setText(food.getItemName());
        costs.setText(String.valueOf(food.getCost()));
        dateTaken.setText(food.getDate());

        item_id = food.getItemID();


        costs.setTextSize(34.9f);
        costs.setTextColor(Color.RED);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCals();
            }
        });


    }

    public void shareCals() {

        StringBuilder dataString = new StringBuilder();

        String name = itemName.getText().toString();
        String cos = costs.getText().toString();
        String date = dateTaken.getText().toString();

        dataString.append(" Item: " + name + "\n");
        dataString.append(" costs: " + cos + "\n");
        dataString.append(" Created on: " + date);

        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );
        startActivity(Intent.createChooser(intent2, "Share via"));

    }

//    @Override
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_item_details, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.deleteItem) {
//
//            AlertDialog.Builder alert = new AlertDialog.Builder(ItemDetailsActivity.this);
//            alert.setTitle("Delete?");
//            alert.setMessage("Are you sure you want to delete this item?");
//            alert.setNegativeButton("No", null);
//            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
//                    dba.deleteFood(item_id);
//
//                    Toast.makeText(getApplicationContext(), "Food Item Deleted!", Toast.LENGTH_LONG)
//                            .show();
//
//                    startActivity(new Intent(ItemDetailsActivity.this, DisplayItemActivity.class));
//
//
//                    //remove this activity from activity stack
//                    ItemDetailsActivity.this.finish();
//
//
//                }
//            });
//            alert.show();
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
}
