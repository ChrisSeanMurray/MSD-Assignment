package ie.dit.msd_assignment;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditEntry extends AppCompatActivity implements View.OnClickListener{
    JournalDBManager db;
    Bundle b;
    Cursor c;
    EditText arrows;
    EditText venue;
    EditText details;
    Button update;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        db = new JournalDBManager(this);
        b = this.getIntent().getExtras();

        db.open();
        c = db.getEntry(b.getInt("id"));
        db.close();

        arrows = (EditText)findViewById(R.id.arrowCountEdit);
        venue = (EditText) findViewById(R.id.venueEdit);
        details = (EditText)findViewById(R.id.journalDetailsEdit);
        update = (Button)findViewById(R.id.buttonEditSave);
        cancel = (Button)findViewById(R.id.buttonEditCancel);

        update.setOnClickListener(this);
        cancel.setOnClickListener(this);

        arrows.setText(c.getString(1));
        venue.setText(c.getString(3));
        details.setText(c.getString(4));
    }

    public void onClick(View v){
        if(v.getId() == update.getId()){
            db.open();
            db.updateEntry(b.getInt("id"),Integer.parseInt(arrows.getText().toString())
                    ,c.getString(2), venue.getText().toString(), details.getText().toString());
            db.close();
            finish();
        }
        if(v.getId() == cancel.getId()){
            finish();
        }
    }
}
