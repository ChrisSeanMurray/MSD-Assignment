package ie.dit.msd_assignment;

import android.database.Cursor;
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

public class EditEntry extends AppCompatActivity {
    JournalDBManager db;
    Bundle b;
    Cursor c;
    EditText arrows;
    EditText venue;
    EditText details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar) ;
        setSupportActionBar(myToolbar);

        db = new JournalDBManager(this);
        b = this.getIntent().getExtras();

        db.open();
        c = db.getEntry(b.getInt("id"));
        db.close();

        arrows = (EditText)findViewById(R.id.arrowCountEdit);
        venue = (EditText) findViewById(R.id.venueEdit);
        details = (EditText)findViewById(R.id.journalDetailsEdit);


        arrows.setText(c.getString(1));
        venue.setText(c.getString(3));
        details.setText(c.getString(4));
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
                db.open();
                db.updateEntry(b.getInt("id"),Integer.parseInt(arrows.getText().toString())
                        ,c.getString(2), venue.getText().toString(), details.getText().toString());
                db.close();
                finish();
                break;
            case R.id.action_cancel:
                finish();
                break;

            default:
                break;
        }

        return true;
    }


}
