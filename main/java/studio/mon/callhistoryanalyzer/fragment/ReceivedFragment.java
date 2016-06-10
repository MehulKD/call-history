package studio.mon.callhistoryanalyzer.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import studio.mon.callhistoryanalyzer.MainActivity;
import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.core.AppConfigs;
import studio.mon.callhistoryanalyzer.core.Constants;
import studio.mon.callhistoryanalyzer.core.CoreActivity;
import studio.mon.callhistoryanalyzer.core.CoreFragment;
import studio.mon.callhistoryanalyzer.core.DBHelper;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by admin on 5/17/2016.
 */
public class ReceivedFragment extends CoreFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static String TAG = ReceivedFragment.class.getSimpleName();
    private ListView listView;
    List<CallAnalyzer> receivedCallList;
    private ArrayAdapter<CallAnalyzer> adapter;
    private SwipeRefreshLayout swipeView;
//    private Button btnRefresh, btnDate, btnMonth, btnClear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
//        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
//        btnDate = (Button) view.findViewById(R.id.btnDate);
//        btnMonth = (Button) view.findViewById(R.id.btnMonth);
//        btnClear = (Button) view.findViewById(R.id.btnClear);

//        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
//        swipeView.setOnRefreshListener(this);
//        swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
        DBHelper db = new DBHelper(mContext);
        refreshReceivedCall();
        adapter = new ArrayAdapter<CallAnalyzer>(mActivity, android.R.layout.simple_list_item_1, receivedCallList);
        listView.setAdapter(adapter);
//        initListener();
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
//        btnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refresh();
//            }
//        });
//        btnClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearData();
//            }
//        });
//        btnDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(v, Constants.DATE_TYPE);
//            }
//        });
//        btnMonth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(v, Constants.MONTH_TYPE);
//            }
//        });
    }

    @Override
    protected void initAnimations() {

    }

    public static final long serialVersionUID = 6036846677812555352L;

    public static CoreActivity mActivity;
    public static ReceivedFragment mInstance;

    public static ReceivedFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new ReceivedFragment();
        }
        mActivity = activity;
        return mInstance;
    }

    public static ReceivedFragment getInstance() {
        if (mInstance == null) {
            mInstance = new ReceivedFragment();
        }
        return mInstance;
    }

    @Override
    public void onRefresh() {
        //swipeView.setRefreshing(true);
    }

    public void refresh() {
        receivedCallList.clear();
        refreshReceivedCall();
        adapter.notifyDataSetChanged();
    }

    public void showDialog(View v, final String timeType) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(mContext,
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
                        receivedCallList.clear();
                        DBHelper db = new DBHelper(mContext);
                        for (CallAnalyzer callAnalyzer : db.getAll()) {
                            if (callAnalyzer.getType().equals(Constants.RECEIVED_CALL)
                                && callAnalyzer.getTime().startsWith(strDate) ) {
                                receivedCallList.add(callAnalyzer);
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

    public void clearData() {
//        DBHelper db = new DBHelper(mContext);
//        db.deleteAll();
        receivedCallList.clear();
        adapter.notifyDataSetChanged();
    }

    public List<CallAnalyzer> refreshReceivedCall() {
        if (receivedCallList == null) {
            receivedCallList = new ArrayList<>();
        } else {
            receivedCallList.clear();
        }
        DBHelper db = new DBHelper(mContext);
        for (CallAnalyzer callAnalyzer : db.getAll()) {
            if (callAnalyzer.getType().equals(Constants.RECEIVED_CALL)) {
                receivedCallList.add(callAnalyzer);
            }
        }
        return receivedCallList;
    }
}
