package com.badtuned.telnetecho;

import java.net.*;
import java.io.*;

/**
 * Simple utilities for sockets.
 * 
 * @author Janne Valkealahti
 *
 */
public class SocketUtil {

    public static BufferedReader getReader(Socket socket) throws IOException {
        return (new BufferedReader(new InputStreamReader(socket.getInputStream())));
    }

    public static PrintWriter getWriter(Socket socket) throws IOException {
        return (new PrintWriter(socket.getOutputStream(), true));
    }

}