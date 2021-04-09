package frc.robot.Modules;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class ParseParallelCmds extends ParallelCommandGroup {
    static ParallelCommandGroup m_ParallelCommands = new  ParallelCommandGroup();

    public int parseParallelCmds (SequentialCommandGroup autoCommands, String[] commands, int currentCommandIndex) {
        int newIndex = currentCommandIndex;
        String scriptCommand;
        String paramList;
        String[] params;

        do {
            scriptCommand = commands[newIndex].substring(0,commands[newIndex].indexOf(')')-1);
            paramList = commands[newIndex].substring(commands[newIndex].indexOf('(')+1, commands[newIndex].indexOf(')')-1);
            params = paramList.split(",");
            
            switch (scriptCommand) {
                case "DT":                        
                        m_ParallelCommands.addCommands(new ScriptDriveTank(Float.parseFloat(params[0]), Float.parseFloat(params[1]), Float.parseFloat(params[2])));
                        break;
                    case "FF":
                        m_ParallelCommands.addCommands(new ScriptFeeder(Float.parseFloat(params[0])));
                        break;
                    case "SH":
                        m_ParallelCommands.addCommands(new ScriptShooter(Float.parseFloat(params[0])));
                        break;
                default:
                    throw new IllegalArgumentException("unknown script command : " + scriptCommand);
            } 

            newIndex++;
        } while (commands[newIndex-1].contains(")P"));

        // add set of parallel commands to auto sequential list
        autoCommands.addCommands(m_ParallelCommands);

        return newIndex;
    }
}
