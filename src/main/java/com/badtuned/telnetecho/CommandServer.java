package com.badtuned.telnetecho;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badtuned.telnetecho.command.CdCommand;
import com.badtuned.telnetecho.command.CommandProcessor;
import com.badtuned.telnetecho.command.ExitCommand;
import com.badtuned.telnetecho.command.HelpCommand;
import com.badtuned.telnetecho.command.InvalidCommand;
import com.badtuned.telnetecho.command.LsCommand;
import com.badtuned.telnetecho.command.MkdirCommand;
import com.badtuned.telnetecho.command.PwdCommand;

/**
 * Threaded implementation on top of {@AbstractCommandServer} to
 * handle simultaneous client connections. Every client is handled
 * in its own processing thread.
 * 
 * @author Janne Valkealahti
 *
 */
public class CommandServer extends AbstractCommandServer implements Runnable {
    
    private static final Log log = LogFactory.getLog(CommandServer.class);    
    
    /**
     * Constructs server with default port {@link DEFAULT_SERVER_PORT}.
     */
    public CommandServer() {
        super();
    }

    /**
     * Constructs server with given port.
     * 
     * @param port
     *            the listen port
     */
    public CommandServer(int port) {
        super(port);
    }

    @Override
    protected void handleSocket(Socket server) {
        ClientRunner connectionThread = new ClientRunner(this, server, new Session());
        connectionThread.start();
    }

    @Override
    public void run() {
        ClientRunner thread = (ClientRunner) Thread.currentThread();
        try {
            super.handleSocket(thread.socket, thread.session);
        } catch (IOException e) {
            log.error("Error handling socket", e);
        }
    }
    
    @Override
    protected CommandProcessor getCommandProcessor(String command) {
        if(command.equals(LsCommand.COMMAND)) {
            return new LsCommand();
        } else if(command.equals(ExitCommand.COMMAND)){    
            return new ExitCommand();
        } else if(command.equals(CdCommand.COMMAND)){    
            return new CdCommand();
        } else if(command.equals(PwdCommand.COMMAND)){    
            return new PwdCommand();
        } else if(command.equals(HelpCommand.COMMAND)){    
            return new HelpCommand();
        } else if(command.equals(MkdirCommand.COMMAND)){    
            return new MkdirCommand();
        } else {
            // process unknown commands
            return new InvalidCommand();
        }
    }    
    
    /**
     * Helper class wrapping socket and a client session. 
     */
    private static class ClientRunner extends Thread {
        
        /** Socket assigned for client */
        protected Socket socket;
        
        /** Session assigned for client */
        protected Session session;

        public ClientRunner(Runnable runnable, Socket socket, Session session) {
            super(runnable);
            this.socket = socket;
            this.session = session;
        }

    }    
    
    /**
     * Main method to run command server. First argument
     * can be a port which server should listen. Default
     * port is '9999'.
     */
    public static void main(String[] args) {
        CommandServer server = null;
        if (args.length > 0) {
            try {
                int port = Integer.parseInt(args[0]);
                server = new CommandServer(port);
            } catch (NumberFormatException nfe) {
            }
            System.out.println("Invalid port spesified");
        } else {
            server = new CommandServer();
        }
        server.listen();
    }
    
}

