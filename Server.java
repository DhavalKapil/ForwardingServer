/**
 * A server program that accepts input from one client and forwards it to all the clients connected to it
 * @author Dhaval Kapil
 * MIT license http://www.opensource.org/licenses/mit-license.php
 */
 
import java.io.*;
import java.net.*;

public class Server
{	public static void main(String[] args)
	throws Exception
	{	if(args.length!=2)
		{	System.out.println("Error in syntax");
			System.out.println("Usage: java Server -port -maxNoConnections");
			return;
		}
		int port;
		int max;
		try
		{	port = Integer.parseInt(args[0]);
			max = Integer.parseInt(args[1]);
		}
		catch(NumberFormatException e)
		{	System.out.println("number expected...");
			return;
		}
		Client.initializeClientArray(max);
		ServerSocket ss = new ServerSocket(port);
		System.out.println("Server started");
		for(int i = 0;i<max;i++)
		{	Socket s = ss.accept();
			Thread t = new Client(s, i);
			t.start();
		}
		ss.close();
	}
}

class Client extends Thread
{	private int id;
	private Socket s;
	private InputStream in;
	private OutputStream out;
	
	private static int maxConnections;
	private static Client[] clients;
	
	public Client(Socket s, int id)
	throws Exception
	{	this.id = id;
		this.s = s;
		in = s.getInputStream();
		out = s.getOutputStream();
		clients[id] = this;
	}
	public static void initializeClientArray(int max)
	{	clients = new Client[max];
		maxConnections = max;
	}
	public void run()
	{	int bytes_read;
		byte[] buffer = new byte[4096];
		while(true)
		{	try
			{	while((bytes_read=in.read(buffer))!=-1)
				{	dispatchAll(buffer, bytes_read);
				}
			}
			catch(Exception e)
			{	break;
			}
		}
	}
	public void dispatch(byte[] buffer, int bytes_read)
	throws Exception
	{	out.write(buffer, 0, bytes_read);
		out.flush();
	}
	public static void dispatchAll(byte[] buffer, int bytes_read)
	{	for(int i = 0;i<maxConnections;i++)
		{	if(clients[i]==null)
				continue;
			try
			{	clients[i].dispatch(buffer, bytes_read);
			}
			catch(Exception e)
			{	
			}
		}
	}
}
