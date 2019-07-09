package com.thinkitive.accessibilityfullfunctional.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkitive.accessibilityfullfunctional.R;
import com.thinkitive.accessibilityfullfunctional.Utils.CallBackToActivity;
import com.thinkitive.accessibilityfullfunctional.model.UserInformationData;

import java.util.List;

public class CredentialsInfoAdapter extends RecyclerView.Adapter<CredentialsInfoAdapter.ViewHolder> implements View.OnClickListener {
    private List<UserInformationData> list;
    private Context context;
    private CallBackToActivity callBackToActivity;

    public CredentialsInfoAdapter(Context context, List<UserInformationData> list, CallBackToActivity callBackToActivity) {
        this.context = context;
        this.list = list;
        this.callBackToActivity = callBackToActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        UserInformationData userInformationData = list.get(position);

        //viewHolder.itemView.setTag(position);
        //viewHolder.itemView.setOnClickListener(this);
        viewHolder.userDisplay.setText("User: "+userInformationData.getUser());
        viewHolder.passDisplay.setText("Password: "+userInformationData.getPassword());
        viewHolder.packDisplay.setText("App: "+userInformationData.getAppName());
        viewHolder.imageView.setOnClickListener(this);
        viewHolder.imageView.setTag(userInformationData);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<UserInformationData> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.deleteIcon:
                UserInformationData userInformationData = (UserInformationData)view.getTag();
                callBackToActivity.deleteEntry(userInformationData.getId());
                break;
            default:
                break;
        }

       /* Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(list.get(position).getPackageName());
        if (launchIntent != null) {
            //Toast.makeText(context,"clicked package: "+list.get(position).getPackageName(),Toast.LENGTH_SHORT).show();
            context.startActivity(launchIntent);//null pointer check in case package name was not found
        }*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userDisplay, passDisplay, packDisplay;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userDisplay = itemView.findViewById(R.id.userDisplay);
            passDisplay = itemView.findViewById(R.id.passwordDisplay);
            packDisplay = itemView.findViewById(R.id.packageNameDisplay);
            imageView = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
