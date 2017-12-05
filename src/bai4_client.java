import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class bai4_client {

	private JFrame frame;
	private JTextField textField;
	private JButton btnBrowse;
	private JButton btnUpload;
	private JFileChooser fc;
	private String fileID;
	private ArrayList<Student> students;
	private JButton btnRead;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bai4_client window = new bai4_client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public bai4_client() {
		initialize();
		
		// tạo nút Browse
		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnBrowse) {
					fc = new JFileChooser(new File(System.getProperty("user.home") + "\\Desktop")); //tạo đường dẫn mặc định ở Desktop
					fc.setDialogTitle("Select Location");
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);

					if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						fileID = fc.getSelectedFile().getPath(); // lấy đường dẫn file
						textField.setText(fileID); //gán đường dẫn file vào text link
					}
				}
			}
		});

		// nút send
		btnUpload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String serverName = "localhost";
				int port = 6066;
				try {
					System.out.println("Connecting to " + serverName + " on port " + port); 
					Socket client = new Socket(serverName, port); //connect vào server

					System.out.println("Just connected to " + client.getRemoteSocketAddress());
					DataOutputStream output = new DataOutputStream(client.getOutputStream());
					output.writeUTF(fileID); // đưa text link tới server 
					
					client.close(); // đóng kết nối
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// nút read
		btnRead.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				String serverName = "localhost";
				int port = 6066;
				try {
					System.out.println("Connecting to " + serverName + " on port " + port);
					Socket client = new Socket(serverName, port);

					System.out.println("Just connected to " + client.getRemoteSocketAddress());
					
					DataOutputStream output = new DataOutputStream(client.getOutputStream());
					output.writeUTF(""); // đưa khoảng trắng sang server
					
					ObjectInputStream input = new ObjectInputStream(client.getInputStream()); // lấy dữ liệu từ server
					System.out.println("Reading...");
					try {
						students = (ArrayList<Student>) input.readObject(); 
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					
					//in ra màn hình
					for (Student student : students) {
						System.out.println(student); 
					}
					
					client.close(); // đóng kết nối
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblFile = new JLabel("File");
		lblFile.setBounds(61, 85, 34, 14);
		frame.getContentPane().add(lblFile);

		textField = new JTextField();
		textField.setBounds(106, 82, 173, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnUpload = new JButton("Send");
		btnUpload.setBounds(106, 127, 70, 23);
		frame.getContentPane().add(btnUpload);

		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(298, 81, 89, 23);
		frame.getContentPane().add(btnBrowse);
		
		btnRead = new JButton("Read");
		btnRead.setBounds(201, 127, 78, 23);
		frame.getContentPane().add(btnRead);

	}
}
