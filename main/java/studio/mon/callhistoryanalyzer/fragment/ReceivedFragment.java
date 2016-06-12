package studio.mon.callhistoryanalyzer.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import studio.mon.callhistoryanalyzer.MainActivity;
import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.core.AppConfigs;
import studio.mon.callhistoryanalyzer.core.Common;
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
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        spinner = (Spinner) view.findViewById(R.id.spinner);

//        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
//        swipeView.setOnRefreshListener(this);
//        swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
        DBHelper db = new DBHelper(mContext);
        receivedCallList = Common.refreshListCall(mContext, receivedCallList, Constants.RECEIVED_CALL);
        adapter = new ArrayAdapter<CallAnalyzer>(mActivity, android.R.layout.simple_list_item_1, receivedCallList);
        listView.setAdapter(adapter);
        initListener();
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
        List<String> spinnerList = Arrays.asList("Refresh", "View date", "View month", "Clear");
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, spinnerList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) ((TextView) view).setText(null);
                Common.spinnerClicked(position, mContext, receivedCallList, adapter, Constants.DIALED_CALL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
}
