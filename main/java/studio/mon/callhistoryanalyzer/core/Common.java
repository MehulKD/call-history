package studio.mon.callhistoryanalyzer.core;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by SonNC on 6/10/2016.
 */
public class Common {
    static public List<CallAnalyzer> refreshListCall(Context context, List<CallAnalyzer> list, String type) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        DBHelper db = new DBHelper(context);
        for (CallAnalyzer callAnalyzer : db.getAll()) {
            if (callAnalyzer.getType().equals(type)) {
                list.add(callAnalyzer);
            }
        }
        return list;
    }
    static public void refresh(Context context, List<CallAnalyzer> list, String type, BaseAdapter adapter) {
        list.clear();
        list = refreshListCall(context, list,type);
        adapter.notifyDataSetChanged();
    }

    static public void showDialog(View v, final String timeType, final Context context, final List<CallAnalyzer> list, final BaseAdapter adapter, final String type) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date date = new Date(year-1900, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = null;
                        if (timeType.equals(Constants.DATE_TYPE)) {
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        } else {
                            sdf = new SimpleDateFormat("yyyy-MM");
                        }
                        String strDate = sdf.format(date);
                        list.clear();
                        DBHelper db = new DBHelper(context);
                        for (CallAnalyzer callAnalyzer : db.getAll()) {
                            if (callAnalyzer.getType().equals(type)
                                    && callAnalyzer.getTime().startsWith(strDate) ) {
                                list.add(callAnalyzer);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
    }

    static public void clearData(List<CallAnalyzer> list, BaseAdapter adapter) {
//        DBHelper db = new DBHelper(mContext);
//        db.deleteAll();
        list.clear();
        adapter.notifyDataSetChanged();
    }
}
