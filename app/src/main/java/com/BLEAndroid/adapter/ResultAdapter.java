package com.BLEAndroid.adapter;

import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.BLEAndroid.R;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private final Context context;
    private final List<BluetoothGattService> bluetoothGattServices;

    /**
     * Create constructor and initialize value
     **/
   public ResultAdapter(Context context) {
        this.context = context;
        bluetoothGattServices = new ArrayList<>();
    }

    public void addResult(BluetoothGattService service) {
        bluetoothGattServices.add(service);
    }

    public void clear() {
        bluetoothGattServices.clear();
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_service, parent, false);
        return new ViewHolder(listItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder holder, int position) {

        final BluetoothGattService service = bluetoothGattServices.get(position);
        String uuid = service.getUuid().toString();

        holder.txt_title.setText(context.getString(R.string.service) + "(" + position + ")");
        holder.txt_uuid.setText(uuid);
        holder.txt_type.setText(context.getString(R.string.type));
        holder.llMainRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.Onclick(service);
            }
        });

    }

    /**
     * Create Interface
     **/
    public interface OnDeviceClickListener {

        void Onclick(BluetoothGattService bleDevice);
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
        return bluetoothGattServices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title, txt_uuid, txt_type;
        LinearLayout llMainRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_uuid = itemView.findViewById(R.id.txt_uuid);
            txt_type = itemView.findViewById(R.id.txt_type);
            llMainRoot = itemView.findViewById(R.id.llMainRoot);
        }
    }
}
