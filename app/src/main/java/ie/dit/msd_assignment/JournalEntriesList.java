package ie.dit.msd_assignment;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class JournalEntriesList extends ListActivity {
    JournalDBManager dbm;
    Cursor c;
    MyCursorAdapter myAdapter;

    public class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView date = (TextView)view.findViewById(R.id.rowDate);
            TextView venue = (TextView)view.findViewById(R.id.rowVenue);
            ImageView rowImage = (ImageView)view.findViewById(R.id.rowImage);
            date.setText(cursor.getString(2));
            venue.setText(cursor.getString(3));
            rowImage.setImageResource(R.drawable.target);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.customrow, parent, false);
            bindView(v, context, cursor);
            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entries_list);

        String[] columns = new String[]{"date", "venue"};
        int[] ids = new int[] {R.id.rowDate, R.id.rowVenue};

        //setting the database manager
        dbm = new JournalDBManager(this);
        //opening database connection
        dbm.open();
        //queriying all entries in the database to a cursor
        c = dbm.getAllEntries();

        //myAdapter = new SimpleCursorAdapter(this,R.layout.customrow, c,columns, ids);
        myAdapter = new MyCursorAdapter(this, c);
        setListAdapter(myAdapter);

        //closing database connection
        dbm.close();


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        c.moveToPosition(position);
        int entryID = c.getInt(0);
        int arrows = c.getInt(1);
        String date = c.getString(2);
        String venue = c.getString(3);
        String details = c.getString(4);

        Intent i = new Intent(this, EntryViewer.class);
        i.putExtra("id", entryID);
        i.putExtra("arrows", arrows);
        i.putExtra("venue", venue);
        i.putExtra("details", details);
        i.putExtra("date", date);

        startActivity(i);
        finish();
    }
}
