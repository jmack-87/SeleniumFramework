package com.ibm.ciclan.Base.CustomAnnotations;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.ibm.ciclan.Base.CustomAnnotations.RetryOnFailCount;

@SuppressWarnings("unused")
public class RetryAnalyzer implements IRetryAnalyzer {

	int counter = 0;

	@Override
	public boolean retry(ITestResult result) {

		// check if the test method had RetryCountIfFailed annotation
		RetryOnFailCount annotation = result.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(RetryOnFailCount.class);
		// based on the value of annotation see if test needs to be rerun
		if((annotation != null) && (counter < annotation.value())) {
			counter++;
			return true;
		}
		return false;
	}
}