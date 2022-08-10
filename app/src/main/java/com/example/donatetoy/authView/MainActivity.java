package com.example.donatetoy.authView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.donatetoy.HomePageActivity;
import com.example.donatetoy.R;
import com.example.donatetoy.SaveUserInfo;
import com.example.donatetoy.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding design;
    private SaveUserInfo saveUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        design = DataBindingUtil.setContentView(this, R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);


            ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo =cm.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnectedOrConnecting()) {

                saveUserInfo = new SaveUserInfo(this);
                saveUserInfo.get();
                if(!saveUserInfo.getEmail().equals("") && !saveUserInfo.getPassword().equals("")){
                    startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                    finish();
                }

            }
        else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.error);
                builder.setIcon(R.drawable.error);
                builder.setMessage(R.string.checkConnection);
                builder.setCancelable(true);

                builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.create().show();

            }


    }


}