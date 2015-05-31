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
        String exDesc = bundle.getString("Description");

        title.setText(exhibitName);
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(exDesc);

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
