package com.example.accessibilityfullfunctional.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.accessibilityfullfunctional.R;
import com.example.accessibilityfullfunctional.service.model.UserInformationData;

import java.util.List;

public class PackageInfoAdapter extends RecyclerView.Adapter<PackageInfoAdapter.ViewHolder> {
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
