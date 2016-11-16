package jun.ibuy;

/**
 * Created by raojun on 11/7/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListviewAdapter extends ArrayAdapter<Item> {

    private int layoutResource;
    private Activity activity;
    private ArrayList<Item> itemList = new ArrayList<>();

    public CustomListviewAdapter(Activity act, int resource, ArrayList<Item> data) {
        super(act, resource, data);
        layoutResource = resource;
        activity = act;
        itemList = data;
        notifyDataSetChanged();//Everthing is refersed correctly


    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Item getItem(int position) {

        return itemList.get(position);
    }

    @Override
    public int getPosition(Item item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if ( row == null || (row.getTag() == null)) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.itemName = (TextView) row.findViewById(R.id.name);
            holder.itemDate = (TextView) row.findViewById(R.id.dateText);
            holder.itemCost = (TextView) row.findViewById(R.id.cost);

            row.setTag(holder);

        }else {

            holder = (ViewHolder) row.getTag();
        }


        holder.item = getItem(position);

        holder.itemName.setText(holder.item.getItemName());
        holder.itemDate.setText(holder.item.getDate());
        holder.itemCost.setText(String.valueOf(holder.item.getCost()));

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, ItemDetailsActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userObj", finalHolder.item);
                i.putExtras(mBundle);


                activity.startActivity(i);


            }
        });

        return row;

    }

    public class ViewHolder {
        Item item;
        TextView itemName;
        TextView itemCost;
        TextView itemDate;

    }
}