package com.example.simpledata.rmapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.simpledata.rmapp.R;
import com.example.simpledata.rmapp.adapter.CharacterAdapter;
import com.example.simpledata.rmapp.model.Characters;
import com.example.simpledata.rmapp.util.Utils;
import com.example.simpledata.rmapp.viewmodel.MainActivityViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CharacterAdapter mAdapter;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    private void initView() {
        progressDialog = Utils.createProgressDialog(this, getString(R.string.msg_loading));
        recyclerView = findViewById(R.id.rvCharacters);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void loadData() {
        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getCharacters().observe(this, characters -> {
            mAdapter = new CharacterAdapter(MainActivity.this, characters);
            mAdapter.getSelected().observe(this, character -> {
                goToDetail(character);
            });
            recyclerView.setAdapter(mAdapter);
        });
        viewModel.getmLoading().observe(this, isLoading -> {
            if(isLoading)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void goToDetail(Characters character) {
        Intent intent = new Intent(MainActivity.this,
                CharacterDetailActivity.class);
        intent.putExtra("id", character.getId());
        intent.putExtra("name", character.getName());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_logout) {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }
}