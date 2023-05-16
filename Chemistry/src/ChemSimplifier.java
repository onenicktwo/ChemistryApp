import java.util.ArrayList;

public class ChemSimplifier 
{
	String equation;
	ArrayList<String> elm1 = new ArrayList<String>(1);
	ArrayList<String> elm2 = new ArrayList<String>(1);
	ArrayList<String> molecule1 = new ArrayList<String>(1);
	ArrayList<String> molecule2 = new ArrayList<String>(1);
	//List of totals by appearance in equation
	ArrayList<Integer> totalElm1 = new ArrayList<Integer>(1);
	ArrayList<Integer> totalElm2 = new ArrayList<Integer>(1);
	//Raw list of elements
	ArrayList<String> rawElm1;
	ArrayList<String> rawElm2;
	//List of totals by appearance and adds copies together
	ArrayList<Integer> totaled1 = new ArrayList<Integer>(1);
	ArrayList<Integer> totaled2 = new ArrayList<Integer>(1);
	//Sorts each element by molecules
	String[][] elmByMol1;
	String[][] elmByMol2;
	
	public ChemSimplifier()
	{
		equation = "";
	}
	
	public void setEquation(String e)
	{
		equation = e;
	}
	
	public void findElements()
	{
		elm1.clear();
		elm2.clear();
		
		if(equation.indexOf("=") > -1)
		{
			for(int i = 0; i < equation.length() - 1; i++)
			{
				if(equation.substring(0, i).indexOf("=") == -1)
				{
					if(isStringLexicon(equation.substring(i, i + 1)))
					{
						//Checks for any lowercase letters (2nd character of an element)
						if(isStringLower(equation.substring(i, i + 1)))
						{
							//Checks for elements like dihydrogen ie H2, this is the most
							//Efficient since checking numbers would require a longer list of if statements
							//In consequence you have to check the final character in the string manually
							if(isStringNumeric(equation.substring(i + 1, i + 2)))
							{
								elm1.add(equation.substring(i - 1, i + 2));
							}
							else
							{
								elm1.add(equation.substring(i - 1, i + 1));
							}
						}
						//Will check for solo capitals not near a lowercase
						else if(isStringUpper(equation.substring(i, i + 1)) && !(isStringLexicon(equation.substring(i + 1, i + 2)) && isStringLower(equation.substring(i + 1, i + 2))))
						{
							//Checks for elements like dihydrogen ie H2, this is the most
							//Efficient since checking numbers would require a longer list of if statements
							//In consequence you have to check the final character in the string manually
							if(isStringNumeric(equation.substring(i + 1, i + 2)))
							{
								elm1.add(equation.substring(i, i + 2));
							}
							else
							{
								elm1.add(equation.substring(i, i + 1));
							}
						}
					}
				}
				//Misses the final character due to numeric characters
				else
				{
					if(isStringLexicon(equation.substring(i, i + 1)))
					{
						//Checks for any lowercase letters (2nd character of an element)
						if(isStringLower(equation.substring(i, i + 1)))
						{
							//Checks for elements like dihydrogen ie H2, this is the most
							//Efficient since checking numbers would require a longer list of if statements
							//In consequence you have to check the final character in the string manually
							if(isStringNumeric(equation.substring(i + 1, i + 2)))
							{
								elm2.add(equation.substring(i - 1, i + 2));
							}
							else
							{
								elm2.add(equation.substring(i - 1, i + 1));
							}
						}
						//Will check for solo capitals not near a lowercase
						else if(isStringUpper(equation.substring(i, i + 1)) && !(isStringLexicon(equation.substring(i + 1, i + 2)) && isStringLower(equation.substring(i + 1, i + 2))))
						{
							//Checks for elements like dihydrogen ie H2, this is the most
							//Efficient since checking numbers would require a longer list of if statements
							//In consequence you have to check the final character in the string manually
							if(isStringNumeric(equation.substring(i + 1, i + 2)))
							{
								elm2.add(equation.substring(i, i + 2));
							}
							else
							{
								elm2.add(equation.substring(i, i + 1));
							}
						}
					}
				}
				if(i == equation.length() - 2)
				{
					if(isStringLexicon(equation.substring(equation.length() - 1)))
					{
						if(isStringLower(equation.substring(equation.length() - 1)))
						{
							elm2.add(equation.substring(equation.length() - 1));
						}
						//Will check for solo capitals not near a lowercase
						else if(isStringUpper(equation.substring(equation.length() - 1)))
						{
							elm2.add(equation.substring(equation.length() - 1));
						}
						
					}
				}
			}
		}
		System.out.println(elm1.toString());
		System.out.println(elm2.toString());
	}
	
