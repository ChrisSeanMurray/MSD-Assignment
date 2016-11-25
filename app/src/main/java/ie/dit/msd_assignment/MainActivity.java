package ie.dit.msd_assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText v_arrows;
    EditText v_venue;
    EditText v_details;
    JournalDBManager dbm;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar) ;
        setSupportActionBar(myToolbar);


        //assigning the edittext views to their corresponding elements
        v_arrows = (EditText)findViewById(R.id.arrowCount);
        v_venue = (EditText)findViewById(R.id.venue);
        v_details = (EditText)findViewById(R.id.journalDetails);


        //seting up db connection
        dbm = new JournalDBManager(this);

        //setting up calendar
        c = Calendar.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbaritems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_save was selected
            case R.id.action_save:

                if(v_venue.getText().toString().length() <= 0 || v_arrows.getText().toString().length()<= 0 || v_details.getText().toString().length() <= 0){
                    Toast.makeText(this, "You have left one of the fields empty", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    int arrowCount = Integer.parseInt(v_arrows.getText().toString());
                    String venue = v_venue.getText().toString();
                    String entry = v_details.getText().toString();


                    //calling system date
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String date = df.format(c.getTime());

                    dbm.open();
                    dbm.insertEntry(arrowCount, date, venue, entry);
                    dbm.close();
                    Toast.makeText(this, "Successful insertion", Toast.LENGTH_SHORT).show();
                    v_arrows.setText("");
                    v_details.setText("");
                    v_venue.setText("");
                    break;
                }
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_cancel:
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    public void onClick(View v) {
    }
}
