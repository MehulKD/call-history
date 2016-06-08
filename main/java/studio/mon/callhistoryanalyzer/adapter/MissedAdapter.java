package studio.mon.callhistoryanalyzer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by admin on 6/8/2016.
 */
public class MissedAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<CallAnalyzer> listCallAnalyzer;
    Activity activity;
    public MissedAdapter(ArrayList<CallAnalyzer> listCallAnalyzer, Activity activity) {
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listCallAnalyzer = listCallAnalyzer;
        this.activity = activity;
    }

    static class ViewHolder {
    }

    @Override
    public int getCount() {
        return listCallAnalyzer.size();
    }

    @Override
    public Object getItem(int position) {
        return listCallAnalyzer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            //convertView = inflater.inflate(R.layout.item_CallAnalyzer_row, null);
            viewHolder = new ViewHolder();


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CallAnalyzer CallAnalyzer = listCallAnalyzer.get(position);


        return convertView;

    }

}