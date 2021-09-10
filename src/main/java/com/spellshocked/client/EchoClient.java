package com.spellshocked.client;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class EchoClient {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
            Future<Void> result = client.connect(
                    new InetSocketAddress("10.104.45.243", 7));
            result.get();
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String str;
            while ((str = stdIn.readLine()) != null) {
                ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
                Future<Integer> writeval = client.write(buffer);
                System.out.println("Writing to server: " + str);
                writeval.get();
                buffer.flip();
                Future<Integer> readval = client.read(buffer);
                readval.get();
                String s = new String(buffer.array()).trim();
                System.out.println("Received from server: "+s);
                buffer.clear();
            }
    }
}