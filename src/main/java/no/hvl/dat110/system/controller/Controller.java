package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;
import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.system.display.DisplayImpl;

import java.io.IOException;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) {
		
		DisplayStub display;
		SensorStub sensor;
		
		RPCClient displayclient,sensorclient;
		
		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
		displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);
		
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);

		// create local display and sensor stub objects
		display = new DisplayStub(displayclient);
		sensor = new SensorStub(sensorclient);

		// connect to sensor and display RPC servers
		try {
			sensorclient.connect();
			displayclient.connect();

		} catch (IOException e) { }

		// read value from sensor using RPC and write to display using RPC
			for (int i =0; i<N; i++){
				String temp = Integer.toString(sensor.read());
				display.write(temp);
				try {
					// To get different temperatures
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


		stopdisplay.stop();
		stopsensor.stop();


		displayclient.disconnect();
		sensorclient.disconnect();
		

		System.out.println("Controller stopping ...");
		
	}


}
