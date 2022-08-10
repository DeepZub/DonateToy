package com.example.donatetoy.friendView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.donatetoy.R;
import com.example.donatetoy.databinding.ActivityFriendsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {
    private ActivityFriendsBinding design;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ArrayList<Fragment> fragmentArrayList =new ArrayList<>();
    private ArrayList<String> fragmentTitleList =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        design = DataBindingUtil.setContentView(this, R.layout.activity_friends);

        tabLayout = design.tabLayout;
        viewPager2 = design.viewPager2;

        fragmentArrayList.add(new MyFriendsFragment());
        fragmentArrayList.add(new FriendRequestsFragment());
        fragmentArrayList.add(new AddFriendFragment());
        fragmentTitleList.add(getResources().getString(R.string.myFriends));
        fragmentTitleList.add(getResources().getString(R.string.friendRequests));
        fragmentTitleList.add(getResources().getString(R.string.addFriend));

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout,viewPager2,
                (tab,position)->tab.setText(fragmentTitleList.get(position))).attach();

        tabLayout.getTabAt(0).setIcon(R.drawable.my_friends_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.friend_request_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.add_friend_icon);

    }

    private class ViewPagerAdapter extends FragmentStateAdapter{

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentArrayList.size();
        }
    }
}