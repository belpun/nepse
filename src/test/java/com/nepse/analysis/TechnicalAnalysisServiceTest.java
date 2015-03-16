package com.nepse.analysis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nepse.dao.JDBCCompanyRepository;

public class TechnicalAnalysisServiceTest {

	private TechnicalAnalysisService underTest;
	
	private Map<Long, Double> testMap;
	
	@Mock
	private JDBCCompanyRepository companyRepository;
	
	private final String[] dates = {
			"27/03/2014",
			"31/03/2014",
			"01/04/2014",
			"02/04/2014",
			"03/04/2014",
			"06/04/2014",
			"07/04/2014",
			"09/04/2014",
			"10/04/2014",
			"13/04/2014",
			"15/04/2014",
			"16/04/2014",
			"17/04/2014",
			"20/04/2014",
			"21/04/2014",
			"22/04/2014",
			"23/04/2014",
			"27/04/2014",
			"28/04/2014",
			"29/04/2014",
			"30/04/2014",
			"04/05/2014",
			"05/05/2014",
			"06/05/2014",
			"07/05/2014",
			"08/05/2014",
			"11/05/2014",
			"12/05/2014",
			"13/05/2014",
			"15/05/2014",
			"18/05/2014",
			"19/05/2014",
			"20/05/2014",
			"21/05/2014",
			"22/05/2014",
			"25/05/2014",
			"26/05/2014",
			"27/05/2014",
			"28/05/2014",
			"01/06/2014",
			"02/06/2014",
			"03/06/2014",
			"04/06/2014",
			"05/06/2014",
			"08/06/2014",
			"09/06/2014",
			"10/06/2014",
			"11/06/2014",
			"12/06/2014",
			"15/06/2014",
			"16/06/2014",
			"17/06/2014",
			"18/06/2014",
			"19/06/2014",
			"23/06/2014",
			"24/06/2014",
			"25/06/2014",
			"26/06/2014",
			"29/06/2014",
			"30/06/2014",
			"01/07/2014",
			"02/07/2014",
			"03/07/2014",
			"06/07/2014",
			"07/07/2014",
			"08/07/2014",
			"09/07/2014",
			"10/07/2014",
			"13/07/2014",
			"14/07/2014",
			"15/07/2014",
			"16/07/2014",
			"17/07/2014",
			"20/07/2014",
			"21/07/2014",
			"22/07/2014",
			"23/07/2014",
			"24/07/2014",
			"27/07/2014",
			"28/07/2014",
			"30/07/2014",
			"31/07/2014",
			"03/08/2014",
			"04/08/2014",
			"05/08/2014",
			"06/08/2014",
			"07/08/2014",
			"12/08/2014",
			"13/08/2014",
			"14/08/2014"

			
	};
	private final double[] close = {400,
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
	
	@Before
	public void setUp() throws ParseException {
		MockitoAnnotations.initMocks(this);
		
		testMap = createDummyMap();
		
		underTest = new TechnicalAnalysisService(companyRepository);
		
		Mockito.when(companyRepository.getClosingPrice(Mockito.anyString())).thenReturn(testMap);
		
		Assert.assertEquals(testMap.size(), 90);
		
	}
	
	@Test
	public void testRsiCalculation () {
		double[] returnedMap = underTest.rsiWith14DaysCalculation(close);
		
		Assert.assertEquals(76, returnedMap.length);
		Assert.assertEquals(47.62, returnedMap[0], 0.01);
		Assert.assertEquals(51.36, returnedMap[1], 0.01);
		Assert.assertEquals(46.58, returnedMap[2], 0.01);
		Assert.assertEquals(44.36, returnedMap[3], 0.01);
		Assert.assertEquals(39.31, returnedMap[4], 0.01);
		Assert.assertEquals(35.8, returnedMap[5], 0.01);
		Assert.assertEquals(35.8, returnedMap[6], 0.01);
		Assert.assertEquals(51.02, returnedMap[7], 0.01);
		Assert.assertEquals(64.03, returnedMap[8], 0.01);
		Assert.assertEquals(68.3, returnedMap[9], 0.01);
		Assert.assertEquals(62.32, returnedMap[10], 0.01);
		Assert.assertEquals(56.15, returnedMap[11], 0.01);
		Assert.assertEquals(52.93, returnedMap[12], 0.01);
		Assert.assertEquals(53.65, returnedMap[13], 0.01);
		Assert.assertEquals(54.39, returnedMap[14], 0.01);
		Assert.assertEquals(49.26, returnedMap[15], 0.01);
		Assert.assertEquals(50.11, returnedMap[16], 0.01);
		Assert.assertEquals(48.37, returnedMap[17], 0.01);
		Assert.assertEquals(51.95, returnedMap[18], 0.01);
		Assert.assertEquals(51.95, returnedMap[19], 0.01);
		Assert.assertEquals(47.2, returnedMap[20], 0.01);
		Assert.assertEquals(46.29, returnedMap[21], 0.01);
		Assert.assertEquals(50.42, returnedMap[22], 0.01);
		Assert.assertEquals(49.4, returnedMap[23], 0.01);
		Assert.assertEquals(48.34, returnedMap[24], 0.01);
		Assert.assertEquals(47.25, returnedMap[25], 0.01);
		Assert.assertEquals(51.91, returnedMap[26], 0.01);
		Assert.assertEquals(57.91, returnedMap[27], 0.01);
		Assert.assertEquals(62.9, returnedMap[28], 0.01);
		Assert.assertEquals(55.78, returnedMap[29], 0.01);
		Assert.assertEquals(49.72, returnedMap[30], 0.01);
		Assert.assertEquals(48.77, returnedMap[31], 0.01);
		Assert.assertEquals(60.24, returnedMap[32], 0.01);
		Assert.assertEquals(62.19, returnedMap[33], 0.01);
		Assert.assertEquals(62.19, returnedMap[34], 0.01);
		Assert.assertEquals(68.22, returnedMap[35], 0.01);
		Assert.assertEquals(70.74, returnedMap[36], 0.01);
		Assert.assertEquals(70.74, returnedMap[37], 0.01);
		Assert.assertEquals(65.9, returnedMap[38], 0.01);
		Assert.assertEquals(59.34, returnedMap[39], 0.01);
		Assert.assertEquals(55.39, returnedMap[40], 0.01);
		Assert.assertEquals(55.39, returnedMap[41], 0.01);
		Assert.assertEquals(58.59, returnedMap[42], 0.01);
		Assert.assertEquals(58.59, returnedMap[43], 0.01);
		Assert.assertEquals(63.85, returnedMap[44], 0.01);
		Assert.assertEquals(56.16, returnedMap[45], 0.01);
		Assert.assertEquals(56.16, returnedMap[46], 0.01);
		Assert.assertEquals(60.85, returnedMap[47], 0.01);
		Assert.assertEquals(56.51, returnedMap[48], 0.01);
		Assert.assertEquals(58.88, returnedMap[49], 0.01);
		Assert.assertEquals(56.67, returnedMap[50], 0.01);
		Assert.assertEquals(71.99, returnedMap[51], 0.01);
		Assert.assertEquals(70.01, returnedMap[52], 0.01);
		Assert.assertEquals(70.87, returnedMap[53], 0.01);
		Assert.assertEquals(74.77, returnedMap[54], 0.01);
		Assert.assertEquals(62.22, returnedMap[55], 0.01);
		Assert.assertEquals(58.44, returnedMap[56], 0.01);
		Assert.assertEquals(60.01, returnedMap[57], 0.01);
		Assert.assertEquals(65.19, returnedMap[58], 0.01);
		Assert.assertEquals(71.96, returnedMap[59], 0.01);
		Assert.assertEquals(69.65, returnedMap[60], 0.01);
		Assert.assertEquals(64.47, returnedMap[61], 0.01);
		Assert.assertEquals(61.65, returnedMap[62], 0.01);
		Assert.assertEquals(68.36, returnedMap[63], 0.01);
		Assert.assertEquals(69.01, returnedMap[64], 0.01);
		Assert.assertEquals(67.52, returnedMap[65], 0.01);
		Assert.assertEquals(58.65, returnedMap[66], 0.01);
		Assert.assertEquals(59.53, returnedMap[67], 0.01);
		Assert.assertEquals(52.33, returnedMap[68], 0.01);
		Assert.assertEquals(47.2, returnedMap[69], 0.01);
		Assert.assertEquals(47.2, returnedMap[70], 0.01);
		Assert.assertEquals(44.19, returnedMap[71], 0.01);
		Assert.assertEquals(47.78, returnedMap[72], 0.01);
		Assert.assertEquals(39.18, returnedMap[73], 0.01);
		Assert.assertEquals(44.3, returnedMap[74], 0.01);
		Assert.assertEquals(43.85, returnedMap[75], 0.01);

	}
	
	@Test
	public void testRsiCalculationMap() {
		Map<Long, Double> returnedMap = underTest.rsiWith14DaysCalculation("MEGA");
		
		Assert.assertEquals(returnedMap.size(), 76);
		
		for (Long date : returnedMap.keySet()) {
			System.out.println(new Date(date) +  " = " + returnedMap.get(date));
		}
		
	}
	
	public Map<Long, Double> createDummyMap() throws ParseException {
		
		Map<Long, Double> testMap = new TreeMap<Long, Double>();
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < dates.length; i++) {
			
			Date parseDate = sdf.parse(dates[i]);
			testMap.put(parseDate.getTime(), close[i]);
		}
		
		return testMap;
	}
	
	

}
