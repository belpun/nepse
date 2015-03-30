package com.nepse.job;

public class UpdateOpeningPriceFromFileToDbJob {

	public static void main(String[] args) {
		new JobLauncherApp().launch("writeOpeningPriceFromFileToDbJob");

	}

}
