package studio.mon.callhistoryanalyzer.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
public class DialedFragment extends CoreFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static String TAG = DialedFragment.class.getSimpleName();

    private ListView listView;
    List<CallAnalyzer> dialedCallList;
    private ArrayAdapter<CallAnalyzer> adapter;
    private Button btnRefresh, btnDate, btnMonth, btnClear;
    private SwipeRefreshLayout swipeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialed, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
        btnDate = (Button) view.findViewById(R.id.btnDate);
        btnMonth = (Button) view.findViewById(R.id.btnMonth);
        btnClear = (Button) view.findViewById(R.id.btnClear);

//        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
//        swipeView.setOnRefreshListener(this);
//        swipeView.setColorSchemeColors(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
        DBHelper db = new DBHelper(mContext);
        dialedCallList = Common.refreshListCall(mContext, dialedCallList, Constants.DIALED_CALL);
        Common.setCustomList(mContext,listView,dialedCallList,Constants.CALLER_OUTGOING);
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
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.refresh(mContext, dialedCallList, Constants.DIALED_CALL, adapter);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.clearData(dialedCallList, adapter);
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.showDialog(v, Constants.DATE_TYPE, mContext, dialedCallList, adapter, Constants.DIALED_CALL);
            }
        });
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.showDialog(v, Constants.MONTH_TYPE, mContext, dialedCallList, adapter, Constants.DIALED_CALL);
            }
        });
    }

    @Override
    protected void initAnimations() {

    }

    public static final long serialVersionUID = 6036846677812555352L;

    public static CoreActivity mActivity;
    public static DialedFragment mInstance;

    public static DialedFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new DialedFragment();
        }
        mActivity = activity;
        return mInstance;
    }

    public static DialedFragment getInstance() {
        if (mInstance == null) {
            mInstance = new DialedFragment();
        }
        return mInstance;
    }

    @Override
    public void onRefresh() {
        //swipeView.setRefreshing(true);
    }
}
