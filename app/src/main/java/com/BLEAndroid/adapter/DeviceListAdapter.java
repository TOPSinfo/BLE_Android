package com.BLEAndroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.BLEAndroid.BleManager;
import com.BLEAndroid.R;
import com.BLEAndroid.data.BleDevice;

import java.util.ArrayList;
import java.util.List;


/**
 *  Adapter class for nearby ble devices list
 * */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {

    private final Context context;
    private final List<BleDevice> bleDeviceList = new ArrayList<>();

    /**
     * Create constructor and initialize value
     **/
    public DeviceListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DeviceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.adapter_device, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceListAdapter.ViewHolder holder, int position) {
        final BleDevice bleDevice = bleDeviceList.get(position);

        if (bleDevice != null) {
            final boolean isConnected = BleManager.getInstance().isConnected(bleDevice);
            String name = bleDevice.getName();
            String mac = bleDevice.getMac();
            int rssi = bleDevice.getRssi();
            if (name == null) {
                holder.txt_name.setVisibility(View.GONE);
            } else {
                holder.txt_name.setVisibility(View.VISIBLE);
            }
            holder.txt_name.setText(name);
            holder.txt_mac.setText(mac);
            holder.txt_rssi.setText(String.valueOf(rssi));
            if (isConnected) {
                holder.img_blue.setVisibility(View.INVISIBLE);
                holder.imgWifi.setColorFilter(ContextCompat.getColor(context, R.color.colorGreen));
                holder.txt_name.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                holder.txt_mac.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                holder.txt_rssi.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                holder.layout_idle.setVisibility(View.GONE);
                holder.layout_connected.setVisibility(View.VISIBLE);

            } else {
                holder.imgWifi.setColorFilter(ContextCompat.getColor(context, R.color.black));
                holder.img_blue.setVisibility(View.VISIBLE);
                holder.img_blue.setImageResource(R.mipmap.ic_blue_remote);
                holder.txt_name.setTextColor(0xFF000000);
                holder.txt_mac.setTextColor(0xFF000000);
                holder.txt_rssi.setTextColor(0xFF000000);
                holder.layout_idle.setVisibility(View.VISIBLE);
                holder.layout_connected.setVisibility(View.GONE);
            }
            holder.llMainRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onDetail(bleDevice, isConnected);
                    }
                }
            });

        }


        holder.btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onConnect(bleDevice);
                }
            }
        });

        holder.btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDisConnect(bleDevice);
                }
            }
        });
    }

    /**
     * Add device list
     **/
    public void addDevice(BleDevice bleDevice) {
        removeDevice(bleDevice);
        bleDeviceList.add(0, bleDevice);
    }

    /**
     * Remove device form list
     **/
    public void removeDevice(BleDevice bleDevice) {
        for (int i = 0; i < bleDeviceList.size(); i++) {
            BleDevice device = bleDeviceList.get(i);
            if (bleDevice.getKey().equals(device.getKey())) {
                bleDeviceList.remove(i);
            }
        }
    }


    /**
     * clear disconnect devices
     **/
    public void clearConnectedDevice() {
        for (int i = 0; i < bleDeviceList.size(); i++) {
            BleDevice device = bleDeviceList.get(i);
            if (BleManager.getInstance().isConnected(device)) {
                bleDeviceList.remove(i);
            }
        }
    }

    /**
     * clear Scan device
     **/
    public void clearScanDevice() {
        for (int i = 0; i < bleDeviceList.size(); i++) {
            BleDevice device = bleDeviceList.get(i);
            if (!BleManager.getInstance().isConnected(device)) {
                bleDeviceList.remove(i);
            }
        }
    }

    /**
     * clear all connected and Scan devices
     **/
    public void clear() {
        clearConnectedDevice();
        clearScanDevice();
    }

    @Override
    public int getItemCount() {
        return bleDeviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_blue, imgWifi;
        TextView txt_name, txt_mac, txt_rssi, btn_disconnect, btn_connect;
        LinearLayout layout_idle, llMainRoot, layout_connected;

        public ViewHolder(View itemView) {
            super(itemView);
            img_blue = itemView.findViewById(R.id.img_blue);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_mac = itemView.findViewById(R.id.txt_mac);
            txt_rssi = itemView.findViewById(R.id.txt_rssi);
            layout_idle = itemView.findViewById(R.id.layout_idle);
            layout_connected = itemView.findViewById(R.id.layout_connected);
            btn_disconnect = itemView.findViewById(R.id.btn_disconnect);
            btn_connect = itemView.findViewById(R.id.btn_connect);
            llMainRoot = itemView.findViewById(R.id.llMainRoot);
            imgWifi = itemView.findViewById(R.id.imgWifi);
        }
    }

    /**
     * Create Interface
     **/
    public interface OnDeviceClickListener {
        void onConnect(BleDevice bleDevice);

        void onDisConnect(BleDevice bleDevice);

        void onDetail(BleDevice bleDevice, boolean isConnected);
    }

    private OnDeviceClickListener mListener;


    /**
     * Initialize Interface
     **/
    public void setOnDeviceClickListener(OnDeviceClickListener listener) {
        this.mListener = listener;
    }
}
