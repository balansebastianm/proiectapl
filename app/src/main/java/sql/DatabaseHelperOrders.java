package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import com.example.proiect.R;

public class DatabaseHelperOrders extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OrdersManager.db";
    private static final String TABLE_ORDER = "orders";
    private static final String COLUMN_ORDER_ID = "_id";
    private static final String COLUMN_ORDER_ID_USER = "user_id";
    private static final String COLUMN_ORDER_USER_NAME = "user_name";
    private static final String COLUMN_ORDER_ADDRESS = "order_address";
    private static final String COLUMN_ORDER_PO_CODE = "order_po_code";
    private static final String COLUMN_ORDER_CITY = "order_city";
    private static final String COLUMN_ORDER_COUNTY = "order_county";
    private static final String COLUMN_ORDER_PRICE = "order_price";

    private final Context context;
    public DatabaseHelperOrders(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_ID_USER + " INTEGER,"
                + COLUMN_ORDER_USER_NAME + " TEXT,"
                + COLUMN_ORDER_ADDRESS + " TEXT,"
                + COLUMN_ORDER_PO_CODE + " INTEGER,"
                + COLUMN_ORDER_CITY + " TEXT,"
                + COLUMN_ORDER_COUNTY + " TEXT,"
                + COLUMN_ORDER_PRICE + " INTEGER"
                + ")";
        db.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String DROP_ORDER_TABLE = "DROP TABLE IF EXISTS " + TABLE_ORDER;
        db.execSQL(DROP_ORDER_TABLE);
        onCreate(db);
    }
    public void addOrder(Orders order){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_ID_USER, order.getIdUserOrder());
        values.put(COLUMN_ORDER_USER_NAME, order.getFullName());
        values.put(COLUMN_ORDER_ADDRESS, order.getAddress());
        values.put(COLUMN_ORDER_PO_CODE, order.getPoCode());
        values.put(COLUMN_ORDER_CITY, order.getCity());
        values.put(COLUMN_ORDER_COUNTY, order.getCounty());
        values.put(COLUMN_ORDER_PRICE, order.getTotalPrice());

        db.insert(TABLE_ORDER, null, values);
        db.close();
    }
    public SimpleCursorAdapter populateListViewFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                DatabaseHelperOrders.COLUMN_ORDER_ID,
                DatabaseHelperOrders.COLUMN_ORDER_USER_NAME,
                DatabaseHelperOrders.COLUMN_ORDER_ADDRESS,
                DatabaseHelperOrders.COLUMN_ORDER_PO_CODE,
                DatabaseHelperOrders.COLUMN_ORDER_CITY,
                DatabaseHelperOrders.COLUMN_ORDER_COUNTY,
                DatabaseHelperOrders.COLUMN_ORDER_PRICE};

        Cursor cursor = db.query(DatabaseHelperOrders.TABLE_ORDER, columns,null, null,null, null, null, null);
        String[] fromFieldNames = new String[]{
                DatabaseHelperOrders.COLUMN_ORDER_ID,
                DatabaseHelperOrders.COLUMN_ORDER_USER_NAME,
                DatabaseHelperOrders.COLUMN_ORDER_ADDRESS,
                DatabaseHelperOrders.COLUMN_ORDER_PO_CODE,
                DatabaseHelperOrders.COLUMN_ORDER_CITY,
                DatabaseHelperOrders.COLUMN_ORDER_COUNTY,
                DatabaseHelperOrders.COLUMN_ORDER_PRICE};
        int[] toViewIDs = new int[]{R.id.order_id, R.id.person_name, R.id.order_address, R.id.order_POCode, R.id.order_city, R.id.order_county, R.id.order_price};

        SimpleCursorAdapter contactAdapter;
        contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.order_history,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }
}
