package com.example.dailyexpensemanager.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.dailyexpensemanager.R;
import com.example.dailyexpensemanager.adapters.AccountsAdapter;
import com.example.dailyexpensemanager.adapters.CategoryAdapter;
import com.example.dailyexpensemanager.databinding.FragmentAddTransactionBinding;
import com.example.dailyexpensemanager.databinding.ListDialogLayoutBinding;
import com.example.dailyexpensemanager.models.AccountsModel;
import com.example.dailyexpensemanager.models.CategoryModel;
import com.example.dailyexpensemanager.models.TransactionModel;
import com.example.dailyexpensemanager.utils.Constants;
import com.example.dailyexpensemanager.utils.Helper;
import com.example.dailyexpensemanager.viewModel.MainViewModel;
import com.example.dailyexpensemanager.views.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransactionFragment extends BottomSheetDialogFragment {
    public AddTransactionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransactionBinding binding;
    TransactionModel transactionModel;
    int selectedTab = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater);
        transactionModel = new TransactionModel();

        binding.incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeButton.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expenseButton.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseButton.setTextColor(getContext().getColor(R.color.default_textcolor));
                binding.incomeButton.setTextColor(getContext().getColor(R.color.incomeButton_textColor));
                transactionModel.setTransactionType(Constants.INCOME);
            }
        });

        binding.expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeButton.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseButton.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.expenseButton.setTextColor(getContext().getColor(R.color.expenseButton_textColor));
                binding.incomeButton.setTextColor(getContext().getColor(R.color.default_textcolor));
                transactionModel.setTransactionType(Constants.EXPENSE);
            }
        });

        binding.selectDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.YEAR, datePicker.getYear());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,yyyy");
                        String dateToShow = Helper.formatDate(calendar.getTime());
                        binding.selectDateEditText.setText(dateToShow);
                        transactionModel.setDate(calendar.getTime());
                        transactionModel.setId(calendar.getTime().getTime());
                    }
                });
                datePickerDialog.show();
            }
        });

        binding.selectCategoryEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogLayoutBinding listDialogLayoutBinding = ListDialogLayoutBinding.inflate(inflater);
                AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
                categoryDialog.setView(listDialogLayoutBinding.getRoot());

                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categoryModelArrayList, new CategoryAdapter.CategoryClickListener() {
                    @Override
                    public void onCategoryClicked(CategoryModel categoryModel) {
                        binding.selectCategoryEditText.setText(categoryModel.getCategoryName());
                        transactionModel.setTransactionCategory(categoryModel.getCategoryName());
                        categoryDialog.dismiss();
                    }
                });
                listDialogLayoutBinding.recyclerView.setAdapter(categoryAdapter);
                listDialogLayoutBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

                categoryDialog.show();
            }
        });

        binding.selectAccountEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogLayoutBinding dialogLayoutBinding = ListDialogLayoutBinding.inflate(inflater);
                AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
                accountsDialog.setView(dialogLayoutBinding.getRoot());

                ArrayList<AccountsModel> accountsModelArrayList = new ArrayList<>();
                accountsModelArrayList.add(new AccountsModel(0, "Cash"));
                accountsModelArrayList.add(new AccountsModel(1, "Bank"));
                accountsModelArrayList.add(new AccountsModel(2, "Card"));
                accountsModelArrayList.add(new AccountsModel(3, "Other"));

                AccountsAdapter accountsAdapter = new AccountsAdapter(getContext(), accountsModelArrayList, new AccountsAdapter.AccountsClickListener() {
                    @Override
                    public void onAccountSelected(AccountsModel accountsModel) {
                        binding.selectAccountEditText.setText(accountsModel.getAccountName());
                        transactionModel.setTransactionAccount(accountsModel.getAccountName());
                        accountsDialog.dismiss();
                    }
                });
                dialogLayoutBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                dialogLayoutBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                dialogLayoutBinding.recyclerView.setAdapter(accountsAdapter);
                accountsDialog.show();
            }
        });

        binding.saveTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount =
                        Double.parseDouble(binding.selectAmountEditText.getText().toString());
                String note =
                        binding.noteEditText.getText().toString();

                if (transactionModel.getTransactionType().equals(Constants.EXPENSE)) {
                    transactionModel.setTransactionAmount(amount*-1);
                } else {
                    transactionModel.setTransactionAmount(amount);
                }
                transactionModel.setTransactionNote(note);
                ((MainActivity)getActivity()).mainViewModel.addTransactions(transactionModel);
                ((MainActivity)getActivity()).getTransactions();
                dismiss();
            }
        });

        return binding.getRoot();
    }
}