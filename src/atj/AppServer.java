package atj;


import atj.communicator.IMessageNotifier;

public class AppServer implements IMessageNotifier {
	
	Server server;

	public static void main(String[] args) {
		Server server = new Server();	
		server.run();    		
	}

	@Override
	public void newMessage() {
		System.out.println(server.getLastMessage());
	}
	

}
