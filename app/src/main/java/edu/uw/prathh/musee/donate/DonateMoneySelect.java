package edu.uw.prathh.musee.donate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

public class DonateMoneySelect extends Activity {
    private TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_money_select);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Select Amount");
        amount = (TextView) findViewById(R.id.amount);
        amount.setText("$ _ _._ _");
        final ImageButton confirm = (ImageButton) findViewById(R.id.next);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = amount.getText().toString();
                if (!text.equals("$ _ _._ _")) {
                    Bundle extras = new Bundle();
                    extras.putDouble("amt", Double.parseDouble(text.substring(1)));
                    Intent confirmed = new Intent(DonateMoneySelect.this, DonationPaymentInfoActivity.class);
                    confirmed.putExtras(extras);
                    startActivity(confirmed);
                }
            }
        });

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateMoneySelect.this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
    }

    public void addNumber(View v) {
        String text = amount.getText().toString();
        if (v.getId() == R.id.backspace) { // backspace pressed
            if (!text.equals("$ _ _._ _")) {
                amount.setText(text.substring(0, text.length() - 1));
                if (amount.getText().toString().equals("$")) {
                    amount.setText("$ _ _._ _");
                }
            }
        } else if (v.getId() == R.id.number10) { // decimal pressed - only add if not there
            if (!text.contains(".")) {
                amount.setText(amount.getText() + ".");
            }
        } else { // number pressed - only add if not already two cents
            Button currentClickedValue = (Button) v;
            if (text.equals("$ _ _._ _")) {
                amount.setText("$" + currentClickedValue.getText());
            } else {
                if (!text.contains(".") || text.indexOf(".") >= text.length() - 2) {
                    amount.setText(amount.getText() + "" + currentClickedValue.getText());
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate_money_select, menu);
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
