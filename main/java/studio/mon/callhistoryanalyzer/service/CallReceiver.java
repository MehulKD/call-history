package studio.mon.callhistoryanalyzer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import studio.mon.callhistoryanalyzer.core.Constants;
import studio.mon.callhistoryanalyzer.core.DBHelper;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

public class CallReceiver extends PhoneCallReceiver {

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.i("PEARLO", "Outgoing started");
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.i("PEARLO", "Outgoing started");
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.i("PEARLO", "Outgoing Ended");
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.i("PEARLO", "MissedCall");
    }

}