package com.example.hp.secondtry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    int basePrice = 5;
    int quantity = 2;
    int whipquantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//BUTTON METHODS
    public void decrement(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void whipdecrement(View view) {
        if (whipquantity == 100) {
            return;
        }
        whipquantity = whipquantity - 1;


        displayQuantitywhip(whipquantity);
    }

    public void whipincrement(View view) {
        if (whipquantity == 100) {
            return;
        }
        whipquantity = whipquantity + 1;
        displayQuantitywhip(whipquantity);

    }

    @SuppressLint("StringFormatInvalid")
    public void submitOrder(View view) {
        EditText editname = (EditText) findViewById(R.id.editName);
        Editable nameEditable = editname.getText();
        String name = nameEditable.toString();

        CheckBox whippedCreambox = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreambox.isChecked();

        CheckBox chocobox = (CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean haschocobox = chocobox.isChecked();

        int cups = numofcups(haschocobox, hasWhippedCream);
        int price = calculateprice(haschocobox, hasWhippedCream);

        String message = createOrdersummary(name, cups, price, haschocobox, hasWhippedCream);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @SuppressLint("StringFormatInvalid")
    private String createOrdersummary(String name, int cups, int price, boolean haschocobox, boolean haswhippedcream) {

        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, haswhippedcream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, haschocobox);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, quantity * basePrice);
        NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }



    private int calculateprice(boolean chocolate, boolean whippedcream) {
        int baseprice = 5;
        if (chocolate) {
            baseprice = (baseprice + 1) * quantity;
        }
        if (whippedcream) {
            baseprice = (baseprice + 2) * whipquantity;
        }
        displayprice(baseprice);
        return baseprice;


    }
    private int numofcups(boolean chocolate, boolean whippedcream) {
        int numcups=0;
        if (chocolate) {
            numcups = quantity;
        }
        if (whippedcream) {
           numcups = whipquantity;
        }
        if (whippedcream & chocolate) {
            numcups = whipquantity+quantity;
        }
        displaycups(numcups);
        return numcups;


    }
//Display methods
    void displayprice(int price) {

        TextView pricequantity = (TextView) findViewById(R.id.display_total);
        pricequantity.setText(" " + price);
    }
    void displaycups(int cups) {

        TextView pricequantity = (TextView) findViewById(R.id.display_cups);
        pricequantity.setText(" " + cups);
    }
    void displayQuantitywhip(int numberofcoffees) {

        TextView quantityTwview = (TextView) findViewById(R.id.quantity_textwhip_view);
        quantityTwview.setText(" " + numberofcoffees);
    }
    void displayQuantity(int numberofcoffees) {

        TextView quantityTview = (TextView) findViewById(R.id.quantity_text_view);
        quantityTview.setText(" " + numberofcoffees);
    }

}
