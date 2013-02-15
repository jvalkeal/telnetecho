package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.Session;

/**
 * Dummy command processor to handle 'exit' command.
 * Null response from a processor indicates that
 * client connection should be closed, so just use
 * that in here.
 * 
 * @author Janne Valkealahti
 *
 */
public class ExitCommand extends BaseCommand {

    public final static String COMMAND = "exit";
    
    @Override
    public String process(Session session, String... args) {
        return null;
    }

}
