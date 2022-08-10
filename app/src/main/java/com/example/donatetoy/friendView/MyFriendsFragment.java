package com.example.donatetoy.friendView;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.donatetoy.R;
import com.example.donatetoy.databinding.FragmentMyFriendsBinding;
import com.example.donatetoy.repo.FriendsRepo;

import java.util.ArrayList;

public class MyFriendsFragment extends Fragment {
    private FragmentMyFriendsBinding design;
    private RecyclerView rv;
    private ArrayList<String> myFriendsUid;
    private FriendsRepo friendsRepo;

    public MyFriendsFragment() {
        this.myFriendsUid = new ArrayList<>();
        this.friendsRepo = new FriendsRepo(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friends, container, false);
        rv = design.rvMyFriends;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        friendsRepo.listMyFriends(rv, myFriendsUid);




        return design.getRoot();
    }
}