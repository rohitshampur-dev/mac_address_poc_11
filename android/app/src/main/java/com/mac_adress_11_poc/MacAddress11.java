package com.mac_adress_11_poc;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
public class MacAddress11 extends ReactContextBaseJavaModule{
    private static final String TAG = "MAC ADDRESS" ;
    Context mContext;
    MacAddress11(ReactApplicationContext context) {
        super(context);
        mContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "MacAddress11Module";
    }
    @ReactMethod
    public void getIPv6Address(String iface, Promise promise){
        String ipv6 = getIPV6Address(iface);
        if(ipv6!=null){
            promise.resolve(ipv6);
        }else{
            promise.reject("404","IPv6 address not available");
        }
    }

    public String getIPV6Address(String iface) {
        try {
            WifiManager wifiMgr = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            if (wifiMgr.isWifiEnabled()) {
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                Log.d(TAG, "getIPV6Address: "+wifiInfo.getSupplicantState());
                NetworkInterface intf = NetworkInterface.getByName(iface);
                Log.d(TAG, "onCreate: "+intf.getDisplayName());
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    String sAddr = addr.getHostAddress();
                    if (!addr.isLoopbackAddress()) {
                        int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                        return delim<0 ? sAddr : sAddr.substring(0, delim);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
