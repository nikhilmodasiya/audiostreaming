import java.io.ByteArrayInputStream;
import java.lang.Runtime;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class client {
	static String[] listModel;
	static byte[] sendData = new byte[50000];
	 static byte[] receiveData = new byte[50000];
	 static byte[] pausearray=new byte[500000];
	 static int pausecount=0;
	 static SourceDataLine source = null;
	 static String sel_audio="";
	 static boolean stop_play=false;
	 static boolean pause_play=false;
	 static boolean play_play=false;
	 static Thread sourcethread;
	static  int count=0;
		 private static class Score {
        public boolean buf;
    }
	
	public static class ClientDialog {
	    private JTextField ip = new JTextField(20);
	    private JTextField port = new JTextField(20);
	    private JOptionPane optionPane;
	    private JPanel panel;

	    public void CreateDialog(){

	        panel = new JPanel(new GridLayout(1, 1));
	        panel.add(new JLabel("IP"));
	        panel.add(ip);
	        int option = JOptionPane.showConfirmDialog(null, panel, "Connect to the Server", JOptionPane.DEFAULT_OPTION);
	        if (option == JOptionPane.OK_OPTION) {
	            System.out.println("OK!"); // do something
	        }
	        
	    }

	}
	
	static AudioFormat format;
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static void main(String args[]) throws IOException, LineUnavailableException, InterruptedException{
		ClientDialog c = new ClientDialog();
	    c.CreateDialog();
		final int port =1000;
		final InetAddress destination = InetAddress.getByName(c.ip.getText());
	    final DatagramSocket clientsocket=new DatagramSocket();
	  AudioInputStream audioInputStream;
	    AudioInputStream ais;
	    
	    final byte[] buffer=new byte[500000];
	
	    final byte[] buffer1=new byte[500000];
	    String sentence = "Request........";
	    
	    sendData = sentence.getBytes();
	    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,destination, port);
	    clientsocket.send(sendPacket);
	   
	    //Receive the number of audio
	    final  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    clientsocket.receive(receivePacket);
	    ByteBuffer wrapped = ByteBuffer.wrap(receivePacket.getData()); // big-endian by default
	    final Integer num = wrapped.getInt(); // 1
	    System.out.println(num);
	   
	    int l=0;
	     listModel = new  String[num];
	   while(l<num){
		   System.out.println(l);
		   DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
		clientsocket.receive(receivePacket1);
		 Thread.sleep(1000);
		String str = new String( receivePacket.getData());
		 listModel[l]=str;
		 System.out.println(l + "  " + listModel[l]);
		 // System.out.println(listElems[l]);
	    
		l++;
	   }
	   final Score score = new Score();
	  
	   for(int i = 0 ; i < listModel.length ; i++)
	   System.out.println("mohit" + listModel[i]);
	    String[] t={"nikhil","mobo","fdfdfdfd","dfdf"};
	    final JList jlst;
	    final JFrame jfrm = new JFrame("List of audio");
	    //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jfrm.setContentPane(contentPane);
		contentPane.setLayout(null);
	    jfrm.setLayout(new FlowLayout());
	    jfrm.setBounds(100, 100, 450, 300);;
	    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jlst = new JList<String>(listModel);
	    
	    jlst.setBounds(139, 132, 100, 100);
	    jfrm.setForeground(Color.BLACK);
	    
	    
	    
	    jfrm.add(jlst);
	    jlst.setForeground(Color.BLACK);
	    jlst.setBackground(Color.GRAY);
	    //System.out.println(jlst);
	    //contentPane.add(jlst);
	    
	    jlst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    jlst.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent le) {
	        int idx = jlst.getSelectedIndex();
	        if (idx != -1){
	          System.out.println("Current selection: " + listModel[idx]);
	          sel_audio= listModel[idx];
	          sendData = sel_audio.getBytes();
	  	      DatagramPacket sendPacket1 = new DatagramPacket(sendData, sendData.length,destination, port);
	  	      try {
				clientsocket.send(sendPacket1);
				jfrm.setVisible(false);
				jfrm.dispose();
				
				final JFrame frame1 = new JFrame("CLIENT");
				
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.setBounds(100, 100, 451, 180);
				JPanel contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				frame1.setContentPane(contentPane);
				contentPane.setLayout(null);
				
				
				
				JLabel lblNewLabel = new JLabel("    Playing.....");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblNewLabel.setForeground(Color.BLUE);
				lblNewLabel.setBounds(30, 11, 134, 23);
				contentPane.add(lblNewLabel);
				
				JLabel lblNewLabel_1 = new JLabel(sel_audio);
				lblNewLabel_1.setBounds(285, 11, 101, 26);
				contentPane.add(lblNewLabel_1);
				
		/*		JLabel label = new JLabel("");
				label.setBounds(30, 36, 46, 14);
				contentPane.add(label);*/
				
				JLabel lblTotalNumberOf = new JLabel("Total number of videos on server");
				lblTotalNumberOf.setForeground(Color.BLUE);
				lblTotalNumberOf.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblTotalNumberOf.setBounds(25, 45, 230, 23);
				contentPane.add(lblTotalNumberOf);
				
				JLabel lblNewLabel_2 = new JLabel(""+num);
				lblNewLabel_2.setBounds(284, 48, 89, 23);
				contentPane.add(lblNewLabel_2);

				
			
				
				
				JButton btnPlay = new JButton("Play");
				btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 16));
				btnPlay.setBackground(new Color(240, 248, 255));
				btnPlay.setForeground(Color.BLUE);
				btnPlay.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.out.println(" play......");
					    pause_play=false;
					    String sentence = "play";
					    
					    sendData = sentence.getBytes();
					    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,destination, port);
					    try {
							clientsocket.send(sendPacket);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//    sourcethread=new Thread();
					  //
					    //sourcethread.start();
					
					}
				});
				btnPlay.setBounds(30, 87, 89, 23);
				contentPane.add(btnPlay);
				
				JButton btnPause = new JButton("Pause");
				btnPause.setFont(new Font("Tahoma", Font.PLAIN, 16));
				btnPause.setBackground(new Color(240, 248, 255));
				btnPause.setForeground(Color.BLUE);
				btnPause.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					pause_play=true;	
					/*DatagramPacket receivePacket2 = new DatagramPacket(receiveData, receiveData.length);
					System.out.println(" pause1......");
					while(!play_play){
						
						if(!play_play){
					  
						if(pausecount<pausearray.length){
							System.out.println(play_play);
							try {
						clientsocket.receive(receivePacket2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    receiveData=receivePacket.getData();
		       	   
		       	     for(int k=0;k<50000;k++){
		       	    	 pausearray[pausecount]=receiveData[k];
		       	    	 pausecount++;
		       	     }
		       	     System.out.println("frame"+count+"receive");
					  count++;	
					}
						else{
							System.out.println("pause array get full");
							play_play=true;
						//	sourcethread=new Thread();
						  //  sourcethread.start();
						}
					   
				   }
						
					}
				   System.out.println(" pause2......");*/
						String sentence = "pause";
					    
					    sendData = sentence.getBytes();
					    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,destination, port);
					    try {
							clientsocket.send(sendPacket);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				   }
				});
				btnPause.setBounds(154, 87, 89, 23);
				contentPane.add(btnPause);
				
				JButton btnStop = new JButton("Stop");
				btnStop.setFont(new Font("Tahoma", Font.PLAIN, 16));
				btnStop.setBackground(new Color(240, 248, 255));
				btnStop.setForeground(Color.RED);
				btnStop.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
							stop_play=true;
							frame1.setVisible(false);
							frame1.dispose();
							clientsocket.disconnect();
							clientsocket.close();
							
					}
				});
				btnStop.setBounds(284, 87, 89, 23);
				contentPane.add(btnStop);
		        frame1.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	        else
	          System.out.println("Please choose a language.");
	      }
	    });
	    
	    jfrm.add(new JScrollPane(jlst));
	    jfrm.setSize(300, 300);
	    jfrm.setVisible(true);
	   // String sel_audio=(String) list.getSelectedValue();
	   // frame.pack();

	    //frame.setVisible(true);
	    
	   
       	    
       	 
       	    try {
				clientsocket.receive(receivePacket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
       	  	String audio_format = new String( receivePacket.getData());
       	    System.out.println(audio_format);
       	    String array[]=audio_format.split(" ");
       	    for(int i=0;i<array.length;i++)
       	    	System.out.println(array[i]);
       	    Encoding p = null;
       	    if(array[0].compareTo("PCM_UNSIGNED")==0){
       	    	 p=AudioFormat.Encoding.PCM_UNSIGNED;
       	     }
       	    if(array[0].compareTo("PCM_SIGNED")==0){
       	    	 p=AudioFormat.Encoding.PCM_SIGNED;
       	     }
       	    if(array[0].compareTo("PCM_FLOAT")==0){
       	    	 p=AudioFormat.Encoding.PCM_FLOAT;
       	     }
       	    if(array[0].compareTo("ALAW")==0){
       	    	 p=AudioFormat.Encoding.ALAW;
       	     }
       	    int samplerate=(int)Float.parseFloat(array[1]);
       	    int samplesizeinbits=(int)Integer.parseInt(array[3]);
       	    int channel = 0;
       	    if(array[5].contains("mono")){
       	    	channel=1;
       	    }
       	    if(array[5].contains("stereo")){
       	    	channel=2;
       	    }
       	    boolean endian_type=false;
       	    if(array[8]==""){
       	    	endian_type=true;
       	    }
       	    int framesize=Integer.parseInt(array[6]);
       	    AudioFormat format=new AudioFormat(p, samplerate,samplesizeinbits,channel,framesize,samplerate,endian_type);
       	    
       	    System.out.println("Audio format get correctly");
       	    
       	   ////////////////////////////////////////////////////////////
       	    
       	    DataLine.Info info=new DataLine.Info(SourceDataLine.class, format);
       	    
       	    
			try {
				source = (SourceDataLine) AudioSystem.getLine(info);
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
       	 
       	   int i=1;
       	   int buffercount=0;
       	   int buffer1count=0;
       	   
       		 score.buf = true;
       		;
       		 	
       	    while(!stop_play){
       	    //	System.out.println("pause button"+pause_play);
			       
       	    		System.out.println("waiting");
			       	   
			       	    try {
							clientsocket.receive(receivePacket);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			       	    
			       	     receiveData=receivePacket.getData();
			       if(!pause_play){	     
			       	  String sentence1 = "cont";
			  	    byte[] send_status=new byte[10000];
			  	  send_status = sentence1.getBytes();
			  	    DatagramPacket sendPacket1 = new DatagramPacket(send_status, send_status.length,destination, port);
			  	    clientsocket.send(sendPacket1);
			       }
			       	     if(score.buf){
			       	     for(int k=0;k<50000;k++){
			       	    	 buffer[buffercount]=receiveData[k];
			       	    	 buffercount++;
			       	     }
			       	     
			       	     }
			       	     if(!score.buf){
			       	     for(int k=0;k<50000;k++){
			       	    	 buffer1[buffer1count]=receiveData[k];
			       	    	 buffer1count++;
			       	     }
			       	   
			       	     }
			       	     try {
							source.open();
						} catch (LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			       			 
			       				//final ByteArrayOutputStream out=new ByteArrayOutputStream();
			       	  sourcethread=new Thread()
						{
							public void run(){
								source.start();
							
							
							System.out.println("hello");
								//System.out.println(out.size());
								//source.write(out.toByteArray(),0,out.size());
							if(!play_play){
							if(!score.buf)
								source.write(buffer, 0,buffer.length);
							else
								source.write(buffer1, 0,buffer1.length);
								System.out.println("end play");
							}
							
							else{
								System.out.println(" playing pause array");
								source.write(pausearray, 0,pausearray.length);	
							}
							}
						};
			       			    
			       			
			       		
			       	    System.out.println("FROM SERVER:" +receivePacket.getLength());
			       	    count++;
			       	   
			       	    System.out.println("frame "+count+" receive");
			       	    
			       		if(count==9*i){
			       			System.out.println(buffercount);
			       			if(score.buf){
			       				buffercount=0;
			       				score.buf=false;
			       			}
			       			else{
			       				buffer1count=0;
			       			   score.buf=true;
			       			}
			       				sourcethread.start();
			       			//	Thread.sleep(9000);
			       			i++;
			       			}
			       	    //Thread.sleep(2000);
			       	    	// sourcethread.suspend();
			       	    	 
			       				
			       				//source.stop();
			       				//source.close();
			       				
			
			       	    
			       	   
			       	  //  clientsocket.close(); 
			       
			       }
     
   
	}
       
	
	



	    

}
