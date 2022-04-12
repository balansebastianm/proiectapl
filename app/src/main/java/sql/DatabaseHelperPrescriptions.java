package sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import com.example.proiect.R;

public class DatabaseHelperPrescriptions extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PrescriptionsManager.db";
    private static final String TABLE_PRESCRIPTION = "prescriptions";
    private static final String COLUMN_PRESCRIPTION_ID = "_id";
    private static final String COLUMN_PRESCRIPTION_USER_ID = "user_id";
    private static final String COLUMN_PRESCRIPTION_DOCTOR_ID = "doctor_id";
    private static final String COLUMN_PRESCRIPTION_DRUG_ID = "drug_id";
    private static final String COLUMN_PRESCRIPTION_DRUG_NO = "drug_no";
    private static final String COLUMN_PRESCRIPTION_DRUG_FR = "drug_fr";
    private static final String COLUMN_PRESCRIPTION_DRUG_NAME = "drug_name";
    private static final String COLUMN_PRESCRIPTION_DRUG_PRICE = "drug_price";

    private final Context context;
    public DatabaseHelperPrescriptions(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_PRESCRIPTIONS_TABLE = "CREATE TABLE " + TABLE_PRESCRIPTION + "("
                + COLUMN_PRESCRIPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRESCRIPTION_USER_ID + " INTEGER,"
                + COLUMN_PRESCRIPTION_DOCTOR_ID + " INTEGER,"
                + COLUMN_PRESCRIPTION_DRUG_ID + " INTEGER,"
                + COLUMN_PRESCRIPTION_DRUG_NO + " INTEGER,"
                + COLUMN_PRESCRIPTION_DRUG_NAME + " TEXT,"
                + COLUMN_PRESCRIPTION_DRUG_PRICE + " INTEGER,"
                + COLUMN_PRESCRIPTION_DRUG_FR + " INTEGER"
                + ")";
        db.execSQL(CREATE_PRESCRIPTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String DROP_PRESCRIPTION_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRESCRIPTION;
        db.execSQL(DROP_PRESCRIPTION_TABLE);
        onCreate(db);
    }

    public void addPrescription(Prescriptions prescription){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRESCRIPTION_USER_ID, prescription.getUser_id());
        values.put(COLUMN_PRESCRIPTION_DOCTOR_ID, prescription.getDoctor_id());
        values.put(COLUMN_PRESCRIPTION_DRUG_ID, prescription.getDrug_id());
        values.put(COLUMN_PRESCRIPTION_DRUG_NO, prescription.getDrug_no());
        values.put(COLUMN_PRESCRIPTION_DRUG_FR, prescription.getDrug_fr());
        values.put(COLUMN_PRESCRIPTION_DRUG_NAME, prescription.getDrug_name());
        values.put(COLUMN_PRESCRIPTION_DRUG_PRICE, prescription.getDrug_price());

        db.insert(TABLE_PRESCRIPTION, null, values);
        db.close();
    }

    public int getTotalPrice(String id){
        String[] columns = { COLUMN_PRESCRIPTION_DRUG_PRICE, COLUMN_PRESCRIPTION_DRUG_NO };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_PRESCRIPTION_USER_ID + " = ?";
        String[] selectionArgs = { id };
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_PRESCRIPTION,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        final int priceIndex = cursor.getColumnIndex(COLUMN_PRESCRIPTION_DRUG_PRICE);
        final int noIndex = cursor.getColumnIndex(COLUMN_PRESCRIPTION_DRUG_NO);
        int priceUnit, noUnits, priceFinal = 0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            priceUnit = cursor.getInt(priceIndex);
            noUnits = cursor.getInt(noIndex);
            priceFinal = priceFinal + (priceUnit*noUnits);
            cursor.moveToNext();
        }
        return priceFinal;
    }

    public boolean deleteOrder(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = { COLUMN_PRESCRIPTION_DRUG_NO, COLUMN_PRESCRIPTION_DRUG_ID };
        String selection = COLUMN_PRESCRIPTION_USER_ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_PRESCRIPTION,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        final int drugsNoIndex = cursor.getColumnIndex(COLUMN_PRESCRIPTION_DRUG_NO);
        final int drugsIdIndex = cursor.getColumnIndex(COLUMN_PRESCRIPTION_DRUG_ID);
        cursor.moveToFirst();
        int[] stock = new int[100];
        int[] drugId = new int[100];
        int[] stockFull = new int[100];
        int i = 0;
        while(!cursor.isAfterLast()){
            stock[i] = cursor.getInt(drugsNoIndex);
            drugId[i] = cursor.getInt(drugsIdIndex);
            i++;
            cursor.moveToNext();
        }
        DatabaseHelperDrugs dbd = new DatabaseHelperDrugs(context);
        SQLiteDatabase dbdd = dbd.getReadableDatabase();
        int j = 0;
        String[] columns2 = {DatabaseHelperDrugs.COLUMN_DRUG_ID, DatabaseHelperDrugs.COLUMN_DRUG_STOCK };
        while(j<i){
            String selection2 = DatabaseHelperDrugs.COLUMN_DRUG_ID + " = ?";
            String[] selectionArgs2 = { Integer.toString(drugId[j]) };
            @SuppressLint("Recycle") Cursor cursor2 = dbdd.query(DatabaseHelperDrugs.TABLE_DRUG,
                    columns2,
                    selection2,
                    selectionArgs2,
                    null,
                    null,
                    null);
            int drugFullStock = cursor2.getColumnIndex(DatabaseHelperDrugs.COLUMN_DRUG_STOCK);
            cursor2.moveToFirst();
            stockFull[j] = cursor2.getInt(drugFullStock);
            j++;
        }
        boolean canOrder = true;
        for(int a = 0 ; a<j; a++){
            System.out.println("ORDER STOCK: " + stock[a]);
            System.out.println("FULL STOCK: " + stockFull[a]);
        }
        for(i=0; i<j; i++){
            if(stock[i] > stockFull[i]){
                System.out.println(stock[i] + ">" + stockFull[i]);
                canOrder = false;
            }
        }
        if(canOrder){
            db.delete(TABLE_PRESCRIPTION, COLUMN_PRESCRIPTION_USER_ID + "=" + id, null);
            int newValue;
            for(i = 0; i<j; i++){
                ContentValues values = new ContentValues();
                newValue = stockFull[i] - stock[i];
                values.put(DatabaseHelperDrugs.COLUMN_DRUG_STOCK, newValue);
                dbdd.update(DatabaseHelperDrugs.TABLE_DRUG, values, "_id="+drugId[i], null);
            }
            return true;
        }
        return false;
    }

    public boolean checkIsOrder(int id){
        String[] columns = { COLUMN_PRESCRIPTION_USER_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_PRESCRIPTION_USER_ID + " = ?";
        String idd;
        idd = Integer.toString(id);
        String[] selectionArgs = { idd };
        Cursor cursor = db.query(TABLE_PRESCRIPTION,
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


    public SimpleCursorAdapter populateListViewFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_ID,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_NAME,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_NO,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_PRICE,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_FR};

        Cursor cursor = db.query(DatabaseHelperPrescriptions.TABLE_PRESCRIPTION, columns,null, null,null, null, null, null);
        String[] fromFieldNames = new String[]{
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_ID,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_NAME,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_NO,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_PRICE,
                DatabaseHelperPrescriptions.COLUMN_PRESCRIPTION_DRUG_FR};
        int[] toViewIDs = new int[]{R.id.drug_id, R.id.drug_name2, R.id.drug_total,  R.id.drug_price2, R.id.drug_fr};

        SimpleCursorAdapter contactAdapter;
        contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.prescription_list,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }
}
