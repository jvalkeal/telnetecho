package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.Session;

/**
 * Command implementation for 'mkdir'.
 * 
 * @author Janne Valkealahti
 *
 */
public class MkdirCommand extends BaseCommand {
    
    public final static String COMMAND = "mkdir";
    
    @Override
    public String process(Session session, String... args) {
        if(args == null || args.length == 0) {
            session.changeToHome();
            return "Missing directory argument";
        }
        
        if(getTemplate().mkdir(args[0], session.getDir())) {
            return "";
        } else {
            return "Unable to create directory " + args[0];
        }
    }

}
