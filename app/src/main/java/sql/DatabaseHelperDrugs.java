package sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import com.example.proiect.R;
public class DatabaseHelperDrugs extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DrugsManager.db";
    public static final String TABLE_DRUG = "drug";
    public static final String COLUMN_DRUG_ID = "_id";
    public static final String COLUMN_DRUG_NAME = "drug_name";
    public static final String COLUMN_DRUG_CODE = "drug_code";
    public static final String COLUMN_DRUG_DESCRIPTION = "drug_description";
    public static final String COLUMN_DRUG_STOCK = "drug_stock";
    public static final String COLUMN_DRUG_PRICE = "drug_price";

    private final Context context;
    public DatabaseHelperDrugs(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_DRUG_TABLE = "CREATE TABLE " + TABLE_DRUG + "("
                + COLUMN_DRUG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DRUG_NAME + " TEXT,"
                + COLUMN_DRUG_CODE + " TEXT,"
                + COLUMN_DRUG_DESCRIPTION + " TEXT,"
                + COLUMN_DRUG_STOCK + " INTEGER,"
                + COLUMN_DRUG_PRICE + " INTEGER"
                + ")";
        db.execSQL(CREATE_DRUG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String DROP_DRUG_TABLE = "DROP TABLE IF EXISTS " + TABLE_DRUG;
        db.execSQL(DROP_DRUG_TABLE);
        onCreate(db);
    }

    public void addDrug(Drugs drugs){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DRUG_NAME, drugs.getName());
        values.put(COLUMN_DRUG_CODE, drugs.getCode());
        values.put(COLUMN_DRUG_DESCRIPTION, drugs.getDescription());
        values.put(COLUMN_DRUG_STOCK, drugs.getStock());
        values.put(COLUMN_DRUG_PRICE, drugs.getPrice());

        db.insert(TABLE_DRUG, null, values);
        db.close();
    }

    @SuppressLint("Recycle")
    public Cursor getDrugs(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        return cursor = db.rawQuery("SELECT * FROM drug ORDER BY drug_name", null);
    }

    public boolean updateDrug(int id, int q){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor;
            String sql ="SELECT _id FROM "+TABLE_DRUG+" WHERE _id="+id;
            cursor= db.rawQuery(sql,null);

            if(cursor.getCount()>0){
                ContentValues values = new ContentValues();
                values.put(COLUMN_DRUG_STOCK, q);
                db.update(TABLE_DRUG, values, "_id="+id, null);
                System.out.println("SUCCESS");
                cursor.close();
                return true;
            }else{
                cursor.close();
                return false;
            }
        }catch(Exception ex){
            System.out.println("ERROR");
            return false;
        }

    }
    public boolean checkDrugExists(int id){
        String[] columns = { COLUMN_DRUG_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_DRUG_ID + " = ?";
        String idd;
        idd = Integer.toString(id);
        String[] selectionArgs = { idd };
        Cursor cursor = db.query(TABLE_DRUG,
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

    public int getDrugPrice(int id){
        String[] columns = { COLUMN_DRUG_PRICE };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_DRUG_ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };
        Cursor cursor = db.query(TABLE_DRUG,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int price;
        if(cursor.moveToFirst() && cursor.getCount() >= 1){
            final int priceIndex = cursor.getColumnIndex(COLUMN_DRUG_PRICE);
            price = cursor.getInt(priceIndex);
            cursor.close();
            return price;
        }
        cursor.close();
        return -1;
    }

    public String getDrugName(int id){
        String[] columns = { COLUMN_DRUG_NAME };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_DRUG_ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_DRUG,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        String name = "";
        if(cursor.moveToFirst() && cursor.getCount() >= 1){
            final int nameIndex = cursor.getColumnIndex(COLUMN_DRUG_NAME);
            name = cursor.getString(nameIndex);
        }
        return name;
    }

    public boolean checkCode(String cod){
        String[] columns = { COLUMN_DRUG_CODE };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_DRUG_CODE + " = ?";
        String[] selectionArgs = { cod };
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_DRUG,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        return !cursor.moveToFirst() || cursor.getCount() < 1;
    }

    public SimpleCursorAdapter populateListViewFromDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {DatabaseHelperDrugs.COLUMN_DRUG_ID, DatabaseHelperDrugs.COLUMN_DRUG_NAME, DatabaseHelperDrugs.COLUMN_DRUG_STOCK, DatabaseHelperDrugs.COLUMN_DRUG_PRICE};
        Cursor cursor = db.query(DatabaseHelperDrugs.TABLE_DRUG, columns,null, null,null, null, null, null);
        String[] fromFieldNames = new String[]{
                DatabaseHelperDrugs.COLUMN_DRUG_ID, DatabaseHelperDrugs.COLUMN_DRUG_NAME, DatabaseHelperDrugs.COLUMN_DRUG_STOCK, DatabaseHelperDrugs.COLUMN_DRUG_PRICE
        };
        int[] toViewIDs = new int[]{R.id.drug_id, R.id.drug_name, R.id.drug_stock, R.id.drug_price};

        SimpleCursorAdapter contactAdapter;
        contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.drug_list,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }

}
