package edu.uw.prathh.musee.donate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

public class DonationPaymentInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_payment_info);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Payment Information");


        ImageButton menuButton = (ImageButton) findViewById(R.id.header).findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationPaymentInfoActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        final Double amount = getIntent().getExtras().getDouble("amt");
        TextView amountShow = (TextView) findViewById(R.id.amount_show);
        amountShow.setText("Donation Amount: $" + amount);

        EditText ccInfo = (EditText) findViewById(R.id.cc_number);
        ccInfo.setHint("Credit Card Number");

        String[] months = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
        Spinner monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(adapter);

        String[] year = new String[20];
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < year.length; i++) {
            year[i] = "" + (thisYear + i);
        }
        Spinner yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, year);
        yearSpinner.setAdapter(yearAdapter);

        EditText cvv = (EditText) findViewById(R.id.cvv);
        cvv.setHint("CVV");

        final ImageButton confirm = (ImageButton) findViewById(R.id.next);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putDouble("amt", amount);
                Intent confirmed = new Intent(DonationPaymentInfoActivity.this, DonateConfirmAmount.class);
                confirmed.putExtras(extras);
                startActivity(confirmed);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donation_payment_info, menu);
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
}
