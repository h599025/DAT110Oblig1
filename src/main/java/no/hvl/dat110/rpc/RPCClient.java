package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

import java.io.IOException;
import java.net.Socket;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() throws IOException {
		// connect using the RPC client
		connection = msgclient.connect();

	}
	
	public void disconnect() {
		connection.close();
	}


	/*
	 Make a remote call om the method on the RPC server by sending an RPC request message and receive an RPC reply message

	 rpcid is the identifier on the server side of the method to be called
	 param is the marshalled parameter of the method to be called
 	*/
	public byte[] call(byte rpcid, byte[] param){
		byte []rpcRequest = RPCUtils.encapsulate(rpcid,param);
		Message messageReply = null;
		Message message = new Message(rpcRequest);
		try {
			connection.send(message);
			messageReply = connection.receive();
			return RPCUtils.decapsulate(messageReply.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
