package com.thinkitive.accessibilityfullfunctional.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkitive.accessibilityfullfunctional.R;
import com.thinkitive.accessibilityfullfunctional.Utils.ApkInfoExtractor;
import com.thinkitive.accessibilityfullfunctional.Utils.CallBackToGetAppName;
import com.thinkitive.accessibilityfullfunctional.view.ui.InsertionTaskActivity;

import java.util.List;

public class InsertionAdapter extends RecyclerView.Adapter<InsertionAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;
    private ApkInfoExtractor apkInfoExtractor;
    private int selected_position = -1;
    private CallBackToGetAppName callBackToGetAppName;

    public InsertionAdapter(Context context, List<String> list, CallBackToGetAppName callBackToGetAppName) {
        this.context = context;
        this.list = list;
        apkInfoExtractor = new ApkInfoExtractor(context);
        this.callBackToGetAppName = callBackToGetAppName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_for_insertion,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String packageName = list.get(position);
        String getAppName = apkInfoExtractor.getAppName(packageName);
        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(packageName);

        viewHolder.appName.setText(getAppName);
        viewHolder.appIcon.setImageDrawable(drawable);
        viewHolder.itemView.setTag(position);
        viewHolder.itemView.setOnClickListener(this);
        viewHolder.itemView.setBackgroundColor(selected_position == position ? Color.GREEN : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int position = (int)view.getTag();

        if (position == RecyclerView.NO_POSITION) return;

        notifyItemChanged(selected_position);
        selected_position = position;
        notifyItemChanged(selected_position);


        String appPackName = list.get(position);
        callBackToGetAppName.getAppName(appPackName);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView appName;
        private ImageView appIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            appIcon = itemView.findViewById(R.id.appIcon);
            appName = itemView.findViewById(R.id.appName);
        }
    }
}
