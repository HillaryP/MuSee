package edu.uw.prathh.musee;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.prathh.musee.camera.CameraActivity;
import edu.uw.prathh.musee.donate.DonateActivity;


public class MenuActivity extends Activity {
    public static final String VENMO_URL = "https://api.venmo.com/v1/oauth/authorize?client_id=2519&scope=make_payments%20access_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setUpList();
        ImageButton backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        Button donate = (Button) findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPay = new Intent(MenuActivity.this, DonateActivity.class);
                startActivity(goToPay);
            }
        });
    }

    public void setUpList() {
        List<MenuListItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuListItem("", "scan artifacts"));
        menuItems.add(new MenuListItem("", "favorites"));
        menuItems.add(new MenuListItem("", "Exhibits"));
        menuItems.add(new MenuListItem("", "Events"));
        //menuItems.add(new MenuListItem("", "Museum Info", true));
        menuItems.add(new MenuListItem("", "Accessibility", true));
        menuItems.add(new MenuListItem("", "Map", true));
        menuItems.add(new MenuListItem("", "Museum Feedback", true));
        //menuItems.add(new MenuListItem("", "Donate to the Museum", true));
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ListItemAdapter(this, R.layout.list_item, menuItems));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
