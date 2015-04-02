package edu.uw.prathh.musee;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.prathh.musee.camera.CameraActivity;


public class MenuActivity extends ActionBarActivity {

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
    }

    public void setUpList() {
        List<MenuListItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuListItem("", "scan artifacts"));
        menuItems.add(new MenuListItem("", "favorites"));
        menuItems.add(new MenuListItem("", "Exhibits"));
        menuItems.add(new MenuListItem("", "Events"));
        menuItems.add(new MenuListItem("", "Museum Info", true));
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
