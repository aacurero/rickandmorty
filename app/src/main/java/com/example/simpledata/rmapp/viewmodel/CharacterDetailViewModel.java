package com.example.simpledata.rmapp.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.simpledata.rmapp.interfaces.Api;
import com.example.simpledata.rmapp.model.Characters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterDetailViewModel extends ViewModel {
    private MutableLiveData<Characters> charcaterData;
    private String url;
    private final MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    public LiveData<Characters> getCharacterDetail(String url){
        this.url = url;
        if(charcaterData == null){
            charcaterData = new MutableLiveData<>();
            loadCharacterData();
        }
        return charcaterData;
    }

    private void loadCharacterData() {
        setmLoading(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = api.getCharacterDetail(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                setmLoading(false);
                if(response.body() != null) {
                    Characters character = readData(response.body());
                    charcaterData.setValue(character);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                setmLoading(false);
                Log.e("servicio character", t.getMessage());
            }
        });
    }

    private Characters readData(ResponseBody response) {
        Characters result = new Characters();
        try {
            JSONObject data = new JSONObject(response.string());
            result.setId(data.getString("id"));
            result.setName(data.getString("name"));
            result.setImage(data.getString("image"));
            result.setStatus(data.getString("status"));
            result.setSpecies(data.getString("species"));
            result.setType(data.getString("type"));
            result.setGender(data.getString("gender"));
            result.setOrigin(data.getJSONObject("origin").getString("name"));
            result.setLocation(data.getJSONObject("location").getString("name"));
            result.setUrl(data.getString("url"));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void loadImage(Context mContext, Characters character, ImageView imageView){
        Glide.with(mContext)
                .load(character.getImage())
                .into(imageView);
    }

    public MutableLiveData<Boolean> getmLoading() {
        return mLoading;
    }

    public void setmLoading(boolean mLoading) {
        this.mLoading.setValue(mLoading);
    }
}
