package edu.uw.prathh.musee.exhibits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;


public class ExhibitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibit);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Exhibits");

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExhibitActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new GridAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String exhibitName = (String)gridview.getItemAtPosition(position);
               String exhibitDescription = getDescription(position);
               Log.i("Exhibit Activity", exhibitDescription);
              //  Log.i("Exhibit Activity", gridview.toString());
              //  Log.i("Exhibit Activity", exhibitName);
               Bundle bundle = new Bundle();
               bundle.putString("Exhibit", exhibitName);
               bundle.putString("Description", exhibitDescription);
               Intent intent = new Intent(ExhibitActivity.this, ExhibitDetail.class);
               intent.putExtras(bundle);
               startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exhibit, menu);
        return true;
    }

    public String getDescription(int position) {
        List<ParseObject> descriptions;
        ParseQuery<ParseObject> query = new ParseQuery<>("Exhibits");
        query.orderByDescending("name");
        try {
            descriptions = query.find();
            String[] descriptionArray = new String[descriptions.size()];
            int index = 0;
            for (ParseObject description : descriptions) {
                descriptionArray[index] = description.getString("description");
                index++;
            }
            return descriptionArray[position];
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
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
