package com.BLEAndroid.adapter;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.BLEAndroid.R;

import java.util.ArrayList;
import java.util.List;

public class CharacteristicsResultAdapter extends RecyclerView.Adapter<CharacteristicsResultAdapter.ViewHolder> {

    private final Context context;
    private final List<BluetoothGattCharacteristic> characteristicList;

    /**
     * Create constructor and initialize value
     **/
   public CharacteristicsResultAdapter(Context context) {
        this.context = context;
        characteristicList = new ArrayList<>();
    }

    public  void addResult(BluetoothGattCharacteristic characteristic) {
        characteristicList.add(characteristic);
    }

    public void clear() {
        characteristicList.clear();
    }


    @NonNull
    @Override
    public CharacteristicsResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_service, parent, false);
        return new ViewHolder(listItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onBindViewHolder(@NonNull CharacteristicsResultAdapter.ViewHolder holder, int position) {

        final BluetoothGattCharacteristic characteristic = characteristicList.get(position);
        String uuid = characteristic.getUuid().toString();

        holder.txt_title.setText(context.getString(R.string.characteristic) + "（" + position + ")");
        holder.txt_uuid.setText(uuid);

        StringBuilder property = new StringBuilder();
        int charaProp = characteristic.getProperties();
        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            property.append("Read");
            property.append(" , ");
        }
        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
            property.append("Write");
            property.append(" , ");
        }
        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
            property.append("Write No Response");
            property.append(" , ");
        }
        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            property.append("Notify");
            property.append(" , ");
        }
        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
            property.append("Indicate");
            property.append(" , ");
        }
        if (property.length() > 1) {
            property.delete(property.length() - 2, property.length() - 1);
        }
        if (property.length() > 0) {
            holder.txt_type.setText(context.getString(R.string.characteristic) + "( " + property + ")");
            holder.img_next.setVisibility(View.VISIBLE);
        } else {
            holder.img_next.setVisibility(View.INVISIBLE);
        }

        holder.llMainRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.Onclick(characteristic);
            }
        });

    }

    /**
     * Create Interface
     **/
    public interface OnDeviceClickListener {

        void Onclick(BluetoothGattCharacteristic characteristic);
    }

    private OnDeviceClickListener mListener;


    /**
     * Initialize Interface
     **/
    public void setOnDeviceClickListener(OnDeviceClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return characteristicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title, txt_uuid, txt_type;
        LinearLayout llMainRoot;
        ImageView img_next;


        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_uuid = itemView.findViewById(R.id.txt_uuid);
            txt_type = itemView.findViewById(R.id.txt_type);
            img_next = itemView.findViewById(R.id.img_next);
            llMainRoot = itemView.findViewById(R.id.llMainRoot);
        }
    }
}
