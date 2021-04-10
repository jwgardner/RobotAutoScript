package frc.robot.Modules;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.*;

public class ParseAutoCmds extends SequentialCommandGroup {    

    public SequentialCommandGroup parseAutoCmds () {
        String tempScript;
        String[] commands;
        
        SequentialCommandGroup autoSeqCommands = new  SequentialCommandGroup();

        // get script from shuffleboard, string for now, P implies include the next command in parallel, can have >1 P commands in sequence
        tempScript = "DT(50,50,4);FF(5)P;SH(5);";
        // remove whitespace
        tempScript = tempScript.replaceAll("\\s+","");

        // validate correct # of params for command
        // strip white space from script
        // validate format of script commands 2 letter, (), ;, numbers and commas in ()
        // validate if command follows command with P
        // ScriptValidator sv = new ScriptValidator();
        // if (!sv.isValid(tempScript) ) 
        //    return null;

        // split commands by delimiter
        commands = tempScript.split(";");

        for (int i=0; i < commands.length; i++) {
            // process parallel commands
            if (commands[i].contains(")P")) {

                // Get parallel command group object and add commands for each in parallel command
                ParallelCommandGroup parallelCommands = new  ParallelCommandGroup();

                do {
                    addScriptCommand(parallelCommands, commands[i]);
                    i++;
                } while (commands[i-1].contains(")P") && i < commands.length);  // check index in case P is on last command (and shouldn't be! validate it!)

                // add the group of parallel commands to the overall sequence command group
                autoSeqCommands.addCommands(parallelCommands);
            }
            else {
                addScriptCommand(autoSeqCommands, commands[i]);                                         
            } 
        }
        return autoSeqCommands;
    }

    public void addScriptCommand(CommandGroupBase commandList, String command) {
        String scriptCommand;
        String paramList;
        String[] params;

        scriptCommand = command.substring(0,command.indexOf(')')-1);
        paramList = command.substring(command.indexOf('(')+1, command.indexOf(')')-1);
        params = paramList.split(",");

        switch (scriptCommand) {
            case "DT":                        
                commandList.addCommands(new ScriptDriveTank(Float.parseFloat(params[0]), Float.parseFloat(params[1]), Float.parseFloat(params[2])));
                break;
            case "FF":
                commandList.addCommands(new ScriptFeeder(Float.parseFloat(params[0])));
                break;
            case "SH":
                commandList.addCommands(new ScriptShooter(Float.parseFloat(params[0])));
                break;
            default:
                throw new IllegalArgumentException("unknown script command : " + scriptCommand);
        }    
    }
}