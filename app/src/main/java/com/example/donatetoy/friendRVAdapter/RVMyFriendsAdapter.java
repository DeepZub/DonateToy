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

public class RVMyFriendsAdapter extends RecyclerView.Adapter<RVMyFriendsAdapter.CardViewDesignMyFriends> {

    private Context context;
    private List<String> myFriends;
    private FriendsRepo friendsRepo;

    public RVMyFriendsAdapter(Context context, List<String> myFriends) {
        this.context = context;
        this.myFriends = myFriends;
        this.friendsRepo = new FriendsRepo(context);
    }


    public class CardViewDesignMyFriends extends RecyclerView.ViewHolder{
        public TextView myFriendNameSurname;
        public Button buttonSendMessage;
        public ImageView imageUser;

        public CardViewDesignMyFriends(@NonNull View itemView) {
            super(itemView);
            myFriendNameSurname = itemView.findViewById(R.id.nameSurname);
            buttonSendMessage = itemView.findViewById(R.id.buttonSendMessage);
            imageUser = itemView.findViewById(R.id.imageUser);
        }
    }

    @NonNull
    @Override
    public CardViewDesignMyFriends onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_my_friends_design,parent,false);

        return new CardViewDesignMyFriends(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewDesignMyFriends holder, int position) {
        String myFriendUid = myFriends.get(position);
        friendsRepo.setImage(myFriendUid,holder.imageUser);
        friendsRepo.uidToNameSurname(myFriendUid, holder.myFriendNameSurname);




    }

    @Override
    public int getItemCount() {
        return myFriends.size();
    }
}
