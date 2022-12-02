package com.BLEAndroid.comm;


import com.BLEAndroid.data.BleDevice;

public interface Observer {

    void disConnected(BleDevice bleDevice);
}
