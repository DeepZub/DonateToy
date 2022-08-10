package com.example.donatetoy;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.donatetoy.toastMessage.ToastMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class HomePageViewModel extends ViewModel {
    private FirebaseAuth myAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage storage;
    private StorageReference reference;
    private DatabaseReference mDatabase;
    private ToastMessage toast;
    private SaveUserInfo saveUserInfo;

    private final int exit = R.string.exitToast;

    public HomePageViewModel(){
        this.myAuth = FirebaseAuth.getInstance();
        this.firebaseUser = myAuth.getInstance().getCurrentUser();
        this.storage = FirebaseStorage.getInstance();
        this.reference = storage.getReference();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.toast = new ToastMessage();

    }

    public void clickedLogOut(Context context){
        saveUserInfo = new SaveUserInfo(context);
        saveUserInfo.remove();
        toast.showToast(context,2,context.getResources().getString(exit));
        myAuth.signOut();
    }

    public void setImage (Context context, ImageView imageView) {

            StorageReference ref = reference.child("usersPhoto/" + firebaseUser.getUid());  //kayıtlı olan kullanıcının resmini çekiyor!
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(imageView);
                }
            });


    }

    public void setTextEmail(TextView textView){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name =snapshot.child("userProfile").child(firebaseUser.getUid()).child("name").getValue().toString();
                String surname =snapshot.child("userProfile").child(firebaseUser.getUid()).child("surname").getValue().toString();

                textView.setText(name + " " + surname);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void saveImage (Context context,Uri uri){
        StorageReference ref = reference.child("usersPhoto/"+ firebaseUser.getUid());
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                toast.showToast(context, 4,context.getResources().getString(R.string.successful));
            }
        });
    }
}
