package edu.uw.prathh.musee.info;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.prathh.musee.ListItemAdapter;
import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.MenuListItem;
import edu.uw.prathh.musee.MuSeeApp;
import edu.uw.prathh.musee.R;
import edu.uw.prathh.musee.media.ArtifactInfoFragment;
import edu.uw.prathh.musee.media.GalleryFragment;

public class FavoritesActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Favorites");

        setUpList();
        ImageButton menuButton = (ImageButton) findViewById(R.id.header).findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
    }

    public void setUpList() {
        List<MenuListItem> menuItems = new ArrayList<>();
        for (String favorite : ((MuSeeApp) getApplication()).getFavorites()) {
            menuItems.add(new MenuListItem(favorite));
        }
        if (menuItems.size() == 0) {
            FrameLayout rl = (FrameLayout) findViewById(R.id.content);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(0, 300, 0, 0);
            TextView text = new TextView(this);
            text.setText("It looks like there's nothing here! Here's how to add a favorite:");
            text.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(text);
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setImageResource(R.drawable.photos);
            linearLayout.addView(image);
            rl.addView(linearLayout);
        } else {
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(new ListItemAdapter(this, R.layout.list_item, menuItems));
        }
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

    public void goToInfo(String label) {
        String fixedFormatString;
        if (label.equalsIgnoreCase("musee"))  {
            fixedFormatString = "MuSee";
        } else if (label.equalsIgnoreCase("Kwakawaka'Wakw House")) {
            fixedFormatString = "Kwakawaka'Wakw House";
        } else {
            String[] parts = label.split(" ");
            fixedFormatString = "";
            for (int i = 0; i < parts.length; i++) {
                String first = "" + parts[i].charAt(0);
                fixedFormatString += " " + first.toUpperCase() + parts[i].substring(1).toLowerCase();
            }
        }

        final ArtifactInfoFragment artifactInfo = new ArtifactInfoFragment();

        Bundle b = new Bundle();
        b.putString("poi", fixedFormatString.trim());
        artifactInfo.setArguments(b);

        Log.i("ListItemAdapter", "context is FragmentActivity");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, artifactInfo)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void addFavorite(ImageButton button, String name) {
        boolean isFilled = ((MuSeeApp) getApplication()).getFavorites().contains(name);
        Log.i("CameraActivity", "Before: " + ((MuSeeApp) getApplication()).getFavorites().toString());
        if (isFilled) {
            ((MuSeeApp) getApplication()).removeFromFavorites(name);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackgroundResource(R.drawable.star);
            } else {
                button.setBackground(getResources().getDrawable(R.drawable.star));
            }
        } else {
            ((MuSeeApp) getApplication()).addToFavorites(name);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackgroundResource(R.drawable.filledstar);
            } else {
                button.setBackground(getResources().getDrawable(R.drawable.filledstar));
            }
        }

        Log.i("CameraActivity", "After: " + ((MuSeeApp) getApplication()).getFavorites().toString());
    }

    public void openGallery(String id) {
        Log.i("CameraActivity", "openGallery called");
        final GalleryFragment gallery = new GalleryFragment();
        Bundle b = new Bundle();
        b.putString("artifactId", id);
        gallery.setArguments(b);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, gallery)
                .addToBackStack(null).commit();
    }
}
