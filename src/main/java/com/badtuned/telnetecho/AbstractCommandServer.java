package com.badtuned.telnetecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badtuned.telnetecho.command.CommandProcessor;
import com.badtuned.telnetecho.io.DataAccessException;

/**
 * Implementation on top of {@link AbstractServer} to add
 * command processing logic.
 * 
 * @author Janne Valkealahti
 *
 */
public abstract class AbstractCommandServer extends AbstractServer {
    
    private static final Log log = LogFactory.getLog(AbstractCommandServer.class);    
    
    /**
     * Constructs server with default port {@link DEFAULT_SERVER_PORT}.
     */
    public AbstractCommandServer() {
        super();
    }

    /**
     * Constructs server with given port.
     * 
     * @param port
     *            the listen port
     */
    public AbstractCommandServer(int port) {
        super(port);
    }

    /**
     * Orchestrates reading client commands from a server,
     * dispatching command to appropriate command processor
     * and sends needed output back to client.
     * 
     * @param server the socket
     * @param session the user session
     * @throws IOException if error occured
     */
    protected void handleSocket(Socket server, Session session) throws IOException {
        log.info("got connection from " + server.getInetAddress().getHostName());
        
        BufferedReader in = SocketUtil.getReader(server);
        PrintWriter out = SocketUtil.getWriter(server);

        printText(out, "Hello. Type 'help' to see supported commands.");
        printCurrentDir(out, session);
        
        String line;
        boolean ok = true;
        
        // processing loop
        do {
            line = in.readLine();
            if(line == null) {
                break;
            }
            log.debug("Got data:" + line);
            String response = processCommand(session, line);
            if(response == null) {
                // we got null response which
                // should be exit command
                ok = false;
                continue;
            }
            out.println(response);
            printCurrentDir(out, session);
        } while (ok);
        
        server.close();
    }
    
    /**
     * Prints current directory path to client.
     * @param out client output
     * @param session client session
     */
    private void printCurrentDir(PrintWriter out, Session session) {
        printText(out, session.getDir().getAbsolutePath() + "#");
    }

    /**
     * Prints simple text to client.
     * @param out client output
     * @param text the text to print
     */
    private void printText(PrintWriter out, String text) {
        out.println(text);
    }
    
    /**
     * Processes user command from a registered command processors.
     * 
     * @param session client session
     * @param line command data
     * @return String data to be send back to client, null indicates error.
     */
    protected String processCommand(Session session, String line) {
        try {
            String[] split = line.split(" ");
            CommandProcessor commandProcessor = getCommandProcessor(split[0]);
            return commandProcessor.process(session, (String[]) ArrayUtils.subarray(split, 1, split.length));
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    /**
     * Gets {@link CommandProcessor} associated for given command.
     * 
     * @param command the base command
     * @return Commands command processor or null if command not implemented.
     */
    protected abstract CommandProcessor getCommandProcessor(String command);        
    
}