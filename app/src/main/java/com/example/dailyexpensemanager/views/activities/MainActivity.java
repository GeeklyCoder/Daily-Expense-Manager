package com.example.dailyexpensemanager.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.dailyexpensemanager.adapters.TransactionsAdapter;
import com.example.dailyexpensemanager.models.TransactionModel;
import com.example.dailyexpensemanager.utils.Constants;
import com.example.dailyexpensemanager.utils.Helper;
import com.example.dailyexpensemanager.viewModel.MainViewModel;
import com.example.dailyexpensemanager.views.fragments.AddTransactionFragment;
import com.example.dailyexpensemanager.R;
import com.example.dailyexpensemanager.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Calendar monthlyCalendar;
    Calendar dailyCalendar;
    ActivityMainBinding binding;
    public MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);
        getSupportActionBar().setTitle("Transactions");

        Constants.setCategories();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //Initializing variables
        monthlyCalendar = Calendar.getInstance();
        dailyCalendar = Calendar.getInstance();
        updateDate();

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddTransactionFragment().show(getSupportFragmentManager(), null);
            }
        });

        binding.nextDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.SELECTED_TAB == Constants.DAILY) {
                    dailyCalendar.add(Calendar.DATE, 1);
                } else if (Constants.SELECTED_TAB == Constants.MONTHLY){
                    monthlyCalendar.add(Calendar.MONTH, 1);
                }
                updateDate();
            }
        });

        binding.previousDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.SELECTED_TAB == Constants.DAILY) {
                    dailyCalendar.add(Calendar.DATE, -1);
                } else if (Constants.SELECTED_TAB == Constants.MONTHLY){
                    monthlyCalendar.add(Calendar.MONTH, -1);
                }
                updateDate();
            }
        });

        binding.mainTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String selectedTabName = tab.getText().toString();
                switch (selectedTabName) {
                    case "Monthly" :
                        Constants.SELECTED_TAB = Constants.MONTHLY;
                        dailyCalendar = Calendar.getInstance();
                        updateDate();
                        Toast.makeText(MainActivity.this, "Month", Toast.LENGTH_SHORT).show();
                        break;

                    case "Daily":
                        Constants.SELECTED_TAB = Constants.DAILY;
                        monthlyCalendar = Calendar.getInstance();
                        updateDate();
                        Toast.makeText(MainActivity.this, "Day", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mainViewModel.realmResultsMutableLiveData.observe(this, new Observer<RealmResults<TransactionModel>>() {
            @Override
            public void onChanged(RealmResults<TransactionModel> transactionModels) {
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this, transactionModels);
                binding.transactionsRecyclerView.setAdapter(transactionsAdapter);
                if (transactionModels.size() > 0) {
                    binding.emptyStateLinearLayout.setVisibility(View.GONE);
                } else {
                    binding.emptyStateLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        mainViewModel.realmResultsMutableLiveDataTotalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeTextView.setText(String.valueOf(aDouble));
            }
        });

        mainViewModel.realmResultsMutableLiveDataTotalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseTextView.setText(String.valueOf(aDouble));
            }
        });

        mainViewModel.realmResultsMutableLiveDataTotalRemaining.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.remainingAmountTextView.setText(String.valueOf(aDouble));
            }
        });
        mainViewModel.getTransactions(monthlyCalendar);
    }

    public void getTransactions() {
        mainViewModel.getTransactions(monthlyCalendar);
    }

    void updateDate() {
        if (Constants.SELECTED_TAB == Constants.DAILY) {
            binding.currentDateTextView.setText(Helper.formatDate(dailyCalendar.getTime()));
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY){
            binding.currentDateTextView.setText(Helper.formatDateByMonth(monthlyCalendar.getTime()));
        }
        mainViewModel.getTransactions(getCurrentCalendar());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    Calendar getCurrentCalendar() {
        if (Constants.SELECTED_TAB == Constants.DAILY) {
            return dailyCalendar;
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY) {
            return monthlyCalendar;
        }
        return null;
    }
}