package ie.dit.msd_assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Button save;
    Button cancel;
    JournalDBManager dbm;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning the edittext views to their corresponding elements
        v_arrows = (EditText)findViewById(R.id.arrowCount);
        v_venue = (EditText)findViewById(R.id.venue);
        v_details = (EditText)findViewById(R.id.journalDetails);

        //assigning the button views to their corresponding elements and setting their listeners
        save = (Button)findViewById(R.id.buttonSave);
        save.setOnClickListener(this);
        cancel = (Button)findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(this);

        //seting up db connection
        dbm = new JournalDBManager(this);

        //setting up calendar
        c = Calendar.getInstance();
    }

    @Override
    public void onClick(View v){
        if(v.getId() == save.getId()){
            int arrowCount = Integer.parseInt(v_arrows.getText().toString());
            String venue = v_venue.getText().toString();
            String entry = v_details.getText().toString();

            //calling system date
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String date = df.format(c.getTime());

            dbm.open();
            dbm.insertEntry(arrowCount,date, venue, entry);
            dbm.close();
            Toast.makeText(this, "Successful insertion", Toast.LENGTH_SHORT).show();
            v_arrows.setText("");
            v_details.setText("");
            v_venue.setText("");
        }


    }
}
