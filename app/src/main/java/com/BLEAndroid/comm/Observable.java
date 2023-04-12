package com.BLEAndroid.comm;


import com.BLEAndroid.data.BleDevice;


/**
 *  Observable interface to
 *  handle event
 *  addObserver
 *  deleteObserver
 *  notifyObserver
 * */

public interface Observable {

    void addObserver(Observer obj);

    void deleteObserver(Observer obj);

    void notifyObserver(BleDevice bleDevice);
}
