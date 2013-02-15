package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.Session;

/**
 * Command implementation for 'pwd'.
 * 
 * @author Janne Valkealahti
 *
 */
public class PwdCommand extends BaseCommand {
    
    public final static String COMMAND = "pwd";
    
    @Override
    public String process(Session session, String... args) {
        return getTemplate().directoryPath(session.getDir());
    }

}
