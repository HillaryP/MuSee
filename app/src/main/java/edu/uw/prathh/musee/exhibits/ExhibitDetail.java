package edu.uw.prathh.musee.exhibits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
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


import java.util.Random;

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

        // attempted to randomly generate images
//        Drawable[] b = {getResources().getDrawable(R.drawable.evolution1), getResources().getDrawable(R.drawable.evolution2),
//                getResources().getDrawable(R.drawable.evolution3), getResources().getDrawable(R.drawable.evolution4)};
//        Random r = new Random();
//        ImageView view = (ImageView) findViewById(R.id.exhibitimage1);
//        ImageView view2 = (ImageView) findViewById(R.id.exhibitimage2);
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            view.setBackgroundResource(r.nextInt(b.length));
//            view2.setBackgroundResource(r.nextInt(b.length));
////            view.setBackground(getResources().getDrawable(r.nextInt(b.length)));
////            view2.setBackground(getResources().getDrawable(r.nextInt(b.length)));
//        }
//        else{
//            view.setBackground(getResources().getDrawable(r.nextInt(b.length)));
//            view2.setBackground(getResources().getDrawable(r.nextInt(b.length)));
////            view.setBackgroundDrawable(getResources().getDrawable(r.nextInt(b.length)));
////            view2.setBackgroundDrawable(getResources().getDrawable(r.nextInt(b.length)));
//        }


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
