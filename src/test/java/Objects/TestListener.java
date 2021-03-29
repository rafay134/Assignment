package Objects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;

public class TestListener implements ITestListener {

	public static String getTestMethodName(ITestResult iTestResult){
   return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	//Text attachments or allure
	@Attachment(value="Page Screenshot", type="image/png")
	public byte[] saveScreenshotPNG (WebDriver driver ){
		
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)	;
				
	
	}
	
	
	@Attachment (value="{0}", type="text/plain")
	public static String  saveTextLog(String message){
		return message;
	}

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
