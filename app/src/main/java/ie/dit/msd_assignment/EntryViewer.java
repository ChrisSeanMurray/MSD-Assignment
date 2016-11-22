package ie.dit.msd_assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EntryViewer extends AppCompatActivity {
    TextView arrowText;
    TextView venueText;
    TextView detailsText;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_viewer);

        arrowText = (TextView)findViewById(R.id.arrowText);
        venueText = (TextView)findViewById(R.id.venueText);
        detailsText = (TextView)findViewById(R.id.detailsText);

        b = this.getIntent().getExtras();

        arrowText.setText("Arrow count : "+b.getString("arrows"));
        venueText.setText("Venue : " + b.getString("venue"));
        detailsText.setText(b.getString("details"));
    }
}
