
package edu.utep.cs.cs4381.pricewatcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewItemDialog.NewItemDialogListener,
        ProductList.PopupItemListener,
        EditItemDialog.EditItemDialogListener {

    /* List of items */
    private static List<Product> productsList = new ArrayList<Product>();
    /* List view that contains the views of each item */
    private ListView itemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Initialize activity and layout */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Initialize the toolbar and set it to act as the action bar for
        this activity' window. */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* Initialize a fixed item for demonstration purposes */
        itemsView = findViewById(R.id.productListView);
        if(productsList.size() <= 0){
            productsList.add(new Product("Focusrite Scarlett 2i2", "https://www.amazon.com/Focusrite-Scarlett-Audio-Interface-Tools/dp/B07QR73T66?pf_rd_p=387d50eb-83f8-4221-a708-8a1575a4a151&pd_rd_wg=lTz6G&pf_rd_r=PHPQ9ZEM4GQWPQS33VTQ&ref_=pd_gw_cr_simh&pd_rd_w=b14vM&pd_rd_r=8d2b8c95-0a9c-40a8-b24a-576d3eabc05e", 159.99f, "10/20/2019")); }
        displayList();
        /* Check if user shared URL from another app */
        String action = getIntent().getAction();
        String type = getIntent().getType();
        if(Intent.ACTION_SEND.equalsIgnoreCase(action) && ("text/plain".equals(type))) {
            /* get the shared URL and display an add new item dialog window */
            String sharedUrl = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            openNewItemDialog(sharedUrl);
        }
    }

    private void displayList() {
        ProductList listAdapter = new ProductList(this, productsList);
        itemsView.setAdapter(listAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add the items to the action bar.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /* The refresh option was selected */
            case R.id.refresh:
                /* Refresh the price of all items */
                for (int i = 0; i < productsList.size(); i++) {
                    productsList.get(i).updatePrice();
                }
                displayList();
                return true;
            /* The add option was selected */
            case R.id.add_item:
                /* Open the dialog window to create an item */
                openNewItemDialog(null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void openNewItemDialog(String sharedUrl){
        NewItemDialog dialog = new NewItemDialog();
        /* The link was shared by another app */
        if(sharedUrl != null) {
            Bundle bundle = new Bundle();
            bundle.putString("url", sharedUrl);
            /* Pass the link to the dialog window */
            dialog.setArguments(bundle);
        }
        /* Show the dialog window */
        dialog.show(getSupportFragmentManager(), "New item added");
    }

    @Override
    public void addItem(String name, String url, String price, String date) {
        productsList.add(new Product(name, url, Float.parseFloat(price), date));
        displayList();
    }


    @Override
    public void deleteItem(int index) {
        productsList.remove(index);
        displayList();
        /* Display a message to the user */
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editItem(int index) {
        /* Bundle to save the items current attributes */
        EditItemDialog dialog = new EditItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("currentName", productsList.get(index).getItem());
        bundle.putString("currentUrl", productsList.get(index).getUrl());
        /* Send attributes to the dialog window */
        dialog.setArguments(bundle);
        /* Display dialog window */
        dialog.show(getSupportFragmentManager(), "Edit item");
    }


    @Override
    public void displayWebsite(int index) {
        /* Get the URL from the items attributes */
        Uri url = Uri.parse(productsList.get(index).getUrl());
        /* Verify that the URL its valid to avoid errors */
        if(!URLUtil.isValidUrl(url.toString())){
            /* If the URL is not valid display an error message */
            Toast.makeText(this, "Invalid URL provided", Toast.LENGTH_SHORT).show();
            return;
        }
        /* Initialize a web browser with the URL of the item */
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, url);
        startActivity(browserIntent);
    }

    @Override
    public void updateItem(String name, String url, int index) {
        productsList.get(index).setItem(name);
        productsList.get(index).setUrl(url);
    }
}