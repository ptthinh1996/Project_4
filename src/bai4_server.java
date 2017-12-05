import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;


public class bai4_server extends Thread {

	private JFrame frame;
	private ServerSocket serverSocket;
	private ArrayList<Student> students;
	private ArrayList<Student> students1;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/students";
	static final String TABLE = "class";

	static final String USER = "root";
	static final String PASS = "admin";

	public bai4_server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(100000);
	}

	public void run() {
		while (true) {
			try {
				students = new ArrayList<Student>();

				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();

				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream input = new DataInputStream(server.getInputStream());
				String path = input.readUTF();
				
				// nếu nhận được khoảng trắng, thực hiện gửi dữ liệu
				if(path.equals("")){
					students1 = new ArrayList<>();
					try {
						Statement statement=JDBCConectionMysql.getJDBCConnection().createStatement();
						String sql="SELECT * FROM student.info";
						ResultSet rs=statement.executeQuery(sql);
						while(rs.next())
						{
							students1.add(new Student(rs.getString("fname"),rs.getString("lname"),rs.getString("bd"),rs.getString("email")));
							int id=rs.getInt("id");
							String fname=rs.getString("fname");
							String lname=rs.getString("lname");
							String bd=rs.getString("bd");
							String email=rs.getString("email");
							System.out.println(id+" "+fname+" "+lname+" "+bd+" "+email);
						}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
					output.writeObject(students1);
					System.out.println("Sent!");
					
				// nếu không thực hiện lưu dữ liệu
				}else{
					BufferedReader in = new BufferedReader(
							new InputStreamReader(new FileInputStream(path), "UTF8"));
					String str = in.readLine();

					while ((str = in.readLine()) != null) {
						students.add(new Student(str));
					}
					in.close();
					
					for(int i=0;i<students.size();i++)
					{
						Insert.insertInfo(students.get(i).getTen(),students.get(i).getHo(),students.get(i).getNgaySinh(), students.get(i).getEmail());
					}
				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	public static void main(String[] args) {
		int port = 6066;
		try {
			Thread t = new bai4_server(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bai4_server window = new bai4_server();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public bai4_server() {
		initialize();

	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}
