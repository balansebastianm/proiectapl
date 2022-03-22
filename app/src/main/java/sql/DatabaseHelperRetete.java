package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect.R;
import com.example.proiect.ui.activities.meniuDoctor.addPatient;

public class DatabaseHelperRetete extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ReteteManager.db";
    private static final String TABLE_RETETE = "reteta";
    private static final String COLUMN_RETETE_ID = "_id";
    private static final String COLUMN_RETETE_USER_ID = "reteta_user_id";
    private static final String COLUMN_RETETE_DOCTOR_ID = "reteta_doctor_id";
    private static final String COLUMN_RETETE_MEDICAMENT_ID = "reteta_medicament_id";
    private static final String COLUMN_RETETE_MEDICAMENT_NR = "reteta_medicament_nr";
    private static final String COLUMN_RETETE_MEDICAMENT_FR = "reteta_medicament_fr";
    private static final String COLUMN_RETETE_MEDICAMENT_NUME = "reteta_medicament_nume";
    private static final String COLUMN_RETETE_MEDICAMENT_PRET = "reteta_medicament_pret";
    private String CREATE_RETETE_TABLE =
            "CREATE TABLE " + TABLE_RETETE + "("
                    + COLUMN_RETETE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_RETETE_USER_ID + " INTEGER,"
                    + COLUMN_RETETE_DOCTOR_ID + " INTEGER,"
                    + COLUMN_RETETE_MEDICAMENT_ID  + " INTEGER,"
                    + COLUMN_RETETE_MEDICAMENT_NR  + " INTEGER,"
                    + COLUMN_RETETE_MEDICAMENT_NUME  + " TEXT,"
                    + COLUMN_RETETE_MEDICAMENT_PRET  + " INTEGER,"
                    + COLUMN_RETETE_MEDICAMENT_FR  + " INTEGER"
                    + ")";

    private String DROP_RETETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_RETETE;

    private Context context;
    public DatabaseHelperRetete(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_RETETE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_RETETE_TABLE);
        onCreate(db);
    }

    public void addReteta(Retete retete){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RETETE_USER_ID, retete.getUser_id());
        values.put(COLUMN_RETETE_DOCTOR_ID, retete.getDoctor_id());
        values.put(COLUMN_RETETE_MEDICAMENT_ID, retete.getMedicament_id());
        values.put(COLUMN_RETETE_MEDICAMENT_NR, retete.getMedicament_nr());
        values.put(COLUMN_RETETE_MEDICAMENT_FR, retete.getMedicament_fr());
        values.put(COLUMN_RETETE_MEDICAMENT_NUME, retete.getMedicament_nume());
        values.put(COLUMN_RETETE_MEDICAMENT_PRET, retete.getMedicament_pret());

        db.insert(TABLE_RETETE, null, values);
        db.close();
    }

    public int getTotalPrice(String id){
        String[] columns = { COLUMN_RETETE_MEDICAMENT_PRET, COLUMN_RETETE_MEDICAMENT_NR };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_RETETE_USER_ID + " = ?";
        String[] selectionArgs = { id };
        Cursor cursor = db.query(TABLE_RETETE,    //Tabel pt query
                columns,
                selection,                      //where
                selectionArgs,                  //valori pt where
                null,
                null,
                null);
        final int pretIndex = cursor.getColumnIndex(COLUMN_RETETE_MEDICAMENT_PRET);
        final int nrIndex = cursor.getColumnIndex(COLUMN_RETETE_MEDICAMENT_NR);
        int pretUnit=0, nrUnit=0, pretFinal = 0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            pretUnit = cursor.getInt(pretIndex);
            nrUnit = cursor.getInt(nrIndex);
            pretFinal = pretFinal + (pretUnit*nrUnit);
            cursor.moveToNext();
        }
        return pretFinal;
    }

    public boolean deleteOrder(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = { COLUMN_RETETE_MEDICAMENT_NR, COLUMN_RETETE_MEDICAMENT_ID };
        String selection = COLUMN_RETETE_USER_ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };
        Cursor cursor = db.query(TABLE_RETETE,    //Tabel pt query
                columns,
                selection,                      //where
                selectionArgs,                  //valori pt where
                null,
                null,
                null);
        final int nrMedicamaneteIndex = cursor.getColumnIndex(COLUMN_RETETE_MEDICAMENT_NR);
        final int idMedicamentIndex = cursor.getColumnIndex(COLUMN_RETETE_MEDICAMENT_ID);
        cursor.moveToFirst();
        int[] stoc = new int[100];
        int[] idMedicament = new int[100];
        int[] stocOriginal = new int[100];
        int i = 0;
        while(!cursor.isAfterLast()){
            stoc[i] = cursor.getInt(nrMedicamaneteIndex);
            idMedicament[i] = cursor.getInt(idMedicamentIndex);
            i++;
            cursor.moveToNext();
        }
        DatabaseHelperDrugs dbd = new DatabaseHelperDrugs(context);
        SQLiteDatabase dbdd = dbd.getReadableDatabase();
        int j = 0;
        String[] columns2 = {DatabaseHelperDrugs.COLUMN_DRUG_ID, DatabaseHelperDrugs.COLUMN_DRUG_STOCK };
        while(j<i){
            String selection2 = DatabaseHelperDrugs.COLUMN_DRUG_ID + " = ?";
            String[] selectionArgs2 = { Integer.toString(idMedicament[j]) };
            Cursor cursor2 = dbdd.query(DatabaseHelperDrugs.TABLE_DRUG,    //Tabel pt query
                    columns2,
                    selection2,                      //where
                    selectionArgs2,                  //valori pt where
                    null,
                    null,
                    null);
            int stocMedicamentOriginal = cursor2.getColumnIndex(DatabaseHelperDrugs.COLUMN_DRUG_STOCK);
            cursor2.moveToFirst();
            stocOriginal[j] = cursor2.getInt(stocMedicamentOriginal);
            j++;
        }
        boolean canOrder = true;
        for(int a = 0 ; a<j; a++){
            System.out.println("STOC COMANDA: " + stoc[a]);
            System.out.println("STOC ORIGINAL: " + stocOriginal[a]);
        }
        for(i=0; i<j; i++){
            if(stoc[i] > stocOriginal[i]){
                System.out.println(stoc[i] + ">" + stocOriginal[i]);
                canOrder = false;
            }
        }
        if(canOrder){
            db.delete(TABLE_RETETE, COLUMN_RETETE_USER_ID + "=" + id, null);
            int newValue;
            for(i = 0; i<j; i++){
                ContentValues values = new ContentValues();
                newValue = stocOriginal[i] - stoc[i];
                values.put(DatabaseHelperDrugs.COLUMN_DRUG_STOCK, newValue);
                dbdd.update(DatabaseHelperDrugs.TABLE_DRUG, values, "_id="+idMedicament[i], null);
            }
            return true;
        }
        return false;
    }

    public boolean checkIsOrder(int id){
        String[] columns = { COLUMN_RETETE_USER_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_RETETE_USER_ID + " = ?";
        String idd;
        idd = Integer.toString(id);
        String[] selectionArgs = { idd };
        Cursor cursor = db.query(TABLE_RETETE,    //Tabel pt query
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


    public SimpleCursorAdapter populateListViewFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String columns[] = {
                DatabaseHelperRetete.COLUMN_RETETE_ID,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_NUME,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_NR,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_PRET,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_FR};

        Cursor cursor = db.query(DatabaseHelperRetete.TABLE_RETETE, columns,null, null,null, null, null, null);
        String[] fromFieldNames = new String[]{
                DatabaseHelperRetete.COLUMN_RETETE_ID,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_NUME,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_NR,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_PRET,
                DatabaseHelperRetete.COLUMN_RETETE_MEDICAMENT_FR};
        int[] toViewIDs = new int[]{R.id.drug_id, R.id.drug_name2, R.id.drug_total,  R.id.drug_price2, R.id.drug_fr};

        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.lista_retete,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }
}
