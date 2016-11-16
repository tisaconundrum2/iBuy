package jun.ibuy;

import java.io.Serializable;

/**
 * Created by raojun on 11/7/16.
 */

public class Item implements Serializable{
    private static final long seriaVersionUID = 10L;
    private String ItemName;
    private int Itemcost;
    private int itemID; // save in our database
    private String Date;

    public Item(String itemName, int cost, int id, String date){
        ItemName = itemName;
        Itemcost = cost;
        itemID = id;
        Date = date;
    }

    //default construct is nothing
    public Item(){

    }



    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getCost() {
        return Itemcost;
    }

    public void setCost(int Itemcost) {
        this.Itemcost = Itemcost;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public static long getSeriaVersionUID() {
        return seriaVersionUID;
    }
}
