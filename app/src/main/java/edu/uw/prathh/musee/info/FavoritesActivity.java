package edu.uw.prathh.musee.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.prathh.musee.ListItemAdapter;
import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.MenuListItem;
import edu.uw.prathh.musee.MuSeeApp;
import edu.uw.prathh.musee.R;

public class FavoritesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Favorites");

        setUpList();
        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setUpList() {
        List<MenuListItem> menuItems = new ArrayList<>();
        for (String favorite : ((MuSeeApp) getApplication()).getFavorites()) {
            menuItems.add(new MenuListItem(favorite));
        }
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ListItemAdapter(this, R.layout.list_item, menuItems));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
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
