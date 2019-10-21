package edu.utep.cs.cs4381.pricewatcher;

import android.content.Context;
import android.text.Html;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class ProductList<view> extends ArrayAdapter<Product> {
    private TextView initialPriceView;
    private TextView priceView;
    private TextView percentView;

    //item name
    private TextView itemName;

    //url
    private TextView urlText;

    private Product product;
    private double initialPrice;

    private String item;
    private String url;

    /* List of items */
    private List<Product> items;
    /* Listener used to call methods inside the main activity */
    private PopupItemListener listener;

    public ProductList(Context context, List<Product> items){
        super(context, -1, items);
        this.items = items;
        listener = (PopupItemListener) getContext();
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        /* Check if the old view is provided for reuse
        If is not, inflate the new view using the layout provided */
        View itemView = convertView != null ? convertView
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout, parent, false);

        /* Get the current item of the list and the TextView from the layout */
        Product product = items.get(position);

        /* Check if the price change is positive or negative for the user */
        String color = "#7a7a7a";
        if(product.getPercentChange() < 0){
            color = "#85b17c";
        }
        else if(product.getPercentChange() > 0){
            color = "#e6756b";
        }
        item = "Focusrite Scarlett 2i2";
        initialPrice = 159.99;
        url = "https://www.amazon.com/Focusrite-Scarlett-Audio-Interface-Tools/dp/B07QR73T66?pf_rd_p=387d50eb-83f8-4221-a708-8a1575a4a151&pd_rd_wg=lTz6G&pf_rd_r=PHPQ9ZEM4GQWPQS33VTQ&ref_=pd_gw_cr_simh&pd_rd_w=b14vM&pd_rd_r=8d2b8c95-0a9c-40a8-b24a-576d3eabc05e";

        itemName = findViewById(R.id.itemsView);
        itemName.setText(item);
        urlText = findViewById(R.id.urlText);
        urlText.setText(url);

        product = new Product(item,initialPrice,url);

        initialPriceView = findViewById(R.id.initialPriceView);
        String initPrice = "$" + initialPrice;
        initialPriceView.setText(initPrice);

        resetPercentage = findViewById(R.id.resetPercentage);
        resetPercentage.setOnClickListener((View view) -> updateProduct());

        gotoUrl = findViewById(R.id.gotoUrl);
        gotoUrl.setOnClickListener((View view) -> searchProduct(url));

        itemView.setOnClickListener(new View.OnClickListener() {
            /* Check if the user clicked this specific item */
            @Override
            public void onClick(View view) {
                /* Create a popup menu passing the current view and the position in the list */
                createPopupMenu(view, position);
            }
        });
        return itemView;
    }

    /**
     * This item creates a popup menu when an item is clicked and performs and
     * action depending on what item was clicked inside the menu.
     * @param view The view that was clicked.
     * @param position The position of the clicked item in the list.
     */
    private void createPopupMenu(View view, final int position){
        /* Initialize the popup menu and provide a layout */
        PopupMenu menu = new PopupMenu(getContext(), view, Gravity.END);
        menu.inflate(R.menu.popup_menu);
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /* Handling what happens when one item inside of the menu is clicked */
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    /* The delete option was selected */
                    case R.id.delete:
                        listener.deleteItem(position);
                        return true;
                    /* The browse option was selected */
                    case R.id.browse:
                        listener.displayWebsite(position);
                        return true;
                    /* The edit option was selected */
                    case R.id.edit:
                        listener.editItem(position);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    /**
     * Interface used to link the MainActivity to the listener.
     */
    public interface PopupItemListener{
        void deleteItem(int index);
        void editItem(int index);
        void displayWebsite(int index);
    }
}
