package com.badtuned.telnetecho.command;

import java.io.File;

import com.badtuned.telnetecho.Session;

/**
 * Command implementation for 'cd'.
 * 
 * @author Janne Valkealahti
 *
 */
public class CdCommand extends BaseCommand {
    
    public final static String COMMAND = "cd";
    
    @Override
    public String process(Session session, String... args) {
        if(args == null || args.length == 0) {
            // just 'cd', move back to home
            session.changeToHome();
            return "";
        }
        if(getTemplate().isDirectory(args[0], session.getDir())) {
            session.setDir(new File(session.getDir(), args[0]));
            return "";
        } else {
            return args[0] + " is not a directory";
        }
    }

}
