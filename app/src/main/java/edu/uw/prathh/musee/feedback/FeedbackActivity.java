package edu.uw.prathh.musee.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;


public class FeedbackActivity extends Activity {

    private RadioGroup radioFeedbackGroup;
    private RadioButton radioFeedbackButton;
    private Button btnFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Feedback");
        addListenerOnButton();

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    public void addListenerOnButton() {
        radioFeedbackGroup = (RadioGroup) findViewById(R.id.radioFeedbackGroup);
        btnFeedback = (Button) findViewById(R.id.btn_feedback);

        btnFeedback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioFeedbackGroup.getCheckedRadioButtonId();
                radioFeedbackButton = (RadioButton) findViewById(selectedId);
                radioFeedbackButton.setBackgroundResource(R.drawable.checked);
                Log.i("Feedback Activity", "Button has been clicked");

                RadioButton rb = (RadioButton) radioFeedbackGroup.findViewById(radioFeedbackGroup.getCheckedRadioButtonId());
                String text = (String)rb.getText();
                int score;
                switch(selectedId) {
                    case (R.id.radio_1):
                        score = 1;
                        break;
                    case (R.id.radio_2):
                        score = 2;
                        break;
                    case (R.id.radio_5):
                        score = 5;
                        break;
                    case (R.id.radio_4):
                        score = 4;
                        break;
                    default:
                        score = 3;
                        break;
                }

                EditText comment = (EditText)findViewById(R.id.editText);
                String value = comment.getText().toString();
                Log.i("Feedback Activity", text);
                Log.i("Feedback Activity", "Comments: " + value);

                Intent intent = new Intent(FeedbackActivity.this, FeedbackThankYou.class);
                Bundle bundle = new Bundle();
                bundle.putString("comment", value);
                bundle.putInt("score", score);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void selectedButtons (View view) {
        //buttons

        // loop through every view/button in group
        // if view is .setSelected (true), one format
        //

        // loop through all radio buttons and check if each button.setSelected(true)
        // set radioFeedbackGroup.setBackgroundResource(R.drawable.checked);
        // set radioFeedbackGroup.setBackgroundResource(R.drawable.unchecked);
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
