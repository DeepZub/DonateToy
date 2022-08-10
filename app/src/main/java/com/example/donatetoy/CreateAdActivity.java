package com.example.donatetoy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.donatetoy.databinding.ActivityCreateAdBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateAdActivity extends AppCompatActivity {
    private ActivityCreateAdBinding design;
    private Uri uri;
    private FirebaseAuth myAuth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference();
    FirebaseUser user = myAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;
    private RadioButton selectedRadioButton;
    private String selectedSex;
    private int boyID=1;
    private int girlID=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        design = DataBindingUtil.setContentView(this,R.layout.activity_create_ad);

        design.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(CreateAdActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CreateAdActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
                else{// izin alınmışsa foto alıyor request code 2 ile onActivityResult a gidiyor
                    Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photo,2);

                }

            }
        });

        design.buttonApply.setOnClickListener(view -> {
            int selectedRadioButtonId = design.radioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1 && uri != null) {
                selectedRadioButton = findViewById(selectedRadioButtonId);
                selectedSex = selectedRadioButton.getText().toString();

                if(selectedSex.equals("Boy")){
                    StorageReference ref = reference.child("advert/"+selectedSex+"/"+ user.getUid()+boyID);
                    ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CreateAdActivity.this,"Success!",Toast.LENGTH_SHORT).show();

                        }
                    });
                    boyID++;
                }else{
                    StorageReference ref = reference.child("advert/"+selectedSex+"/"+ user.getUid()+girlID);
                    ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CreateAdActivity.this,"Success!",Toast.LENGTH_SHORT).show();

                        }
                    });
                    girlID++;

                }


            } else {
                Toast.makeText(CreateAdActivity.this,R.string.requiredFieldsMessage,Toast.LENGTH_SHORT).show();
            }

        });


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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {// kullanıcıdan resim alındığında buraya geliyor
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode==RESULT_OK && data!=null){
            //resmi imageviewe yazdırma
            uri = data.getData();
            Glide.with(CreateAdActivity.this).load(uri).into(design.imageView);




        }
    }



}