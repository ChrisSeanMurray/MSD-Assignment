package ie.dit.msd_assignment;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class journalEntriesList extends ListActivity {
    JournalDBManager dbm;
    Cursor c;
    SimpleCursorAdapter myAdapter;

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

        myAdapter = new SimpleCursorAdapter(this,R.layout.customrow, c,columns, ids);
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
