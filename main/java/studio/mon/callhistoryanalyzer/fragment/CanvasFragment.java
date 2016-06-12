package studio.mon.callhistoryanalyzer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;

import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.core.Common;
import studio.mon.callhistoryanalyzer.core.Constants;
import studio.mon.callhistoryanalyzer.core.CoreActivity;
import studio.mon.callhistoryanalyzer.core.CoreFragment;

public class CanvasFragment extends CoreFragment implements View.OnClickListener{

	private LinearLayout tabMissed, tabReceived, tabDialed, tabTotal;
	private LinearLayout mSearchBlock, /*mTitleBlock,*/ idSort;
	private ImageView imMissed, imReceived ,imDialed, imTotal;
//	private TextView mTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_canvas, container, false);
		initViews(view);
		initModels();
		initListener();
		initAnimations();

		return view;
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	public void changeColor(int imgId) {
		if (imgId == imMissed.getId()) {
			imMissed.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			imMissed.setBackgroundColor(Color.rgb(128, 203, 196));
		}
		if (imgId == imDialed.getId()) {
			imDialed.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			imDialed.setBackgroundColor(Color.rgb(128, 203, 196));
		}
		if (imgId == imReceived.getId()) {
			imReceived.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			imReceived.setBackgroundColor(Color.rgb(128, 203, 196));
		}
		if (imgId == imTotal.getId()) {
			imTotal.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			imTotal.setBackgroundColor(Color.rgb(128, 203, 196));
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tab_missed:
				mPager.setCurrentItem(0);
				changeColor(imMissed.getId());
				break;
			case R.id.tab_received:
				mPager.setCurrentItem(1);
				changeColor(imReceived.getId());
				break;
			case R.id.tab_dialed:
				mPager.setCurrentItem(2);
				changeColor(imDialed.getId());
				break;
			case R.id.tab_total:
				mPager.setCurrentItem(3);
				changeColor(imTotal.getId());
				break;
		default:
			break;
		}
	}

	public static final int NUM_PAGES = 4;
	public static ViewPager mPager;
	public PagerAdapter mPagerAdapter;

	public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public CoreFragment getItem(int position) {
			switch (position) {
				case 0:
					return MissedFragment.getInstance(mActivity);
				case 1:
					return ReceivedFragment.getInstance(mActivity);
				case 2:
					return DialedFragment.getInstance(mActivity);
				case 3:
					return TotalFragment.getInstance(mActivity);
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	public void changeTabState(boolean missed, boolean received, boolean dial, boolean total) {
		if(missed){
			changeColor(imMissed.getId());
		}
		if(received){
			changeColor(imReceived.getId());
		}
		if(dial){
			changeColor(imDialed.getId());
		}
		if(total){
			changeColor(imTotal.getId());
		}
	}

	public void initModels() {
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		mPager.setOffscreenPageLimit(NUM_PAGES);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch (mPager.getCurrentItem()) {
					case 0:
						changeTabState(true, false, false, false);
						break;
					case 1:
						changeTabState(false, true, false, false);
						break;
					case 2:
						changeTabState(false, false, true , false);
						break;
					case 3:
						changeTabState(false, false, false , true);
						break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		
		mPager.setCurrentItem(firstTab);
		changeTabState(true, false, false, false);

	}

	@Override
	protected void initViews(View v) {
		mPager = (ViewPager) v.findViewById(R.id.viewpager);
		tabMissed = (LinearLayout) v.findViewById(R.id.tab_missed);
		tabReceived = (LinearLayout) v.findViewById(R.id.tab_received);
		tabDialed = (LinearLayout) v.findViewById(R.id.tab_dialed);
		tabTotal = (LinearLayout) v.findViewById(R.id.tab_total);
//		mTitle = (TextView) v.findViewById(R.id.fragment_canvas_title);
//		mTitleBlock = (LinearLayout) v.findViewById(R.id.fragment_canvas_title_block);
		imMissed = (ImageView) v.findViewById(R.id.imMissed);
		imReceived = (ImageView) v.findViewById(R.id.imReceived);
		imDialed = (ImageView) v.findViewById(R.id.imDialed);
		imTotal = (ImageView) v.findViewById(R.id.imTotal);
	}


	@Override
	protected void initListener() {
		tabMissed.setOnClickListener(this);
		tabReceived.setOnClickListener(this);
		tabDialed.setOnClickListener(this);
		tabTotal.setOnClickListener(this);

	}



	@Override
	protected void initAnimations() {

	}

	public static int firstTab = 0;
	public static CoreActivity mActivity;
	public static CanvasFragment mInstance;

	public static CanvasFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new CanvasFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static CanvasFragment getInstance() {
		if (mInstance == null) {
			mInstance = new CanvasFragment();
		}
		return mInstance;
	}

	@Override
	public void onResume(){
	    super.onResume();
	}
}
