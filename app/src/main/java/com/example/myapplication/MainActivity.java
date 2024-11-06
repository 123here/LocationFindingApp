package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;


public class MainActivity extends AppCompatActivity {


    // Variable Decleration
    private LocationDatabaseHelper dbHelper;
    private EditText editTextAddress;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private TextView textViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ID in the XML to use
        dbHelper = new LocationDatabaseHelper(this);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        textViewLocation = findViewById(R.id.textViewLocation);

        Button buttonQuery = findViewById(R.id.buttonQuery);
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryLocation();
            }
        });

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrUpdateLocation();  // Updated to call addOrUpdateLocation()
            }
        });

        Button buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLocation();
            }
        });

        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
            }
        });

    }

    // Method to Query location
    private void queryLocation() {
        String address = editTextAddress.getText().toString().trim();
        double[] cordinates = getLocation(address);
        // Check to see if location exists and display cordinates
        if (cordinates != null) {
            textViewLocation.setText("Latitude: " + cordinates[0] + ", Longitude: " + cordinates[1]);
        } else {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get location for DB
    private double[] getLocation(String address) {
        double[] cordinates = null;
        // Checks the Database for location
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + LocationDatabaseHelper.COLUMN_LATITUDE + ", "
                + LocationDatabaseHelper.COLUMN_LONGITUDE + " FROM "
                + LocationDatabaseHelper.TABLE_LOCATIONS + " WHERE "
                + LocationDatabaseHelper.COLUMN_ADDRESS + "=?", new String[]{address});

        // Checking the value for the database
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cordinates = new double[2];
                cordinates[0] = cursor.getDouble(0); // Latitude
                cordinates[1] = cursor.getDouble(1); // Longitude
            }
            cursor.close();
        }
        return cordinates;
    }

    // Add or update the location
    private void addOrUpdateLocation() {
        String address = editTextAddress.getText().toString().trim();
        String latitudeString = editTextLatitude.getText().toString().trim();
        String longitudeString = editTextLongitude.getText().toString().trim();

        // Check if fields are empty
        if (address.isEmpty() || latitudeString.isEmpty() || longitudeString.isEmpty()) {
            Toast.makeText(this, "Please enter an address, latitude, and longitude.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Check if the location with the given address already exists
            Cursor cursor = db.query(
                    LocationDatabaseHelper.TABLE_LOCATIONS,         // Table name
                    new String[]{LocationDatabaseHelper.COLUMN_ID}, // Columns to return
                    LocationDatabaseHelper.COLUMN_ADDRESS + " = ?", // WHERE clause
                    new String[]{address},                          // WHERE arguments
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                // Location exists, update it
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(LocationDatabaseHelper.COLUMN_ID));
                ContentValues values = new ContentValues();
                values.put(LocationDatabaseHelper.COLUMN_LATITUDE, latitude);
                values.put(LocationDatabaseHelper.COLUMN_LONGITUDE, longitude);
                db.update(LocationDatabaseHelper.TABLE_LOCATIONS, values, LocationDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
                Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
            } else {
                // Location does not exist, add it as a new entry
                dbHelper.addLocation(db, address, latitude, longitude);
                Toast.makeText(this, "Location added", Toast.LENGTH_SHORT).show();
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
        }
    }

    // method to delete location
    private void deleteLocation() {
        String address = editTextAddress.getText().toString().trim();

        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter an address to delete.", Toast.LENGTH_SHORT).show();
            return;
        }

        int rowsDeleted = dbHelper.deleteLocationByAddress(address);

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Location deleted", Toast.LENGTH_SHORT).show();
            textViewLocation.setText("");  // Clear the displayed location after deletion
        } else {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateLocation() {
        String address = editTextAddress.getText().toString().trim();
        String latitudeString = editTextLatitude.getText().toString().trim();
        String longitudeString = editTextLongitude.getText().toString().trim();

        if (address.isEmpty() || latitudeString.isEmpty() || longitudeString.isEmpty()) {
            Toast.makeText(this, "Please enter an address, latitude, and longitude.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double newLatitude = Double.parseDouble(latitudeString);
            double newLongitude = Double.parseDouble(longitudeString);
            int idToUpdate = getLocationId(address);
            if (idToUpdate != -1) {
                dbHelper.updateLocation(idToUpdate, address, newLatitude, newLongitude);
                Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid latitude or longitude", Toast.LENGTH_SHORT).show();
        }
    }

    private int getLocationId(String address) {
        int id = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + LocationDatabaseHelper.COLUMN_ID + " FROM "
                + LocationDatabaseHelper.TABLE_LOCATIONS + " WHERE "
                + LocationDatabaseHelper.COLUMN_ADDRESS + "=?", new String[]{address});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }
        return id;
    }
}
