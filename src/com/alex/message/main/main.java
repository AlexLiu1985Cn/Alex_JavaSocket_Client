package com.alex.message.main;

import java.io.IOException;

import com.alex.message.main.port.TcpClient;

public class main {

	public static void main(String[] args){
		System.out.println("helloworld client");
		TcpClient tcpClient = null;
		try {
			tcpClient = new TcpClient("192.168.1.5", 5600);
			System.out.println("client: " + tcpClient);
		} catch (IOException e) {
			e.printStackTrace();
		}
		tcpClient.run();
	}
}
