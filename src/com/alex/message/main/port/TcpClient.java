package com.alex.message.main.port;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TcpClient implements Runnable {
	private Socket socket;
	private boolean isReadBackGround = true;
	private PrintWriter out;
	private BufferedReader in;

	public TcpClient(String host, int port) throws IOException {
		// �������ӵ����������׽��֡�
		socket = new Socket(host, port);
	}

	@Override
	public void run() {
		System.out.println("client: run");
		try (Socket s = socket) {
			// �ٴμ��ٴ���������
			// ��װ�׽��ֵ��������������������ı��С�
			System.out.println("client: s = " + s);
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(),
					StandardCharsets.UTF_8), true);
			// ��װ�׽��ֵ��������Զ�ȡ���������ص��ı��С�
			in = new BufferedReader(new InputStreamReader(s.getInputStream(),
					StandardCharsets.UTF_8));

			BufferedReader bf_line = new BufferedReader(new InputStreamReader(
					System.in));
			String line = null;
			System.out.println("client: line = " + line);
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (isReadBackGround) {
						try {
							String readLine = in.readLine();
							if (readLine != null) {
								System.out
										.println("client: receive from server = "
												+ readLine);
							}
						} catch (IOException e) {
							System.out.println("client: close connection");
							e.printStackTrace();
						}
					}
				}
			}).start();
			System.out.println("client: waiting for input...");
			while ((line = bf_line.readLine()) != null) {
				if (line.equals("bye")) {
					isReadBackGround = false;
					break;
				}
				out.println(line);
				System.out.println("client: waiting for input...");
			}
			out.println("bye");
			bf_line.close();
			out.close();
			in.close();
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
