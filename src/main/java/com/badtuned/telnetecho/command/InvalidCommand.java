package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.Session;

/**
 * Dummy command processor handling invalid commands.
 * 
 * @author Janne Valkealahti
 *
 */
public class InvalidCommand extends BaseCommand {

    @Override
    public String process(Session session, String... args) {
        return "Unknown command";
    }


}
