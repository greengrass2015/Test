package com.yicai.autonews.cvsfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.FileWriterWithEncoding;

import com.github.cyl.autonews.dao.quotation.QuotationDao;
import com.yicai.autonews.staticclass.MyDate;


public class QuotationCsvFileExport {

	public void exportQuotationsToCsv(List<String> oneYearQuotation, String outpath){
		//取数据
		//List<String> strList = quotationDao.getOneQuotations(2015, 8);
		//数据输出
		File file = new File(outpath);
		CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator('\n'); // 每条记录间隔符
		if (file.exists()) {
			file.delete();
		}
		FileWriterWithEncoding writer = null;
		CSVPrinter printer = null;
		try {
			writer = new FileWriterWithEncoding(outpath, "gbk", true);
			printer = new CSVPrinter(writer, format);
		} catch (Exception e) {
			throw new RuntimeException("Csv File output preparing fails", e);
		}
		
		appendRecordsListToCsv(printer, oneYearQuotation);


		try {
			printer.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void appendRecordsListToCsv(CSVPrinter printer,
			List<String> recordsList) {
		for (int i = 0; i < recordsList.size(); i++){
			String record = recordsList.get(i);
			try {
				printer.printRecord(record);
				System.out.println(record);
				} catch (IOException e) {
					throw new RuntimeException("Writing to csv file fails", e);
				}
		}
	}
	

}
