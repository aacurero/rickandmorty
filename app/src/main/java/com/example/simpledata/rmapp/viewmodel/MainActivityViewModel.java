package com.example.simpledata.rmapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpledata.rmapp.interfaces.Api;
import com.example.simpledata.rmapp.model.Characters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Characters>> charactersData;
    private final MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    public LiveData<ArrayList<Characters>> getCharacters(){
        if(charactersData == null){
            charactersData = new MutableLiveData<>();
            loadCharactersList();
        }
        return charactersData;
    }

    private void loadCharactersList() {
        setmLoading(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = api.getCharactersList();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                setmLoading(false);
                if(response.body() != null) {
                    ArrayList<Characters> charData = readData(response.body());
                    charactersData.setValue(charData);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                setmLoading(false);
                Log.e("servicio character list", t.getMessage());
            }
        });
    }

    private ArrayList<Characters> readData(ResponseBody response) {
        ArrayList<Characters> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONObject(response.string())
                    .getJSONArray("results");
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Characters characters = new Characters();
                characters.setId(object.getString("id"));
                characters.setName(object.getString("name"));
                characters.setImage(object.getString("image"));
                result.add(characters);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public MutableLiveData<Boolean> getmLoading() {
        return mLoading;
    }

    public void setmLoading(boolean mLoading) {
        this.mLoading.setValue(mLoading);
    }
}
