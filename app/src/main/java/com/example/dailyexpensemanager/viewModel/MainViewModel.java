package com.example.dailyexpensemanager.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dailyexpensemanager.models.TransactionModel;
import com.example.dailyexpensemanager.utils.Constants;
import com.example.dailyexpensemanager.views.activities.MainActivity;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
    public MutableLiveData<RealmResults<TransactionModel>> realmResultsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Double> realmResultsMutableLiveDataTotalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> realmResultsMutableLiveDataTotalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> realmResultsMutableLiveDataTotalRemaining = new MutableLiveData<>();
    Realm realm;
    Calendar calendar;
    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        initializeDatabase();
    }

    public void getTransactions(Calendar calendar) {
        this.calendar = calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        double totalIncome = 0;
        double totalExpense = 0;
        double totalRemaining = 0;

        RealmResults<TransactionModel> newTransactionsRealmResults = null;

        if (Constants.SELECTED_TAB == Constants.DAILY) {
            newTransactionsRealmResults = realm.where(TransactionModel.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24*60*60*1000)))
                    .findAll();

            totalIncome =
                    realm.where(TransactionModel.class)
                            .greaterThanOrEqualTo("date", calendar.getTime())
                            .lessThan("date", new Date(calendar.getTime().getTime() + (24*60*60*1000)))
                            .equalTo("transactionType",
                                    Constants.INCOME)
                            .sum("transactionAmount")
                            .doubleValue();

            totalExpense =
                    realm.where(TransactionModel.class)
                            .greaterThanOrEqualTo("date", calendar.getTime())
                            .lessThan("date", new Date(calendar.getTime().getTime() + (24*60*60*1000)))
                            .equalTo("transactionType",
                                    Constants.EXPENSE)
                            .sum("transactionAmount")
                            .doubleValue();

            totalRemaining = realm.where(TransactionModel.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24*60*60*1000)))
                    .sum("transactionAmount")
                    .doubleValue();
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endTime = calendar.getTime();

            newTransactionsRealmResults = realm.where(TransactionModel.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .findAll();

            totalIncome =
                    realm.where(TransactionModel.class)
                            .greaterThanOrEqualTo("date", calendar.getTime())
                            .lessThan("date", new Date(calendar.getTime().getTime() + (24*60*60*1000)))
                            .equalTo("transactionType",
                                    Constants.INCOME)
                            .sum("transactionAmount")
                            .doubleValue();

            totalExpense =
                    realm.where(TransactionModel.class)
                            .greaterThanOrEqualTo("date", calendar.getTime())
                            .lessThan("date", new Date(calendar.getTime().getTime() + (24*60*60*1000)))
                            .equalTo("transactionType",
                                    Constants.EXPENSE)
                            .sum("transactionAmount")
                            .doubleValue();

            totalRemaining = realm.where(TransactionModel.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24*60*60*1000)))
                    .sum("transactionAmount")
                    .doubleValue();
        }

        realmResultsMutableLiveDataTotalIncome.setValue(totalIncome);
        realmResultsMutableLiveDataTotalExpense.setValue(totalExpense);
        realmResultsMutableLiveDataTotalRemaining.setValue(totalRemaining);
        realmResultsMutableLiveData.setValue(newTransactionsRealmResults);
    }

    void initializeDatabase() {
        realm = Realm.getDefaultInstance();
    }

    public void addTransactions(TransactionModel transactionModel) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transactionModel);
        realm.commitTransaction();
    }

    public void deleteTransaction(TransactionModel transactionModel) {
        realm.beginTransaction();
        transactionModel.deleteFromRealm();
        realm.commitTransaction();
        getTransactions(calendar);
    }
}