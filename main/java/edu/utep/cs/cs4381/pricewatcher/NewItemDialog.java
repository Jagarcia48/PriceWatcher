package edu.utep.cs.cs4381.pricewatcher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewItemDialog extends AppCompatDialogFragment {

    /* Text fields displayed in the dialog window */
    private EditText productsName;
    private EditText productsUrl;
    private EditText productsPrice;
    /* Listener used to call methods inside the main activity */
    public NewItemDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /* Set dialog's layout from resources */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_layout, null);
        /* Set properties of the dialog window */
        builder.setView(view).setTitle("Add new item")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    /* Action that executes when user presses cancel button */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    /* Action that executes when user presses ok button */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = productsName.getText().toString();
                        String url = productsUrl.getText().toString();
                        String price = productsPrice.getText().toString();

                    }
                });
        /* References to the layout's views */
        productsName = view.findViewById(R.id.itemName);
        productsUrl = view.findViewById(R.id.urlText);
        productsPrice = view.findViewById(R.id.priceSet);
        /* If the user shared a link from another application */
        if(getArguments() != null) {
            /* set the link of the new dialog to the one shared by the user */
            productsUrl.setText(getArguments().getString("url"));
        }
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /* Initialize listener to the implementation in the MainActivity */
        listener = (NewItemDialogListener) context;
    }

    public interface NewItemDialogListener{
        void addItem(String name, String url, String price, String date);
    }
}
