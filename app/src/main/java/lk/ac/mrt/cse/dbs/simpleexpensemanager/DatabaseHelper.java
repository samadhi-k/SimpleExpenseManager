package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME= "Exp_Manger.db";
    public static final String ACCOUNTS = "accounts";
    public static final String TRANS = "trans";


    public DatabaseHelper( Context context) {
        super(context, DB_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String create_table1 = "create table " + ACCOUNTS + " (account_no varchar(50) primary key  not null,bank varchar(50), account_holder varchar(50), balance integer )";
        final String create_table2 = "create table " + TRANS + " (trans_id integer primary key autoincrement not null,type varchar(20), amount integer, date varchar(100) , account_no varchar(50) )" ;
        sqLiteDatabase.execSQL(create_table1);
        sqLiteDatabase.execSQL(create_table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + ACCOUNTS);
        sqLiteDatabase.execSQL("drop table if exists " + TRANS);
        onCreate(sqLiteDatabase);
    }
}
