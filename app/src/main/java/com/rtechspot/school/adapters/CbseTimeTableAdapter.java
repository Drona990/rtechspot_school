package com.rtechspot.school.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.rtechspot.school.R;
import com.rtechspot.school.model.CbseExamTimeTableModel;
import com.rtechspot.school.model.CbseTimetableModel;
import com.rtechspot.school.students.CbseTimeTableActivity;

import java.util.ArrayList;

public class CbseTimeTableAdapter extends RecyclerView.Adapter<CbseTimeTableAdapter.MyViewHolder>  {
    CbseTimeTableActivity context;
    private ArrayList<CbseExamTimeTableModel> cbseexamlist = new ArrayList<>();
    private Fragment fragment;
    public CbseTimeTableAdapter(CbseTimeTableActivity context, ArrayList<CbseExamTimeTableModel> cbseexamlist, Fragment fragment) {

        this.cbseexamlist = cbseexamlist;
        this.context = context;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public CbseTimeTableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cbse_timetable_adapter, parent, false);
        return new CbseTimeTableAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CbseTimeTableAdapter.MyViewHolder myViewHolder, int position) {
        CbseExamTimeTableModel model = cbseexamlist.get(position);
        myViewHolder.name.setText(model.getName());

        ArrayList<CbseTimetableModel> subjectList = model.getTime_table();
        CbseExaminationTimetableAdapter adapter = new CbseExaminationTimetableAdapter(context,subjectList,fragment);
        myViewHolder.recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        myViewHolder.recyclerview.setItemAnimator(new DefaultItemAnimator());
        myViewHolder.recyclerview.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return cbseexamlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView recyclerview,assrecyclerview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            recyclerview=itemView.findViewById(R.id.recyclerview);

        }
    }
}
