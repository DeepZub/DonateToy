package com.example.donatetoy.friendRVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donatetoy.repo.FriendsRepo;
import com.example.donatetoy.R;

import java.util.List;

public class RVAddFriendAdapter extends RecyclerView.Adapter<RVAddFriendAdapter.CardViewDesign> {
    private Context context;
    private List<String> allUser;
    private FriendsRepo friendsRepo;

    public RVAddFriendAdapter(Context context, List<String> allUser) {
        this.context = context;
        this.allUser = allUser;
        this.friendsRepo = new FriendsRepo(context);
    }

    public class CardViewDesign extends RecyclerView.ViewHolder{
        public TextView userFriendNameSurname;
        public Button buttonSendRequest,buttonCancelRequest;
        public ImageView imageUser;

        public CardViewDesign(@NonNull View view) {
            super(view);
            userFriendNameSurname = view.findViewById(R.id.nameSurname);
            buttonSendRequest = view.findViewById(R.id.buttonSendRequest);
            imageUser = view.findViewById(R.id.imageUser);
            buttonCancelRequest = view.findViewById(R.id.buttonCancelRequest);
        }
    }
    @NonNull
    @Override
    public CardViewDesign onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_design,parent,false);
        return new CardViewDesign(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewDesign holder, int position) {
        String uid = allUser.get(position);
        friendsRepo.setImage(uid,holder.imageUser);
        friendsRepo.uidToNameSurname(uid, holder.userFriendNameSurname);
        holder.buttonCancelRequest.setEnabled(false);
        friendsRepo.controlRequestFriend(uid,holder.buttonSendRequest,holder.buttonCancelRequest);



        holder.buttonSendRequest.setOnClickListener(view -> {
            friendsRepo.sendRequest(view,uid);
            holder.buttonSendRequest.setEnabled(false);

        });

        holder.buttonCancelRequest.setOnClickListener(view -> {

                friendsRepo.cancelRequest(view,uid);
                holder.buttonSendRequest.setEnabled(true);
                holder.buttonCancelRequest.setEnabled(false);



        });

    }

    @Override
    public int getItemCount() {
        return allUser.size();
    }

}
