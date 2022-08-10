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
import com.example.donatetoy.databinding.FragmentAddFriendBinding;
import com.example.donatetoy.repo.FriendsRepo;

import java.util.ArrayList;


public class AddFriendFragment extends Fragment {
    private FragmentAddFriendBinding design;

    private RecyclerView rv;
    private ArrayList<String> usersEmail;
    private FriendsRepo friendsRepo;

    public AddFriendFragment() {
        this.usersEmail = new ArrayList<>();
        this.friendsRepo = new FriendsRepo(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        design = DataBindingUtil.inflate(inflater, R.layout.fragment_add_friend, container, false);
        rv = design.rvAddFriend;

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        friendsRepo.listAllUsers(rv,usersEmail);



        return design.getRoot();

    }
}