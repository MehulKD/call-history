package studio.mon.callhistoryanalyzer.service;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        storeCall(ctx, number, start, end, Constants.RECEIVED_CALL);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        storeCall(ctx, number, start, end, Constants.DIALED_CALL);
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.i("PEARLO", "MissedCall");
        storeCall(ctx, number, start, null, Constants.MISSED_CALL);
    }

    private void storeCall(Context ctx, String number, Date start, Date end, String type) {
        DBHelper dbHelper = new DBHelper(ctx);
        CallAnalyzer callAnalyzer = new CallAnalyzer();
        callAnalyzer.setName(getContactName(ctx, number));
        callAnalyzer.setNumber(number);
        callAnalyzer.setType(type);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        callAnalyzer.setTime(dt.format((start)));
        if (end != null) {
            callAnalyzer.setDuration(getDiffSeconds(start,end)+ "s");
        } else {
            callAnalyzer.setDuration("0");
        }
        dbHelper.add(callAnalyzer);
    }

    public long getDiffSeconds(Date start, Date end) {
        return (end.getTime() - start.getTime())/ DateUtils.SECOND_IN_MILLIS;
    }

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName == null ? "Unknown" : contactName;
    }
}