package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public PersistentTransactionDAO(DatabaseHelper dbHelper){
        this.dbHelper = dbHelper;
        this.db = dbHelper.getWritableDatabase();
    }
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        String q;
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String strDate = dateFormat.format(date);
        System.out.println(strDate);
        if (expenseType == ExpenseType.EXPENSE){
            q= "insert into trans (type, amount, date,account_no ) values( 'expense', "+amount+",'"+strDate+"','"+accountNo+"')";
        }else{
            q= "insert into trans (type, amount, date,account_no ) values( 'income', "+amount+",'"+strDate+"','"+accountNo+"')";
        }
        db.execSQL(q);
    }

    @Override
    public List<Transaction> getAllTransactionLogs()  {
        List<Transaction> returnList = new ArrayList<>();
        final String q2 = "select * from trans";
        Cursor cr = db.rawQuery(q2, null);
        if (cr.moveToFirst()){
            while(cr.isAfterLast() == false){
                int y = cr.getInt(0);
                String x = cr.getString(1).trim();
                ExpenseType expenseType = ExpenseType.INCOME;
                if (x.equalsIgnoreCase("expense")){
                    expenseType = ExpenseType.EXPENSE;
                }
                double amount = cr.getDouble(2);
                String date = cr.getString(3);
                String accountNO = cr.getString(4);

                try {
                    Date date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
                    Transaction trans = new Transaction(date1, accountNO, expenseType, amount);
                    returnList.add(trans);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cr.moveToNext();
            }
        }
        cr.close();
        return returnList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> allTransactions = getAllTransactionLogs();
        int size = allTransactions.size();
        if (size <= limit) {
            return allTransactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return allTransactions.subList(size - limit, size);
    }
}
