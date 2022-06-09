package com.nicolas.nfcreaderjava.nfc.se;

import com.nicolas.nfcreaderjava.nfc.se.states.CommandResult;
import com.nicolas.nfcreaderjava.nfc.se.states.InitialState;
import com.nicolas.nfcreaderjava.nfc.se.states.State;
import com.nicolas.nfcreaderjava.log.Log;

public class SecureElement {
    private static final String TAG = "SoftwareSEWrapper";
    private State state;

    public SecureElement(Application[] applications) {
        this.state = new InitialState(applications);
    }

    byte[] processCommand(Command command) {
        Log.i(TAG, "processCommand()");

        CommandResult result = this.state.processCommand(command);
        this.state = result.getState();
        return result.getResponse();
    }
}
