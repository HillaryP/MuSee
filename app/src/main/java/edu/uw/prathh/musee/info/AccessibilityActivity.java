package edu.uw.prathh.musee.info;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;


public class AccessibilityActivity extends Activity {
    ImageButton button;
    ImageButton button_two;
    ImageButton button_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Accessibility");

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessibilityActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        View accessibilityList = (View) findViewById(R.id.linearlayout1);
        View accessibilityList2 = (View) findViewById(R.id.linearlayout2);
        View accessibilityList3 = (View) findViewById(R.id.linearlayout3);

        accessibilityList2.setVisibility(View.GONE);
        accessibilityList3.setVisibility(View.GONE);
        accessibilityList.setVisibility(View.GONE);


        button = (ImageButton) findViewById(R.id.tab1);
        button_two = (ImageButton) findViewById(R.id.tab2);
        button_three = (ImageButton) findViewById(R.id.tab3);


        button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    //button.setBackgroundColor(Color.parseColor("#3F4752"));
                    View list = (View) findViewById(R.id.linearlayout1);
                    View list2 = (View) findViewById(R.id.linearlayout2);
                    View list3 = (View) findViewById(R.id.linearlayout3);

                    list2.setVisibility(View.INVISIBLE);
                    list2.setVisibility(View.GONE);
                    list3.setVisibility(View.INVISIBLE);
                    list3.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                }

            });

        button_two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //button_two.setBackgroundColor(Color.parseColor("#3F4752"));
                View list = (View)findViewById(R.id.linearlayout1);
                View list2 = (View)findViewById(R.id.linearlayout2);
                View list3 = (View)findViewById(R.id.linearlayout3);

                list.setVisibility(View.INVISIBLE);
                list.setVisibility(View.GONE);
                list3.setVisibility(View.INVISIBLE);
                list3.setVisibility(View.GONE);
                list2.setVisibility(View.VISIBLE);
            }
        });

        button_three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //button_three.setBackgroundColor(Color.parseColor("#3F4752"));

                View list = (View)findViewById(R.id.linearlayout1);
                View list2 = (View)findViewById(R.id.linearlayout2);
                View list3 = (View)findViewById(R.id.linearlayout3);

                list.setVisibility(View.INVISIBLE);
                list2.setVisibility(View.INVISIBLE);
                list3.setVisibility(View.VISIBLE);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accessibility, menu);
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
