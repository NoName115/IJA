package solitaire.gui;


public class ButtonUndo extends Button
{
    public ButtonUndo(ControlPanel panel, String buttonName, double topSpacing, double leftSpacing, double rightSpacing)
    {
        super(panel, buttonName, topSpacing, leftSpacing, rightSpacing);
    }

    @Override
    public void clicked()
    {
        this.panel.game.undo();
    }
}