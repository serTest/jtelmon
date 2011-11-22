
/* 
 * http://www.edumobile.org/android/android-development/use-of-sqlite/
 * 
 */

package net.learn2develop.AndroidViews;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.List;

public class DataManipulator {
	private static final  String DATABASE_NAME = "easysales.db";
	private static final int DATABASE_VERSION = 1;
	static final String TABLE_NAME = "orders1";
	private static Context context;
	static SQLiteDatabase db;

	private SQLiteStatement insertStmt;
	
    private static final String INSERT = "insert into "
		+ TABLE_NAME + " (clientName,productName,piecesNumber,discountNumber) values (?,?,?,?)";

	public DataManipulator(Context context) {
		DataManipulator.context = context;
		OpenHelper openHelper = new OpenHelper(DataManipulator.context);
		DataManipulator.db = openHelper.getWritableDatabase();
		this.insertStmt = DataManipulator.db.compileStatement(INSERT);

	}
	public long insert(String clientName,String productName,String piecesNumber,String discountNumber) {
		this.insertStmt.bindString(1, clientName);
		this.insertStmt.bindString(2, productName);
		this.insertStmt.bindString(3, piecesNumber);
		this.insertStmt.bindString(4, discountNumber);
		return this.insertStmt.executeInsert();
	}

	public void deleteAll() {
		db.delete(TABLE_NAME, null, null);
	}

	public List<String[]> selectAll()
	{

		List<String[]> list = new ArrayList<String[]>();
		Cursor cursor = db.query(TABLE_NAME, new String[] { "lineOrderId","clientName","productName","piecesNumber","discountNumber" },	null, null, null, null, "clientName asc"); 

//		Cursor cursor = db.query(TABLE_NAME, new String[] { "clientName","productName","piecesNumber","discountNumber" },
//		null, null, null, null, "name asc"); 
		
		
		int x=0;
		if (cursor.moveToFirst()) {
			do {
				String[] b1=new String[]{cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)};

				list.add(b1);

				x=x+1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		} 
		cursor.close();

		return list;
	}


	public void delete(int rowId) {
		db.delete(TABLE_NAME, null, null); 
	}



	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (lineOrderId INTEGER PRIMARY KEY, clientName TEXT, productName TEXT, piecesNumber TEXT, discountNumber TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

}
