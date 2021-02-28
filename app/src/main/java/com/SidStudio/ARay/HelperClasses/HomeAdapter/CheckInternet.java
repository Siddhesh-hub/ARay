package com.SidStudio.ARay.HelperClasses.HomeAdapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.SidStudio.ARay.SetNewPassword;

public class CheckInternet {

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())){
            return true;
        } else{
            return false;
        }
    }
}
