package project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.ws4d.coap.core.CoapClient;
import org.ws4d.coap.core.CoapConstants;
import org.ws4d.coap.core.connection.BasicCoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapChannelManager;
import org.ws4d.coap.core.connection.api.CoapClientChannel;
import org.ws4d.coap.core.enumerations.CoapMediaType;
import org.ws4d.coap.core.enumerations.CoapRequestCode;
import org.ws4d.coap.core.messages.api.CoapRequest;
import org.ws4d.coap.core.messages.api.CoapResponse;
import org.ws4d.coap.core.rest.CoapData;
import org.ws4d.coap.core.tools.Encoder;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


public class Pr_client extends JFrame implements CoapClient{
	private static final boolean exitAfterResponse = false;
	JButton btn_start = new JButton("Start Monitoring!");
	
	String led_state = "Not Detected";
	JButton btn_warning = new JButton("Warning");
	JTextArea payload_text = new JTextArea("", 1,1);//스크롤바 없음
	JTextArea display_text = new JTextArea();
	JScrollPane display_text_jp  = new JScrollPane(display_text);
	JLabel display_label = new JLabel("Display");
	
	
	CoapClientChannel clientChannel = null;
	
	int warning_cnt = 0;
	boolean detection_flag = false;

	
	public Pr_client (String serverAddress, int serverPort) {
		//제목 설정
		super("임베디드 프로젝트 Client");
		//레이아웃 설정
		this.setLayout(null);
		String sAddress = serverAddress;
		int sPort = serverPort;

		CoapChannelManager channelManager = BasicCoapChannelManager.getInstance();

		

		try {
			clientChannel = channelManager.connect(this, InetAddress.getByName(sAddress), sPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}


		if (null == clientChannel) {
			return;
		}

		//btn
		btn_start.setBounds(20, 670, 200, 50);
		
		btn_warning.setBounds(450, 670, 200, 50);


		
		btn_start.addActionListener(new ActionListener() {
			/**
			 *
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = "/pir";
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.GET, path, true);
				request.setToken(Encoder.StringToByte("ObToken"));
				request.setObserveOption(0);
				displayRequest(request);
				clientChannel.sendMessage(request);
				display_text.append(System.lineSeparator());
				display_text.append("START MONITORING SYSTEM");
				display_text.append(System.lineSeparator());
			}
		});

		
		btn_warning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path = "lcd";
				String payload = payload_text.getText();
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, path, true);
				request.setPayload(new CoapData(payload, CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
				display_text.append(System.lineSeparator());
				display_text.append("WARNING!");
				display_text.append(System.lineSeparator());
			}
		});
		
			
		
		display_label.setBounds(20, 10, 100, 20);
		display_text.setLineWrap(true);
		display_text.setFont(new Font("arian", Font.BOLD, 15));
		display_text_jp.setBounds(20, 40, 740, 430);
		

		
		
				
		this.add(btn_start);
		this.add(btn_warning);
		this.add(payload_text);
		this.add(display_text_jp);
		this.add(display_label);

		//프레임 크기 지정	
		this.setSize(800, 800);

		//프레임 보이기
		this.setVisible(true);

		//swing에만 있는 X버튼 클릭시 종료
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	@Override
	public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
		System.out.println("Connection Failed");
		System.exit(-1);
	}

	@Override
	public void onResponse(CoapClientChannel channel, CoapResponse response) {
		if (Encoder.ByteToString(response.getToken()).equals("ObToken")) {
			warning_function(Encoder.ByteToString(response.getPayload()));
			} 
	
		/*if(Encoder.ByteToString(response.getToken()).equals("ObToken")){
            auto_led(Encoder.ByteToString(response.getPayload()));
      }
   }
   
   public void auto_led(String t) {
      Float temp = Float.parseFloat(t);
      
      if(detection_flag==true) {
         if(!led_state.equals("Detected")) {
            CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/led",true);
            request.setPayload(new CoapData("Detected",CoapMediaType.text_plain));
            displayRequest(request);
            clientChannel.sendMessage(request);
            led_state = "Detected";
         }
      
      else {
         CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/led",true);
         request.setPayload(new CoapData("Not Detected",CoapMediaType.text_plain));
         displayRequest(request);
         clientChannel.sendMessage(request);
         led_state = "Not Detected";
      }
   
   }*/
   }
	public void warning_function(String msg) {
		
		
		if(msg.equals("Detected")) {
			
	
			detection_flag = true;
			if (warning_cnt >= 3) {
				
				display_text.append(System.lineSeparator());
				display_text.append("Someone is staying., Calling out Host");
				display_text.append(System.lineSeparator());
				
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/lcd", true);
				request.setPayload(new CoapData("Callout Host", CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
				
			}
			else {
				display_text.append(System.lineSeparator());
				display_text.append("Animal detected("+Integer.toString(warning_cnt)+"/3)");
				display_text.append(System.lineSeparator());
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/lcd", true);
				request.setPayload(new CoapData("Stay Away ("+Integer.toString(warning_cnt)+"/3)", CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
			}
			warning_cnt += 1;
		}
		else {
			if(detection_flag==true) {
				display_text.append(System.lineSeparator());
				display_text.append("Animal disappeared");
				display_text.append(System.lineSeparator());
				CoapRequest request = clientChannel.createRequest(CoapRequestCode.PUT, "/3", true);
				request.setPayload(new CoapData("Monitoring", CoapMediaType.text_plain));
				displayRequest(request);
				clientChannel.sendMessage(request);
			}
			detection_flag = false;	
			warning_cnt = 0;
		}
}
	
	
	@Override
	public void onMCResponse(CoapClientChannel channel, CoapResponse response, InetAddress srcAddress, int srcPort) {
		// TODO Auto-generated method stub
	}
	
	private void displayRequest(CoapRequest request){
		//if(request.getPayload() != null){
		//	display_text.append("Request: "+request.toString() + " payload: " + Encoder.ByteToString(request.getPayload()) + " resource: " + request.getUriPath());
		//	display_text.setCaretPosition(display_text.getDocument().getLength());  
		//} 
		//else{
		//	display_text.append("Request: "+request.toString() + " resource: " + request.getUriPath());
		//	display_text.setCaretPosition(display_text.getDocument().getLength());  
		//}
		//display_text.append(System.lineSeparator());
		//display_text.append("");
		//display_text.append(System.lineSeparator());
	}
	

	public static void main(String[] args){
		//프레임 열기
		Pr_client gui = new Pr_client("fe80::7bfe:41d:4669:148e%8", CoapConstants.COAP_DEFAULT_PORT);
	}
	
	
}
