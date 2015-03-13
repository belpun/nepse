package com.nepse.analysis;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.meta.helpers.SimpleHelper;

public class TechnicalAnalysisService {
	
	
	public static void main(String[] args) {
//		double close[] = {
//                1.4554, 1.4560, 1.4562, 1.4559, 1.4557, 1.4557, 1.4551, 1.4554, 1.4556, 1.4556,
//                1.4554, 1.4560, 1.4562, 1.4559, 1.4557, 1.4557, 1.4551, 1.4554, 1.4556, 1.4556,
//                1.4554, 1.4560, 1.4562, 1.4559, 1.4557, 1.4557, 1.4551, 1.4554, 1.4556, 1.4556,
//                1.4554, 1.4560, 1.4562, 1.4559, 1.4557, 1.4557, 1.4551, 1.4554, 1.4556, 1.4556,
//                1.4554, 1.4560, 1.4562, 1.4559, 1.4557, 1.4557, 1.4551, 1.4554, 1.4556, 1.4556,
//                1.4554, 1.4560, 1.4562, 1.4559, 1.4557, 1.4557, 1.4551, 1.4554, 1.4556, 1.4556
//        };
		double close[] = {400,
				400,
				403,
				410,
				400,
				404,
				403,
				400,
				401,
				404,
				405,
				401,
				401,
				402,
				398,
				401,
				397,
				395,
				390,
				386,
				386,
				398,
				415,
				423,
				417,
				410,
				406,
				407,
				408,
				402,
				403,
				401,
				405,
				405,
				400,
				399,
				403,
				402,
				401,
				400,
				404,
				410,
				416,
				410,
				404,
				403,
				417,
				420,
				420,
				430,
				435,
				435,
				431,
				425,
				421,
				421,
				425,
				425,
				432,
				425,
				425,
				431,
				427,
				430,
				428,
				455,
				453,
				455,
				465,
				451,
				446,
				449,
				460,
				479,
				476,
				469,
				465,
				483,
				485,
				483,
				470,
				472,
				460,
				450,
				450,
				444,
				450,
				431,
				440,
				439
        };
		
		rsiWith14DaysCalculation(close);
		
	}
	
	//relative Strength Index Calculation
	public static double[] rsiWith14DaysCalculation(double close[]){
		
		  double output1[] = new double[close.length - 1];
		  
		  MInteger lOutIdx  = new MInteger();
		  MInteger lOutSize = new MInteger();
		  
		  String func = "rsi";
		  List<String> params = new ArrayList<String>();
	        
          params.clear();
          params.add("14");
          
          SimpleHelper calc = new SimpleHelper(func, params);
          
          System.out.println("===============================================");
          System.out.println(func);
          try {
			calc.calculate(0, output1.length , new Object[] { close }, new Object[] { output1 }, lOutIdx, lOutSize);
		    System.out.println("lookback="+calc.getLookback());
	          System.out.println("outBegIdx    = "+lOutIdx.value+ "    outNbElement = "+lOutSize.value);
	          for (int i=0; i<lOutSize.value; i++) {
	              System.out.println("output1["+i+"]="+output1[i]);
	          }
	          
	          return output1;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
          
          return null;
	}

}
