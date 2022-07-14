/********************************************************************************************************
 * @file OnlineDeviceListAdapter.java
 *
 * @brief for TLSR chips
 *
 * @author telink
 * @date Sep. 30, 2017
 *
 * @par Copyright (c) 2017, Telink Semiconductor (Shanghai) Co., Ltd. ("TELINK")
 *
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *
 *              http://www.apache.org/licenses/LICENSE-2.0
 *
 *          Unless required by applicable law or agreed to in writing, software
 *          distributed under the License is distributed on an "AS IS" BASIS,
 *          WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *          See the License for the specific language governing permissions and
 *          limitations under the License.
 *******************************************************************************************************/
package com.telink.ble.mesh.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.telink.ble.mesh.demo.R;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.model.NodeInfo;
import com.telink.ble.mesh.ui.IconGenerator;
import com.telink.ble.mesh.util.Arrays;

import java.util.List;

/**
 * online devices
 * Created by Administrator on 2016/10/25.
 */
public class OnlineDeviceListAdapter extends BaseRecyclerViewAdapter<OnlineDeviceListAdapter.ViewHolder> {
    List<NodeInfo> mDevices;
    Context mContext;

    CardView card;

    public OnlineDeviceListAdapter(Context context, List<NodeInfo> devices) {
        mContext = context;
        mDevices = devices;
    }

    public void resetDevices(List<NodeInfo> devices) {
        this.mDevices = devices;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_online_device, null, false);
        ViewHolder holder = new ViewHolder(itemView);
        holder.tv_name = itemView.findViewById(R.id.tv_name);
        holder.img_icon = itemView.findViewById(R.id.img_icon);
        holder.tv_device_key = itemView.findViewById(R.id.tv_device_key);
        holder.tv_device_uuid = itemView.findViewById(R.id.tv_device_uuid);
        holder.tv_element_cnt = itemView.findViewById(R.id.tv_element_cnt);
        holder.tv_mac_address = itemView.findViewById(R.id.tv_mac_address);
        holder.tv_mesh_address = itemView.findViewById(R.id.tv_mesh_address);
        holder.tv_online_state = itemView.findViewById(R.id.tv_online_state);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mDevices == null ? 0 : mDevices.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        NodeInfo device = mDevices.get(position);
        final int pid = device.compositionData != null ? device.compositionData.pid : 0;
        holder.img_icon.setImageResource(IconGenerator.getIcon(pid, device.getOnlineState()));

        if (device.meshAddress == MeshService.getInstance().getDirectConnectedNodeAddress()) {
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        /*if (device.getOnOff() == -1) {
        //    holder.img_icon.setImageResource(R.drawable.icon_light_offline);
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            if (device.macAddress != null && device.macAddress.equals(MeshService.getInstance().getCurDeviceMac())) {
                holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            //        holder.tv_name.setText(models.get(position).getAddress());
        }*/
        String info;
        if (device.meshAddress <= 0xFF) {
            info = String.format("%02X", device.meshAddress);
        } else {
            info = String.format("%04X", device.meshAddress);
        }
        if (device.bound) {
            info += (device.compositionData.cid == 0x0211 ? "(Pid-" + String.format("%02X", device.compositionData.pid) + ")"
                    : "(cid-" + String.format("%02X", device.compositionData.cid) + ")");
            /*if (device.nodeInfo.cpsData.lowPowerSupport()) {
                info += "LPN";                 ", networkKey=" + Arrays.bytesToHexString(networkKey) +

            }*/
            /*" : " +(device.getOnOff() == 1 ? Math.max(0, device.lum) : 0) + " : " +
                    device.temp*/
        } else {
            info += "(unbound)";
        }
        holder.tv_name.setText(info);
        holder.tv_device_key.setText(Arrays.bytesToHexString(device.deviceKey));
        holder.tv_device_uuid.setText(Arrays.bytesToHexString(device.deviceUUID));
        holder.tv_element_cnt.setText(String.valueOf(device.elementCnt));
        holder.tv_mac_address.setText(device.macAddress);
        holder.tv_mesh_address.setText(String.valueOf(device.meshAddress));
        holder.tv_online_state.setText(device.getOnlineState().toString());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_icon;
        public TextView tv_name;
        public TextView tv_device_key;
        public TextView tv_device_uuid;
        public TextView tv_element_cnt;
        public TextView tv_mac_address;
        public TextView tv_mesh_address;
        public TextView tv_online_state;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
