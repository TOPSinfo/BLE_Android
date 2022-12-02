package com.BLEAndroid.operation;


import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.BLEAndroid.R;
import com.BLEAndroid.adapter.CharacteristicsResultAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ListOfCharacteristicFragment extends Fragment {

    private CharacteristicsResultAdapter mResultAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_characteric_list, null);
        initView(v);
        return v;
    }

    /**
     * Initialize views
     **/
    private void initView(View v) {
        mResultAdapter = new CharacteristicsResultAdapter(getActivity());
        RecyclerView listView_device = v.findViewById(R.id.list_service);
        listView_device.setHasFixedSize(true);
        listView_device.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView_device.setAdapter(mResultAdapter);
        mResultAdapter.setOnDeviceClickListener(new CharacteristicsResultAdapter.OnDeviceClickListener() {
            @Override
            public void Onclick(final BluetoothGattCharacteristic characteristic) {
                final List<Integer> propList = new ArrayList<>();
                List<String> propNameList = new ArrayList<>();
                int charaProp = characteristic.getProperties();
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    propList.add(DetailsCharacteristicFragment.PROPERTY_READ);
                    propNameList.add("Read");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                    propList.add(DetailsCharacteristicFragment.PROPERTY_WRITE);
                    propNameList.add("Write");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
                    propList.add(DetailsCharacteristicFragment.PROPERTY_WRITE_NO_RESPONSE);
                    propNameList.add("Write No Response");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    propList.add(DetailsCharacteristicFragment.PROPERTY_NOTIFY);
                    propNameList.add("Notify");
                }
                if ((charaProp & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
                    propList.add(DetailsCharacteristicFragment.PROPERTY_INDICATE);
                    propNameList.add("Indicate");
                }

                if (propList.size() > 1) {
                    new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                            .setTitle(getActivity().getString(R.string.select_operation_type))
                            .setItems(propNameList.toArray(new String[propNameList.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((DeviceDetailsActivity) getActivity()).setCharacteristic(characteristic);
                                    ((DeviceDetailsActivity) getActivity()).setCharaProp(propList.get(which));
                                    ((DeviceDetailsActivity) getActivity()).changePage(2);
                                }
                            })
                            .show();
                } else if (propList.size() > 0) {
                    ((DeviceDetailsActivity) getActivity()).setCharacteristic(characteristic);
                    ((DeviceDetailsActivity) getActivity()).setCharaProp(propList.get(0));
                    ((DeviceDetailsActivity) getActivity()).changePage(2);
                }
            }
        });
    }

    /**
     * show data
     **/
    public void showData() {
        BluetoothGattService service = ((DeviceDetailsActivity) Objects.requireNonNull(getActivity())).getBluetoothGattService();
        mResultAdapter.clear();
        for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
            mResultAdapter.addResult(characteristic);
        }
        mResultAdapter.notifyDataSetChanged();
    }
}
