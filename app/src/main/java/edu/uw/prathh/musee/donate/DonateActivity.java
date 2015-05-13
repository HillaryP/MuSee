package edu.uw.prathh.musee.donate;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

public class DonateActivity extends Activity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Donate");

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void optionSelected(View v) {
        double amt = 0.0;
        switch (v.getId()) {
            case R.id.ten:
                amt = 10.0;
                break;
            case R.id.twenty:
                amt = 20.0;
                break;
            case R.id.fifty:
                amt = 50.0;
                break;
            case R.id.hundred:
                amt = 100.0;
                break;
            default:
                Intent moneySelect = new Intent(this, DonateMoneySelect.class);
                startActivity(moneySelect);
                break;
        }
        if (v.getId() != R.id.other) {
            Bundle b = new Bundle();
            b.putDouble("amt", amt);
            Intent moneySelect = new Intent(this, DonationPaymentInfoActivity.class);
            moneySelect.putExtras(b);
            startActivity(moneySelect);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate, menu);
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