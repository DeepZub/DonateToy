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
import com.example.donatetoy.databinding.FragmentFriendRequestsBinding;
import com.example.donatetoy.repo.FriendsRepo;

import java.util.ArrayList;


public class FriendRequestsFragment extends Fragment {
    private FragmentFriendRequestsBinding design;

    private ArrayList<String> listRequest;
    private RecyclerView rv;
    private FriendsRepo friendsRepo;


    public FriendRequestsFragment() {
        this.listRequest = new ArrayList<>();
        this.friendsRepo = new FriendsRepo(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_friend_requests,container,false);
        rv = design.rvFriendRequests;

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        friendsRepo.listRequests(rv,listRequest);


        return design.getRoot();
    }

}