package com.nicolas.nfcreaderjava.nfc.se.states;

import com.nicolas.nfcreaderjava.nfc.se.Command;

public abstract class State {
    public abstract CommandResult processCommand(Command c);
}

