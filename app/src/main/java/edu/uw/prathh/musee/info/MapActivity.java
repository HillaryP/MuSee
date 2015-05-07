package edu.uw.prathh.musee.info;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;


public class MapActivity extends Activity {
    Button button;
    Button button_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Map");

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        View upperFloor = (View) findViewById(R.id.lowerfloor);
        View lowerFloor = (View) findViewById(R.id.upperfloor);

        upperFloor.setVisibility(View.GONE);
        lowerFloor.setVisibility(View.VISIBLE);

        button = (Button) findViewById(R.id.floor1);
        button_two = (Button) findViewById(R.id.floor2);

        button_two.getBackground().setColorFilter(Color.parseColor("#3F4752"), PorterDuff.Mode.MULTIPLY);
        button_two.setTextColor(Color.WHITE);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                button.getBackground().setColorFilter(Color.parseColor("#3F4752"), PorterDuff.Mode.MULTIPLY);
                button.setTextColor(Color.WHITE);
                button_two.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                button_two.setTextColor(Color.BLACK);

                View list = (View) findViewById(R.id.lowerfloor);
                View list2 = (View) findViewById(R.id.upperfloor);

                list2.setVisibility(View.INVISIBLE);
                list2.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
            }

        });

        button_two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                button.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                button.setTextColor(Color.BLACK);
                button_two.getBackground().setColorFilter(Color.parseColor("#3F4752"), PorterDuff.Mode.MULTIPLY);
                button_two.setTextColor(Color.WHITE);

                View list = (View) findViewById(R.id.lowerfloor);
                View list2 = (View) findViewById(R.id.upperfloor);

                list.setVisibility(View.INVISIBLE);
                list.setVisibility(View.GONE);
                list2.setVisibility(View.VISIBLE);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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
