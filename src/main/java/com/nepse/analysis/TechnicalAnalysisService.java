package com.nepse.analysis;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.exception.InsufficientDataException;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.meta.helpers.SimpleHelper;

@Service
public class TechnicalAnalysisService {

	private final JDBCCompanyRepository companyRepository;

	@Autowired
	public TechnicalAnalysisService(JDBCCompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public Map<Long, Double> rsiWith14DaysCalculation(String symbol) {
		Map<Long, Double> closingPrice = companyRepository
				.getClosingPrice(symbol);

		if (closingPrice.size() < 15) {
			throw new InsufficientDataException("Not enought data to calculate");
		}

		long[] dates = new long[closingPrice.size()];
		double[] price = new double[closingPrice.size()];

		int index = 0;
		for (Long key : closingPrice.keySet()) {
			dates[index] = key;
			price[index] = closingPrice.get(key);
			index++;
		}

		double[] rsiCalculation = rsiWith14DaysCalculation(price);
		Map<Long, Double> rsiMap = new TreeMap<Long, Double>();
		for (int i = 0; i < rsiCalculation.length; i++) {
			rsiMap.put(dates[i + 14], rsiCalculation[i]);
		}

		return rsiMap;
	}

	// relative Strength Index Calculation
	public double[] rsiWith14DaysCalculation(double close[]) {

		double output1[] = new double[close.length - 14];

		MInteger lOutIdx = new MInteger();
		MInteger lOutSize = new MInteger();

		String func = "rsi";
		List<String> params = new ArrayList<String>();

		params.clear();
		params.add("14");

		SimpleHelper calc = new SimpleHelper(func, params);

		System.out.println("===============================================");
		System.out.println(func);
		try {
			calc.calculate(0, close.length - 1, new Object[] { close },
					new Object[] { output1 }, lOutIdx, lOutSize);
			System.out.println("lookback=" + calc.getLookback());
			System.out.println("outBegIdx    = " + lOutIdx.value
					+ "    outNbElement = " + lOutSize.value);

			for (int i = 0; i < output1.length; i++) {
				System.out.println("output1[" + i + "]=" + output1[i]);
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
