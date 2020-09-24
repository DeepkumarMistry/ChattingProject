//package chatPack;
import java.awt.Color;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
public class Login extends JFrame implements ActionListener
{
	JPanel c;
	JLabel l1,l2,l3,l4;
	JTextField t1;
	JPasswordField pw;
	JButton bsub,breset,bcan,bpw,bsignup;
	public static String textUid="";
	Connection con;
	public Login()
	{
		super("Welcome to Chat world");
 		c = new JPanel();
		//JPanel p = new JPanel();

		l1 = new JLabel("User Id  ");
		l2 = new JLabel("Password ");
		l3 = new JLabel("Forgot Password Click here");
		l4 = new JLabel("If u r a new User Click here");

		t1 = new JTextField();
		pw = new JPasswordField();

		bsub = new JButton("Submit");
		breset = new JButton("Reset");
		bcan = new JButton("Cancel");
		bpw=new JButton("Click here");
		bsignup=new JButton("Sign Up");

		bsub.addActionListener(this);
		breset.addActionListener(this);
		bcan.addActionListener(this);
		bpw.addActionListener(this);
		bsignup.addActionListener(this);

		c.setLayout(null);
		//p.setLayout(null);

		l1.setBounds(20,20,80,20);
		l2.setBounds(20,50,80,20);
		t1.setBounds(130,20,100,20);
		pw.setBounds(130,50,100,20);
		bsub.setBounds(60,100,80,30);
		breset.setBounds(140,100,80,30);
		bcan.setBounds(220,100,80,30);
		l3.setBounds(20,105,250,150);
		bpw.setBounds(220,163,100,30);
		l4.setBounds(20,160,250,150);
		bsignup.setBounds(220,220,100,30);


		c.add(bsub);
		c.add(l2);
		c.add(t1);
		c.add(l1);
		c.add(pw);
		c.add(bcan);
		c.add(breset);
		c.add(l3);
		c.add(bpw);
		c.add(l4);
		c.add(bsignup);

		c.setBounds(0,0,400,400);
		//p.setBounds(420,0,300,450);
		c.setBackground(Color.pink);
		//p.setBackground(Color.yellow);

		Font fnt=new Font("Monotype Corsiva",Font.BOLD+Font.ITALIC,15);

		bsub.setFont(fnt);
		l2.setFont(fnt);
		t1.setFont(fnt);
		l1.setFont(fnt);
		pw.setFont(fnt);
		bcan.setFont(fnt);
		breset.setFont(fnt);
		l3.setFont(fnt);
		bpw.setFont(fnt);
		l4.setFont(fnt);
		bsignup.setFont(fnt);

		l1.setForeground(Color.blue);
		l2.setForeground(Color.blue);
		l3.setForeground(Color.blue);
		l4.setForeground(Color.blue);

		t1.setForeground(Color.red);
		pw.setForeground(Color.red);

		bsub.setBackground(Color.gray);
		bcan.setBackground(Color.gray);
		breset.setBackground(Color.gray);
		bpw.setBackground(Color.gray);
		bsignup.setBackground(Color.gray);

		getContentPane().setLayout(null);
		getContentPane().add(c);
		//getContentPane().add(p);

		setSize(400,360);
		setResizable(false);
		setVisible(true);
	}
	public Connection getConnect() throws Exception
	{

		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		System.out.println("driver loaded....");
		return DriverManager.getConnection("jdbc:odbc:dd","hr1","hr1");

	}
	public void actionPerformed(ActionEvent ev)
	{
		JOptionPane pobj=new JOptionPane();
		try
		{
			if(ev.getSource()==bsub)
			{
				System.out.println("submit.......");
				String uid=t1.getText();
				String upw=pw.getText();
				con=getConnect();

				//PreparedStatement stat=con.prepareStatement("select * from chat_login where user_id=? and password=?");

				String strQuery = "Select user_id,password,name from chat_login where user_id='"+uid+"' and password='"+upw+"'";

				Statement state = con.createStatement();

				ResultSet result = state.executeQuery(strQuery);
				System.out.println("zzzzzzzzzzzzzzzz");
				if(result.next())
				{
					pobj.showMessageDialog(this,new String("Congratulation ")+result.getString(3)+new String(" u have successfully logged in"),"Welcome to Chatting world",JOptionPane.INFORMATION_MESSAGE);
					textUid=t1.getText();
					System.out.println(textUid+"........");
					//pobj.showMessageDialog(this,new String("CLIENT PAGE UNDER CONSTRUCTION..."));
					new ChatClient();
					setVisible(false);
				}
				else
				{
					pobj.showMessageDialog(this,new String("Invalid user...plz give correct user id"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);
				}
			}
	  }catch(Exception e){}

	try
	{

		if(ev.getSource()==bpw)
		{
			String uid=t1.getText();
			con=getConnect();

			String strQuery = "Select password from chat_login where user_id='"+uid+"'";

			Statement state = con.createStatement();

			ResultSet result = state.executeQuery(strQuery);

			if(result.next())
			{
				pobj.showMessageDialog(this,new String("ur password is ::  ")+result.getString(1),"Take ur password",JOptionPane.INFORMATION_MESSAGE);

			}
			else
			{
				pobj.showMessageDialog(this,new String("Invalid user...plz give correct user id"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);
			}
		}
	  }catch(Exception e){}
	  try
	  {

		if(ev.getSource()==bsignup)
		{
			new Registration();
			setVisible(false);
		}
	}catch(Exception e){}

	if(ev.getSource()==breset)
	{
		t1.setText("");
		pw.setText("");
	}
	if(ev.getSource()==bcan)
	{
		System.exit(0);
	}


	}

	public static void main(String a[])
	{
		new Login();
	}
}