package com.badtuned.telnetecho;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Helper class for fork system to fire up command server.
 * 
 * @author Janne Valkealahti
 *
 */
public class CommandServerProcess {

    public static void main(String[] args) throws Exception {
        final CommandServer server = new CommandServer();
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    server.listen();
                } catch (Exception ex) {
                    // ignore and exit
                }
            }
        }).start();
        
        // JavaForkUtil will check that control file exists
        JavaForkUtil.createControlFile(CommandServerProcess.class.getName());
        
        System.out.println("Waiting for signal");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        bufferedReader.readLine();

        System.out.println("Received signal");

        System.out.println("Waiting for shutdown");
        bufferedReader.readLine();
    }

}
