package com.example.dailyexpensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyexpensemanager.R;
import com.example.dailyexpensemanager.databinding.AccountsSampleLayoutBinding;
import com.example.dailyexpensemanager.models.AccountsModel;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsAdapterViewHolder> {
    Context context;
    ArrayList<AccountsModel> accountsModelArrayList;
    public interface AccountsClickListener {
        void onAccountSelected(AccountsModel accountsModel);
    }

    AccountsClickListener accountsClickListener;

    public AccountsAdapter(Context context, ArrayList<AccountsModel> accountsModelArrayList, AccountsClickListener accountsClickListener) {
        this.context = context;
        this.accountsModelArrayList = accountsModelArrayList;
        this.accountsClickListener = accountsClickListener;
    }

    @NonNull
    @Override
    public AccountsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountsAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.accounts_sample_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsAdapterViewHolder holder, int position) {
        AccountsModel accountsModel = accountsModelArrayList.get(position);
        holder.binding.accountNameTextView.setText(accountsModel.getAccountName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountsClickListener.onAccountSelected(accountsModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountsModelArrayList.size();
    }

    public class AccountsAdapterViewHolder extends RecyclerView.ViewHolder {
        AccountsSampleLayoutBinding binding;
        public AccountsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AccountsSampleLayoutBinding.bind(itemView);
        }
    }
}
