package fr.wildcodeschool.kelian.wcstravel;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final String TAG = String.format("WCSTravel %s", getClass().getSimpleName());
    private FlightController mFlightController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFlightController = FlightController.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        EditText editTextDepartureAirport = findViewById(R.id.editTextDepartureAirport);
        EditText editTextDestinationAirport = findViewById(R.id.editTextDestinationAirport);
        EditText editTextDepartureDate = findViewById(R.id.editTextDepartureDate);
        EditText editTextReturnDate = findViewById(R.id.editTextReturnDate);

        int d,m,y;
        Calendar calendar = Calendar.getInstance();
        d = calendar.get(Calendar.DAY_OF_MONTH);
        m = calendar.get(Calendar.MONTH);
        y = calendar.get(Calendar.YEAR);

        DatePickerDialog departureDateDialog = new DatePickerDialog(this
                , (datePicker, year, month, day) -> {
            if (month < 10) {
                editTextDepartureDate.setText(String.format(getString(R.string.jan_to_sep_date), year, month + 1, day));
            } else {
                editTextDepartureDate.setText(String.format(getString(R.string.oct_to_dec_date), year, month + 1, day));
            }
        }, y, m, d);

        DatePickerDialog destinationDateDialog = new DatePickerDialog(this
                , (datePicker, year, month, day) -> {
            if (month < 10) {
                editTextReturnDate.setText(String.format(getString(R.string.jan_to_sep_date), year, month + 1, day));
            } else {
                editTextReturnDate.setText(String.format(getString(R.string.oct_to_dec_date), year, month + 1, day));
            }
        }, y, m, d);

        editTextDepartureDate.setOnClickListener(view -> {
            departureDateDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            departureDateDialog.show();
        });

        editTextReturnDate.setOnClickListener(view -> {
            destinationDateDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            destinationDateDialog.show();
        });

        // Read from the database
        myRef.child("students").child("KekeTSC").child("hasContent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean value = dataSnapshot.getValue(Boolean.class);
                Log.d(TAG, "Value is: " + String.valueOf(value));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(view -> {
            if (!(editTextDepartureAirport.getText().toString().isEmpty()
                    && editTextDestinationAirport.getText().toString().isEmpty()
                    && editTextDepartureDate.getText().toString().isEmpty()
                    && editTextReturnDate.getText().toString().isEmpty())) {
                mFlightController.searchFly(getApplicationContext()
                        , editTextDepartureAirport.getText().toString()
                        , editTextDestinationAirport.getText().toString()
                        , editTextDepartureDate.getText().toString()
                        , editTextReturnDate.getText().toString());
            } else {
                Toast.makeText(this, R.string.please_fill_fields, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
