package com.yash.gitpull.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.yash.gitpull.R;
import com.yash.gitpull.utils.RetrofitClient;
import com.yash.gitpull.model.ClosedPullRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
RecyclerView myPullLists;
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPullLists = findViewById(R.id.recyleview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myPullLists.setHasFixedSize(true);
        myPullLists.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading your data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if(isNetworkAvailable(this)){
            getClosedPullRequestList();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "No internet available", Toast.LENGTH_LONG).show();
        }

    }

    private void getClosedPullRequestList() {
        Call<ArrayList<ClosedPullRequest>> call = RetrofitClient.getInstance().getMyApi().getClosedRequest();
        call.enqueue(new Callback<ArrayList<ClosedPullRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<ClosedPullRequest>> call, Response<ArrayList<ClosedPullRequest>> response) {
                progressDialog.dismiss();
                myPullLists.setAdapter( new ClosedRequestAdapter(getApplicationContext(),response.body()));
            }
            @Override
            public void onFailure(Call<ArrayList<ClosedPullRequest>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Went wrong! Try Again.", Toast.LENGTH_LONG).show();
            }
        });
    }
    public static boolean isNetworkAvailable(Context context) {
        boolean outcome = false;

        if(context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            // For 3G check
            if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null &&
                    cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting()) {
                outcome = true;
            }
            // For WiFi Check
            if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null &&
                    cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()) {
                outcome = true;
            }

            // For ethernet check
            if(cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET) != null &&
                    cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).isConnectedOrConnecting()) {
                outcome = true;
            }

        }

        return outcome;
    }
}