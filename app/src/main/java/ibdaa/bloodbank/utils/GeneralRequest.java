package ibdaa.bloodbank.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import ibdaa.bloodbank.adapter.EmptySpinnerAdapter;
import ibdaa.bloodbank.data.model.generalResponse.GeneralResponse;
import ibdaa.bloodbank.data.model.login.Login;
import ibdaa.bloodbank.view.activity.EmptyHomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ibdaa.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static ibdaa.bloodbank.utils.InternetState.isConnected;

public class GeneralRequest {

    static String USER_PASSWORD;
    static String REMEMBER;

    public static void getSpinnerData(Activity activity, Spinner spinner, EmptySpinnerAdapter adapter,
                                      Call<GeneralResponse> call,String hint, int selectedId){
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if(response.body().getStatus() == 1){
                        spinner.setVisibility(View.VISIBLE);
                        adapter.setData(response.body().getData(),hint);
                        spinner.setAdapter(adapter);

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });

    }

    public static void getSpinnerData(Activity activity,Spinner spinner , EmptySpinnerAdapter adapter,
                                      Call<GeneralResponse> call,String hint,int selectedId, Spinner spinner2, EmptySpinnerAdapter adapter2,
                                      Call<GeneralResponse> call2, String hint2,int selectedId2){
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.body().getStatus() == 1) {
                    adapter.setData(response.body().getData(),hint);
                    spinner.setAdapter(adapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != 0) {
                                getSpinnerData(activity, spinner2,adapter2, call2, hint2,selectedId2);
                            }else {
                                spinner2.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    /*public static void getSpinnerData(Call<GeneralResponse> call, Spinner spinner, EmptySpinnerAdapter adapter,
                                      Integer selectedId, String hint, AdapterView.OnItemSelectedListener listener) {
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try{
                    adapter.setData(response.body().getData(),hint);
                    spinner.setAdapter(adapter);
                    for (int i = 0; i< adapter.generalResponseDataList.size() ; i++) {

                        if(adapter.generalResponseDataList.get(i).getId() == selectedId)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }

                    spinner.setOnItemSelectedListener(listener);
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    public static void getSpinnerData(Call<GeneralResponse> call, Spinner spinner, final EmptySpinnerAdapter adapter,
                                      Integer selectedId,String hint) {
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try{
                    adapter.setData(response.body().getData(),hint);
                    spinner.setAdapter(adapter);
                    for (int i = 0; i< adapter.generalResponseDataList.size() ; i++) {

                        if(adapter.generalResponseDataList.get(i).getId() == selectedId)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }*/

    public static void userData(Activity activity, Call<Login> call, String password, boolean remember, boolean auth)
    {
        if (isConnected(activity)) {
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if(response.body().getStatus() == 1){
                        SaveData(activity, USER_PASSWORD, password);

                        if(auth){
                            SaveData(activity, REMEMBER, remember);
                            Intent i = new Intent(activity, EmptyHomeActivity.class);
                            activity.startActivity(i);
                            activity.finish();
                        }
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                }
            });
        }

    }

    public static void getNewSpinnerData(Activity activity, Spinner spinner, EmptySpinnerAdapter adapter,
                                      Call<GeneralResponse> call,String hint, int selectedId){
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if(response.body().getStatus() == 1){
                        spinner.setVisibility(View.VISIBLE);
                        adapter.setData(response.body().getData(),hint);
                        spinner.setAdapter(adapter);
                        for (int i = 0; i < adapter.generalResponseDataList.size(); i++) {
                            if (selectedId == adapter.generalResponseDataList.get(i).getId()) {
                                spinner.setSelection(i);
                                break;
                            }
                        }

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
            }
        });
    }

    public static void getNewSpinnerData(Activity activity, Spinner spinner, EmptySpinnerAdapter adapter,
                                         Call<GeneralResponse> call, String hint, int selectedId, AdapterView.OnItemSelectedListener listener){
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if(response.body().getStatus() == 1){
                        spinner.setVisibility(View.VISIBLE);
                        adapter.setData(response.body().getData(),hint);
                        spinner.setAdapter(adapter);
                        for (int i = 0; i < adapter.generalResponseDataList.size(); i++) {
                            if (selectedId == adapter.generalResponseDataList.get(i).getId()) {
                                spinner.setSelection(i);
                                break;
                            }
                        }

                        spinner.setOnItemSelectedListener(listener);

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
            }
        });
    }
}