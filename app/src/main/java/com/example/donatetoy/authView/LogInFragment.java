package com.example.donatetoy.authView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.donatetoy.HomePageActivity;
import com.example.donatetoy.R;
import com.example.donatetoy.toastMessage.ToastMessage;
import com.example.donatetoy.databinding.FragmentLogInBinding;
import com.example.donatetoy.authViewModel.LogInViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends Fragment {
    private FragmentLogInBinding design;
    private LogInViewModel viewModel;
    private MutableLiveData<FirebaseUser> userLiveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false);
        design.setViewModel(new LogInViewModel(getActivity().getApplicationContext()));
        design.setFragment(this);
        design.executePendingBindings();




        return design.getRoot();
    }


    public void fabClick(View view){
        Navigation.findNavController(view).navigate(new ActionOnlyNavDirections(R.id.signInTrans));
    }
    public void forgotPasswordClick(View view){
        Navigation.findNavController(view).navigate(new ActionOnlyNavDirections(R.id.forgotPasswordTrans));
    }
    public void passwordEyeClick(boolean checked){
        if(checked){
            design.editTextLogPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        else{
            design.editTextLogPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }




}