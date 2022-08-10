package com.example.donatetoy.authViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.Navigation;

import com.example.donatetoy.HomePageActivity;
import com.example.donatetoy.R;
import com.example.donatetoy.SaveUserInfo;
import com.example.donatetoy.authView.MainActivity;
import com.example.donatetoy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInViewModel extends BaseObservable {

    private Users user;
    private FirebaseAuth myAuth;
    private SaveUserInfo saveUserInfo;
    private Context context;

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


    public LogInViewModel(Context context) {
        this.context = context;
        user = new Users("","");
        this.myAuth = FirebaseAuth.getInstance();

    }

    public boolean onLoginClicked(View view) {

        if (isInputDataEmpty()) {
            setToastMessage(R.string.requiredFieldsMessage,1);
        } else {

            myAuth.signInWithEmailAndPassword(getUserEmail(), getUserPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveUserInfo = new SaveUserInfo(context);
                                saveUserInfo.save(getUserEmail(),getUserPassword());
                                setToastMessage(R.string.loginSucces,4);

                                Navigation.findNavController(view).navigate(new ActionOnlyNavDirections(R.id.action_logInFragment_to_homePage));

                            } else {
                                setToastMessage(R.string.incorrectFields,3);
                            }
                        }
                    });

        }
        return  true;
    }

    public boolean isInputDataEmpty() {
        return TextUtils.isEmpty(getUserEmail()) || TextUtils.isEmpty(getUserPassword());
    }



}