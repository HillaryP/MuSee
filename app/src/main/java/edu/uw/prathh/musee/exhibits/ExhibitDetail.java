package edu.uw.prathh.musee.exhibits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

public class ExhibitDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibit_detail);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Exhibit Detail");
        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExhibitDetail.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();

        String exhibitName = bundle.getString("Exhibit");

     //   Log.i("Exhibit Detail", exhibitName);

        title.setText(exhibitName);

        ParseQuery<ParseObject> queryEvent = ParseQuery.getQuery("Exhibits");
        queryEvent.whereEqualTo("name", exhibitName);
        queryEvent.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> exhibitDes, ParseException e) {
                //String exDesc = exhibitDes.getString("description");
                //TextView description = (TextView) findViewById(R.id.header).findViewById(R.id.description);
                //Log.i("Exhibit Description", exDesc);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exhibit_detail, menu);
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
