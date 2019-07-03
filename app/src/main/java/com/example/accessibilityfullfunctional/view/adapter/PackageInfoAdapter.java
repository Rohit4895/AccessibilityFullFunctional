package com.example.accessibilityfullfunctional.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accessibilityfullfunctional.R;
import com.example.accessibilityfullfunctional.service.model.UserInformationData;

import java.util.List;

public class PackageInfoAdapter extends RecyclerView.Adapter<PackageInfoAdapter.ViewHolder> implements View.OnClickListener {
    private List<UserInformationData> list;
    private Context context;

    public PackageInfoAdapter(Context context, List<UserInformationData> list) {
        this.context = context;
        this.list = list;
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

        viewHolder.itemView.setTag(position);
        viewHolder.itemView.setOnClickListener(this);
        viewHolder.userDisplay.setText("User: "+userInformationData.getUser());
        viewHolder.passDisplay.setText("Password: "+userInformationData.getPassword());
        viewHolder.packDisplay.setText("Package: "+userInformationData.getPackageName());

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

        int position = (int) view.getTag();

        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(list.get(position).getPackageName());
        if (launchIntent != null) {
            Toast.makeText(context,"clicked package: "+list.get(position).getPackageName(),Toast.LENGTH_SHORT).show();
            context.startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userDisplay, passDisplay, packDisplay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userDisplay = itemView.findViewById(R.id.userDisplay);
            passDisplay = itemView.findViewById(R.id.passwordDisplay);
            packDisplay = itemView.findViewById(R.id.packageNameDisplay);
        }
    }
}
