package ie.dit.msd_assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    Button newEntry;
    Button allEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        newEntry = (Button)findViewById(R.id.button);
        newEntry.setOnClickListener(this);
        allEntries = (Button)findViewById(R.id.button2);
        allEntries.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId()==allEntries.getId()){
            Intent i = new Intent(this, journalEntriesList.class);
            startActivity(i);
        }
        if(v.getId()==newEntry.getId()){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}
