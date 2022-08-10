package com.example.donatetoy.friendRVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donatetoy.repo.FriendsRepo;
import com.example.donatetoy.R;

import java.util.List;

public class RVFriendRequestsAdapter extends RecyclerView.Adapter<RVFriendRequestsAdapter.CardViewDesignFriendRequests> {

    private Context context;
    private List<String> requestsUid;
    private FriendsRepo friendsRepo;

    public RVFriendRequestsAdapter(Context context, List<String> myFriends) {
        this.context = context;
        this.requestsUid = myFriends;
        this.friendsRepo = new FriendsRepo(context);
    }

    public class CardViewDesignFriendRequests extends RecyclerView.ViewHolder{
        public ImageButton acccept,reject;
        public TextView requestNameSurname;
        public ImageView imageUser;

        public CardViewDesignFriendRequests(@NonNull View itemView) {

            super(itemView);
            acccept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.rejection);
            requestNameSurname = itemView.findViewById(R.id.nameSurname);
            imageUser = itemView.findViewById(R.id.imageUser);

        }
    }

    @NonNull
    @Override
    public CardViewDesignFriendRequests onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_friend_request_design,parent,false);
        return new CardViewDesignFriendRequests(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewDesignFriendRequests holder, int position) {
        String requestUid = requestsUid.get(position);
        friendsRepo.setImage(requestUid, holder.imageUser);
        friendsRepo.uidToNameSurname(requestUid, holder.requestNameSurname);

        holder.reject.setOnClickListener(view -> {
            String email = holder.requestNameSurname.getText().toString();
            friendsRepo.nameSurnameToUid(email);
            friendsRepo.rejectRequest(view);
        });

        holder.acccept.setOnClickListener(view -> {

            String email = holder.requestNameSurname.getText().toString();
            friendsRepo.nameSurnameToUid(email);
            friendsRepo.acceptRequest(view);

        });

    }

    @Override
    public int getItemCount() {
        return requestsUid.size();
    }


}
