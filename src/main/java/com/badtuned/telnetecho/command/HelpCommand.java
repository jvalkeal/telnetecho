package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.Session;

/**
 * Command implementation for 'help'.
 * 
 * @author Janne Valkealahti
 *
 */
public class HelpCommand extends BaseCommand {
    
    public final static String COMMAND = "help";
    
    @Override
    public String process(Session session, String... args) {
        return "Commands implemented: " +
                COMMAND + ' ' +
                CdCommand.COMMAND + ' ' +
                ExitCommand.COMMAND + ' ' +
                LsCommand.COMMAND + ' ' +
                PwdCommand.COMMAND;
    }

}
