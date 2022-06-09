package com.nicolas.nfcreaderjava;

import static com.nicolas.nfcreaderjava.nfc.Iso7816.wrapApdu;

import android.content.Context;

import com.nicolas.nfcreaderjava.log.Log;
import com.nicolas.nfcreaderjava.nfc.ByteUtils;

import java.io.IOException;

class HCE {
    private static final String TAG = "HCE";

    static Response selectAndroidApp(Context context, IsoDep isoDep) throws IOException {
        Log.i(TAG, "selectAndroidApp()");

        String HCE_AID = context.getString(R.string.hce_aid);
        byte[] commandApdu = wrapApdu(ByteUtils.toByteArray(HCE_AID));
        return isoDep.transceive(commandApdu);
    }
}
