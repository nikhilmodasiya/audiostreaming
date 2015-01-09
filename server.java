
import java.awt.Container;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
public class server {
	
	public static void main(String args[]) throws IOException, UnsupportedAudioFileException, InterruptedException{
	
		@SuppressWarnings("resource")
		DatagramSocket serversocket = new DatagramSocket(1000);
		
		AudioInputStream file = null;
		
  	    byte[] receiveData = new byte[10000000];
	    
  	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
  	    JFrame frame1=new JFrame();
    	  JOptionPane.showMessageDialog(frame1,
    		    "Waiting .......",
    		    "",
    		    JOptionPane.PLAIN_MESSAGE);
  	    System.out.println("waiting");
  	    serversocket.receive(receivePacket);
  	
  	    
  	    String sentence = new String( receivePacket.getData());
  	    System.out.println("RECEIVED: " + sentence);
  	
  	    InetAddress IPAddress = receivePacket.getAddress();
  	    int port = receivePacket.getPort(); 
  	  JOptionPane.showMessageDialog(frame1,
  		    "Request From client"+IPAddress.getHostName(),
  		    "",
  		    JOptionPane.PLAIN_MESSAGE);
  	    
  	    String pathoffolder = "E:\\video tutorial\\try";
	     File f1 = new File(pathoffolder);
	     File[] listOfFiles = f1.listFiles();
	     String text="";
	     
	     System.out.println(listOfFiles.length);
	     ByteArrayOutputStream bos = new ByteArrayOutputStream();
	     DataOutputStream out = new DataOutputStream(bos);
	     out.writeInt(listOfFiles.length);
	     out.close();
	     byte[] int_bytes = bos.toByteArray();
	     bos.close();
	     
	     
	     DatagramPacket    num_audio = new DatagramPacket (int_bytes,int_bytes.length,IPAddress,port);
         serversocket.send(num_audio);
	  for (int j = 0; j < listOfFiles.length; j++) {
       if (listOfFiles[j].isFile()) {
               text = listOfFiles[j].getName();
               receiveData=text.getBytes();
               DatagramPacket    name_audio = new DatagramPacket (receiveData,receiveData.length,IPAddress,port);
               serversocket.send(name_audio);
              
       }
	  }
	  DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
	     serversocket.receive(receivePacket1);
	     String sel_audio=new String(receivePacket1.getData());
	     System.out.println(sel_audio);
	   
  	  String path = "E:\\video tutorial\\try\\"+sel_audio;
  	  System.out.println(path);
	  	File f = new File(path);
  	    file=	AudioSystem.getAudioInputStream(new File(path));
 
  	    		
    //  System.out.println( AudioSystem.getAudioFileFormat(f).getFrameLength());
      //  System.out.println(file.getFormat().getFrameSize());
       // System.out.println(AudioSystem.getAudioFileFormat(f).getFormat().toString());
         String s=AudioSystem.getAudioFileFormat(f).getFormat().toString();
         	System.out.println(s);
           receiveData=s.getBytes();
           DatagramPacket    packet_format = new DatagramPacket (receiveData,receiveData.length,IPAddress,port);
           serversocket.send(packet_format);
         //System.out.println(AudioSystem.getAudioFileFormat(f).getFormat());
        	  
        	int numofframe=(AudioSystem.getAudioFileFormat(f).getFrameLength()/50000) + 1;
        	int count=0;
        	
        	
//        	DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
//      	    System.out.println("waiting");
//      	    serversocket.receive(receivePacket1);
//      	    
//      	    String sentence1 = new String( receivePacket1.getData());
      	//making of a frame after server has started sending to client
        	JFrame frame=new JFrame("STATUS OF SERVER");
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setBounds(100, 100, 393, 249);
    		JPanel contentPane1 = new JPanel();
    		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
    		frame.setContentPane(contentPane1);
    		contentPane1.setLayout(null);
    		
    		//here progress bar has been set up
    		JProgressBar progressBar = new JProgressBar();
    		progressBar.setStringPainted(true);
    		progressBar.setBounds(213, 182, 146, 14);
    		contentPane1.add(progressBar);
    		
    		JLabel lblAmountOfData = new JLabel("Amount of data send");
    		lblAmountOfData.setBounds(39, 180, 150, 14);
    		contentPane1.add(lblAmountOfData);
    		
    		JLabel lblNewLabel = new JLabel("Server is connected to :");
    		lblNewLabel.setBounds(38, 29, 150, 14);
    		contentPane1.add(lblNewLabel);
    		
    		JLabel lblNewLabel_1 = new JLabel("Client has requested audio file:");
    		lblNewLabel_1.setBounds(38, 67, 162, 14);
    		contentPane1.add(lblNewLabel_1);
    		
    		JLabel lblNewLabel_2 = new JLabel(IPAddress.getHostName());
    		lblNewLabel_2.setBounds(242, 29, 117, 14);
    		contentPane1.add(lblNewLabel_2);
    		
    		JLabel lblNewLabel_3 = new JLabel(sel_audio);
    		lblNewLabel_3.setBounds(242, 67, 117, 14);
    		contentPane1.add(lblNewLabel_3);
    		
    		JLabel lblNewLabel_4 = new JLabel("Size of requested audio file is :");
    		lblNewLabel_4.setBounds(38, 104, 200, 14);
    		contentPane1.add(lblNewLabel_4);
    		
    		JLabel lblNewLabel_5 = new JLabel(""+AudioSystem.getAudioFileFormat(f).getFrameLength()/1024+"KB");
    		lblNewLabel_5.setBounds(242, 104, 117, 14);
    		contentPane1.add(lblNewLabel_5);
    		
    		JLabel lblTypeOfFile = new JLabel("type of file                   :");
    		lblTypeOfFile.setBounds(39, 145, 146, 14);
    		contentPane1.add(lblTypeOfFile);
    		
    		JLabel lblNewLabel_6 = new JLabel("Wave Sound (.wav)");
    		lblNewLabel_6.setBounds(242, 145, 46, 14);
    		contentPane1.add(lblNewLabel_6);

    		frame.setVisible(true);
    		
    		
  	   while(count<numofframe){
  		 byte[] b=new byte[50000];
  		 float per=((float)count/numofframe)*100;
  		 System.out.println(per);
  		 progressBar.setValue((int)per);
  		 Thread.sleep(1000);
  		
  	    	file.read(b, 0,50000);
  	    	
  	      DatagramPacket    packet = new DatagramPacket (b,b.length,IPAddress,port);
  	   // System.out.println(packet1.getLength());

  	      serversocket.send(packet);
  	    byte[] rec_status=new byte[100000];
  	    DatagramPacket receivePacket2 = new DatagramPacket(rec_status, rec_status.length);
  	    
	     serversocket.receive(receivePacket2);
	     String status=new String(receivePacket2.getData());
	     System.out.println(status);
	     Thread.sleep(1000);
	     if(status.compareTo("pause")==0){
	    //	 JFrame frm=new JFrame("dialog");
//	    	 JOptionPane.showMessageDialog(null,"client wants to  pause the video ");
	    	 JLabel lblPause = new JLabel("pause.......");
	 		lblPause.setBounds(151, 162, 46, 14);
	 		contentPane1.add(lblPause);
	    	 
	    	 byte[] rec_status1=new byte[10000];
	    	 DatagramPacket receivePacket3 = new DatagramPacket(rec_status1, rec_status1.length);
		     serversocket.receive(receivePacket3);
		     String status1=new String(receivePacket3.getData());
		     System.out.println(status1);
         if(status1.compareTo("play")==0){
        	 JLabel lblPlay = new JLabel("play.......");
     		lblPlay.setBounds(151, 162, 46, 14);
     		contentPane1.add(lblPlay);
        	 status="cont";
        	 break;
         }
	     }
  	      count++;
  	     System.out.println("frame "+count+" send");
  	      
  	  	}
  	 System.out.println("all frame has been sent");
  	   while(true);
  	  
	
	}
	
}
