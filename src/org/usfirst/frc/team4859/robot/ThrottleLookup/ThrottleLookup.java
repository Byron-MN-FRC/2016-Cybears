package org.usfirst.frc.team4859.robot.ThrottleLookup;

public class ThrottleLookup 
{
	/* The tables are set up as follows, the first range of numbers are associated with the
	 * number being passed to the calcJoystickCorrection routine.  In the correctionTable1 
	 * below, {.015, .25, .50, .75, 1.0} are the values that "x" is compared to in calcJoystickCorrection.
	 * The second array of numbers, {.000, .05, .1, .2, 0.4}, make up the array of values that will be
	 * return.  For instance, if the value of x is .623, a value on the line between .1 and .2 will be returned.
	 */

		// SlowY
		public static double[][] correctionTable1 = {
			{.015, .25, .500, .7500, 1.00},
			{.000, .05, .1,   .2,    0.4}};
		
		// NormY
		public static double[][] correctionTable2 = {
			{.015, .25, .50, .75, 1.0},
			{.000, .15, .35, .60, 1.0}};

		// SlowT
		public static double[][] correctionTable3 = {
			{.015, .2500, .5, .75, 1.0},
			{.000, .0625, .1, .20, 0.4}};

		// NormT
		public static double[][] correctionTable4 = {
			{.015, .25, .500, .75, 1.0},
			{.000, .10, .175, .30, 0.6}};
	
	/* This routine use the values of the above tables to return a calculated
	 * corrected value.  It handles numbers between fractional numbers between -1 and 1.
	 */
	public static double calcJoystickCorrection(String tableName, double x)
	{
		double[][] correctionTable;
		
		switch (tableName)
		{
			case "SlowY" : correctionTable = correctionTable1;
				break;
			case "NormY" : correctionTable = correctionTable2;
				break;
			case "SlowT": correctionTable = correctionTable3;
				break;
			case "NormT": correctionTable = correctionTable4;
				break;
			default : correctionTable = correctionTable1;
		}
		
		// Determine if x is negative and remember it
		boolean isNegative = x < 0;
		
		// Make it positive so we don't have to deal with negatives differently.
		x = Math.abs(x);
		
		int pos = 0; 
		double returnValue;
		
		// Find the position in the table where x lies.  (row 0, columns 0-4)
		while ((pos < 5) && (x > correctionTable[0][pos]))
		{
			pos++;
		}
		
		if (pos < 5)
		{
			if (pos != 0)
			{
				double y1 = correctionTable[1][pos];
				double y2 = correctionTable[1][pos-1];
				double x1 = correctionTable[0][pos];
				double x2 = correctionTable[0][pos-1];
				returnValue = y1 + ((y1-y2)/(x1-x2)) * (x-x1);
			}
			else
			{
				returnValue = 0.0;
			}
		}
		else
		{
			returnValue = 1.0;
		}
		
		// return the return value (negate it it was originally negative.
		if (isNegative)
			returnValue = -returnValue;
		
		return returnValue;
	}
}