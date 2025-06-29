package com.rtechspot.school.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rtechspot.school.R;
import com.rtechspot.school.utils.Constants;
import com.rtechspot.school.utils.Utility;


public class StudentProfileCustomAdapter extends RecyclerView.Adapter<StudentProfileCustomAdapter.MyViewHolder> {

    private Context context;
    String key;


    public StudentProfileCustomAdapter(Context applicationContext,String key) {

        this.context = applicationContext;
        this.key=key;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTV, valueTV;

        public MyViewHolder(View view) {
            super(view);
            headerTV = (TextView) view.findViewById(R.id.adapter_student_profile_head);
            valueTV = (TextView) view.findViewById(R.id.adapter_student_profile_value);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_profile, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Utility.setLocale(context, Utility.getSharedPreferences(context.getApplicationContext(), Constants.langCode));


    }

    @Override
    public int getItemCount() {
        return key.length();
    }
}