package com.example.emanager.views.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.emanager.adapters.TransactionsAdapter;
import com.example.emanager.models.Transaction;
import com.example.emanager.utils.Constants;
import com.example.emanager.utils.Helper;
import com.example.emanager.viewmodels.MainViewModel;
import com.example.emanager.views.fragments.AddTransactionFragment;
import com.example.emanager.R;
import com.example.emanager.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Calendar calendar;
    // 0 = Daily
    // 1 = Monthly
    // 2 = Calendar
    // 3 = Summary
    // 4 = Notes

    public MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

        calendar = Calendar.getInstance();
        updateDate();

        binding.nextDate.setOnClickListener(c -> {
            if(Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, 1);
            }else if(Constants.SELECTED_TAB == Constants.MONTHLY){
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });

        binding.previousDate.setOnClickListener(c -> {
            if(Constants.SELECTED_TAB == Constants.DAILY) {
                calendar.add(Calendar.DATE, -1);
            }else if(Constants.SELECTED_TAB == Constants.MONTHLY){
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.floatingActionButton.setOnClickListener(c -> {
            new AddTransactionFragment().show(getSupportFragmentManager(), null);
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                if(tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB = 1;
                    updateDate();
                } else if (tab.getText().equals("Daily")){
                    Constants.SELECTED_TAB = 0;
                    updateDate();
                } else if (tab.getText().equals("Calendar")){
                    Constants.SELECTED_TAB = 2;
                    updateDate();
                } else if (tab.getText().equals("Summary")){
                    Constants.SELECTED_TAB = 3;
                    updateDate();
                } else if (tab.getText().equals("Notes")){
                    Constants.SELECTED_TAB = 4;
                    updateDate();
                }
            }
        });

        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));

        viewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>(){
            @Override
            public void onChanged(RealmResults<Transaction> transactions){
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(this, transactions);
                binding.transactionsList.setAdapter(transactionsAdapter);
                if(transactions.size() > 0){
                    binding.emptyState.setVisibility(View.GONE);
                }else{
                    binding.emptyState.setVisibility(View.VISIBLE);
                }

            }
        });

        viewModel.totalIncome.observe(this, new Observer<Double>(){
            @Override
            public void onChanged(Double aDouble){
                binding.incomeLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalExpense.observe(this, new Observer<Double>(){
            @Override
            public void onChanged(Double aDouble){
                binding.expenseLbl.setText(String.valueOf(aDouble));

            }
        });

        viewModel.totalAmount.observe(this, new Observer<Double>(){
            @Override
            public void onChanged(Double aDouble){
                binding.totalLbl.setText(String.valueOf(aDouble));

            }
        });


        viewModel.getTransactions(calendar);


    }

    public void getTransactions(){
        viewModel.getTransactions(calendar) ;
    }

    void updateDate(){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        if(Constants.SELECTED_TAB == Constants.DAILY) {
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        }else if(Constants.SELECTED_TAB == Constants.MONTHLY){
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransactions(calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
