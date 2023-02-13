package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;
import no.hvl.dat110.system.display.DisplayImpl;
import no.hvl.dat110.system.sensor.SensorImpl;

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public void write (String message) {

		byte [] request = RPCUtils.marshallString(message);
		byte [] response = rpcclient.call((byte) Common.WRITE_RPCID,request);
		// implement marshalling, call and unmarshalling for write RPC method
		RPCUtils.unmarshallVoid(response);
	}
}
