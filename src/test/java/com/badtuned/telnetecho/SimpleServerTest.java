package com.badtuned.telnetecho;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import junit.framework.TestCase;

/**
 * Test to fork server and then using a simple client to read and exit.
 * 
 * @author Janne Valkealahti
 * 
 */
public class SimpleServerTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        JavaForkUtil.commandServer();
    }

    public void testConnectAndExitWithResponse() throws Exception {
        SimpleClient client = new SimpleClient();
        String response = client.connectAndExit();
        // 'Hello' is written to a client
        // as first message from a server.
        assertEquals(true, response.contains("Hello"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        JavaForkUtil.sendSignal();
    }

    /**
     * Simple client which connects to a forked server,
     * issues exit command and reads data written by a server.
     */
    private class SimpleClient {
        String connectAndExit() throws Exception {
            Socket socket = null;
            PrintWriter out = null;
            BufferedReader in = null;
            String response = "";

            socket = new Socket("localhost", 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println("exit");

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            String data;

            while ((data = in.readLine()) != null) {
                response += data;
            }

            out.close();
            in.close();
            stdIn.close();
            socket.close();

            return response;
        }
    }

}
