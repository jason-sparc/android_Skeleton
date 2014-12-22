package me.shkschneider.skeleton.network;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;

import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.helpers.SystemHelper;
import me.shkschneider.skeleton.helpers.SystemProperties;
import me.shkschneider.skeleton.helpers.SystemServices;
import me.shkschneider.skeleton.java.StringHelper;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkHelper {

    public static Integer type() {
        final NetworkInfo networkInfo = SystemServices.connectivity().getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.warning("NetworkInfo was NULL");
            return null;
        }
        if (! networkInfo.isConnected()) {
            LogHelper.warning("Disconnected");
            return null;
        }

        return networkInfo.getType();
    }

    public static NetworkInfo.State state() {
        final NetworkInfo networkInfo = SystemServices.connectivity().getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.warning("NetworkInfo was NULL");
            return null;
        }

        return networkInfo.getState();
    }

    @Deprecated
    public static String hostname(@NonNull final Activity activity) {
        // FIXME return InetAddress.getLocalHost().getHostName();
        return null;
    }

    public static String userAgent() {
        final String userAgent = SystemProperties.httpAgent();
        return (userAgent != null ? userAgent : String.format("%s-%debug", AndroidHelper.PLATFORM, AndroidHelper.api()));
    }

    public static boolean online() {
        final NetworkInfo networkInfo = SystemServices.connectivity().getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.warning("NetworkInfo was NULL");
            return false;
        }

        return networkInfo.isConnected();
    }

    public static boolean onlineOrConnecting() {
        final NetworkInfo networkInfo = SystemServices.connectivity().getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.warning("NetworkInfo was NULL");
            return false;
        }

        return networkInfo.isConnectedOrConnecting();
    }

    public static boolean wifiEnabled() {
        final WifiManager wifiManager = SystemServices.wifi();
        if (wifiManager == null) {
            LogHelper.warning("WifiManager was NULL");
            return false;
        }

        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
    }

    public static String macAddress() {
        final WifiManager wifiManager = SystemServices.wifi();
        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String macAddress = wifiInfo.getMacAddress();
        if (StringHelper.nullOrEmpty(macAddress)) {
            LogHelper.warning("MacAddress was NULL");
            return null;
        }

        return macAddress;
    }

    public static boolean validUrl(@NonNull final String url) {
        return URLUtil.isValidUrl(url);
    }

    public static List<String> ipAddresses() {
        try {
            final List<String> ipAddresses = new ArrayList<String>();
            for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                    final InetAddress inetAddress = enumerationInetAddress.nextElement();
                    final String ipAddress = inetAddress.getHostAddress();
                    if (! inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipAddress)) {
                        ipAddresses.add(ipAddress);
                    }
                }
            }
            return ipAddresses;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String ipAddress() {
        try {
            for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                    final InetAddress inetAddress = enumerationInetAddress.nextElement();
                    final String ipAddress = inetAddress.getHostAddress();
                    if (! inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipAddress)) {
                        return ipAddress;
                    }
                }
            }
            return null;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}