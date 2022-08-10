package com.example.donatetoy.authView;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.donatetoy.R;
import com.example.donatetoy.databinding.FragmentSignInBinding;
import com.example.donatetoy.authViewModel.SignInViewModel;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding design;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        design = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        design.setViewModel(new SignInViewModel());
        design.setFragment(this);
        design.executePendingBindings();




        return design.getRoot();
    }

    public void passwordEyeClick(boolean checked){
        if(checked){
            design.editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        else{
            design.editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }
}