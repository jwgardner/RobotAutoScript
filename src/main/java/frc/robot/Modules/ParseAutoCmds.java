package frc.robot.Modules;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

// validate correct # of params for command
// strip white space from script
// validate format of script commands 2 letter, (), ;, numbers and commas in ()
// validate if command follows command with P

public class ParseAutoCmds extends SequentialCommandGroup {
    
    static SequentialCommandGroup m_AutoCommands = new  SequentialCommandGroup();

    public static SequentialCommandGroup parseAutoCmds () {
        String tempScript;
        String[] commands;
        String scriptCommand;
        String paramList;
        String[] params;

        // get script from shuffleboard, string for now
        tempScript = "DT(50,50,4); FF(5)P; SH(5);";

        // split commands by delimiter
        commands = tempScript.split(";");

        for (int i=0; i < commands.length; i++) {
            // get parallel indicator - To Be Implemented
            if (commands[i].contains(")P")) {
                // process ParallelCommandGroup, returns new index
                ParseParallelCmds parallelObj = new ParseParallelCmds();
                i = parallelObj.parseParallelCmds(m_AutoCommands, commands, i);                
            }
            else {
                scriptCommand = commands[i].substring(0,commands[i].indexOf(')')-1);
                paramList = commands[i].substring(commands[i].indexOf('(')+1, commands[i].indexOf(')')-1);
                params = paramList.split(",");

                switch (scriptCommand) {
                    case "DT":                        
                        m_AutoCommands.addCommands(new ScriptDriveTank(Float.parseFloat(params[0]), Float.parseFloat(params[1]), Float.parseFloat(params[2])));
                        break;
                    case "FF":
                        m_AutoCommands.addCommands(new ScriptFeeder(Float.parseFloat(params[0])));
                        break;
                    case "SH":
                        m_AutoCommands.addCommands(new ScriptShooter(Float.parseFloat(params[0])));
                        break;
                    default:
                        throw new IllegalArgumentException("unknown script command : " + scriptCommand);
                }           
            } 
        }
        return m_AutoCommands;
    }
}
