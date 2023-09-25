package com.example.dailyexpensemanager.utils;

import com.example.dailyexpensemanager.R;
import com.example.dailyexpensemanager.models.CategoryModel;

import java.util.ArrayList;

public class Constants {
    public static String INCOME = "Income";
    public static String EXPENSE = "Expense";
//    public static String CASH = "Cash";
//    public static String CARD = "Card";
//    public static String BANK = "Bank";
//    public static String OTHER = "Other";

    public static int SELECTED_TAB = 0;
    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY  = 3;
    public static int NOTES = 4;

    public static ArrayList<CategoryModel> categoryModelArrayList;
    public static void setCategories() {
        categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Salary", R.drawable.salary, R.color.category_salary));
        categoryModelArrayList.add(new CategoryModel("Business", R.drawable.business, R.color.category_business));
        categoryModelArrayList.add(new CategoryModel("Investment", R.drawable.investment, R.color.category_investment));
        categoryModelArrayList.add(new CategoryModel("Rent", R.drawable.rent, R.color.category_rent));
        categoryModelArrayList.add(new CategoryModel("Loan", R.drawable.loan, R.color.category_loan));
        categoryModelArrayList.add(new CategoryModel("Other", R.drawable.other, R.color.category_other));
    }

    public static CategoryModel getCategoryDetails(String categoryName) {
        for (CategoryModel categoryModel : categoryModelArrayList) {
            if (categoryModel.getCategoryName().equals(categoryName)) {
                return categoryModel;
            }
        }

        return null;
    }

    public static int getAccountColor(String accountName) {
        switch (accountName) {
            case "Bank":
                return R.color.bank_color;

            case "Cash":
                return R.color.cash_color;

            case "Card":
                return R.color.card_color;

            default:
                return R.color.other_color;
        }
    }
}