// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SubDriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ScriptDriveTank extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SubDriveTrain m_subsystem;
  private float m_lPower;
  private float m_rPower;
  private float m_seconds;

  /**
   * Creates a new ScriptDriveTank.
   *
   * @param subsystem The subsystem used by this command.
   */
  // public ScriptDriveTank(SubDriveTrain subsystem) {
  //  m_subsystem = subsystem;
  // Use addRequirements() here to declare subsystem dependencies.
  //  addRequirements(subsystem);
  //}

  public ScriptDriveTank(float lPower, float rPower, float seconds) {
    m_lPower = lPower;
    m_rPower = rPower;
    m_seconds = seconds;
    m_subsystem = null;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.print("Drive Tank (DT) - Seconds: ");
    System.out.println(m_seconds);
    System.out.println(m_lPower);
    System.out.println(m_rPower);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
