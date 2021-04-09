package frc.robot.Modules;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Command;

public class ParseAutoCmds extends SequentialCommandGroup {
    
    SequentialCommandGroup m_AutoCommands = new  SequentialCommandGroup();

    public static SequentialCommandGroup parseAutoCmds () {
        String tempScript;
        String[] commands;
        String commandName;
        String paramList;
        String commandToAdd;

        // get script from shuffleboard, string for now
        tempScript = "DT(50,50,4); FF(5)P; SH(5);";

        // split commands by delimiter
        commands = tempScript.split(";");

        for (int i=0; i < commands.length; i++) {
            // get command name
            commandName = getCommandName(commands[i].substring(0,commands[i].indexOf(')')-1));

            // get parameter list
            paramList = commands[i].substring(commands[i].indexOf('('), commands[i].indexOf(')'));

            // Command to add
            commandToAdd = commandName + paramList;

            // get parallel indicator - To Be Implemented

            // add command to sequential command list *** string need to be interpreted differently ?????
            m_AutoCommands.addCommands(new Command commandToAdd);

        }

        return m_AutoCommands;
    }

    public static String getCommandName (String scriptCommand) {
        String commandName;
        // convert script notation to command name to add
        switch (scriptCommand) {
            case "DT":
                commandName = "ScriptDriveTank";
                break;
            case "FF":
                commandName = "ScriptFeeder";
                break;
            case "SH":
                commandName = "ScriptShooter";
                break;
            default:
            throw new IllegalArgumentException("unknown script command : " + scriptCommand);
        }
        return commandName;
    }

}
