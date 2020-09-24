//package chatPack;
import java.awt.Color;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
public class Registration extends JFrame implements ActionListener
{
	JPanel c;
	JLabel l1,l2,l3,l4;
	JTextField t1,t2;
	JPasswordField pw;
	JButton bsub,breset,bcan,byes,bno;

	public Registration()
	{
		super("Registration Page");
 		c = new JPanel();

		l1 = new JLabel("User Id  ");
		l2 = new JLabel("Password ");
		l3 = new JLabel("Name     ");
		l4 = new JLabel("Do u want to continue to chat...");

		t1 = new JTextField();
		t2 = new JTextField();
		pw = new JPasswordField();

		bsub = new JButton("Submit");
		breset = new JButton("Reset");
		bcan = new JButton("Cancel");
		byes=new JButton("yes");
		bno=new JButton("no");

		bsub.addActionListener(this);
		breset.addActionListener(this);
		bcan.addActionListener(this);
		byes.addActionListener(this);
		bno.addActionListener(this);

		c.setLayout(null);

		l1.setBounds(20,20,80,20);
		l2.setBounds(20,50,80,20);
		l3.setBounds(20,80,80,20);
		t1.setBounds(130,20,100,20);
		pw.setBounds(130,50,100,20);
		t2.setBounds(130,80,100,20);
		bsub.setBounds(60,130,80,30);
		breset.setBounds(140,130,80,30);
		bcan.setBounds(220,130,80,30);

		l4.setBounds(20,180,200,30);
		byes.setBounds(220,180,80,30);
		bno.setBounds(220,220,80,30);

		c.add(l1);
		c.add(t1);
		c.add(l2);
		c.add(pw);
		c.add(l3);
		c.add(t2);
		c.add(bsub);
		c.add(bcan);
		c.add(breset);
		c.add(l4);
		c.add(byes);
		c.add(bno);

		l4.setVisible(false);
		byes.setVisible(false);
		bno.setVisible(false);

		c.setBounds(0,0,400,400);
		c.setBackground(Color.pink);

		getContentPane().setLayout(null);
		getContentPane().add(c);
		//getContentPane().add(p);

		setSize(330,300);
		setResizable(false);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent ae)
	{
		JOptionPane pobj=new JOptionPane();
		try
		{
			Object obj = ae.getSource();
			if(obj == bsub)
			{

				String uid=t1.getText();
				String pwd=pw.getText();
				String nm=t2.getText();
				if(uid.length()==0 && pwd.length()==0 && nm.length()==0)
				{
					System.out.println("fields shouldn't be left blank");
					pobj.showMessageDialog(this,new String("fields shouldn't be left blank!!!"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);
				}
				else if(uid.length()==0 && pwd.length()!=0 && nm.length()!=0)
				{
					System.out.println("user id shouldn't be left blank!!!");
					pobj.showMessageDialog(this,new String("user id shouldn't be left blank!!!"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);
				}
				else if(uid.length()!=0 && pwd.length()==0 && nm.length()!=0)
				{
					System.out.println("password shouldn't be left blank!!!");
					pobj.showMessageDialog(this,new String("password shouldn't be left blank!!!"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);
				}
				else if(uid.length()!=0 && pwd.length()!=0 && nm.length()==0)
				{
					System.out.println("Name shouldn't be left blank!!!");
					pobj.showMessageDialog(this,new String("Name shouldn't be left blank!!!"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);
				}
				else if(pwd.length()<6)
				{
					System.out.println("password should at least of 6 characters!!!");
					pobj.showMessageDialog(this,new String("password should at least of 6 characters!!!"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					System.out.println("driver loaded.... registration...");
					Connection con= DriverManager.getConnection("jdbc:odbc:dd","hr1","hr1");

					String strQ="select user_id from chat_login where user_id='"+uid+"'";
					Statement stat=con.createStatement();
					ResultSet result=stat.executeQuery(strQ);
					if(result.next())
					{
						System.out.println("Sorry user exists....!!!!!!");
						pobj.showMessageDialog(this,new String("Sorry user exists....!!!!!!"),"Sorry!!!!",JOptionPane.ERROR_MESSAGE);

					}
					else
					{
						String strQuery="insert into chat_login values('"+uid+"','"+pwd+"','"+nm+"')";

						Statement st=con.createStatement();
						st.executeUpdate(strQuery);
						System.out.println("inserted.......");
						pobj.showMessageDialog(this,new String("U have successfully inserted..."),"Congrats",JOptionPane.INFORMATION_MESSAGE);

						l4.setVisible(true);
						byes.setVisible(true);
						bno.setVisible(true);
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("h"+e);
		}
		if(ae.getSource()==byes)
		{
			setVisible(false);
			new Login();
		}
		if(ae.getSource()==bno)
		{
			System.exit(0);
		}
		if(ae.getSource()==breset)
		{
			t1.setText("");
			t2.setText("");
			pw.setText("");
		}
		if(ae.getSource()==bcan)
		{
			System.exit(0);
		}
	}
	public static void main(String arg[])
	{
		new Registration();
	}
}