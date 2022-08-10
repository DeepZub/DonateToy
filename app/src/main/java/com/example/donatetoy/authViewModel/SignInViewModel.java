package com.example.donatetoy.authViewModel;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.donatetoy.R;
import com.example.donatetoy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInViewModel extends BaseObservable {

    private Users user;
    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    private int toastMessage[] = {0,0};

    @Bindable
    public int[] getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(int toastMessage, int type) {
        this.toastMessage[0] = toastMessage;
        this.toastMessage[1] = type;
        notifyPropertyChanged(BR.toastMessage);
    }


    public void setUserEmail(String email) {
        user.setEmail(email);
        notifyPropertyChanged(BR.userEmail);
    }

    @Bindable
    public String getUserEmail() {
        return user.getEmail();
    }

    @Bindable
    public String getUserPassword() {
        return user.getPassword();
    }

    public void setUserPassword(String password) {
        user.setPassword(password);
        notifyPropertyChanged(BR.userPassword);
    }

    public void setName(String name) {
        user.setName(name);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getName() {
        return user.getName();
    }

    public void setSurname(String surname) {
        user.setSurname(surname);
        notifyPropertyChanged(BR.surname);
    }

    @Bindable
    public String getSurname() {
        return user.getSurname();
    }

    public SignInViewModel() {
        user = new Users("","","","");
        this.myAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void onSigninClicked() {
        if (isInputDataEmpty()) {
            setToastMessage(R.string.requiredFieldsMessage,1);
        } else {
            if ((user.emailGecerliMi()) && user.sifreGecerliMi()) {
                register(getUserEmail(),getUserPassword());
            } else {
                if(!user.sifreGecerliMi()){
                    setToastMessage(R.string.invalidPassword,1);
                }if(!user.emailGecerliMi()){
                    setToastMessage(R.string.invalidMail,1);
                }if(!user.emailGecerliMi() && !user.sifreGecerliMi()){
                    setToastMessage(R.string.invalidMessage,1);
                }
            }
        }
    }


    public boolean isInputDataEmpty() {
        return TextUtils.isEmpty(getUserEmail()) || TextUtils.isEmpty(getUserPassword());
    }

    public void register(String email, String password){
        myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mDatabase.child("userProfile").child(myAuth.getCurrentUser().getUid()).setValue(user);
                    mDatabase.child("userProfile").child(myAuth.getCurrentUser().getUid()).child("uid").setValue(myAuth.getCurrentUser().getUid());

                    setToastMessage(R.string.registerSuccess,4);
                }
                else{

                    setToastMessage(R.string.usedMail,1);
                }
            }
        });


    }

}
