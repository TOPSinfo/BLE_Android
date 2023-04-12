package com.BLEAndroid.comm;


import com.BLEAndroid.data.BleDevice;


/**
 * Observer interface for
 * disconnection event
 * */

public interface Observer {

    void disConnected(BleDevice bleDevice);
}
