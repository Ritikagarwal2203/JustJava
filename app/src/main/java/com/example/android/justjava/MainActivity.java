/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedcreamcheckbox=(CheckBox) findViewById(R.id.Whipped_Cream_Checkbox);
        boolean hasWhippedCream=whippedcreamcheckbox.isChecked();
        CheckBox chocolatebox=(CheckBox) findViewById(R.id.Chocolate_Checkbox);
        boolean hasChocolate=chocolatebox.isChecked();
        EditText text=(EditText) findViewById(R.id.name_field);
        String value=text.getText().toString();
        int price=calculatePrice(hasWhippedCream,hasChocolate);

        String priceMessage=createOrderSummary(hasWhippedCream,hasChocolate,value,price);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava's order for "+ value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
    public void increment(View view){
        if (quantity==100){
            Toast.makeText(this,"You cannot order more than this",Toast.LENGTH_SHORT).show();
            return;
        }

        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view){
        if (quantity==1){
            Toast.makeText(this,"You cannot order less than this",Toast.LENGTH_SHORT).show();
            return;
        }

        quantity=quantity-1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */

    /**
     * This method displays the given text on the screen.
     */

    private String createOrderSummary(boolean addWhippedCream,boolean addchocolate,String name,int price){
        String priceMessage="Name:"+name;
        priceMessage+="\nAdd whipped cream?"+addWhippedCream;
        priceMessage+="\nAdd Chocolate?"+addchocolate;
        priceMessage=priceMessage+"\nQuantity:"+quantity;
        priceMessage=priceMessage+"\nTotal: $" + (quantity *price);
        priceMessage+= "\nThank you!";
        return priceMessage;
    }
    private int calculatePrice(boolean n,boolean m){
        int fixprice=5;
        if (m){
           fixprice+=2;
        }
        if (n){
            fixprice+=1;
        }
        return fixprice;
    }
}