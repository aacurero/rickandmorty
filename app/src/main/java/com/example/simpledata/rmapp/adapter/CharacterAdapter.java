package com.example.simpledata.rmapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simpledata.rmapp.R;
import com.example.simpledata.rmapp.model.Characters;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Characters> mData;
    protected MutableLiveData<Characters> itemSelected = new MutableLiveData<>();

    public CharacterAdapter(Context mContext, ArrayList<Characters> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_characters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        Characters character = mData.get(position);
        holder.tvCharName.setText(character.getName());
        Glide.with(mContext)
                .load(character.getImage())
                .into(holder.ivCharPic);
        holder.cvMain.setOnClickListener(v -> {
            itemSelected.setValue(character);
        });
    }

    public MutableLiveData<Characters> getSelected() {
        return itemSelected;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cvMain;
        private final ImageView ivCharPic;
        private final TextView tvCharName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvMain = itemView.findViewById(R.id.cvCharacterMain);
            ivCharPic = itemView.findViewById(R.id.ivCharacterPic);
            tvCharName = itemView.findViewById(R.id.tvCharacterName);
        }
    }
}
