package com.example.donatetoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.donatetoy.authView.MainActivity;
import com.example.donatetoy.databinding.ActivityHomePageBinding;
import com.example.donatetoy.toastMessage.ToastMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class HomePageActivity extends AppCompatActivity {
    private ActivityHomePageBinding design;
    private ImageView imageView, imageButton;
    private Uri imageUri;
    private TextView textViewMail;
    private Button buttonLogout;
    private Switch nightMode;
    private HomePageViewModel viewModel;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        design = DataBindingUtil.setContentView(this,R.layout.activity_home_page);
        viewModel = new ViewModelProvider(this).get(HomePageViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavigationUI.setupWithNavController(design.navigationView,navHostFragment.getNavController());
        NavigationUI.setupWithNavController(design.bottomNav,navHostFragment.getNavController());

        design.toolbar.setTitle(R.string.app_name);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, design.drawer, design.toolbar, 0,0);
        design.drawer.addDrawerListener(toggle);
        toggle.syncState();


        /***********************************************************************/

        View title = design.navigationView.inflateHeaderView(R.layout.nav_menu_title);

        textViewMail =title.findViewById(R.id.textViewMail);
        imageView = title.findViewById(R.id.imageView3);
        imageButton = title.findViewById(R.id.imageButton);
        buttonLogout = design.navigationView.findViewById(R.id.buttonLogOut);
        nightMode = design.navigationView.findViewById(R.id.switchNightMode);

        /*****************************************************************/

        viewModel.setImage(getApplicationContext(),imageView);
        viewModel.setTextEmail(textViewMail);


        buttonLogout.setOnClickListener(new View.OnClickListener() {// sistemden çıkış işlemi yapıyor
            @Override
            public void onClick(View view) {
                viewModel.clickedLogOut(view.getContext());
                startActivity(new Intent(HomePageActivity.this, MainActivity.class));
                finish();
            }
        });

        nightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() { // kullanıcıdan izin alınarak galeriye erişip foto alıyor
            @Override
            public void onClick(View view) {

                    // izin alınmamışsa request code 1 ile onRequestPermissionsResult ye gidiyor
               if(ContextCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(HomePageActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
                else{// izin alınmışsa foto alıyor request code 2 ile onActivityResult a gidiyor
                    Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photo,2);

                }
            }
        });


    }



    @Override
    public void onBackPressed() {// drawer açık kapalı kontrolü
        if(design.drawer.isDrawerOpen(GravityCompat.START)){
            design.drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photo,2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {// kullanıcıdan resim alındığında buraya geliyor
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode==RESULT_OK && data!=null){
            //resmi imageviewe yazdırma
            imageUri = data.getData();
                Glide.with(HomePageActivity.this).load(imageUri).into(imageView);
                viewModel.saveImage(getApplicationContext(),imageUri);
        }
    }
}