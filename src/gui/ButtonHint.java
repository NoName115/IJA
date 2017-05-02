package src.gui;


public class ButtonHint extends Button
{
    public ButtonHint(ControlPanel panel, String buttonName, double topSpacing, double leftSpacing, double rightSpacing)
    {
        super(panel, buttonName, topSpacing, leftSpacing, rightSpacing);
    }

    @Override
    public void clicked()
    {
        String output;
        if ((output = this.panel.game.showHint()) != null)
        {
            System.out.println(output);
        }
        else
        {
            System.out.println("No hint available");
        }
    }
}
