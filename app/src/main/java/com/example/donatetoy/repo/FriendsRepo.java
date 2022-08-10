package com.example.donatetoy.repo;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.donatetoy.friendRVAdapter.RVAddFriendAdapter;
import com.example.donatetoy.friendRVAdapter.RVFriendRequestsAdapter;
import com.example.donatetoy.friendRVAdapter.RVMyFriendsAdapter;
import com.example.donatetoy.R;
import com.example.donatetoy.model.RequestFriend;
import com.example.donatetoy.toastMessage.ToastMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FriendsRepo {
    private Context context;
    private FirebaseUser firebaseUser;
    private DatabaseReference allUsers,friendRequest,myFriends;
    private FirebaseStorage storage;
    private StorageReference reference;
    private RVAddFriendAdapter rvAddFriendAdapter;
    private RVFriendRequestsAdapter rvFriendRequestsAdapter;
    private RVMyFriendsAdapter rvMyFriendsAdapter;
    private ToastMessage toast;
    private int requestId;
    private int myFriendReceiverId;
    private int myFriendTransmitterId = 0;
    private String convertedFromEmailUid = "";

    public FriendsRepo(Context context){
        this.context = context;
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.allUsers = FirebaseDatabase.getInstance().getReference("userProfile");
        this.friendRequest = FirebaseDatabase.getInstance().getReference().child("FriendRequest");
        this.myFriends = FirebaseDatabase.getInstance().getReference().child("MyFriends");
        this.storage = FirebaseStorage.getInstance();
        this.reference = storage.getReference();
        this.toast = new ToastMessage();
        this.requestId = controlRequestId();
        this.myFriendReceiverId = controlReceiverId();
    }

    public void listAllUsers(RecyclerView rv , ArrayList<String> usersUid){

        allUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersUid.clear();
                for (DataSnapshot snp : snapshot.getChildren()) {
                    if (!(firebaseUser.getUid()).equals(snp.child("uid").getValue()))
                        usersUid.add((String) snp.child("uid").getValue());
                }
                rvAddFriendAdapter = new RVAddFriendAdapter(context, usersUid);
                rv.setAdapter(rvAddFriendAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void listRequests(RecyclerView rv , ArrayList<String> listRequest){

        friendRequest.orderByChild("receiverUid").equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listRequest.clear();
                for(DataSnapshot snp : dataSnapshot.getChildren()){
                    listRequest.add((String) snp.child("transmitterUid").getValue());
                }
                rvFriendRequestsAdapter = new RVFriendRequestsAdapter(context, listRequest);
                rv.setAdapter(rvFriendRequestsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void listMyFriends(RecyclerView rv , ArrayList<String> myFriendsEmail){

        myFriends.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myFriendsEmail.clear();
                for (DataSnapshot snp : snapshot.getChildren()) {
                    myFriendsEmail.add((String) snp.child("userUid").getValue());
                }
                rvMyFriendsAdapter = new RVMyFriendsAdapter(context, myFriendsEmail);
                rv.setAdapter(rvMyFriendsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void sendRequest(View view,String receiverUid){

            friendRequest.child(String.valueOf(requestId+1)).setValue(new RequestFriend(firebaseUser.getUid(),receiverUid));
            toast.showToast(view.getContext(),4,view.getContext().getResources().getString(R.string.sendRequestToast));

    }

    public void uidToNameSurname(String requestUid, TextView textView){

        allUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snp : dataSnapshot.getChildren()){
                    if (snp.getKey().equals(requestUid)){
                        String nameSurname = snp.child("name").getValue()+" "+snp.child("surname").getValue();
                        textView.setText(nameSurname);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void nameSurnameToUid(String nameSurname){


        allUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snp : dataSnapshot.getChildren()){
                    if ((snp.child("name").getValue()+" "+snp.child("surname").getValue()).equals(nameSurname)){
                        convertedFromEmailUid = snp.getKey();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public int controlRequestId(){

        friendRequest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp : snapshot.getChildren()){
                    requestId = Integer.parseInt(snp.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return  requestId;
    }

    public void rejectRequest(View view){

        if(convertedFromEmailUid.equals("")){
            toast.showToast(view.getContext(),2,view.getContext().getResources().getString(R.string.sureRejectToast));
        }else {
            friendRequest.orderByChild("receiverUid").equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snp : snapshot.getChildren()){
                        if(snp.child("transmitterUid").getValue().equals(convertedFromEmailUid))
                            snp.getRef().removeValue();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public int controlReceiverId(){

        myFriends.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp : snapshot.getChildren()){
                    myFriendReceiverId = Integer.parseInt(snp.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return  myFriendReceiverId;
    }

    public void acceptRequest(View view){
        if(convertedFromEmailUid.equals("")){
            toast.showToast(view.getContext(),2,view.getContext().getResources().getString(R.string.sureAcceptToast));
        }else {

            myFriends.child(convertedFromEmailUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snp : snapshot.getChildren()){
                        myFriendTransmitterId = Integer.parseInt(snp.getKey());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            friendRequest.orderByChild("receiverUid").equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snp : snapshot.getChildren()){
                        if(snp.child("transmitterUid").getValue().equals(convertedFromEmailUid))
                            snp.getRef().removeValue();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            myFriends.child(firebaseUser.getUid()).child(String.valueOf(myFriendReceiverId+1)).child("userUid").setValue(convertedFromEmailUid);
            myFriends.child(convertedFromEmailUid).child(String.valueOf(myFriendTransmitterId+1)).child("userUid").setValue(firebaseUser.getUid());
        }
    }

    public void controlRequestFriend(String receiverUid, Button buttonSendRequest , Button buttonCancelRequest) {

        friendRequest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp : snapshot.getChildren()){
                    if(snp.child("transmitterUid").getValue().equals(firebaseUser.getUid()) && snp.child("receiverUid").getValue().equals(receiverUid)){
                        buttonSendRequest.setEnabled(false);
                        buttonCancelRequest.setEnabled(true);
                    }
                    if(snp.child("transmitterUid").getValue().equals(receiverUid) && snp.child("receiverUid").getValue().equals(firebaseUser.getUid())){
                        buttonSendRequest.setEnabled(false);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        myFriends.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snp : snapshot.getChildren()){
                    if(snp.child("userUid").getValue().equals(receiverUid)){
                        buttonSendRequest.setEnabled(false);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setImage(String uid,ImageView imageView){

        StorageReference ref = reference.child("usersPhoto/" + uid);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(imageView.getContext()).load(uri).into(imageView);
            }
        });
    }

    public void cancelRequest(View view, String uid) {

        friendRequest.orderByChild("transmitterUid").equalTo(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp : snapshot.getChildren()){
                    if(snp.child("receiverUid").getValue().equals(uid))
                        snp.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        toast.showToast(view.getContext(),4, view.getContext().getResources().getString(R.string.cancelRequestToast));


    }
}
