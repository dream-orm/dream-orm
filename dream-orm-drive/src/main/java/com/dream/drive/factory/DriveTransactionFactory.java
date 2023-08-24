package com.dream.drive.factory;

import com.dream.drive.transaction.DriveTransaction;
import com.dream.system.transaction.Transaction;
import com.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;

public class DriveTransactionFactory implements TransactionFactory {
    @Override
    public Transaction getTransaction(DataSource dataSource) {
        return new DriveTransaction(dataSource);
    }
}
