package com.nicolas.nfcreaderjava.nfc.se.states;

import com.nicolas.nfcreaderjava.log.Log;
import com.nicolas.nfcreaderjava.nfc.se.Application;
import com.nicolas.nfcreaderjava.nfc.se.Command;
import com.nicolas.nfcreaderjava.nfc.se.desfire.AID;
import com.nicolas.nfcreaderjava.nfc.se.desfire.Commands;
import com.nicolas.nfcreaderjava.nfc.se.desfire.InvalidParameterException;
import com.nicolas.nfcreaderjava.nfc.se.desfire.ResponseCodes;

public class InitialState extends State {
    private static final String TAG = "InitialState";
    private final Application[] applications;

    public InitialState(Application[] applications) {
        this.applications = applications;
    }

    public CommandResult processCommand(Command command) {
        Log.i(TAG, "processCommand()");

        if (command.getCode() == Commands.SELECT_APPLICATION) {
            return selectApplication(command.getData());
        } else {
            return new CommandResult(this, ResponseCodes.ILLEGAL_COMMAND);
        }
    }

    private CommandResult selectApplication(byte[] aid) {
        Log.i(TAG, "selectApplication()");

        try {
            AID aidToSelect = new AID(aid);
            return new CommandResult(selectApplication(aidToSelect), ResponseCodes.SUCCESS);
        } catch (InvalidParameterException ex) {
            return new CommandResult(this, ResponseCodes.LENGTH_ERROR);
        } catch (ApplicationNotFoundException ex) {
            return new CommandResult(this, ResponseCodes.APPLICATION_NOT_FOUND);
        }
    }

    private ApplicationSelectedState selectApplication(AID aidToSelect) throws ApplicationNotFoundException {
        Log.i(TAG, "selectApplication()");
        for (Application a : applications) {
            if (a.getAid().equals(aidToSelect)) {
                return new ApplicationSelectedState(a);
            }
        }
        throw new ApplicationNotFoundException();
    }

}