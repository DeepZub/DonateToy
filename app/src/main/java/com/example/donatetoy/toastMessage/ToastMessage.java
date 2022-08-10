package com.example.donatetoy.toastMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.Navigation;

import com.example.donatetoy.R;


public class ToastMessage {


    public void showToast(Context context, int type, String strContent) {
        LayoutInflater lIInterface = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewImage = lIInterface.inflate(R.layout.toast_layout, null);
        LinearLayout capsule = viewImage.findViewById(R.id.capsule);
        ImageView icon = viewImage.findViewById(R.id.icon);
        TextView content = viewImage.findViewById(R.id.content);
        content.setText(strContent);
        if (type == 1)  // Red
        {
            capsule.setBackgroundResource(R.drawable.left_edge_red);
            icon.setImageResource(R.drawable.icon_toast_red_24dp);
            content.setTextColor(context.getResources().getColor(R.color.toast_red));
        }
        else if (type == 2) // Blue
        {
            capsule.setBackgroundResource(R.drawable.left_edge_blue);
            icon.setImageResource(R.drawable.icon_toast_blue_24dp);
            content.setTextColor(context.getResources().getColor(R.color.toast_blue));
        }
        else if (type == 3) // Orange
        {
            capsule.setBackgroundResource(R.drawable.left_edge_orange);
            icon.setImageResource(R.drawable.icon_toast_orange_24dp);
            content.setTextColor(context.getResources().getColor(R.color.toast_orange));
        }
        else if (type == 4) // Green
        {
            capsule.setBackgroundResource(R.drawable.left_edge_green);
            icon.setImageResource(R.drawable.icon_toast_green_24dp);
            content.setTextColor(context.getResources().getColor(R.color.toast_green));
        }

        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setView(viewImage);
        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
        toast.show();
    }
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, int[] message) {
        if (message[0] != 0) {
            ToastMessage toast = new ToastMessage();
            toast.showToast(view.getContext(), message[1], view.getResources().getString(message[0]));
        }


    }

}


