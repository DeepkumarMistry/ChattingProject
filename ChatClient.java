//chart Client
//package chatPack;
import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
public class ChatClient extends Frame //implements ActionListener
{
 	//declaration of variables
 	//Panel loginPanel;
 	Panel mainPanel;
 	Panel chatPanel;
 	Panel messagePanel;
 	CardLayout c1;
 	TextArea chatTextArea;
 	Label loginLabel;
 	Label currLoginLabel;
 	//Button logonButton;
 	TextField loginText;
 	java.awt.List loginList;
 	TextField chatMessage;
 	Button sendButton,logoutButton;
 	String serverMess;
 	String cmdStr;
 	BufferedReader in;
 	PrintStream out;
 	Socket serverSocket;
 	StringTokenizer readTokenizer;
 	Thread listUpdateThread;
 	ListMesgUpdate listMesgObj;
 	SendMessage sendMessageObj;
 	//String currLogin;
	LogoutClient lclient;

	String s1="";
        

 	public ChatClient()
 	{
		super("Welcome to Chat Room");
  		//initialize the input and output streams
  		try
  		{
   			serverSocket=new Socket("localhost",1001);
   			in=new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
   			out=new PrintStream(serverSocket.getOutputStream());
  		}
  		catch(Exception Excep)
  		{
   			System.out.println("Socket error on client"+Excep);
  		}
  		c1=new CardLayout();

  		setLayout(new BorderLayout());

  		mainPanel=new Panel();
  		add(mainPanel,BorderLayout.CENTER);

  		chatPanel=new Panel();
  		messagePanel=new Panel();

  		mainPanel.setLayout(c1);

  		mainPanel.add("card2",chatPanel);

  		loginLabel=new Label("Login");
  		currLoginLabel=new Label("Current Login");
  		loginText=new TextField(30);

  		loginList=new java.awt.List(10,true);
  		loginList.setBackground(Color.pink);
  		loginList.setForeground(Color.magenta);

  		chatMessage=new TextField(30);
  		sendButton=new Button("Send");
  		logoutButton=new Button("Logout");
  		sendMessageObj=new SendMessage();
  		sendButton.addActionListener(sendMessageObj);
  		lclient=new LogoutClient();
  		logoutButton.addActionListener(lclient);


  		c1.show(mainPanel,"card1");

  		chatPanel.setLayout(new BorderLayout());
  		chatTextArea=new TextArea(20,20);
  		chatPanel.add(chatTextArea,BorderLayout.CENTER);
  		chatTextArea.setBackground(Color.pink);

  		chatPanel.add(loginList,BorderLayout.EAST);
  		messagePanel.add(chatMessage);
  		messagePanel.add(sendButton);
  		messagePanel.add(logoutButton);
  		chatPanel.add(messagePanel,BorderLayout.SOUTH);

		chatPanel.setBackground(Color.pink);
		messagePanel.setBackground(Color.pink);

		Font fnt=new Font("Monotype Corsiva",Font.BOLD+Font.ITALIC,12);

		chatTextArea.setFont(fnt);
		chatMessage.setFont(fnt);
		sendButton.setFont(fnt);
		logoutButton.setFont(fnt);
		loginList.setFont(fnt);
		chatMessage.setForeground(Color.red);
		chatTextArea.setForeground(Color.blue);
		sendButton.setBackground(Color.blue);
		logoutButton.setBackground(Color.blue);

		setSize(350,330);

  		setVisible(true);

		s1=(String)Login.textUid;

		messagePanel.add(currLoginLabel);
  		String logMesg="Login:"+s1;
  		out.println(logMesg);
  		System.out.println("logMesg is:"+logMesg);
  		listMesgObj=new ListMesgUpdate();


  		listMesgObj.start();
  	}
 	public static void main(String arg[])
 	{
		new ChatClient();
	}

  /** public class Button implements Actionlistener
    {        ActionListener a;   
           sendButton.addActionListener(a);
      public void actionPerformed(ActionEvent ev2)
		{ 
                    if(ev2.getSource()==sendButton)
                         {
                              setBackground(Color.green);
System.out.println("send button clicked.......");
                         }
                }
     }*/
 	public class LogoutClient implements ActionListener
 	{
		public void actionPerformed(ActionEvent ev1)
		{
			/*if(ev1.getSource()==sendButton)
                         {
                              setBackground(Color.green);
                            
			
                         }
                        else
                         {*/
                        System.out.println("logout clicked.......");
			String logMesg="Logout:"+s1;
  			out.println(logMesg);
                         
                           
			System.exit(0);
                    
		
             }

	}

  	class ListMesgUpdate extends Thread
 	{
  		public void run()
  		{
   			String loginName;
   			while(true)
   			{
    			try
    			{
     				serverMess=in.readLine();
    			}
    			catch(Exception Excep)
    			{
     				System.out.println(Excep);
    			}
    			readTokenizer=new StringTokenizer(serverMess,":");
    			//storing the information from the client
    			cmdStr=readTokenizer.nextToken();
    			//incase the new client logged in
    			if(cmdStr.equals("NewLogin"))
    			{
    				loginList.removeAll();
     				//add the login names to the Tokenizer
     				while(readTokenizer.hasMoreTokens())
     				{
      					loginName=readTokenizer.nextToken();
      					loginList.add(loginName);
     				}
    			}
    			if(cmdStr.equals("NewLoginMess"))
    			{
     				while(readTokenizer.hasMoreTokens())
     				{
      					loginName=readTokenizer.nextToken();
      					chatTextArea.append(loginName+" has logged in\n");
     				}
    			}
    			if(cmdStr.equals("DelLogin"))
    			{
     				loginList.removeAll();
     				while(readTokenizer.hasMoreTokens())
     				{
     					loginName=readTokenizer.nextToken();
     					loginList.add(loginName);
     				}
    			}
    			if(cmdStr.equals("DelLoginMess"))
    			{
    				while(readTokenizer.hasMoreTokens())
     				{
      					loginName=readTokenizer.nextToken();
      					chatTextArea.append("Good Bye::>"+loginName+" \n");
     				}
    			}
    			if(cmdStr.equals("ChatMess"))
    			{
     				String chatMessage=readTokenizer.nextToken();
     				String sender=readTokenizer.nextToken();
     				chatTextArea.append(sender+" "+"says :"+chatMessage+" \n");
    			}
    			c1.show(mainPanel,"card2");
   			}//while true
  		}//end of run
 	}//end of ListMesgUpdate
 class SendMessage implements ActionListener
 	{
  		public void actionPerformed(ActionEvent sendAction)
  		{
   			String[] selectList=new String[10];
   			selectList=loginList.getSelectedItems();
   			if(selectList.length>0)
   			{
    			String createMess="SendMessage:"+chatMessage.getText()+":"+s1;
    			for(int i=0;i<selectList.length;i++)
    			{
     				createMess+=":";
     				createMess+=selectList[i];
    			}
    			out.println(createMess);
    			chatMessage.setText("");
   			}
  		}
 	}//end of SendMessage

}//end of ChatClient
	