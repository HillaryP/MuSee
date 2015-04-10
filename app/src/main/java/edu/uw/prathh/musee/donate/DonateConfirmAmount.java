package edu.uw.prathh.musee.donate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.uw.prathh.musee.MuSeeApp;
import edu.uw.prathh.musee.R;

public class DonateConfirmAmount extends ActionBarActivity {
    public static final String URL_TEST = "https://sandbox-api.venmo.com/v1/payments";
    //public static final String URL = "https://api.venmo.com/v1/payments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_confirm_amount);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Confirm Amount");
        TextView amount = (TextView) findViewById(R.id.amt);
        final double amt = getIntent().getExtras().getDouble("amt");
        amount.setText("$" + amt);
        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRequest(amt);
                Intent confirmed = new Intent(DonateConfirmAmount.this, DonateThankYou.class);
                Bundle b = new Bundle();
                b.putString("amount", "" + getIntent().getExtras().getDouble("amt"));
                confirmed.putExtras(b);
                startActivity(confirmed);
            }
        });
    }

    public void runRequest(double amt) {
        Log.i("DonateThankYou", "Recip: 15555555555 " + amt);
        new PaymentRequestTask().execute(URL_TEST, "" + amt, "15555555555"); //TODO: this.recipient);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate_confirm_amount, menu);
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



    private class PaymentRequestTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... uri) {
            if (uri[0] != null) {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    Log.i("Payment", "Attempting to request: " + uri[0]);
                    HttpPost httpPost = new HttpPost(uri[0]);
                    List<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("access_token", ((MuSeeApp)getApplication()).getAccessToken()));
                    nameValuePairs.add(new BasicNameValuePair("phone", uri[2]));
                    nameValuePairs.add(new BasicNameValuePair("note", "Burke Donation"));
                    nameValuePairs.add(new BasicNameValuePair("amount", "0.1")); // TODO: uri[1]));
                    Log.i("Payment", "nameValuePairs: " + nameValuePairs.toString());

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);
                    StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        String jsonString = EntityUtils.toString(response.getEntity());
                        JSONObject json = new JSONObject(jsonString);
                        return json;
                    } else {
                        Log.i("Payment", "ENTITY: " + response.getEntity().toString());
                    }
                } catch (Exception e) {
                    Log.w("Payment", "Something went wrong while getting url response: " + e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.i("Payment", result.toString());
            } else {
                Log.i("Payment", "result was null");
            }
        }
    }
}
