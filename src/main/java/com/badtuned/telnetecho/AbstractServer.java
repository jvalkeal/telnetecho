package com.badtuned.telnetecho;

import java.net.*;
import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple base class to handle server listening port and listening loop.
 * 
 * @author Janne Valkealahti
 * 
 */
public abstract class AbstractServer {

    private static final Log log = LogFactory.getLog(AbstractServer.class);

    /** Default listen port */
    public final static int DEFAULT_SERVER_PORT = 9999;

    /** Listen port */
    private final int serverPort;

    /**
     * Constructs server with default port {@link DEFAULT_SERVER_PORT}.
     */
    public AbstractServer() {
        this(DEFAULT_SERVER_PORT);
    }

    /**
     * Constructs server with given port.
     * 
     * @param port
     *            the listen port
     */
    public AbstractServer(int port) {
        serverPort = port;
    }

    /**
     * Main socket handling and listening loop. This method has to be called
     * from implementing subclass.
     */
    public void listen() {
        log.info("Starting server: serverPort=" + serverPort);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                handleSocket(socket);
            }
        } catch (IOException e) {
            log.error("Error accepting connection", e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // nothing to do
                }
            }
        }
    }

    /**
     * Gets the current listening port.
     * 
     * @return listening port
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * This method has to be implemented by subclasses to do something useful
     * for incoming connections. Typically for low level operations you'd set
     * input and output for this socket.
     * 
     * @param socket
     *            the socket
     * @throws IOException
     *             If socket error happens
     */
    protected abstract void handleSocket(Socket socket) throws IOException;

}