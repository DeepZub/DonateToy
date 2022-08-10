package com.example.donatetoy.authViewModel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.Navigation;

import com.example.donatetoy.R;
import com.example.donatetoy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordViewModel extends BaseObservable {
    private Users user;
    private FirebaseAuth myAuth;
    private final int invalidMail = R.string.invalidMail;
    private final int mailSentInfo = R.string.mailSentInfo;
    private final int mailNotAvailable = R.string.mailNotAvailable;

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
    public ForgotPasswordViewModel() {
        user = new Users("");
        this.myAuth = FirebaseAuth.getInstance();
    }

    public void sendMailClicked() {

        if (user.emailGecerliMi()) {
            send(getUserEmail());
        } else {
            setToastMessage(R.string.invalidMail,1);
        }
    }

    public void send(String email){
        myAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            setToastMessage(R.string.mailSentInfo,4);

                        } else {
                            setToastMessage(R.string.mailNotAvailable,3);
                        }
                    }
                });
    }
}
