package com.base;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class Execution_Report extends Utility {
	public static String PassedCount;
	public static String FailedCount;
	public static String StartTime;
	public static String EndTime;
	public static int total;
	public static int totalPassedCount;
	public static int totalFailedCount;
	public static int totalCount;
    public static ReadConfig prop = new ReadConfig();

    public static void store_suite_1Results() {

		PassedCount = readTestResults("PassedCount");
		FailedCount = readTestResults("FailedCount");
		int pcountt = Integer.parseInt(PassedCount);
		int fcountt = Integer.parseInt(FailedCount);
		int totalCount = Integer.parseInt(PassedCount) + Integer.parseInt(FailedCount);
        System.out.println("Passed TC= " + pcountt);
		System.out.println("Failed TC= " + fcountt);
		System.out.println("Total TC executed= " + totalCount);
		StartTime = readTestResults("Start");
		EndTime = readTestResults("End");
		try {

			prop.putSuite_1Passvalue(PassedCount);
			prop.putSuite_1Failvalue(FailedCount);
			prop.putSuite_1Totalvalue(String.valueOf(totalCount));
			
			prop.putSuite_1_starttime(StartTime);
			prop.putSuite_1EndTime(EndTime);
            
			
			prop.putSuite_2Passvalue("0");
			prop.putSuite_2Failvalue("0");
			prop.putSuite_2Totalvalue("0");
			
			System.out.println("Suite one result updated sucessfully");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void store_suite_2Results() {

		PassedCount = readTestResults("PassedCount");
		FailedCount = readTestResults("FailedCount");
		int pcountt = Integer.parseInt(PassedCount);
		int fcountt = Integer.parseInt(FailedCount);
		int totalCount = Integer.parseInt(PassedCount) + Integer.parseInt(FailedCount);

		System.out.println("Passed TC= " + pcountt);
		System.out.println("Failed TC= " + fcountt);
		System.out.println("Total TC executed= " + totalCount);
		
		try {

			System.out.println("REPORT_DATA table updated sucessfully");
			prop.putSuite_2Passvalue(PassedCount);
			prop.putSuite_2Failvalue(FailedCount);
			prop.putSuite_2Totalvalue(String.valueOf(totalCount));
	
			System.out.println("Suite two result updated sucessfully");
			


		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String readTestResults(String property) {
		String value = null;
		

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("D:\\Users\\Shaheed\\parallel\\NGA2_CE_WF_DDF\\reports\\TestResults.txt");
			
			prop.load(input);
			value = (prop.getProperty(property));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return value;

	}

	public static String getnewDate(String reportDate) throws ParseException {

		String givenReportDate = reportDate;

		String gDate[] = givenReportDate.split(",");

		String gmonthAndDate[] = givenReportDate.split(" ");
		String newCreatedDate = (gmonthAndDate[1].replace(",", "") + "-" + gmonthAndDate[0] + "-" + gDate[1].trim());

		String string_newCreatedDate = newCreatedDate;

		SimpleDateFormat formatter7 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		java.util.Date date7 = formatter7.parse(string_newCreatedDate);
		String newCreatedDateIST = date7.toString();
		String newCreatedDateGMT = newCreatedDateIST.replace("IST", "GMT");

		// System.out.println(newCreatedDateGMT);
		return newCreatedDateGMT;

	}

	public static int overallTime(String sTime, String eTime) {

		long endTimeInMinutes = 0;
		long startTimeInMinutes = 0;

		String nsTime = sTime;
		String[] newSTime = nsTime.split(" ");

		String neTime = eTime;
		String[] newETime = neTime.split(" ");

		String startTime = newSTime[3];
		String[] split = startTime.split(":");
		if (split.length == 3) {
			startTimeInMinutes = TimeUnit.HOURS.toMinutes(Integer.parseInt(split[0])) + Integer.parseInt(split[1]);
			// System.out.println(startTimeInMinutes);
		}

		String endTime = newETime[3];
		String[] esplit = endTime.split(":");
		if (split.length == 3) {
			endTimeInMinutes = TimeUnit.HOURS.toMinutes(Integer.parseInt(esplit[0])) + Integer.parseInt(esplit[1]);
			// System.out.println(endTimeInMinutes);
		}

		int overallTime = (int) (endTimeInMinutes - startTimeInMinutes);
		System.out.println("Overall Execution Time= " + overallTime);

		return overallTime;

	}

    
}