package com.diasonti.descriptiontinder.utils;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionUtils {

    public static void afterTransaction(Runnable method) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
            public void afterCommit(){
                method.run();
            }
        });
    }

}
