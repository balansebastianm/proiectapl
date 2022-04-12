package sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.proiect.ui.activities.singleton;

public class DatabaseHelperUser extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USER_LAST_NAME = "user_last_name";
    private static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String  COLUMN_USER_DOCTOR = "user_doctor";
    private static final String COLUMN_USER_ASSIGNED_DOCTOR = "user_assigned_doctor";

    public DatabaseHelperUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_LAST_NAME + " TEXT,"
                + COLUMN_USER_FIRST_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_USER_USERNAME + " TEXT,"
                + COLUMN_USER_DOCTOR + " INTEGER,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_ASSIGNED_DOCTOR + " INTEGER"
                + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LAST_NAME, user.getLast_name());
        values.put(COLUMN_USER_FIRST_NAME, user.getFirst_name());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_DOCTOR, user.getDoctor());
        values.put(COLUMN_USER_ASSIGNED_DOCTOR, 0);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String username,String email){
        String[] columns = { COLUMN_USER_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?" + " OR " + COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { username, email };
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
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
        String[] columns = { COLUMN_USER_ID, COLUMN_USER_USERNAME, COLUMN_USER_LAST_NAME, COLUMN_USER_FIRST_NAME, COLUMN_USER_EMAIL, COLUMN_USER_DOCTOR };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        if(cursorCount>0){
            writeToFile(cursor);
        }

        cursor.close();
        db.close();
        System.out.println("Check login");
        return cursorCount > 0;
    }

    @SuppressLint("Range")
    public void writeToFile(Cursor cursor){
        String id, username, last_name, first_name, email, isDoctor;
        cursor.moveToFirst();
        id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        username = cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME));
        last_name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME));
        first_name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME));
        email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL));
        isDoctor = cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOCTOR));
        System.out.println("id: " + id);
        System.out.println("Nume: " + last_name);
        System.out.println("Prenume: " + first_name);
        System.out.println("username: " + username);
        System.out.println("email: " + email);
        System.out.println("isDoctor: " + isDoctor);
        singleton.getInstance().setId(id);
        singleton.getInstance().setUsername(username);
        singleton.getInstance().setLast_name(last_name);
        singleton.getInstance().setFirst_name(first_name);
        singleton.getInstance().setEmail(email);
        singleton.getInstance().setIsDoctor(isDoctor);
    }


    public boolean isDoctor(String username, String password){
        String[] columns = { COLUMN_USER_DOCTOR };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
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
    public boolean checkIfHasDoctor(int idUser){
        String[] columns = { COLUMN_USER_ID, COLUMN_USER_USERNAME, COLUMN_USER_LAST_NAME, COLUMN_USER_FIRST_NAME, COLUMN_USER_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_ID + " = ?" + " AND " + COLUMN_USER_ASSIGNED_DOCTOR + " !=0";
        String idu;
        idu = Integer.toString(idUser);
        String[] selectionArgs = { idu };
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean checkAssignedDoctor(int idUser, int idDoctor){
        String[] columns = { COLUMN_USER_ID, COLUMN_USER_USERNAME, COLUMN_USER_LAST_NAME, COLUMN_USER_FIRST_NAME, COLUMN_USER_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_ID + " = ?" + " AND " + COLUMN_USER_ASSIGNED_DOCTOR + " = ?";
        String idu, idd;
        idu = Integer.toString(idUser);
        idd = Integer.toString(idDoctor);
        String[] selectionArgs = { idu, idd };
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
    public boolean assignDoctor(int idUser, int idDoctor){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor;
            String sql ="SELECT _id FROM "+TABLE_USER+" WHERE _id="+idUser;
            cursor= db.rawQuery(sql,null);

            if(cursor.getCount()>0){
                ContentValues values = new ContentValues();
                values.put(COLUMN_USER_ASSIGNED_DOCTOR, idDoctor);
                db.update(TABLE_USER, values, "_id="+idUser, null);
                System.out.println("SUCCES");
                cursor.close();
                return true;
            }else{
                cursor.close();
                return false;
            }
        }catch(Exception ex){
            System.out.println("ERR");
            return false;
        }
    }
}
