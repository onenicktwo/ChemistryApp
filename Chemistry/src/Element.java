import javafx.scene.control.Hyperlink;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Element 
{
	String name = "";
	int weight = 0;
	
	//Sets the layout, due to how the table is made
	int totalEnergy = 0;
	
	double meltPoint = 0.0;
	double boilPoint = 0.0;
	
	//Metals, metalloids, non-metals, etc.
	String elementType = "";
	String elementLabel = "";
	
	//Temp reaction
	boolean isSolid = false;
	boolean isLiquid = false;
	boolean isGas = false;
	
	//GUI
	Rectangle eBorder;
	Text textTE;
	Hyperlink symbol;
	Text textW;
	
	public Element(String n, double w, int tE, double mP, double bP, String eL, String eT)
	{
		name = n;
		weight = (int) w;
		totalEnergy = tE;
		meltPoint = mP;
		boilPoint = bP;
		elementLabel = eL;
		elementType = eT;
	}
	
	public void setBorder(Rectangle border)
	{
		eBorder = border;
	}
	
	public void setTextTE(Text t)
	{
		textTE = t;
	}
	
	public void setHyperSymbol(Hyperlink h)
	{
		symbol = h;
	}
		
	public void setTextWeight(Text t)
	{
		textW = t;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public int getTE()
	{
		return totalEnergy;
	}
	
	public double getMeltPoint()
	{
		return meltPoint;
	}
	
	public double getBoilPoint()
	{
		return boilPoint;
	}
	
	public String getEL()
	{
		return elementLabel;
	}
}
