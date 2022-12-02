package com.BLEAndroid.operation;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.BLEAndroid.BleManager;
import com.BLEAndroid.R;
import com.BLEAndroid.adapter.ResultAdapter;
import com.BLEAndroid.data.BleDevice;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ServiceListFragment extends Fragment {

    private TextView txt_name, txt_mac;
    private ResultAdapter mResultAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_list, null);
        initView(v);
        showData();
        return v;
    }

    /**
     * Initialize views
     **/
    private void initView(View v) {
        txt_name = v.findViewById(R.id.txt_name);
        txt_mac = v.findViewById(R.id.txt_mac);
        RecyclerView rvList = v.findViewById(R.id.list_service);

        /*Set adapter*/
        mResultAdapter = new ResultAdapter(getActivity());
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(mResultAdapter);
        mResultAdapter.setOnDeviceClickListener(new ResultAdapter.OnDeviceClickListener() {
            @Override
            public void Onclick(BluetoothGattService bleDevice) {
                ((DeviceDetailsActivity) getActivity()).setBluetoothGattService(bleDevice);
                ((DeviceDetailsActivity) getActivity()).changePage(1);
            }
        });
    }

    /**
     * Set data
     **/
    private void showData() {
        BleDevice bleDevice = ((DeviceDetailsActivity) getActivity()).getBleDevice();
        String name = bleDevice.getName();
        String mac = bleDevice.getMac();
        BluetoothGatt gatt = BleManager.getInstance().getBluetoothGatt(bleDevice);

        txt_name.setText(getActivity().getString(R.string.name) + name);
        txt_mac.setText(getActivity().getString(R.string.mac) + mac);

        mResultAdapter.clear();
        for (BluetoothGattService service : gatt.getServices()) {
            mResultAdapter.addResult(service);
        }
        mResultAdapter.notifyDataSetChanged();
    }
}
