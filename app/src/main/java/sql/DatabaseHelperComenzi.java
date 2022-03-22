package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import com.example.proiect.R;

public class DatabaseHelperComenzi extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ComenziManager.db";
    private static final String TABLE_COMENZI = "comenzi";
    private static final String COLUMN_COMENZI_ID = "_id";
    private static final String COLUMN_COMENZI_ID_PERSOANA = "id_persoana";
    private static final String COLUMN_COMENZI_NUME_PERSOANA = "nume_persoana";
    private static final String COLUMN_COMENZI_ADRESA = "adresa_comanda";
    private static final String COLUMN_COMENZI_COD_POSTAL = "cod_postal_comanda";
    private static final String COLUMN_COMENZI_ORAS = "oras_comanda";
    private static final String COLUMN_COMENZI_JUDET = "judet_comanda";
    private static final String COLUMN_COMENZI_PRET = "pret_comanda";
    private String CREATE_COMENZI_TABLE =
            "CREATE TABLE " + TABLE_COMENZI + "("
                    + COLUMN_COMENZI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_COMENZI_ID_PERSOANA + " INTEGER,"
                    + COLUMN_COMENZI_NUME_PERSOANA + " TEXT,"
                    + COLUMN_COMENZI_ADRESA  + " TEXT,"
                    + COLUMN_COMENZI_COD_POSTAL  + " INTEGER,"
                    + COLUMN_COMENZI_ORAS   + " TEXT,"
                    + COLUMN_COMENZI_JUDET  + " TEXT,"
                    + COLUMN_COMENZI_PRET  + " INTEGER"
                    + ")";

    private String DROP_COMENZI_TABLE = "DROP TABLE IF EXISTS " + TABLE_COMENZI;

    private Context context;
    public DatabaseHelperComenzi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_COMENZI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_COMENZI_TABLE);
        onCreate(db);
    }
    public void addOrder(Comenzi comanda){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMENZI_ID_PERSOANA, comanda.getIdUserComanda());
        values.put(COLUMN_COMENZI_NUME_PERSOANA, comanda.getNumeComplet());
        values.put(COLUMN_COMENZI_ADRESA, comanda.getAdresa());
        values.put(COLUMN_COMENZI_COD_POSTAL, comanda.getCodPostal());
        values.put(COLUMN_COMENZI_ORAS, comanda.getOras());
        values.put(COLUMN_COMENZI_JUDET, comanda.getJudet());
        values.put(COLUMN_COMENZI_PRET, comanda.getPretTotal());

        db.insert(TABLE_COMENZI, null, values);
        db.close();
    }
    public SimpleCursorAdapter populateListViewFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                DatabaseHelperComenzi.COLUMN_COMENZI_ID,
                DatabaseHelperComenzi.COLUMN_COMENZI_NUME_PERSOANA,
                DatabaseHelperComenzi.COLUMN_COMENZI_ADRESA,
                DatabaseHelperComenzi.COLUMN_COMENZI_COD_POSTAL,
                DatabaseHelperComenzi.COLUMN_COMENZI_ORAS,
                DatabaseHelperComenzi.COLUMN_COMENZI_JUDET,
                DatabaseHelperComenzi.COLUMN_COMENZI_PRET};

        Cursor cursor = db.query(DatabaseHelperComenzi.TABLE_COMENZI, columns,null, null,null, null, null, null);
        String[] fromFieldNames = new String[]{
                DatabaseHelperComenzi.COLUMN_COMENZI_ID,
                DatabaseHelperComenzi.COLUMN_COMENZI_NUME_PERSOANA,
                DatabaseHelperComenzi.COLUMN_COMENZI_ADRESA,
                DatabaseHelperComenzi.COLUMN_COMENZI_COD_POSTAL,
                DatabaseHelperComenzi.COLUMN_COMENZI_ORAS,
                DatabaseHelperComenzi.COLUMN_COMENZI_JUDET,
                DatabaseHelperComenzi.COLUMN_COMENZI_PRET};
        int[] toViewIDs = new int[]{R.id.comanda_id, R.id.person_name, R.id.adresa_comanda, R.id.cod_postal_comanda, R.id.oras_comanda, R.id.judet_comanda, R.id.pret_comanda};

        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.lista_istoric,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }
}
