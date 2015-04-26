package edu.uw.prathh.musee.donate;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.MuSeeApp;
import edu.uw.prathh.musee.R;

public class DonateActivity extends Activity {
    public final String ME_URL = "https://api.venmo.com/v1/me?access_token=";
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Donate");
        if (((MuSeeApp)getApplication()).getAccessToken() == null) {
            Uri data = getIntent().getData();
            String token = data.getQueryParameter("access_token");
            ((MuSeeApp) getApplication()).setAccessToken(token);
            new InfoRequestTask().execute(ME_URL + token);
        } else {
            String token = ((MuSeeApp)getApplication()).getAccessToken();
            new InfoRequestTask().execute(ME_URL + token);
        }

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
        String token = ((MuSeeApp)getApplication()).getAccessToken();
        new InfoRequestTask().execute(ME_URL + token);
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
            Intent moneySelect = new Intent(this, DonateConfirmAmount.class);
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

    private class InfoRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... uri) {
            if (uri[0] != null) {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    Log.i("Payment", "Attempting to request: " + uri[0]);
                    HttpResponse response = httpClient.execute(new HttpGet(uri[0]));
                    StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        String jsonString = EntityUtils.toString(response.getEntity());
                        JSONObject json = new JSONObject(jsonString);
                        return "Welcome to MuSee, " + json.getJSONObject("data").getJSONObject("user").getString("first_name");
                    } else {
                        return "Response status: " + statusLine.getReasonPhrase();
                    }
                } catch (Exception e) {
                    Log.w("Payment", "Something went wrong while getting url response: " + e);
                }
            }
            return "Something went wrong with your request";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("DonateActivity", result);
        }
    }
}