package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.Session;
import com.badtuned.telnetecho.io.DataAccessException;

/**
 * Interface for command processors.
 * 
 * @author Janne Valkealahti
 *
 */
public interface CommandProcessor {

    /**
     * Process command and its arguments.
     * 
     * @param session client session
     * @param args command arguments
     * @return Message to be returned to a client.
     * @throws DataAccessException if error occured.
     */
    public String process(Session session, String... args) throws DataAccessException;
    
}
