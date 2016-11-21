package ie.dit.msd_assignment;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
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
}
