package com.example.donatetoy.authView;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.donatetoy.R;
import com.example.donatetoy.databinding.FragmentForgotPasswordBinding;
import com.example.donatetoy.authViewModel.ForgotPasswordViewModel;

public class ForgotPasswordFragment extends Fragment {
    private FragmentForgotPasswordBinding design;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        design = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false);
        design.setViewModel(new ForgotPasswordViewModel());
        design.executePendingBindings();


        return design.getRoot();
    }


}