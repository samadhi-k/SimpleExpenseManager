package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public PersistentAccountDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.db = dbHelper.getWritableDatabase();
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> returnList = new ArrayList<>();
        final String q1 = "select account_no from accounts";
        Cursor cr = db.rawQuery(q1, null);
        if (cr.moveToFirst()){
            do{
                String acc_no = cr.getString(0);
                returnList.add(acc_no);
            }while(cr.moveToNext());
        }
        cr.close();
        return returnList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> returnList = new ArrayList<>();
        final String q2 = "select * from accounts";
        Cursor cr = db.rawQuery(q2, null);
        if (cr.moveToFirst()){
            while(cr.isAfterLast() == false){
                String accNo = cr.getString(0);
                String bank = cr.getString(1);
                String accountHolder = cr.getString(2);
                double balance = cr.getDouble(3);
                Account account = new Account(accNo, bank, accountHolder, balance);
                returnList.add(account);
                cr.moveToNext();
            }
        }
        cr.close();
        return returnList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        final String q2 = "select * from accounts where account_no=" + accountNo;
        Cursor cr = db.rawQuery(q2, null);
        if (cr.moveToFirst()) {

            String accNo = cr.getString(0);
            String bank = cr.getString(1);
            String accountHolder = cr.getString(2);
            double balance = cr.getDouble(3);
            Account account = new Account(accNo, bank, accountHolder, balance);

            cr.close();
            return account;
        } else {
            return null;
        }
    }

    @Override
    public void addAccount(Account account) {
        final String q = "insert into accounts values('"+account.getAccountNo()+"','"+account.getBankName()+"','"+account.getAccountHolderName()+"',"+account.getBalance()+")";
        db.execSQL(q);

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        final String q = "delete from accounts where account_no='"+accountNo+"'";
        db.execSQL(q);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        String q;
        if (expenseType == ExpenseType.EXPENSE){
             q = "update accounts set balance= balance - " +amount+" where account_no= '"+accountNo+"'" ;
        }else{
            q = "update accounts set balance= balance + " +amount+" where account_no= '"+accountNo+"'" ;

        }
        db.execSQL(q);

    }
}
