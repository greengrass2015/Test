import com.github.cyl.autonews.dao.analysis.AnalysisDao;
import com.github.cyl.autonews.dao.cpi.CPIDao;
import com.github.cyl.autonews.dao.quotation.QuotationDao;
import com.github.cyl.autonews.pojo.analysis.Article;
import com.github.cyl.autonews.pojo.cpi.MonthCPI;
//import com.yicai.autonews.cvsfile.CSVFileUtil;
import com.yicai.autonews.cvsfile.CsvFileExport;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class Test {

	public static void main(String[] args) {
		AnalysisDao analysisDao = new AnalysisDao();
		Article article = analysisDao.getOneAnalysisArticle(2015, 5);
		System.out.println(article);
		
		CPIDao cpiDao = new CPIDao();
		MonthCPI monthCPI = cpiDao.getOneMonthCPI(2015, 6);
		//List<MonthCPI>
       QuotationDao quotationDao = new QuotationDao();
		List<String> list = quotationDao.getOneQuotations(2015, 8);
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
		CsvFileExport quotationExport = new CsvFileExport() ;
		String outpath = "quotations.csv";
		//quotationExport.exportQuotationsToCsv(list, outpath);
	


	}

}
