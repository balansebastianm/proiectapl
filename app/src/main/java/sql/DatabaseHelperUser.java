package sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import com.example.proiect.ui.activities.singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;

public class DatabaseHelperUser extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USER_NUME = "user_nume";
    private static final String COLUMN_USER_PRENUME = "user_prename";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String  COLUMN_USER_DOCTOR = "user_doctor";
    private static final String COLUMN_USER_ASSIGNED_DOCTOR = "user_assigned_doctor";
    private String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NUME + " TEXT,"
            + COLUMN_USER_PRENUME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_USERNAME  + " TEXT,"
            + COLUMN_USER_DOCTOR  + " INTEGER,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_ASSIGNED_DOCTOR + " INTEGER"
            + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelperUser(Context context) {
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
        values.put(COLUMN_USER_ASSIGNED_DOCTOR, 0);

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
        String[] columns = { COLUMN_USER_ID, COLUMN_USER_USERNAME, COLUMN_USER_NUME, COLUMN_USER_PRENUME, COLUMN_USER_EMAIL, COLUMN_USER_DOCTOR };
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
        String id, username, nume, prenume, email, isDoctor;
        cursor.moveToFirst();
        id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        username = cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME));
        nume = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NUME));
        prenume = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PRENUME));
        email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL));
        isDoctor = cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOCTOR));
        System.out.println("id: " + id);
        System.out.println("Nume: " + nume);
        System.out.println("Prenume: " + prenume);
        System.out.println("username: " + username);
        System.out.println("email: " + email);
        System.out.println("isDoctor: " + isDoctor);
        singleton.getInstance().setId(id);
        singleton.getInstance().setUsername(username);
        singleton.getInstance().setNume(nume);
        singleton.getInstance().setPrenume(prenume);
        singleton.getInstance().setEmail(email);
        singleton.getInstance().setIsDoctor(isDoctor);
    }


    public boolean isDoctor(String username, String password){
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
    public boolean checkIfHasDoctor(int idUser, int idDoctor){
        String[] columns = { COLUMN_USER_ID, COLUMN_USER_USERNAME, COLUMN_USER_NUME, COLUMN_USER_PRENUME, COLUMN_USER_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_ID + " = ?" + " AND " + COLUMN_USER_ASSIGNED_DOCTOR + " !=0";
        String idu, idd;
        idu = Integer.toString(idUser);
        idd = Integer.toString(idDoctor);
        String[] selectionArgs = { idu };
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
        return cursorCount > 0;
    }

    public boolean checkAssignedDoctor(int idUser, int idDoctor){
        String[] columns = { COLUMN_USER_ID, COLUMN_USER_USERNAME, COLUMN_USER_NUME, COLUMN_USER_PRENUME, COLUMN_USER_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_ID + " = ?" + " AND " + COLUMN_USER_ASSIGNED_DOCTOR + " = ?";
        String idu, idd;
        idu = Integer.toString(idUser);
        idd = Integer.toString(idDoctor);
        String[] selectionArgs = { idu, idd };
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
        return cursorCount > 0;
    }
    public boolean assignDoctor(int idUser, int idDoctor){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = null;
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
