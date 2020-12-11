package com.esatic.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.esatic.events.Constantes.TABLE_NAME;
import static com.esatic.events.Constantes.TIME;
import static com.esatic.events.Constantes.TITLE;

public class ListEvent<builder> extends AppCompatActivity {
    private EventsDataBase events =  new EventsDataBase(ListEvent.this) ;

    private static String[] FROM = { _ID, TIME, TITLE, };
    private static String ORDER_BY = TIME + " DESC" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        Cursor cursor = getEvents();

        List<EventItem> eventsItems = showEvents(cursor);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(this, eventsItems));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                EventItem country = (EventItem) o;
                Toast.makeText(ListEvent.this, "Selected :" + " " + country, Toast.LENGTH_LONG).show();
            }
        });
    }

    private Cursor getEvents() {
        SQLiteDatabase db= events.getReadableDatabase();
        Cursor cursor= db.query(TABLE_NAME, FROM, null, null, null,null, ORDER_BY);
        startManagingCursor(cursor);
        //CursorLoader
        return cursor;
    }

    private List<EventItem> showEvents(Cursor cursor) {
        // On les met tousdansunegrossechainede caractères
        List<EventItem> list = new ArrayList<EventItem>();

        while(cursor.moveToNext()) {
            //On peututilizer getColumnIndexOrThrow() pour récupérerles indexes
            // long id = cursor.getLong(0);
            String time = cursor.getString(1),
                    title= cursor.getString(2);

            list.add(new EventItem(title, "ic_baseline_event_available_24", time));
        }

        return list;
    }
}