	public boolean validElements()
	{
		for(String e1 : elm1)
		{
			if(isStringNumeric(e1.substring(e1.length() - 1)))
			{
				if(elm2.toString().indexOf(e1.substring(0, e1.length() - 1)) == -1)
				{
					return false;
				}
			}
			else
			{
				if(elm2.toString().indexOf(e1) == -1)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void findMolecules()
	{
		molecule1.clear();
		molecule2.clear();
		
		for(int i1 = 0; i1 < equation.length() - 1; i1++)
		{
			if(equation.substring(0, i1 + 1).indexOf("=") == -1)
			{
				if(equation.substring(i1, i1 + 1).equals("+"))
				{
					if(equation.substring(0, i1 + 1).indexOf("+") < i1)
					{
						for(int i2 = i1; i2 > 0; i2--)
						{
							if(equation.substring(i2, i2 + 1).equals("+") && i1 != i2)
							{
								molecule1.add(equation.substring(i2 + 1, i1));
							}
						}
					}
					else
					{
						molecule1.add(equation.substring(0, i1));
					}
				}
			}
			//Fix
			else
			{
				//Should only activate once when it first enters the else
				if(equation.substring(i1, i1 + 1).equals("="))
				{
					for(int i2 = i1; i2 > 0; i2--)
					{
						if(equation.substring(i2, i2 + 1).equals("+"))
						{
							molecule1.add(equation.substring(i2 + 1, i1));
							i2 = 1;
						}
					}
					for(int i2 = i1; i2 < equation.length(); i2++)
					{
						if(equation.substring(i2, i2 + 1).equals("+"))
						{
							molecule2.add(equation.substring(i1 + 1, i2));
							i2 = equation.length();
						}
						else if(i2 == equation.length() - 1)
						{
							molecule2.add(equation.substring(i1 + 1, equation.length()));
							i2 = equation.length();
						}
					}
				}
				//Activates like the other + sign if statement, but forward
				else if(equation.substring(i1, i1 + 1).equals("+"))
				{
					for(int i2 = i1 + 1; i2 < equation.length(); i2++)
					{
						if(equation.substring(i2, i2 + 1).equals("+"))
						{
							molecule2.add(equation.substring(i1 + 1, i2));
							i2 = equation.length();
						}
						else if(i2 == equation.length() - 1)
						{
							molecule2.add(equation.substring(i1 + 1));
						}
					}
					
				}
			}
		}
		System.out.println(molecule1.toString());
		System.out.println(molecule2.toString());
	}

	//Can only hold at most 5 elements per molecule
	//Putting the list of elements into a temporary one and delete them after they are placed
	public void sortElementsByMolecules()
	{
		ArrayList<String> tempElm1 = new ArrayList<String>();
		ArrayList<String> tempElm2 = new ArrayList<String>();
		tempElm1.addAll(elm1);
		tempElm2.addAll(elm2);
		int count = 0;
		String temp = "";
		
		elmByMol1 = new String[molecule1.size()][5];
		elmByMol2 = new String[molecule2.size()][5];
		
		for(int i1 = 0; i1 < molecule1.size(); i1++)
		{
			for(int i2 = 0; i2 < tempElm1.size(); i2++)
			{
				if(molecule1.get(i1).indexOf(tempElm1.get(i2)) != -1)
				{
					if(molecule1.get(i1).indexOf(temp + tempElm1.get(i2)) != -1)
					{
						temp += tempElm1.get(i2);
						elmByMol1[i1][count] = tempElm1.get(i2);
						count++;
						tempElm1.set(i2, "XX");
					}
				}
			}
			count = 0;
			temp = "";
		}
		for(int i1 = 0; i1 < molecule2.size(); i1++)
		{
			for(int i2 = 0; i2 < tempElm2.size(); i2++)
			{
				if(molecule2.get(i1).indexOf(tempElm2.get(i2)) != -1)
				{
					if(molecule2.get(i1).indexOf(temp + tempElm2.get(i2)) != -1)
					{
						temp += tempElm2.get(i2);
						elmByMol2[i1][count] = tempElm2.get(i2);
						count++;
						tempElm2.set(i2, "XX");
					}
				}
			}
			count = 0;
			temp = "";
		}
		setNullToChar();
		
		for(int r = 0; r < elmByMol1.length; r++)
		{
			for(int c = 0; c < elmByMol1.length; c++)
			{
				System.out.print(elmByMol1[r][c] + " ");
			}
			System.out.println();
		}
		for(int r = 0; r < elmByMol2.length; r++)
		{
			for(int c = 0; c < elmByMol2.length; c++)
			{
				System.out.print(elmByMol2[r][c] + " ");
			}
			System.out.println();
		}
	}
	
	public void setTotalElementIso()
	{
		totalElm1.clear();
		totalElm2.clear();
		
		for(String elm : elm1)
		{
			for(int i = elm.length() - 1; i >= 0; i--)
			{
				if(isStringLexicon(elm.substring(i, i + 1)) && i == elm.length() - 1)
				{
					totalElm1.add(1);
					i = -1;
				}
				else if(isStringLexicon(elm.substring(i, i + 1)) && i != elm.length() - 1)
				{
					totalElm1.add(Integer.parseInt(elm.substring(i + 1, elm.length())));
					i = -1;
				}
			}
		}
		for(String elm : elm2)
		{
			for(int i = elm.length() - 1; i >= 0; i--)
			{
				if(isStringLexicon(elm.substring(i, i + 1)) && i == elm.length() - 1)
				{
					totalElm2.add(1);
					i = -1;
				}
				else if(isStringLexicon(elm.substring(i, i + 1)) && i != elm.length() - 1)
				{
					totalElm2.add(Integer.parseInt(elm.substring(i + 1, elm.length())));
					i = -1;
				}
			}
		}
	}
	
	//Finds total of a element
	//Finds its use within a molecule (if it happens multiple times within molecules it'll go by appearance)
	//It'll check the front of the molecule using a for loop (in case the number is bigger then 9), and multiply the front by the total of the element found
	public void setTotalElementMol()
	{	
		String temp = "";
		for(int r = 0; r < elmByMol1.length; r++)
		{
			for(int i1 = 0; i1 < molecule1.get(r).length(); i1++)
			{
				if(isStringLexicon(molecule1.get(r).substring(i1, i1 + 1)) && i1 == 0)
				{
					temp = "1";
					i1 = molecule1.get(r).length();
				}
				else if(isStringLexicon(molecule1.get(r).substring(i1, i1 + 1)) && i1 != 0)
				{
					temp = molecule1.get(r).substring(0, i1);
					i1 = molecule1.get(r).length();
				}
			}
			for(int i2 = 0; i2 < elm1.size(); i2++)
			{
				for(int c = 0; c < elmByMol1[0].length; c++)
				{
					if(elmByMol1[r][c] == elm1.get(i2))
					{
						totalElm1.set(i2, totalElm1.get(i2) * Integer.parseInt(temp));
					}
				}
			}
			temp = "";
		}
		for(int r = 0; r < elmByMol2.length; r++)
		{
			for(int i1 = 0; i1 < molecule2.get(r).length(); i1++)
			{
				if(isStringLexicon(molecule2.get(r).substring(i1, i1 + 1)) && i1 == 0)
				{
					temp = "1";
					i1 = molecule2.get(r).length();
				}
				else if(isStringLexicon(molecule2.get(r).substring(i1, i1 + 1)) && i1 != 0)
				{
					temp = molecule2.get(r).substring(0, i1);
					i1 = molecule2.get(r).length();
				}
			}
			for(int i2 = 0; i2 < elm2.size(); i2++)
			{
				for(int c = 0; c < elmByMol2[0].length; c++)
				{
					if(elmByMol2[r][c] == elm2.get(i2))
					{
						totalElm2.set(i2, totalElm2.get(i2) * Integer.parseInt(temp));
					}
				}
			}
			temp = "";
		}
	}

	public void setRawCopies()
	{
		//Raw copies aren't copying correctly
		rawElm1 = new ArrayList<String>(elm1);
		rawElm2 = new ArrayList<String>(elm2);
		String temp = "";
		
		//Sets a temp list of elements to their exact elements ignoring isotope
		for(int i1 = 0; i1 < rawElm1.size(); i1++)
		{
			for(int i2 = 0; i2 < rawElm1.get(i1).length(); i2++)
			{
				if(isStringNumeric(rawElm1.get(i1).substring(i2, i2 + 1)))
				{
					temp = rawElm1.get(i1).substring(0, i2);
					i2 = rawElm1.get(i1).length();
				}
				else if(i2 == rawElm1.get(i1).length() - 1)
				{
					temp = rawElm1.get(i1);
				}
			}
			rawElm1.set(i1, temp);
		}
		for(int i1 = 0; i1 < rawElm2.size(); i1++)
		{
			for(int i2 = 0; i2 < rawElm2.get(i1).length(); i2++)
			{
				if(isStringNumeric(rawElm2.get(i1).substring(i2, i2 + 1)))
				{
					temp = rawElm2.get(i1).substring(0, i2);
					i2 = rawElm2.get(i1).length();
				}
				else if(i2 == rawElm2.get(i1).length() - 1)
				{
					temp = rawElm2.get(i1);
				}
			}
			rawElm2.set(i1, temp);
		}
	}

	public void setTotals()
	{
		totaled1.clear();
		totaled2.clear();
		//Finds copies and makes a new total based off of total amount of raw element
		for(int i1 = 0; i1 < rawElm1.size(); i1++)
		{
			if(!isThereCopies1(i1))
			{
				totaled1.add(totalElm1.get(i1));
				for(int i2 = i1 + 1; i2 < rawElm1.size(); i2++)
				{
					if(rawElm1.get(i1).equals(rawElm1.get(i2)))
					{
						totaled1.set(i1, totaled1.get(i1) + totalElm1.get(i2));
					}
				}
			}
		}
		for(int i1 = 0; i1 < rawElm2.size(); i1++)
		{
			if(!isThereCopies2(i1))
			{
				totaled2.add(totalElm2.get(i1));
				for(int i2 = i1 + 1; i2 < rawElm2.size(); i2++)
				{
					if(rawElm2.get(i1).equals(rawElm2.get(i2)))
					{
						totaled2.set(i1, totaled2.get(i1) + totalElm2.get(i2));
					}
				}
			}
		}
		System.out.println(totaled1.toString());
		System.out.println(totaled2.toString());
	}
	
	public void Solve()
	{
		int temp;
		int index = 0;
		int mol = 1;
		boolean check = false;
		
		while(!isSolved())
		{
			//Algorithm
			//it should check if it is more or less then its counter part (use elmByMol)
			//then for each molecule, change the amount by one or ++
			//If less, continue to add to the molecule
			//If more, move to the next molecule
			//This should solve the equation, there is a chance that
			
			temp = 1;
			if(mol == 1)
			{
				check = isCounterpartLessOrMore1(index);
				if(check)
				{
					//Fix, not changing molecules (also doesn't hit the else if)
					for(int i1 = 0; i1 < molecule1.get(index).length(); i1++)
					{
						if(isStringLexicon(molecule1.get(index).substring(i1, i1 + 1)) && i1 == 0)
						{
							molecule1.set(index, temp + molecule1.get(index).substring(i1));
							i1 = molecule1.get(index).length();
						}
						else if(isStringLexicon(molecule1.get(index).substring(i1, i1 + 1)) && i1 != 0)
						{
							//Put this up there ^
							temp = Integer.parseInt(molecule1.get(index).substring(0, i1));
							temp++;
							molecule1.set(index, temp + molecule1.get(index).substring(i1));
							i1 = molecule1.get(index).length();
						}
					}
				}
				else
				{
					index++;
					if(index == molecule1.size())
					{
						mol++;
						index = 0;
					}
				}
			}
			else
			{
				check = isCounterpartLessOrMore2(index);
				if(check)
				{
					//Fix, not changing molecules (also doesn't hit the else if)
					for(int i1 = 0; i1 < molecule2.get(index).length(); i1++)
					{
						if(isStringLexicon(molecule2.get(index).substring(i1, i1 + 1)) && i1 == 0)
						{
							molecule2.set(index, temp + molecule2.get(index).substring(i1));
							i1 = molecule2.get(index).length();
						}
						else if(isStringLexicon(molecule2.get(index).substring(i1, i1 + 1)) && i1 != 0)
						{
							temp = Integer.parseInt(molecule2.get(index).substring(0, i1));
							temp++;
							molecule2.set(index, temp + molecule2.get(index).substring(i1));
							i1 = molecule2.get(index).length();
						}
					}
				}
				else
				{
					index++;
					if(index == molecule2.size())
					{
						mol = 1;
						index = 0;
					}
				}
			}
			setTotalElementIso();
			setTotalElementMol();
			setRawCopies();
			setTotals();
		}
		System.out.println("Done");
	}
	
	public boolean isCounterpartLessOrMore1(int index)
	{
		for(int i1 = 0; i1 < elmByMol1[index].length; i1++)
		{
			for(int i2 = 0; i2 < rawElm1.size(); i2++)
			{
				if(!isThereCopies1(i2) && !elmByMol1[index][i1].equals("XX"))
				{
					//Sees if the element in the molecule is equal to the raw type and double checks since elements have the same letter sometimes (example: C and Ca)
					if(elmByMol1[index][i1].indexOf(rawElm1.get(i2)) != -1 && rawElm1.get(i2).equals(elmByMol1[index][i1].substring(elmByMol1[index][i1].indexOf(rawElm1.get(i2)), elmByMol1[index][i1].indexOf(rawElm1.get(i2)) + rawElm1.get(i2).length())))
					{
						//i2 only works for totaled1
						for(int i3 = 0; i3 < rawElm2.size(); i3++)
						{
							if(rawElm1.get(i2).equals(rawElm2.get(i3)))
							{
								if(totaled1.get(i2) < totaled2.get(i3))
								{
									return true;
								}
								i3 = rawElm2.size();
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isCounterpartLessOrMore2(int index)
	{
		for(int i1 = 0; i1 < elmByMol2[index].length; i1++)
		{
			for(int i2 = 0; i2 < rawElm2.size(); i2++)
			{
				if(!isThereCopies2(i2) && !elmByMol2[index][i1].equals("XX"))
				{
					//Sees if the element in the molecule is equal to the raw type and double checks since elements have the same letter sometimes (example: C and Ca)
					if(elmByMol2[index][i1].indexOf(rawElm2.get(i2)) != -1 && rawElm2.get(i2).equals(elmByMol2[index][i1].substring(elmByMol2[index][i1].indexOf(rawElm2.get(i2)), elmByMol2[index][i1].indexOf(rawElm2.get(i2)) + rawElm2.get(i2).length())))
					{
						//i2 only works for totaled2
						for(int i3 = 0; i3 < rawElm1.size(); i3++)
						{
							if(rawElm2.get(i2).equals(rawElm1.get(i3)))
							{
								if(totaled2.get(i2) < totaled1.get(i3))
								{
									return true;
								}
								i3 = rawElm1.size();
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isSolved()
	{
		//Checks totaled1 to totaled2 based off the raw type
		for(int i1 = 0; i1 < rawElm1.size(); i1++)
		{
			if(!isThereCopies1(i1))
			{
				for(int i2 = 0; i2 < rawElm2.size(); i2++)
				{
					if(!isThereCopies2(i2))
					{
						if(rawElm1.get(i1).equals(rawElm2.get(i2)))
						{
							if(totaled1.get(i1) != totaled2.get(i2))
							{
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean isThereCopies1(int i1)
	{
		for(int i2 = i1 - 1; i2 >= 0; i2--)
		{
			if(rawElm1.get(i1).equals(rawElm1.get(i2)))
			{
				return true;
			}
		}
		return false;
	}
	public boolean isThereCopies2(int i1)
	{
		for(int i2 = i1 - 1; i2 >= 0; i2--)
		{
			if(rawElm2.get(i1).equals(rawElm2.get(i2)))
			{
				return true;
			}
		}
		return false;
	}
	
	public String ignoreNum(ArrayList<String> elm, int index)
	{
		for(int i2 = 0; i2 < elm.get(index).length(); i2++)
		{
			if(!isStringLexicon(elm.get(index).substring(i2, i2 + 1)))
			{
				return elm.get(index).substring(0, i2);
			}
			else if(i2 == elm.get(index).length() - 1)
			{
				return elm1.get(index).substring(0, i2 + 1);
			}
		}
		return "";
	}
	
	public void setNullToChar()
	{
		for(int r = 0; r < elmByMol1.length; r++)
		{
			for(int c = 0; c < elmByMol1[0].length; c++)
			{
				if(elmByMol1[r][c] == null)
				{
					elmByMol1[r][c] = "XX";
				}
			}
		}
		for(int r = 0; r < elmByMol2.length; r++)
		{
			for(int c = 0; c < elmByMol2[0].length; c++)
			{
				if(elmByMol2[r][c] == null)
				{
					elmByMol2[r][c] = "XX";
				}
			}
		}
	}
	
	public boolean isStringLexicon(String str)
	{
		if(str.toUpperCase() != str.toLowerCase())
		{
			return true;
		}
		return false;
	}
	
	public boolean isStringNumeric(String str)
	{
		try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	public boolean isStringUpper(String str)
	{
		if(str.toUpperCase().equals(str))
		{
			return true;
		}
		return false;
	}
	
	public boolean isStringLower(String str)
	{
		if(str.toLowerCase().equals(str))
		{
			return true;
		}
		return false;
	}
}
