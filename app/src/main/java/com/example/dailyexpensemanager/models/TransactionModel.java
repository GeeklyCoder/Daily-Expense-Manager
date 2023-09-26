package com.example.dailyexpensemanager.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TransactionModel extends RealmObject {
    private String transactionType, transactionCategory, transactionAccount, transactionNote, transactionTitle;
    private Date date;
    private double transactionAmount;
    @PrimaryKey
    private long id;

    public TransactionModel(double transactionAmount, String transactionType, String transactionCategory, String transactionAccount, String transactionNote, Date date, long id, String transactionTitle) {
        this.transactionType = transactionType;
        this.transactionCategory = transactionCategory;
        this.transactionAccount = transactionAccount;
        this.transactionNote = transactionNote;
        this.date = date;
        this.transactionAmount = transactionAmount;
        this.transactionTitle = transactionTitle;
        this.id = id;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TransactionModel() {

    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionTitle() {
        return transactionTitle;
    }

    public void setTransactionTitle(String transactionTitle) {
        this.transactionTitle = transactionTitle;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionAccount() {
        return transactionAccount;
    }

    public void setTransactionAccount(String transactionAccount) {
        this.transactionAccount = transactionAccount;
    }

    public String getTransactionNote() {
        return transactionNote;
    }

    public void setTransactionNote(String transactionNote) {
        this.transactionNote = transactionNote;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}