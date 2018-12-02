package atj;

import javax.jms.JMSException;

import atj.communicator.CommunicatorService;
import atj.communicator.IMessageNotifier;

public class AppClient implements  IMessageNotifier{
	
	private Client client;
	
	public static void main(String arg[]) {
		AppClient app = new AppClient(); 
 			
		try {
			
			app.send("Hello");
			Thread.sleep(100);
			app.send("Hello1");
			Thread.sleep(2000);
			app.send("Hello2");
			Thread.sleep(2000);
			app.send("Hello3");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AppClient() {
		client = new Client("Piotr", this);	
	}
	
	public void send(String text) {
		client.send(text);
	}
	
	
	
	public void newMessage() {
//		client.getMessages().forEach(System.out::println);
		System.out.println("Client messege received: "+client.getLastMessage());
	}

}
