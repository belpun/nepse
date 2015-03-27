package com.nepse.analysis;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.exception.InsufficientDataException;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

@Service
public class TechnicalAnalysisService {

	private final JDBCCompanyRepository companyRepository;

	Core c = new Core();

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

		System.out.println("===============================================");
		RetCode retCode = c.rsi(0, close.length - 1, close, 14, lOutIdx,
				lOutSize, output1);

		if (retCode == RetCode.Success) {
			return output1;
		} else {
			System.out.println("Error");
			return null;
		}
	}

	// public static void main(String[] args) {
	// double[] closePrice = {8938,
	// 6892,
	// 9433,
	// 10692,
	// 9713,
	// 8740,
	// 14300,
	// 15092,
	// 12276,
	// 9405,
	// 10813,
	// 11055
	// };

	// Map<Long, Double> closingPrice = new HashMap<Long, Double>();
	//
	// closingPrice.put(new Long(1l), new Double(8938));
	// closingPrice.put(new Long(2l), new Double(6892));
	// closingPrice.put(new Long(3l), new Double(9433));
	// closingPrice.put(new Long(4l), new Double(10692));
	// closingPrice.put(new Long(5l), new Double(9713));
	// closingPrice.put(new Long(6l), new Double(8740));
	// closingPrice.put(new Long(7l), new Double(14300));
	// closingPrice.put(new Long(8l), new Double(15092));
	// closingPrice.put(new Long(9l), new Double(12276));
	// closingPrice.put(new Long(10l), new Double(9405));
	// closingPrice.put(new Long(11l), new Double(10813));
	// closingPrice.put(new Long(12l), new Double(11055));
	//
	// TechnicalAnalysisService.simpleMovingAverage("", 3, closingPrice);
	// }

	public Map<Long, Double> simpleMovingAverage(String symbol) {

		int PERIODS_AVERAGE = 30;
		Map<Long, Double> smaMap = new TreeMap<Long, Double>();

		Map<Long, Double> closingPrice = companyRepository
				.getClosingPrice(symbol);

		if (closingPrice.size() < PERIODS_AVERAGE + 1) {
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

		// c.ad

		int TOTAL_PERIODS = price.length;

		double[] out = new double[TOTAL_PERIODS - PERIODS_AVERAGE + 1];
		MInteger begin = new MInteger();
		MInteger length = new MInteger();

		RetCode retCode = c.sma(0, price.length - 1, price, PERIODS_AVERAGE,
				begin, length, out);

		if (retCode == RetCode.Success) {
			for (int i = 0; i < out.length; i++) {
				if (i == out.length - 1) {
					// get the last date and add one more data
					DateTime dt = new DateTime(dates[dates.length - 1]);
					dt = dt.plusDays(1);
					smaMap.put(dt.toDate().getTime(), out[i]);

				} else {
					smaMap.put(dates[PERIODS_AVERAGE + i], out[i]);
				}
			}
			return smaMap;
			// maMap
		} else {
			System.out.println("Error");
			return null;
		}
	}

	public Map<Long, Double> exponentialMovingAverage(String symbol) {

		int PERIODS_AVERAGE = 30;
		Map<Long, Double> emaMap = new TreeMap<Long, Double>();

		Map<Long, Double> closingPrice = companyRepository
				.getClosingPrice(symbol);

		if (closingPrice.size() < PERIODS_AVERAGE + 1) {
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

		// c.ad

		int TOTAL_PERIODS = price.length;

		double[] out = new double[TOTAL_PERIODS - PERIODS_AVERAGE + 1];
		MInteger begin = new MInteger();
		MInteger length = new MInteger();

		RetCode retCode = c.ema(0, price.length - 1, price, PERIODS_AVERAGE,
				begin, length, out);

		if (retCode == RetCode.Success) {
			for (int i = 0; i < out.length; i++) {
				if (i == out.length - 1) {
					// get the last date and add one more data
					DateTime dt = new DateTime(dates[dates.length - 1]);
					dt = dt.plusDays(1);
					emaMap.put(dt.toDate().getTime(), out[i]);

				} else {
					emaMap.put(dates[PERIODS_AVERAGE + i], out[i]);
				}
			}
			return emaMap;
			// maMap
		} else {
			System.out.println("Error");
			return null;
		}
	}
}
