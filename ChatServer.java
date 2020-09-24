//package chatPack;
import java.util.*;
import java.net.*;
import java.io.*;
public class ChatServer
{
 	//Declaration of variables
 	private ServerSocket serverSock;
 	private Socket fromClient;
 	private static Vector loginNames;
 	private static Vector loginSocks;

 	public ChatServer() throws IOException
 	{
 		try
  		{
   			//login names and sockets are stored in two vectors
   			loginNames=new Vector();
   			loginSocks=new Vector();
   			serverSock=new ServerSocket(1001);
  		}
  		catch(Exception Excep)
  		{
   			System.out.println("Server start problem"+Excep);
  		}

  		/*System.out.println("       *                                                           *");
  	    System.out.println("       *                                                           *");
  		System.out.println("       *                                                           *");
  		System.out.println("       *                                                           *");
  		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
  		System.out.println("       *                                                           *");
  		System.out.println("       *                                                           *");
  		System.out.println("       *          Welcome To Your Own Online Chat Server           *");
  		System.out.println("       *                                                           *");
  		System.out.println("       *                    Server started...                      *");
  		System.out.println("       *                                                           *");
  		System.out.println("       *                                                           *");
  		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
  		System.out.println("       *                                                           *");
  		System.out.println("       *                                                           *");
  		System.out.println("       *                                                           *");
                System.out.println("       *                                                           *");*/
System.out.println("Welcome To Your Own Online Chat Server");

 		// this.start();
  		while(true)
  		{
  			try
   			{
    			fromClient=serverSock.accept();
    			System.out.println("Server:Client Connected");
    			Connect con=new Connect(fromClient);
System.out.println(con);
   			}
   			catch(NullPointerException Excep)
   			{
   				System.out.println("Accept Problem"+Excep);
   			}
  		}//while
 	}//constructor
 	public static void main(String []args)
 	{
		try
		{
   			new ChatServer();
		}
		catch(Exception Expe)
 		{
 		}

 	}//main close

  	class Connect extends Thread
  	{
  		//declaration of variables
   		Socket listClientSocket;
   		PrintStream outPrintStream;
   		String stringFromClient;
   		String currentLogin;
   		String delLogin;
   		String cmdStr;
   		StringTokenizer readTokenizer;
   		Iterator myIterator;
   		String tmpNameList;
   		String tmpName;
   		String tmpMesg;
   		Socket fromClient;
   		boolean threadOk=true;
   		private PrintStream out;
   		private BufferedReader in;

   		public Connect(Socket fromClient)
   		{
   			this.fromClient=fromClient;
    		try
    		{
    			out=new PrintStream(fromClient.getOutputStream());
     			in=new BufferedReader(new InputStreamReader(fromClient.getInputStream()));
    		}
    		catch(Exception Excep)
    		{
     			System.out.println("Stream error");
    		}
    		this.start();
   		}//constructor
   		public void run()
   		{
   			System.out.println("Server:Thread started for "+fromClient);
    		while(threadOk)
    		{
    			try
     			{
     				//accept the input from the client
      				stringFromClient=in.readLine();
      				System.out.println("Server:"+stringFromClient);
      				//storing the value in a StringTokenizer variable
      				readTokenizer=new StringTokenizer(stringFromClient,":");
      				//move to the next element
      				cmdStr=readTokenizer.nextToken();
      				//if a new client has logged in
      				if (cmdStr.equals("Login"))
      				{
      					//move to the next element and store the name
       					currentLogin=readTokenizer.nextToken();
       					// add the login name in the vector
       					loginNames.add(currentLogin);
       					//add the socket to the vector
       					loginSocks.add(fromClient);
       					//moving to the vector i.e Login names
       					myIterator=loginNames.iterator();
       					tmpName="NewLogin";
       					tmpMesg="NewLoginMess:"+currentLogin;
       					while(myIterator.hasNext())
       					{
       						tmpName+=":";
       						tmpName+=(String)myIterator.next();
       					}
       					myIterator=loginSocks.iterator();
       					//moving through the vector LoginSocks
       					while(myIterator.hasNext())
       					{
       						listClientSocket=(Socket)myIterator.next();
       						outPrintStream=new PrintStream(listClientSocket.getOutputStream());
       						//passing the information about the new person
       						outPrintStream.println(tmpName);
       						outPrintStream.println(tmpMesg);
       					}//while close
      				}//if close
      				//if any client has logged out
      				if(cmdStr.equals("Logout"))
      				{
      					delLogin=readTokenizer.nextToken();
       					//storing the element number who has logged out
       					int vecIndex=loginNames.indexOf((Object)delLogin);
       					int curIndex=loginSocks.indexOf((Object)fromClient);
       					if (vecIndex==curIndex)
       					{
       						threadOk=false;
        					//remove the loggedout client from the vector
        					loginNames.removeElementAt(vecIndex);
        					loginSocks.removeElementAt(vecIndex);
        					myIterator=loginNames.iterator();
        					tmpName="DelLogin";
        					tmpMesg="DelLoginMess:"+delLogin;
        					while(myIterator.hasNext())
        					{
        						tmpName+=":";
        						tmpName+=(String)myIterator.next();
        					}
        					myIterator=loginSocks.iterator();
        					//send the information of the logged out client to the othe client
        					while(myIterator.hasNext())
        					{
        						listClientSocket=(Socket)myIterator.next();
        						outPrintStream=new PrintStream(listClientSocket.getOutputStream());
        						outPrintStream.println(tmpName);
        						outPrintStream.println(tmpMesg);
        					}//while close
       					}//if close
      				}
       				//if the client wants to chat
       				if(cmdStr.equals("SendMessage"))
       				{
       					//store the message of the client
        				String mesg=readTokenizer.nextToken();
        				//store the message of the sender
        				String sender=readTokenizer.nextToken();
        				String receiver;
        				Socket sendToSocket;
        				//send the message to all the client
        				while(readTokenizer.hasMoreTokens())
        				{
        					receiver=readTokenizer.nextToken();
        					int vecIndex=loginNames.indexOf((Object)receiver);
        					sendToSocket=(Socket)loginSocks.elementAt(vecIndex);
        					outPrintStream=new PrintStream(sendToSocket.getOutputStream());
        					outPrintStream.println("ChatMess:"+mesg+":"+sender);
        				}//while
       				}//if
      			}
      			catch(Exception Excep)
      			{
      				System.out.println("InputStream problem"+Excep);
      			}
     		}
    	}
   	}//connect()
}//class ChatServer
