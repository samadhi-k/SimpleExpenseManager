package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentExpenseManager extends  ExpenseManager{

        DatabaseHelper dbHelper;
        MainActivity ma;

        public PersistentExpenseManager(MainActivity ma) throws ExpenseManagerException {
                this.ma = ma;
                setup();
        }

        @Override
        public void setup()  {

                this.dbHelper = new DatabaseHelper(ma);

                TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(dbHelper);
                setTransactionsDAO(persistentTransactionDAO);

                AccountDAO persistentAccountDAO = new PersistentAccountDAO(dbHelper);
                setAccountsDAO(persistentAccountDAO);



        }
}
