package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.Session;

/**
 * Command implementation for 'ls'.
 * 
 * @author Janne Valkealahti
 *
 */
public class LsCommand extends BaseCommand {
    
    public final static String COMMAND = "ls";
    
    @Override
    public String process(Session session, String... args) {
        String[] fileNames = getTemplate().filesWithModifyOrder(session.getDir());
        StringBuilder buf = new StringBuilder();
        for(String name : fileNames) {
            buf.append(name);
            buf.append('\n');            
        }
        return buf.toString();
    }

}
