package studio.mon.callhistoryanalyzer.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.core.Common;
import studio.mon.callhistoryanalyzer.core.Constants;
import studio.mon.callhistoryanalyzer.core.CoreActivity;
import studio.mon.callhistoryanalyzer.core.CoreFragment;
import studio.mon.callhistoryanalyzer.core.DBHelper;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by admin on 5/17/2016.
 */
public class TotalFragment extends CoreFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static String TAG = TotalFragment.class.getSimpleName();

    private ListView listView;
    private List<CallAnalyzer> listMissedCall, listReceivedCall, listDialedCall;
    public static TextView tvMissed, tvDialed, tvReceived;


    private SwipeRefreshLayout swipeView;
    //private Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        //spinner = (Spinner) view.findViewById(R.id.spinner);
        tvMissed = (TextView) view.findViewById(R.id.tvMissed);
        tvDialed = (TextView) view.findViewById(R.id.tvDialed);
        tvReceived = (TextView) view.findViewById(R.id.tvReceived);
        refreshTotal();
        initListener();
//        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
//        swipeView.setOnRefreshListener(this);
//        swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);

        return view;

    }

    @Override
    protected void initModels() {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initListener() {
//        List<String> spinnerList = Arrays.asList("Refresh", "View date", "View month", "Clear", "");
//        ArrayAdapter<String> spinnerAdapter =
//                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, spinnerList){
//                    @Override
//                    public int getCount() {
//                        return(4);
//                    }
//                };
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(spinnerAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (view != null) ((TextView) view).setText(null);
//                switch (position){
//                    case 0:
//                        refreshTotal();
//                        break;
//                    case 1:
//                        showTotalDialog(Constants.DATE_TYPE, mContext);
//                        break;
//                    case 2:
//                        showTotalDialog(Constants.MONTH_TYPE, mContext);
//                        break;
//                    case 3:
//                        clear();
//                        break;
//                }
//                spinner.setSelection(4);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    public void refreshTotal(){
        listMissedCall = Common.refresh(mContext, listMissedCall, Constants.MISSED_CALL);
        listDialedCall = Common.refresh(mContext, listDialedCall, Constants.DIALED_CALL);
        listReceivedCall = Common.refresh(mContext, listReceivedCall, Constants.RECEIVED_CALL);
        setTotalContent();
    }

    public void showTotalDialog(final String timeType, final Context context) {
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
                        listMissedCall.clear();
                        listDialedCall.clear();
                        listReceivedCall.clear();
                        DBHelper db = new DBHelper(context);
                        for (CallAnalyzer callAnalyzer : db.getAll()) {
                            if (callAnalyzer.getType().equals(Constants.MISSED_CALL)
                                    && callAnalyzer.getTime().startsWith(strDate) ) {
                                listMissedCall.add(callAnalyzer);
                            }
                        }
                        for (CallAnalyzer callAnalyzer : db.getAll()) {
                            if (callAnalyzer.getType().equals(Constants.DIALED_CALL)
                                    && callAnalyzer.getTime().startsWith(strDate) ) {
                                listDialedCall.add(callAnalyzer);
                            }
                        }
                        for (CallAnalyzer callAnalyzer : db.getAll()) {
                            if (callAnalyzer.getType().equals(Constants.RECEIVED_CALL)
                                    && callAnalyzer.getTime().startsWith(strDate) ) {
                                listReceivedCall.add(callAnalyzer);
                            }
                        }
                    setTotalContent();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
    }

    private void setTotalContent(){
        int totalDialed = 0, totalReceived = 0;
        if (listDialedCall.size() > 0){
            for(CallAnalyzer c : listDialedCall){
                totalDialed += Integer.parseInt(c.getDuration().substring(0, (c.getDuration().length() - 1)));
            }
        }
        if (listReceivedCall.size() > 0){
            for(CallAnalyzer c : listReceivedCall){
                totalReceived += Integer.parseInt(c.getDuration().substring(0, (c.getDuration().length() - 1)));
            }
        }
        tvMissed.setText(listMissedCall.size() + " call");
        tvDialed.setText(totalDialed + "s");
        tvReceived.setText(totalReceived + "s");
    }
    public void clear(){
        tvMissed.setText("");
        tvDialed.setText("");
        tvReceived.setText("");
    }

    @Override
    protected void initAnimations() {

    }

    public static final long serialVersionUID = 6036846677812555352L;

    public static CoreActivity mActivity;
    public static TotalFragment mInstance;

    public static TotalFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new TotalFragment();
        }
        mActivity = activity;
        return mInstance;
    }

    public static TotalFragment getInstance() {
        if (mInstance == null) {
            mInstance = new TotalFragment();
        }
        return mInstance;
    }

    @Override
    public void onRefresh() {
        //swipeView.setRefreshing(true);
    }
}
