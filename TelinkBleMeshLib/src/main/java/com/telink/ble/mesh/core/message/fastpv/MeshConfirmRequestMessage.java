/********************************************************************************************************
 * @file MeshConfirmRequestMessage.java
 *
 * @brief for TLSR chips
 *
 * @author telink
 * @date     Sep. 30, 2017
 *
 * @par     Copyright (c) 2017, Telink Semiconductor (Shanghai) Co., Ltd. ("TELINK")
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
package com.telink.ble.mesh.core.message.fastpv;

import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.generic.GenericMessage;


public class MeshConfirmRequestMessage extends GenericMessage {

    public static MeshConfirmRequestMessage getSimple(int destinationAddress, int appKeyIndex) {
        MeshConfirmRequestMessage message = new MeshConfirmRequestMessage(destinationAddress, appKeyIndex);
        message.setResponseMax(0);
        message.setRetryCnt(1);
        return message;
    }

    public MeshConfirmRequestMessage(int destinationAddress, int appKeyIndex) {
        super(destinationAddress, appKeyIndex);
    }

    @Override
    public int getOpcode() {
        return Opcode.VD_MESH_PROV_CONFIRM.value;
    }

    @Override
    public int getResponseOpcode() {
        return Opcode.VD_MESH_PROV_CONFIRM_STS.value;
    }

    @Override
    public byte[] getParams() {
        return null;
    }

}
