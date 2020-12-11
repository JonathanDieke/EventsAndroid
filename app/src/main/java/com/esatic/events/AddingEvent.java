package com.esatic.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import static com.esatic.events.Constantes.TABLE_NAME;
import static com.esatic.events.Constantes.TIME;
import static com.esatic.events.Constantes.TITLE;

public class AddingEvent extends AppCompatActivity {

    private EventsDataBase events ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_event);

        TextView inputTitle = (TextView) findViewById(R.id.inputTitle);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        Button empty =  (Button) findViewById(R.id.btnEmpty);
        Button create =  (Button) findViewById(R.id.btnCreate);

        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTitle.setText("");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                events = new EventsDataBase(AddingEvent.this);

                try{
                    addEvent( inputTitle.getText().toString() );
                    inputTitle.setText("");
                    Toast.makeText(AddingEvent.this, "Enregitrement Réussi !", Toast.LENGTH_LONG).show();
                }finally{
                    events.close();
                }
            }
        });
    }

    private void addEvent(String string) {
        // Insèreun nouvel enregistrement dansla bd de données Events

        SQLiteDatabase db = events.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, string);

        db.insertOrThrow(TABLE_NAME, null, values);
    }

}