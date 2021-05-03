package com.example.simpledata.rmapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpledata.rmapp.R;
import com.example.simpledata.rmapp.interfaces.Api;
import com.example.simpledata.rmapp.model.Characters;
import com.example.simpledata.rmapp.util.Utils;
import com.example.simpledata.rmapp.viewmodel.CharacterDetailViewModel;
import com.example.simpledata.rmapp.viewmodel.MainActivityViewModel;

public class CharacterDetailActivity extends AppCompatActivity {
    private String id, name;
    private TextView tvName, tvStatus, tvType, tvSpecies, tvGender, tvOrigin, tvLocation, tvUrl;
    private ImageView ivPicture;
    private CharacterDetailViewModel viewModel;
    private Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        if(getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString("id", "");
            name = getIntent().getExtras().getString("name", "");
        }
        initView();
        initToolbar();
    }

    private void initView() {
        progressDialog = Utils.createProgressDialog(this, getString(R.string.msg_loading));
        tvName = findViewById(R.id.tvName);
        tvStatus = findViewById(R.id.tvStatus);
        tvType = findViewById(R.id.tvType);
        tvSpecies = findViewById(R.id.tvSpecies);
        tvGender = findViewById(R.id.tvGender);
        tvOrigin = findViewById(R.id.tvOrigin);
        tvLocation = findViewById(R.id.tvLocation);
        tvUrl = findViewById(R.id.tvUrl);
        ivPicture = findViewById(R.id.ivCharDetailPic);
        viewModel = new ViewModelProvider(this).get(CharacterDetailViewModel.class);
        viewModel.getCharacterDetail(Api.BASE_URL + getString(R.string.character_url) + id)
                .observe(this, character -> {
                loadData(character);
        });
        viewModel.getmLoading().observe(this, isLoading ->{
            if(isLoading)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void loadData(Characters character) {
        tvName.setText(character.getName());
        tvStatus.setText(character.getStatus());
        tvType.setText(character.getType());
        tvSpecies.setText(character.getSpecies());
        tvGender.setText(character.getGender());
        tvOrigin.setText(character.getOrigin());
        tvLocation.setText(character.getLocation());
        tvUrl.setText(character.getUrl());
        viewModel.loadImage(this, character, ivPicture);
    }

    private void initToolbar(){
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
