package com.example.donatetoy;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.donatetoy.databinding.FragmentListAdBinding;


public class ListAdFragment extends Fragment {
    private FragmentListAdBinding design;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        design = DataBindingUtil.inflate(inflater,R.layout.fragment_list_ad,container,false);






















        return design.getRoot();

    }
}