import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application 
{
	//C3H8+O2=H2O+CO2
	//N2+O2=N2O5
	
	int count = 0;
	ArrayList<Element> table = new ArrayList<Element>();
	Pane root = new Pane();

	Rectangle border;
	Text t;
	Text s;
	Hyperlink h;
	double X = 80 / 1.20;
	double Y = 100 / 1.20;
	int incX = 200;
	int incY = -60;
	
	TextField inputEquation  = new TextField();
	Text simpEqu = new Text("Example: 2O+3Ca=O+Ca\nDo not leave spaces");
	ChemSimplifier chemSimp = new ChemSimplifier();
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{				
			//Background
			Rectangle background = new Rectangle(10, 10, Color.rgb(137, 144, 163));
			background.toBack();
			root.getChildren().add(background);
			
			//Set scene
			Scene scene = new Scene(root, 1540, 880);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			AnimationTimer timer = new AnimationTimer() 
			{
				@Override
				public void handle(long now) 
				{
					background.setWidth(scene.getWidth());
					background.setHeight(scene.getHeight());
				}
			};
			timer.start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		setList();
		fill();
		setChoices();
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
	
	//Sets borders and text
	public void fill()
	{
		int countX = 1;
		int countY = 1;
		
		for(Element e : table)
		{
			if(!((e.getTE() >= 57 && e.getTE() <= 71) || (e.getTE() >= 89 && e.getTE() <= 103)))
			{
				border = new Rectangle(X, Y, Color.WHITE);
				border.setStroke(Color.BLACK);
				border.setStrokeWidth(2);
				root.getChildren().add(border);
				
				t = new Text("" + e.getTE());
				root.getChildren().add(t);
	
				s = new Text("" + e.getWeight());
				root.getChildren().add(s);
				
				h = new Hyperlink(e.getEL());
				root.getChildren().add(h);
				h.setTextFill(Color.BLACK);
				h.setUnderline(false);
				h.setScaleX(3);
				h.setScaleY(3);
			}
				
				if(e.getTE() == 2)
				{
					countX = 18;
				}
				if(e.getTE() == 5 || e.getTE() == 13)
				{
					countX = 13;
				}
				if(countX > 18)
				{
					countY++;
					countX = 1;
				}
				if(e.getTE() == 57 || e.getTE() == 89)
				{
					countX++;
				}
				if(e.getTE() >= 57 && e.getTE() <= 71)
				{
					System.out.println("");
				}
				else if(e.getTE() >= 89 && e.getTE() <= 103)
				{
					System.out.println("");
				}
				else
				{
					border.setY(Y * countY + incY);
					border.setX(X * countX + incX);
					
					setTextLoc(t, e);
					setSymbol(h, e);
					setMassLoc(s, e);
					e.setBorder(border);
					
					countX++;
				}
		}
		
		countX = 4;
		countY = 9;
		for(Element e : table)
		{
			if((e.getTE() >= 57 && e.getTE() <= 71) || (e.getTE() >= 89 && e.getTE() <= 103))
			{
				border = new Rectangle(X, Y, Color.WHITE);
				border.setStroke(Color.BLACK);
				border.setStrokeWidth(2);
				root.getChildren().add(border);
				
				Text t = new Text("" + e.getTE());
				root.getChildren().add(t);
	
				Text s = new Text("" + e.getWeight());
				root.getChildren().add(s);
				
				Hyperlink h = new Hyperlink(e.getEL());
				root.getChildren().add(h);
				h.setTextFill(Color.BLACK);
				h.setUnderline(false);
				h.setScaleX(3);
				h.setScaleY(3);
			
				if(e.getTE() >= 57 && e.getTE() <= 71)
				{
					border.setY(Y * countY + incY);
					border.setX(X * countX + incX);
					
					setTextLoc(t, e);
					setSymbol(h, e);
					setMassLoc(s, e);
					e.setBorder(border);
					
					countX++;
					
					if(e.getTE() == 71)
					{
						countY++;
						countX = 4;
					}
				}
				else if(e.getTE() >= 89 && e.getTE() <= 103)
				{
					border.setY(Y * countY + incY);
					border.setX(X * countX + incX);
					
					setTextLoc(t, e);
					setSymbol(h, e);
					setMassLoc(s, e);
					e.setBorder(border);
					
					countX++;
				}
			}
		}
	}
	
	public void setTextLoc(Text t, Element e)
	{
		t.setX(border.getX() + 2);
		t.setY(border.getY() + 12);
		
		e.setTextTE(t);
	}
	
	public void setSymbol(Hyperlink h, Element e)
	{
		if(e.getEL().length() == 1)
		{
			h.setTranslateX(border.getX() + 24);
			h.setTranslateY(border.getY() + 25);
		}
		else if(e.getEL().substring(0, 1).equals("M"))
		{
			h.setTranslateX(border.getX() + 21);
			h.setTranslateY(border.getY() + 25);
		}
		else
		{
			h.setTranslateX(border.getX() + 22);
			h.setTranslateY(border.getY() + 25);
		}
		e.setHyperSymbol(h);
		h.setOnAction(event -> Word(event));
	}
	
	public void setMassLoc(Text t, Element e)
	{
		t.setX(border.getX() + 2);
		t.setY(border.getY() + 80);
		
		e.setTextWeight(t);
	}
	
	private void Word(ActionEvent event)
	{
		System.out.println((Hyperlink)event.getSource());
	}
	
	private void Table(ActionEvent event)
	{
		tableOn();
		inputEquation.setVisible(false);
		simpEqu.setVisible(false);
	}
	
	private void Equations(ActionEvent event)
	{
		tableOff();
		inputEquation.setVisible(true);
		simpEqu.setVisible(true);
	}
	
	//Creates events and GUI links for each section
	public void setChoices()
	{
		for(int i = 1; i <= 2; i++)
		{
			Rectangle textBack = new Rectangle(200, 50, Color.WHITE);
			textBack.setStroke(Color.BLACK);
			textBack.setStrokeWidth(1);
			textBack.setTranslateX(40);
			textBack.setTranslateY(50 * i - 25);
			Hyperlink choice = new Hyperlink("");
			
			if(i == 1)
			{
				choice.setLayoutX(92);
				choice.setLayoutY(50 * i - 12.5);
				choice.setText("PERIODIC TABLE");
				choice.setOnAction(event -> Table(event));
			}
			else if(i == 2)
			{
				choice.setLayoutX(92);
				choice.setLayoutY(50 * i - 	12.5);
				choice.setText("SIMPLIFICATION");
				choice.setOnAction(event -> Equations(event));
				
				root.getChildren().addAll(inputEquation, simpEqu);
				inputEquation.setVisible(false);
				inputEquation.setTranslateX(270);
				inputEquation.setTranslateY(25);
				inputEquation.setPrefWidth(1240);
				
				/*Detects change in the textfield text, then looks for certain characters
				Step 1: checks to see if it typed correctly by finding the = sign
				Step 2: gets each element on each side, sees if each side has the same type of elements
				Step 3: trys to simplify by grabbing amount of elements and multiplying each molecule (spaced by + signs)
				Step 4: returns simplification if it found one, or returns unsolvable
				*/
				inputEquation.textProperty().addListener((observable) -> {
					simpEqu.setText(inputEquation.getText());
					
					chemSimp.setEquation(simpEqu.getText());
					chemSimp.findElements();
					if(chemSimp.validElements())
					{
						chemSimp.setRawCopies();
						chemSimp.findMolecules();
						chemSimp.setTotalElementIso();
						chemSimp.sortElementsByMolecules();
						chemSimp.setTotalElementMol();
						chemSimp.setTotals();
						chemSimp.Solve();
					}
				});
				
				simpEqu.setVisible(false);
				simpEqu.setTranslateX(270);
				simpEqu.setTranslateY(75);
				simpEqu.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20));
			}
			
			choice.setTextFill(Color.BLACK);
			choice.setUnderline(false);
			choice.setScaleX(2);
			choice.setScaleY(2);
			root.getChildren().addAll(textBack, choice);
		}
	}
	
	public void tableOff()
	{
		for(Element e : table)
		{
			e.eBorder.setVisible(false);
			e.symbol.setVisible(false);
			e.textTE.setVisible(false);
			e.textW.setVisible(false);
		}
	}
	
	public void tableOn()
	{
		for(Element e : table)
		{
			e.eBorder.setVisible(true);
			e.symbol.setVisible(true);
			e.textTE.setVisible(true);
			e.textW.setVisible(true);
		}
	}
	//Elements
	public void setList()
	{
			table.add(new Element("Hydrogen", 1.008, 1, -259.1, -252.9, "H", "Reactive Nonmetals"));
			table.add(new Element("Helium", 14.002602, 2, 0,  -269,  "He", "Noble Gases"));
			table.add(new Element("Lithium", 6.94, 3, 180.54, 1342, "Li", "Alkali Metals"));
			table.add(new Element("Beryllium", 9.0121831, 4, 1287, 2470, "Be", "Alkali Earth Metals"));
			table.add(new Element("Boron", 10.81, 5, 2075, 4000, "B", "Metalloids"));
			table.add(new Element("Carbon", 12.011, 6, 3642, 3642, "C", "Reactive Nonmetal"));
			table.add(new Element("Nitrogen", 14.007, 7, -210.1, -195.8, "N", "Reactive Nonmetal"));
			table.add(new Element("Oxygen", 15.999, 8, -218, -183, "O", "Reactive Nonmetal"));
			table.add(new Element("Flourine", 18.998403163, 9 , -220, -188.1, "F", "Reactive Nonmetal"));
			table.add(new Element("Neon", 20.1797, 10, -248.6, -246.1, "Ne", "Noble Gas"));
			table.add(new Element("Sodium", 22.98976928, 11, 97.720, 882.9, "Na", "Akali Metals"));
			table.add(new Element("Magnesium", 24.305, 12, 620, 1090, "Mg", "Akaline Earth Metals"));
			table.add(new Element("Aluminium", 26.9815385, 13, 660.32, 2519, "Al", "Post-transition metals"));
			table.add(new Element("Silicon", 28.085, 14, 1414, 2900, "Si", "Metalloids"));
			table.add(new Element("Phosphuros", 30.973761998, 15, 44.15, 280.5, "P", "Reactive Nonmetals"));
			table.add(new Element("Sulfur", 32.06, 16, 115.21, 444.72, "S", "Reactive Nonmetals"));
			table.add(new Element("Chlorine", 35.45, 17, -101.5, -34.040, "Cl", "Reactive Nonmetals"));
			table.add(new Element("Argon", 39.948, 18, -189, -186, "Ar", "Noble Gases"));				
			table.add(new Element("Potassium", 39.0983, 19, 63.39, 758.9, "K", "Akali Metals"));
			table.add(new Element("Calcium", 40.078, 20, 841.9, 1484, "Ca", "Akaline Earth Metals"));
			table.add(new Element("Scandium", 44.955908, 21, 1541, 2830, "Sc", "Transition Metals"));
			table.add(new Element("Titanium", 47.867, 22, 1668, 3287, "Ti", "Transition Metals"));
			table.add(new Element("Vanadium", 50.9415, 23, 1910, 3407, "V", "Transition Metals"));
			table.add(new Element("Chromium", 51.9961, 24, 1907, 2671, "Cr", "Transition Metals"));
			table.add(new Element("Manganese", 54.938044, 25, 1246, 2061, "Mn", "Transition Metals"));
			table.add(new Element("Iron", 55.845, 26, 1538, 2861, "Fe", "Transition Metals"));
			table.add(new Element("Cobalt", 58.933194, 27, 1495, 2900, "Co", "Transition Metals"));
			table.add(new Element("Nickel", 58.6934, 28, 1455, 2913, "Ni", "Transition Metals"));
			table.add(new Element("Copper", 63.546, 29, 1084.62, 2562, "Cu", "Transition Metals"));
			table.add(new Element("Zinc", 65.38, 30, 419.53, 906.9, "Zn", "Transition Metals"));
			table.add(new Element("Gallium", 69.723, 31, 29.760, 2204, "Ga", "Post-Transition Metals"));
			table.add(new Element("Germanium", 72.63, 32, 938.25, 2820, "Ge", "Metalloids"));
			table.add(new Element("Arsenic", 74.921595, 33, 816.9, 614, "As", "Metalloids"));
			table.add(new Element("Selenium", 78.971, 34, 221, 865, "Se", "Reactive Nonmetals"));
			table.add(new Element("Bromine", 79.904, 35, -7.350, 58.9, "Br", "Reactive Nonmetals"));
			table.add(new Element("Krypton", 83.798, 36, -157.36, -153.22, "Kr", "Noble Gases"));
			table.add(new Element("Rubidium", 85.4678, 37, 39.310, 688, "Rb", "Alkali Metals"));
			table.add(new Element("Strontium", 87.62, 38, 776.9, 1382, "Sr", "Alkaline Earth Metals"));
			table.add(new Element("Yttrium", 88.90584, 39, 1526, 3345, "Y", "Transition Metals"));
			table.add(new Element("Zirconium", 91.224, 40, 1855, 4409, "Zr", "Transition Metals"));
			table.add(new Element("Niobium", 92.90637, 41, 2477, 4744, "Nb", "Transition Metals"));			
			table.add(new Element("Molybdenum", 95.95, 42, 2623, 4639, "Mo", "Transition Metals"));
			table.add(new Element("Technetium", 98, 43, 2157, 4265, "Tc", "Transition Metals"));
			table.add(new Element("Ruthenium", 101.07, 44, 2334, 4150, "Ru", "Transition Metals"));
			table.add(new Element("Rhodium", 102.90550, 45, 1964, 3695, "Rh", "Transition Metals"));
			table.add(new Element("Palladium", 106.42, 46, 1554.90, 2963, "Pd", "Transition Metals"));
			table.add(new Element("Silver", 107.8682, 47, 961.780, 2162, "Ag", "Transition Metals"));
			table.add(new Element("Cadmium", 112.414, 48, 321.07, 766.9, "Cd", "Transition Metals"));
			table.add(new Element("Indium", 114.818, 49, 156.60, 2072, "In", "Post-Transition Metals"));
			table.add(new Element("Tin", 118.710, 50, 231.93, 2602, "Sn", "Post-Transition Metals"));
			table.add(new Element("Antimony", 121.760, 51, 630.63, 1587, "Sb", "Metalloids"));
			table.add(new Element("Tellurium", 127.60, 52, 449.51, 987.9, "Te", "Metalloids"));
			table.add(new Element("Iodine", 126.90447, 53, 113.70, 184.3, "I", "Reactive Nonmetals"));
			table.add(new Element("Xenon", 131.293, 54, -111.8, -108.0, "Xe", "Noble Gases"));
			table.add(new Element("Caesium", 132.90545196, 55, 28.440, 671, "Cs", "Alkali Metals"));
			table.add(new Element("Barium", 137.327, 56, 730, 1870, "Ba", "Alkaline Earth Metals"));
			table.add(new Element("Lanthanum", 138.90547, 57, 919.9, 3464, "La", "Lanthanoids"));
			table.add(new Element("Cerium", 140.116, 58, 797.9, 3360, "Ce", "Lanthanoids"));
			table.add(new Element("Praseodymium", 140.90766, 59, 930.9, 3290, "Pr", "Lanthanoids"));
			table.add(new Element("Neodymium", 144.242, 60, 1021, 3100, "Nd", "Lanthanoids"));
			table.add(new Element("Promethium", 145, 61, 1100, 3000, "Pm", "Lanthanoids"));
			table.add(new Element("Samarium", 150.36, 62, 1072, 1803, "Sm", "Lanthanoids"));
			table.add(new Element("Europium", 151.964, 63, 821.9, 1500, "Eu", "Lanthanoids"));
			table.add(new Element("Gadolinium", 157.25, 64, 1313, 3250, "Gd", "Lanthanoids"));
			table.add(new Element("Terbium", 158.92535, 65, 1356, 3130, "Tb", "Lanthanoids"));
			table.add(new Element("Dysprosium", 162.500, 66, 1412, 2567, "Dy", "Lanthanoids"));
			table.add(new Element("Holmium", 164.93033, 67, 1474, 2700, "Ho", "Lanthanoids"));
			table.add(new Element("Erbium", 167.259, 68, 1497, 2868, "Er", "Lanthanoids"));
			table.add(new Element("Thulium", 168.93422, 69, 1545, 1950, "Tm", "Lanthanoids"));
			table.add(new Element("Ytterbium", 173.045, 70, 818.9, 1196, "Yb", "Lanthanoids"));
			table.add(new Element("Lutetium", 174.9668, 71, 1663, 3402, "Lu", "Lanthanoids"));
			table.add(new Element("Hafnium", 178.486, 72, 2233, 4603, "Hf", "Transition Metals"));
			table.add(new Element("Tantalum", 180.94788, 73, 3017, 5458, "Ta", "Transition Metals"));
			table.add(new Element("Tungsten", 183.84, 74, 3422, 5555, "W", "Transition Metals"));
			table.add(new Element("Rhenium", 186.207, 75, 3186, 5596, "Re", "Transition Metals"));
			table.add(new Element("Osmium", 190.23, 76, 3033, 5012, "Os", "Transition Metals"));
			table.add(new Element("Iridium", 192.217, 77, 2466, 4428, "Ir", "Transition Metals"));
			table.add(new Element("Platinum", 195.084, 78, 1768.3, 3825, "Pt", "Transition Metals"));
			table.add(new Element("Gold", 196.966569, 79, 1064.18, 2856, "Au", "Transition Metals"));
			table.add(new Element("Mercury", 200.59, 80, -38.830, 356.73, "Hg", "Transition Metals"));
			table.add(new Element("Thallium", 204.38, 81, 304, 1473, "Tl", "Post-Transition Metals"));
			table.add(new Element("Lead", 207.2, 82, 327.46, 1749, "Pb", "Post-Transition Metals"));
			table.add(new Element("Bismuth", 208.98040, 83, 271.3, 1564, "Bi", "Post-Transition Metals"));
			table.add(new Element("Polonium", 209, 84, 255, 961.9, "Po", "Post-Transition Metals"));
			table.add(new Element("Astatine", 210, 85, 302, 350, "At", "Metalloids"));
			table.add(new Element("Radon", 222, 86, -71.1, -61.85, "Rn", "Noble Gases"));
			table.add(new Element("Francium", 223, 87, 20.9, 650, "Fr", "Alkali Metals"));
			table.add(new Element("Radium", 226, 88, 700, 1737, "Ra", "Alkaline Earth Metals"));
			table.add(new Element("Actinium", 227, 89, 1050, 3200, "Ac", "Actinoids"));
			table.add(new Element("Thorium", 232.0377, 90, 1750, 4820, "Th", "Actinoids"));
			table.add(new Element("Protactinium", 231.03588, 91, 1572, 4000, "Pa", "Actinoids"));
			table.add(new Element("Uranium", 238.02891, 92, 1135, 3900, "U", "Actinoids"));
			table.add(new Element("Neptunium", 237, 93, 644, 4000, "Np", "Actinoids"));
			table.add(new Element("Plutonium", 244, 94, 640, 3230, "Pu", "Actinoids"));
			table.add(new Element("Americium", 243, 95, 1176, 2011, "Am", "Actinoids"));
			table.add(new Element("Curium", 247, 96, 1345, 3110, "Cm", "Actinoids"));
			table.add(new Element("Berkelium", 247, 97, 1050, 0, "Bk", "Actinoids"));
			table.add(new Element("Californium", 251, 98, 899.9, 0, "Cf", "Actinoids"));
			table.add(new Element("Einsteinium", 252, 99, 259.9, 0, "Es", "Actinoids"));
			table.add(new Element("Fermium", 257, 100, 1500, 0, "Fm", "Actinoids"));
			table.add(new Element("Mendelevium", 258, 101, 830, 0, "Md", "Actinoids"));
			table.add(new Element("Nobelium", 259, 102, 830, 0, "No", "Actinoids"));
			table.add(new Element("Lawrencium", 266, 103, 1600, 0, "Lr", "Actinoids"));
			table.add(new Element("Rutherfordium", 267, 104, 0, 0, "Rf", "Transition Metals"));
			table.add(new Element("Dubnium", 268, 105, 0, 0, "Db", "Transition Metals"));
			table.add(new Element("Seaborgium", 269, 106, 0, 0, "Sg", "Transition Metals"));
			table.add(new Element("Bohrium", 270, 107, 0, 0, "Bh", "Transition Metals"));
			table.add(new Element("Hassium", 277, 108, 0, 0, "Hs", "Transition Metals"));
			table.add(new Element("Meitnerium", 278, 109, 0, 0, "Mt", "N/A"));
			table.add(new Element("Darmstadtium", 281, 110, 0, 0, "Ds", "N/A"));
			table.add(new Element("Roentgenium", 282, 111, 0, 0, "Rg", "N/A"));
			table.add(new Element("Copernicium", 285, 112, 0, 0, "Cn", "N/A"));
			table.add(new Element("Nihonium", 286, 113, 0, 0, "Nh", "N/A"));
			table.add(new Element("Flerovium", 289, 114, 0, 0, "Fl", "N/A"));
			table.add(new Element("Moscovium", 290, 115, 0, 0, "Mc", "N/A"));
			table.add(new Element("Livermonium", 293, 116, 0, 0, "Lv", "N/A"));
			table.add(new Element("Tennessine", 294, 117, 0, 0, "Ts", "N/A"));
			table.add(new Element("Oganesson", 294, 118, 0, 0, "Og", "N/A"));
	}
}