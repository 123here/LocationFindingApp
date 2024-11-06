package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationDatabaseHelper extends SQLiteOpenHelper {

    // Name for Database
    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public LocationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    // Create the Table in the Database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LOCATIONS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_LATITUDE + " REAL, "
                + COLUMN_LONGITUDE + " REAL)");
        initializeDatabase(db); // Initialize the database with predefined locations
    }

    private void initializeDatabase(SQLiteDatabase db) {
        //  100 predefined locations with address, latitude, and longitude
        String[] addresses = {
                "CN Tower", "Royal Ontario Museum", "Toronto Islands", "Art Gallery of Ontario",
                "St. Lawrence Market", "Hockey Hall of Fame", "Ripley's Aquarium of Canada",
                "Casa Loma", "High Park", "Kensington Market",
                "Distillery District", "Yorkville", "Queen's Park",
                "Toronto Zoo", "Evergreen Brick Works", "Woodbine Beach",
                "Scarborough Bluffs", "Ontario Science Centre", "Black Creek Pioneer Village",
                "Toronto Eaton Centre", "PATH", "Yonge-Dundas Square",
                "Toronto Botanical Garden", "Bata Shoe Museum", "Royal Conservatory of Music",
                "Spadina Museum", "Lake Ontario", "Graffiti Alley",
                "Toronto Sign", "Nathan Phillips Square", "Bellwoods Brewery",
                "Mount Pleasant Cemetery", "Toronto Reference Library", "Harbourfront Centre",
                "Canoe Landing Park", "Luminato Festival", "Theatre District",
                "St. Michael's Cathedral", "Union Station", "St. Lawrence Centre for the Arts",
                "Toronto Symphony Orchestra", "The Second City Toronto", "Budweiser Stage",
                "Toronto Marlies", "Toronto Raptors", "Toronto FC",
                "Royal Agricultural Winter Fair", "Toronto International Film Festival",
                "Toronto Pride", "Caribana", "Beaches International Jazz Festival",
                "Toronto Christmas Market", "Toronto Comic Arts Festival", "Hot Docs",
                "Inside Out LGBT Film Festival", "Nuit Blanche", "Canadian National Exhibition",
                "Word on the Street", "Toronto Fringe Festival", "Kensington Market Jazz Festival",
                "Canadian Music Week", "Toronto Outdoor Art Exhibition", "SummerWorks Performance Festival",
                "Luminato Festival", "Taste of the Danforth", "Toronto's Festival of Beer",
                "Toronto Caribbean Carnival", "Toronto Salsa Festival", "Toronto Island Park",
                "Evergreen Brick Works", "Rouge National Urban Park", "Don Valley Brick Works Park",
                "Gus Harris Park", "Christie Pits Park", "Trinity Bellwoods Park",
                "Sherwood Park", "G. Ross Lord Park", "Berczy Park",
                "Sunnyside Park", "Queens Park", "Woodbine Park",
                "Humber Bay Park", "Tommy Thompson Park", "Ashbridges Bay Park"
        };

        double[] latitudes = {
                43.642566, 43.6682, 43.6205, 43.6532,
                43.6491, 43.3663, 43.6281, 43.6767,
                43.6478, 43.6576, 43.6547, 43.6721,
                43.6674, 43.7115, 43.6731, 43.6501,
                43.6866, 43.7326, 43.6757, 43.6553,
                43.6494, 43.6588, 43.6505, 43.6785,
                43.6779, 43.6807, 43.6543, 43.6895,
                43.6494, 43.6494, 43.6537, 43.6554,
                43.6568, 43.6593, 43.6598, 43.6417,
                43.6549, 43.6534, 43.6749, 43.6542,
                43.6521, 43.6366, 43.6584, 43.6545,
                43.6682, 43.6678, 43.6882, 43.6854,
                43.6553, 43.6469, 43.6681, 43.6483,
                43.6727, 43.6318, 43.6352, 43.6678,
                43.6371, 43.6675, 43.6351, 43.6584,
                43.6487, 43.6428, 43.6742, 43.6691,
                43.6728, 43.6789, 43.6889, 43.6902,
                43.6911, 43.6685, 43.6682, 43.6722,
                43.6538, 43.6503, 43.6579, 43.6499,
                43.6499, 43.6647, 43.6673, 43.6587,
                43.6535, 43.6487, 43.6513, 43.6681,
                43.6725, 43.6901, 43.6741, 43.6575,
                43.6572, 43.6738, 43.6714, 43.6617,
                43.6652, 43.6746, 43.6548, 43.6484
        };

        double[] longitudes = {
                -79.387057, -79.394079, -79.373697, -79.383186,
                -79.371818, -79.375551, -79.384120, -79.410674,
                -79.395851, -79.399733, -79.378136, -79.371801,
                -79.367193, -79.381463, -79.364166, -79.372233,
                -79.338826, -79.384198, -79.400030, -79.379134,
                -79.383240, -79.384433, -79.370302, -79.370668,
                -79.395376, -79.388913, -79.396016, -79.373295,
                -79.394258, -79.394446, -79.400858, -79.390079,
                -79.396247, -79.386966, -79.387674, -79.393016,
                -79.378784, -79.394086, -79.396335, -79.397147,
                -79.393382, -79.389181, -79.396652, -79.395429,
                -79.392956, -79.391012, -79.392870, -79.389210,
                -79.391890, -79.392110, -79.389800, -79.384450,
                -79.387910, -79.389705, -79.391002, -79.392200,
                -79.389100, -79.387810, -79.384810, -79.387910,
                -79.390100, -79.396640, -79.391200, -79.393010,
                -79.388300, -79.395200, -79.396830, -79.392750,
                -79.394550, -79.392150, -79.388450, -79.388900,
                -79.395750, -79.392820, -79.390850, -79.392970,
                -79.388500, -79.386700, -79.396200, -79.393800,
                -79.396050, -79.391740, -79.390800, -79.392220,
                -79.387550, -79.384300, -79.390750, -79.393950,
                -79.388880, -79.384370, -79.393430, -79.388670,
                -79.396900, -79.391150, -79.386120, -79.392000,
                -79.385920, -79.387930
        };

        // Insert each location into the database
        for (int i = 0; i < addresses.length; i++) {
            addLocation(db, addresses[i], latitudes[i], longitudes[i]);
        }
    }

    // Add new location to the database
    public void addLocation(SQLiteDatabase db, String address, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.insert(TABLE_LOCATIONS, null, values);


    }

    // Method to update a location
    public boolean updateLocation(int id, String address, double latitude, double longitude) {
        SQLiteDatabase db = null;
        boolean isUpdated = false;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ADDRESS, address);
            values.put(COLUMN_LATITUDE, latitude);
            values.put(COLUMN_LONGITUDE, longitude);

            int rowsAffected = db.update(TABLE_LOCATIONS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            isUpdated = (rowsAffected > 0);

            // Check if the database got updated
            if (isUpdated) {
                Log.d("Database", "Updated location with id = " + id);
            } else {
                Log.d("Database", "No location found with id = " + id);
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error while updating location with id = " + id, e);

        }

        return isUpdated;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete location in the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        // create the new entry in the database table
        onCreate(db);
    }

    public int deleteLocationByAddress(String address) {
        SQLiteDatabase db = null;
        int rowsAffected = 0;
        try {
            db = this.getWritableDatabase();
            rowsAffected = db.delete(TABLE_LOCATIONS, COLUMN_ADDRESS + " = ?", new String[]{address});

            if (rowsAffected > 0) {
                Log.d("Database", "Deleted " + rowsAffected + " rows where address = " + address);
            } else {
                Log.d("Database", "No rows found with address = " + address);
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error while deleting location by address: " + address, e);

        }
        return rowsAffected;
    }

}
