package studio.mon.callhistoryanalyzer.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.core.AppConfigs;
import studio.mon.callhistoryanalyzer.core.CoreActivity;
import studio.mon.callhistoryanalyzer.core.CoreFragment;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

/**
 * Created by admin on 5/17/2016.
 */
public class MissedFragment extends CoreFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static String TAG = MissedFragment.class.getSimpleName();

    private ListView listView;

    private SwipeRefreshLayout swipeView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missed, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);

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
        swipeView.setRefreshing(true);
    }
}
