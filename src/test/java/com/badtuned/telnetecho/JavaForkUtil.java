package com.badtuned.telnetecho;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Utility to fork command server from unit tests.
 * 
 * @author Janne Valkealahti
 *
 */
public class JavaForkUtil {

    private static String TMPDIR = System.getProperty("java.io.tmpdir");
    
    private static OutputStream output;

    /**
     * Clone running unit test vm.
     */
    private static OutputStream cloneJVM(String arguments) {
        
        String cp = System.getProperty("java.class.path");
        String home = System.getProperty("java.home");

        Process proc = null;
        String sp = System.getProperty("file.separator");
        String java = home + sp + "bin" + sp + "java";

        String[] args = arguments.split("\\s+");
        String[] cmd = new String[args.length + 3];
        cmd[0] = java;
        cmd[1] = "-cp";
        cmd[2] = cp;
        for (int i = 3; i < cmd.length; i++) {
            cmd[i] = args[i - 3];
        }

        try {
            proc = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.out.println("[FORK-ERROR] " + e.getMessage());
            throw new IllegalStateException("Cannot start command " + arrayToString(cmd), e);
        }

        System.out.println("Started fork from command\n" + arrayToString(cmd));
        final Process p = proc;

        final BufferedReader er = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        final AtomicBoolean run = new AtomicBoolean(true);

        new Thread(new Runnable() {
            public void run() {
                try {
                    String line = null;
                    do {
                        while ((line = er.readLine()) != null) {
                            System.out.println("[FORK-ERROR] " + line);
                        }
                        Thread.sleep(200);
                    } while (run.get());
                } catch (Exception ex) {
                    // ignore and exit
                }
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Stopping fork...");
                run.set(false);
                output = null;
                if (p != null)
                    p.destroy();

                try {
                    p.waitFor();
                } catch (InterruptedException e) {
                    // ignore
                }
                System.out.println("Fork stopped");
            }
        });

        output = proc.getOutputStream();
        return output;
    }

    public static OutputStream commandServer() {
        return startCommandServer("com.badtuned.telnetecho.CommandServerProcess");
    }

    public static OutputStream startCommandServer(String args) {
        String className = args.split(" ")[0];

        System.out.println("main class:" + className);

        if (controlFileExists(className)) {
            deleteControlFile(className);
        }
        OutputStream os = cloneJVM(args);
        int maxTime = 30000;
        int time = 0;
        while (!controlFileExists(className) && time < maxTime) {
            try {
                Thread.sleep(500);
                time += 500;
            } catch (InterruptedException ex) {
                // ignore and move on
            }
        }
        if (controlFileExists(className)) {
            System.out.println("[FORK] Started command server");
        } else {
            throw new RuntimeException("could not fork command server");
        }
        return os;
    }

    public static void sendSignal() {
        try {
            output.write("\n".getBytes());
            output.flush();
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot communicate with forked VM", ex);
        }
    }

    public static boolean deleteControlFile(String name) {
        String path = TMPDIR + File.separator + name;
        return new File(path).delete();
    }

    public static boolean createControlFile(String name) throws IOException {
        String path = TMPDIR + File.separator + name;
        return new File(path).createNewFile();
    }

    public static boolean controlFileExists(String name) {
        String path = TMPDIR + File.separator + name;
        return new File(path).exists();
    }
    
    private static String arrayToString(String[] array) {
        StringBuilder buf = new StringBuilder();
        for(String s : array) {
            buf.append(s);
            buf.append(' ');
        }
        return buf.toString();
    }
    
}