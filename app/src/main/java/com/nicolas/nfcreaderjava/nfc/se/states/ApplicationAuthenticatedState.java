package com.nicolas.nfcreaderjava.nfc.se.states;

import com.nicolas.nfcreaderjava.log.Log;
import com.nicolas.nfcreaderjava.nfc.ByteUtils;
import com.nicolas.nfcreaderjava.nfc.se.Application;
import com.nicolas.nfcreaderjava.nfc.se.Command;
import com.nicolas.nfcreaderjava.nfc.se.desfire.Commands;
import com.nicolas.nfcreaderjava.nfc.se.desfire.ResponseCodes;
import com.nicolas.nfcreaderjava.nfc.se.desfire.File;


public class ApplicationAuthenticatedState extends State {
    private static final String TAG = "ApplicationAuthenticatedState";
    private final Application application;
    private final byte[] sessionKey;

    ApplicationAuthenticatedState(Application application, byte[] sessionKey) {
        this.application = application;
        this.sessionKey = sessionKey;
    }

    public CommandResult processCommand(Command command) {
        Log.i(TAG, "processCommand()");

        if (command.getCode() == Commands.READ_DATA) {
            byte[] commandData = command.getData();
            if (commandData.length == 7) {
                return readData(commandData);
            } else {
                return new CommandResult(this, ResponseCodes.LENGTH_ERROR);
            }
        } else {
            return new CommandResult(this, ResponseCodes.ILLEGAL_COMMAND);
        }
    }

    private CommandResult readData(byte[] commandData) {
        byte fileNumber = commandData[0];
        if (fileNumber == 0) {
            File file = application.getFile0();
            return readFile(file, commandData);
        } else {
            return new CommandResult(this, ResponseCodes.FILE_NOT_FOUND);
        }
    }

    private CommandResult readFile(File file, byte[] commandData) {
        byte[] offsetBytes = new byte[] {commandData[1], commandData[2], commandData[3]};
        byte[] lengthBytes = new byte[] {commandData[4], commandData[5], commandData[6]};
        int offset = ByteUtils.threeBytesToInt(offsetBytes);
        int length = ByteUtils.threeBytesToInt(lengthBytes);

        try {
            byte[] data = file.readData(offset, length);
            data = ByteUtils.concatenate(data, getCRC(data));
            return new CommandResult(this, ByteUtils.concatenate(ResponseCodes.SUCCESS, data));
        } catch (Exception e) {
            e.printStackTrace();
            return new CommandResult(this, ResponseCodes.BOUNDARY_ERROR);
        }
    }

    private byte[] getCRC(byte[] data) {
        Log.i(TAG, "sessionKey: " + ByteUtils.toHexString(sessionKey));
        Log.i(TAG, "generating CRC for: " + ByteUtils.toHexString(data));

        // TODO: implement CRC
        return new byte[8];
    }

}