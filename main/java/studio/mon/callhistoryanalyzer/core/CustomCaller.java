package studio.mon.callhistoryanalyzer.core;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by megapunk on 12/06/2016.
 */
public class CustomCaller extends ArrayAdapter{

    private List<CallAnalyzer> callAnalyzerList;
    private int resource;
    private LayoutInflater layoutInflater;

    public CustomCaller(Context context, int resource, List<CallAnalyzer> objects) {
        super(context, resource, objects);
        callAnalyzerList = objects;
        this.resource = resource;
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(resource, null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvCaller = (TextView) convertView.findViewById(R.id.tvCaller);
            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivIcon.setImageResource(callAnalyzerList.get(position).getIcon());
        viewHolder.tvCaller.setText(callAnalyzerList.get(position).getName());
        viewHolder.tvNumber.setText(String.valueOf(callAnalyzerList.get(position).getNumber()));
        viewHolder.tvTime.setText(String.valueOf(callAnalyzerList.get(position).getTime()));

        return convertView;
    }

    class ViewHolder{
        private ImageView ivIcon;
        private TextView tvCaller;
        private TextView tvNumber;
        private TextView tvTime;
    }
}
