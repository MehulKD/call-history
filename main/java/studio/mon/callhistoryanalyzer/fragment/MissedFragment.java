package studio.mon.callhistoryanalyzer.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.adapter.CustomCallAdapter;
import studio.mon.callhistoryanalyzer.core.Common;
import studio.mon.callhistoryanalyzer.core.Constants;
import studio.mon.callhistoryanalyzer.core.CoreActivity;
import studio.mon.callhistoryanalyzer.core.CoreFragment;
import studio.mon.callhistoryanalyzer.core.DBHelper;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by admin on 5/17/2016.
 */
public class MissedFragment extends CoreFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static String TAG = MissedFragment.class.getSimpleName();

    //private Spinner spinner;
    private ListView listView;
    public static List<CallAnalyzer> missedCallList = new ArrayList<>();
    public static ArrayAdapter<CallAnalyzer> adapter;
    private SwipeRefreshLayout swipeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missed, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        //spinner = (Spinner) view.findViewById(R.id.spinner);
//        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
//        swipeView.setOnRefreshListener(this);
//        swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
        DBHelper db = new DBHelper(mContext);
        missedCallList = Common.refresh(mContext, missedCallList, Constants.MISSED_CALL);
        Log.i("Count:", "" + missedCallList.size());
        adapter = new CustomCallAdapter(mContext, R.layout.fragment_customlayer, missedCallList);
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
//                Common.spinnerClicked(position, mContext, missedCallList, adapter,Constants.MISSED_CALL);
//                spinner.setSelection(4);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    @Override
    protected void initAnimations() {

    }

    public static final long serialVersionUID = 6036846677812555352L;

    public static CoreActivity mActivity;
    public static MissedFragment mInstance;

    public static MissedFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new MissedFragment();
        }
        mActivity = activity;
        return mInstance;
    }

    public static MissedFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MissedFragment();
        }
        return mInstance;
    }

    @Override
    public void onRefresh() {
        //swipeView.setRefreshing(true);
    }
}
