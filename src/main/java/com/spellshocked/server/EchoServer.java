package com.spellshocked.server;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class EchoServer {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        byte[] buf = new byte[256];
        AsynchronousServerSocketChannel socket = AsynchronousServerSocketChannel.open();
        socket.bind(new InetSocketAddress("10.104.45.243",
                7));
        Future<AsynchronousSocketChannel> acceptCon =
                socket.accept();
        AsynchronousSocketChannel client = acceptCon.get(10,
                TimeUnit.SECONDS);


        while ((client!= null) && (client.isOpen())) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> readval = client.read(buffer);
            readval.get();
            buffer.flip();
            String s = new String(buffer.array()).trim();
            System.out.println("Received from client: " + s);
            String str = "hello";//"I'm fine. Thank you!";
            Future<Integer> writeVal = client.write(
                    ByteBuffer.wrap(str.getBytes()));
            writeVal.get();
            System.out.println("Writing back to client: "+str);
            buffer.clear();
        }

    }
}

//TODO
//UDP!!!!!
//OAuth2.0 for tokens