package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    Logger logger = LogManager.getLogger(Listener.class);
    public void onTestFailure(ITestResult result){
        logger.info(result.getMethod().getMethodName()+" Failed");

        test.log(Status.FAIL, "Failed");
        extent.flush();
    }

    ExtentReports extent = ExtentReporter.extentReporterGenerator();
    ExtentTest test;
    @Override
    public void onTestStart(ITestResult result) {
        logger.info(result.getMethod().getMethodName()+" Started");

        test = extent.createTest(result.getMethod().getMethodName());

    }

    public void onTestSuccess(ITestResult result){
        logger.info(result.getMethod().getMethodName()+" Passed");

        test.log(Status.PASS, "Successful");
        extent.flush();
    }
}

