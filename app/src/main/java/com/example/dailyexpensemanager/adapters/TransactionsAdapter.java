package com.example.dailyexpensemanager.adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyexpensemanager.R;
import com.example.dailyexpensemanager.databinding.RowTransactionsBinding;
import com.example.dailyexpensemanager.databinding.TransactionDetailsCustomDialogLayoutBinding;
import com.example.dailyexpensemanager.models.CategoryModel;
import com.example.dailyexpensemanager.models.TransactionModel;
import com.example.dailyexpensemanager.utils.Constants;
import com.example.dailyexpensemanager.utils.Helper;
import com.example.dailyexpensemanager.views.activities.MainActivity;

import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionAdapterViewHolder> {
    Context context;
    RealmResults<TransactionModel> transactionModelArrayList;


    public TransactionsAdapter(Context context, RealmResults<TransactionModel> transactionModels) {
        this.context = context;
        this.transactionModelArrayList = transactionModels;
    }

    @NonNull
    @Override
    public TransactionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transactions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapterViewHolder holder, int position) {
        TransactionModel transactionModel = transactionModelArrayList.get(position);
        holder.binding.transactionAmountlTextView.setText(String.valueOf(transactionModel.getTransactionAmount()));
        holder.binding.transactionAccountTypeTextView.setText(transactionModel.getTransactionAccount());
        holder.binding.transactionDateTextView.setText(Helper.formatDate(transactionModel.getDate()));
        holder.binding.transactionCategoryTextView.setText(transactionModel.getTransactionCategory());
        holder.binding.transactionTitleTextView.setText(transactionModel.getTransactionTitle());

        CategoryModel transactionCategory = Constants.getCategoryDetails(transactionModel.getTransactionCategory());

        holder.binding.transactionCategoryImageView.setImageResource(transactionCategory.getCategoryImage());
        holder.binding.transactionCategoryImageView.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));
        holder.binding.transactionAccountTypeTextView.setBackgroundTintList(context.getColorStateList(Constants.getAccountColor(transactionModel.getTransactionAccount())));


        if (transactionModel.getTransactionType().equals(Constants.EXPENSE)) {
            holder.binding.transactionAmountlTextView.setTextColor(context.getColor(R.color.expenseButton_textColor));
        } else if (transactionModel.getTransactionType().equals(Constants.INCOME)){
            holder.binding.transactionAmountlTextView.setTextColor(context.getColor(R.color.incomeButton_textColor));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog deleteDialog =
                        new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Are you sure you want to DELETE this transaction?");

                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity)context).mainViewModel.deleteTransaction(transactionModel);
                    }
                });

                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDialog.dismiss();                    }
                });
                deleteDialog.show();
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                TransactionDetailsCustomDialogLayoutBinding binding = TransactionDetailsCustomDialogLayoutBinding.inflate(LayoutInflater.from(context));
                dialog.setContentView(binding.getRoot());

                //Setting values to the respective textViews
                binding.dialogTransactionTitle.setText("" + transactionModel.getTransactionTitle());
                binding.dialogTransactionAmount.setText("" + transactionModel.getTransactionAmount());
                binding.dialogTransactionType.setText("" + transactionModel.getTransactionType());
                binding.dialogTransactionCategory.setText("" + transactionModel.getTransactionCategory());
                binding.dialogTransactionAccount.setText("" + transactionModel.getTransactionAccount());
                binding.dialogTransactionDate.setText("" + transactionModel.getDate());
                binding.dialogTransactionNote.setText("" + transactionModel.getTransactionNote());

                Button closeButton = binding.dialogCloseButton;
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionModelArrayList.size();
    }

    public class TransactionAdapterViewHolder extends RecyclerView.ViewHolder {
        RowTransactionsBinding binding;
        public TransactionAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionsBinding.bind(itemView);
        }
    }
}