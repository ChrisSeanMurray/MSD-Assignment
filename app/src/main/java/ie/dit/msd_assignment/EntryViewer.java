package ie.dit.msd_assignment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EntryViewer extends AppCompatActivity implements View.OnClickListener {
    TextView arrowText;
    TextView venueText;
    TextView detailsText;
    Bundle b;
    Button edit;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_viewer);

        arrowText = (TextView)findViewById(R.id.arrowText);
        venueText = (TextView)findViewById(R.id.venueText);
        detailsText = (TextView)findViewById(R.id.detailsText);
        edit = (Button)findViewById(R.id.buttonEdit);
        edit.setOnClickListener(this);
        image = (ImageView)findViewById(R.id.imageView);

        b = this.getIntent().getExtras();

        arrowText.setText("Arrow count : "+b.getInt("arrows"));
        venueText.setText("Venue : " + b.getString("venue"));
        detailsText.setText(b.getString("details"));
        if(b.getByteArray("image") != null) {
            image.setImageBitmap(BitmapFactory.decodeByteArray(b.getByteArray("image"), 0, b.getByteArray("image").length));
        }
        setTitle(b.getString("date"));
    }

    public void onClick(View v){
        Intent i = new Intent(this,EditEntry.class);
        i.putExtra("id", b.getInt("id"));
        startActivity(i);
        finish();

    }
}
