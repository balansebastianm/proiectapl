package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NUME = "user_nume";
    private static final String COLUMN_USER_PRENUME = "user_prename";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String  COLUMN_USER_DOCTOR = "user_doctor";

    private String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NUME + " TEXT,"
            + COLUMN_USER_PRENUME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_USERNAME  + " TEXT,"
            + COLUMN_USER_DOCTOR  + " INTEGER,"
            + COLUMN_USER_PASSWORD + " TEXT"
            + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NUME, user.getNume());
        values.put(COLUMN_USER_PRENUME, user.getPrenume());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_DOCTOR, user.getDoctor());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String username,String email){
        String[] columns = { COLUMN_USER_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?" + " OR " + COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { username, email };
        Cursor cursor = db.query(TABLE_USER,    //Tabel pt query
                columns,                        //coloanele returnate
                selection,                      //where
                selectionArgs,                  //valori pt where
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        System.out.println("Check register");
        return cursorCount > 0;
    }
    public boolean checkUserLogin(String username, String password){
        String[] columns = { COLUMN_USER_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USER,    //Tabel pt query
                columns,
                selection,                      //where
                selectionArgs,                  //valori pt where
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        System.out.println("Check login");
        return cursorCount > 0;
    }

    public boolean isDoctor(String username, String password){
        System.out.println("AAAAAA");
        String[] columns = { COLUMN_USER_DOCTOR };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USER,    //Tabel pt query
                columns,
                selection,                      //where
                selectionArgs,                  //valori pt where
                null,
                null,
                null);

        int isDoc = 0;
        if(cursor.moveToFirst() && cursor.getCount() >= 1){
            final int docIndex = cursor.getColumnIndex(COLUMN_USER_DOCTOR);
            isDoc = cursor.getInt(docIndex);
                if(isDoc == 1){
                    System.out.println("doctor");
                }
        }
           if(isDoc == 0){
               System.out.println("nu e doctor");
               return false;
           }
        cursor.close();
        db.close();
        return true;
    }
}
