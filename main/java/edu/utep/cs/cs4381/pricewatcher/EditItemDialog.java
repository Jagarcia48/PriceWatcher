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


public class EditItemDialog extends AppCompatDialogFragment {

    private EditText productName;
    private EditText productUrl;

    public EditItemDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /* Set dialog's layout from resources */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit, null);

        /* Set properties of the dialog window */
        builder.setView(view).setTitle("Edit item")
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
                        String name = productName.getText().toString();
                        String url = productUrl.getText().toString();
                        /* Validate edit item fields by checking if any of them are empty */
                        if(!name.equals("") && !url.equals("")){
                            /* call MainActivity addItem method passing new item's attributes */
                            listener.updateItem(name, url, getArguments().getInt("index"));
                        }
                    }
                });

        /* References to the layout's views */
        productName = view.findViewById(R.id.itemName);
        productUrl = view.findViewById(R.id.urlText);

        /* Original name and URL of the item being edit */
        productName.setText(getArguments().getString("currentName"));
        productUrl.setText(getArguments().getString("currentUrl"));
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /* Initialize listener to the implementation in the MainActivity */
        listener = (EditItemDialogListener) context;
    }

    public interface EditItemDialogListener{
        void updateItem(String name, String url, int index);
    }
}
