package edu.uw.prathh.musee.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseObject;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

public class FeedbackThankYou extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_thank_you);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("");

        ImageButton menuButton = (ImageButton) findViewById(R.id.header).findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackThankYou.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = this.getIntent().getExtras();
        ParseObject feedback = new ParseObject("feedback");
        feedback.put("comment", extras.getString("comment"));
        feedback.put("score", extras.getInt("score"));
        feedback.saveInBackground();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback_thank_you, menu);
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
