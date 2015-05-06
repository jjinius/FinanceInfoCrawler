import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class StockAnalysis {

	static HttpResponse response = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	conn = connect();

		String address = "http://www.krx.co.kr/por_kor/common/COM00030.jsp?url=/common/COM00040.jsp&bld=/m1/m1_1/m1_1_5/hpkor01001_05_excel";
		String marketIdxCdA = "1001";
		String marketIdxCdB = "2001";

		String date = "20140606";



        for (int i = 0; i < 5; i++ ) {
            for (int j=0; j <31; j++)    {
                int intDate = Integer.valueOf(date)+1;
                date = String.valueOf(intDate);
                String url = "";

                try {

                    StockBasicInfoCrawlerDAO stockBasicInfoCrawlerDAO = new StockBasicInfoCrawlerDAO();
                    stockBasicInfoCrawlerDAO.deleteStockBasicInfo(date);

                    //step1.
                    url = address + "&indx_ind_cd=" + marketIdxCdB + "&work_dt=" + date;
                    stockBasicInfoCrawlerDAO.insertStockBasicInfo(getDataList(getHttpResponse(url)),date);

                    System.out.println("-------------Step1 Complete.");

                    //step2.
                    url = address + "&indx_ind_cd=" + marketIdxCdA + "&work_dt=" + date;
                    stockBasicInfoCrawlerDAO.insertStockBasicInfo(getDataList(getHttpResponse(url)),date);

                    System.out.println("-------------Step2 Complete.");

                    System.out.println("-------------Data Insert Complete : " + date + "-------------");

                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            date = String.valueOf(Integer.parseInt(date)+100).substring(0,6)+"00";
            System.out.println("움하하하"+date);
        }
		

	}

	public static HttpResponse getHttpResponse(String url) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(200000000)
				.setConnectTimeout(100000000)
				.setConnectionRequestTimeout(100000000)
				.build();
		
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(requestConfig);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Firefox/3.6.13");

		HttpClientContext context = HttpClientContext.create();
		
		context.setAttribute("http.protocol.version", HttpVersion.HTTP_1_1);
		
		response = httpClient.execute(httpget);
		
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new IllegalStateException("Method failed: " + response.getStatusLine());
		}
		
		return response;
	}
	
	public static Vector<StockBasicInfoCrawlerData> getDataList(HttpResponse response) throws IllegalStateException, IOException {
		
		Vector<StockBasicInfoCrawlerData> vo = new Vector<StockBasicInfoCrawlerData>();
		
		HSSFWorkbook workbook = new HSSFWorkbook((InputStream)response.getEntity().getContent());
		
		if (workbook.getNumberOfSheets() < 1) {
			throw new IllegalStateException("Method failed : No data");
		}
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
        	StockBasicInfoCrawlerData sv = new StockBasicInfoCrawlerData();
        	sv.setStockCd(sheet.getRow(i).getCell(0).toString());
        	sv.setCurrentPrice(Integer.parseInt(nullToZero(sheet.getRow(i).getCell(2).toString().replace(",",""))));
        	sv.setChangeRate(Float.parseFloat(nullToZero(sheet.getRow(i).getCell(4).toString().replace(",",""))));
        	sv.setVolume(Integer.parseInt(nullToZero(sheet.getRow(i).getCell(7).toString().replace(",",""))));
        	sv.setStartPrice(Integer.parseInt(nullToZero(sheet.getRow(i).getCell(9).toString().replace(",",""))));
        	sv.setHighPrice(Integer.parseInt(nullToZero(sheet.getRow(i).getCell(10).toString().replace(",",""))));
        	sv.setLowPrice(Integer.parseInt(nullToZero(sheet.getRow(i).getCell(11).toString().replace(",",""))));
        	vo.add(sv);
        }
		return vo;
	}
	
	public static String nullToZero(String value) {
		if(value == null || value.equals("")) {
			value = "0";
		}
		return value;
	}
        


}

