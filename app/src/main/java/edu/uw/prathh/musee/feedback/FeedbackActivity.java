package edu.uw.prathh.musee.feedback;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.view.View;
import android.view.View.OnClickListener;

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

            }
        });
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
