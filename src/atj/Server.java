package atj;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import atj.communicator.CommunicatorMessageData;
import atj.communicator.CommunicatorService;
import atj.communicator.IMessageDataCollector;
import atj.communicator.IMessageNotifier;

public class Server implements IMessageDataCollector {

	CommunicatorService communicatorService;
	IMessageNotifier messageNotifier;

	private List<CommunicatorMessageData> messages = new ArrayList<>();

	public Server(IMessageNotifier messageNotifier) {
		this.messageNotifier = messageNotifier;
  	    try {
			communicatorService = new CommunicatorService(this, true);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Server() {
		this(null);
	}
	
	public void run() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		while (true) {
			try {
				System.out.println(LocalDateTime.now().format(formatter)+" > Server is running (health check)");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<CommunicatorMessageData> getMessages() {
		return messages;
	}

	@Override
	public CommunicatorMessageData getLastMessage() {
		return messages.size() == 0 ? null : messages.get(messages.size() - 1);
	}

	@Override
	public void addMessageData(CommunicatorMessageData message) {
		messages.add(message);
		communicatorService.send(message.getName(), message.getMessage());
		notifyNewMessage();
	}

	public void notifyNewMessage() {
		System.out.println("new message!");
		if (messageNotifier != null) {
			messageNotifier.newMessage();
		}
	}

}
