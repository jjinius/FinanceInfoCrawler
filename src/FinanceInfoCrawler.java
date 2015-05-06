import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dart에서 공시 목록과 본문을 가져온다.
 */
public class FinanceInfoCrawler {
    // 페이지를 반복하는 대신 한 페이지에서 가져올 수 있는 최대치를 요청한다.
    private static final String FINANCE_INFO_URL = "http://companyinfo.stock.naver.com/v1/company/cF1001.aspx?finGubun=MAIN&cmp_cd=";

    /**
     * HTTP GET 요청을 보낸다.
     *
     * @param targetURL
     * @return 응답 바디
     */
    private String requestGet(String targetURL) {
        URL url;
        HttpURLConnection connection = null;

        try {
            // Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public ArrayList<FinanceInfoCrawlerData> requestFinanceContent(String stockCd) throws Exception {
        String financeContent = requestGet(FINANCE_INFO_URL+stockCd);

        JSONArray financeDateObj = makeJSONArrayObj(financeContent, "var changeFin = ([\\s\\S]+?);");
        JSONArray financeInfoObj = makeJSONArrayObj(financeContent, "var changeFinData = ([\\s\\S]+?);");

        JSONArray financeQuarterDataInfo = (JSONArray) financeDateObj.get(1);
        //재무재표의 연도 정보와 분기정보 중 분기 정보만 추출하기 위해 두번째 정보를 가져온다.

        ArrayList<FinanceInfoCrawlerData> financeResultList = new ArrayList<FinanceInfoCrawlerData>();

        for (int i = 0; i < financeQuarterDataInfo.size(); i++) { //각 분기별 재무정보 전체 데이터 추출
            HashMap<String, String> financeResultInfo = new HashMap<String, String>();
            financeResultInfo.put("주식코드",stockCd);
            if((String) financeQuarterDataInfo.get(i) != null && !financeQuarterDataInfo.get(i).equals("")) {
                financeResultInfo.put("분기정보",((String) financeQuarterDataInfo.get(i)).substring(2, 7).replace("/", "-"));
                for (int j = 0; j < financeInfoObj.size(); j++) {
                    //재무재표 각 모듈 별로 분해 - 매출 / 자산 / 영업활동 / 영업 이익
                    JSONArray financeInfoOneModule = (JSONArray) financeInfoObj.get(j);

                    for (int m = 0; m < financeInfoOneModule.size(); m = m+2) {
                        JSONArray financeInfoModule = (JSONArray) financeInfoOneModule.get(m);
                        for (int n = 0; n < financeInfoModule.size(); n++) {
                            //모듈에서 상세 정보 분류 - 매출 모듈에서 영업이익/당기순이익 정보 등 상세 정보 추출
                            JSONArray financeOneInfo = (JSONArray) financeInfoModule.get(n);
                            String temp = (String)financeOneInfo.get(0);

                            for(int l = m+1; l < financeInfoOneModule.size(); l++) {
                                JSONArray financeInfoQuarterModule = (JSONArray) financeInfoOneModule.get(l);
                                //for (int o = 0; o < financeInfoQuarterModule.size(); o++) {
                                    JSONArray financeOneQuarterInfo = (JSONArray) financeInfoQuarterModule.get(n);
                                    financeResultInfo.put(temp, ((String) financeOneQuarterInfo.get(i)).replace(",", ""));
                               // }
                            }
                        }
                    }
                }

                FinanceInfoCrawlerData financeInfoCrawlerData = new FinanceInfoCrawlerData().createInstance(financeResultInfo);
                financeResultList.add(financeInfoCrawlerData);
            }
        }


        return financeResultList;

    }

    public JSONArray makeJSONArrayObj(String content, String str) throws Exception {
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(content);

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;

        while (matcher.find()) {
            jsonArray = (JSONArray) parser.parse(matcher.group(1));
        }

        if (jsonArray == null) {
           throw new Exception("서버 응답에서 재무정보 값을 파싱하지 못함 - 응답패턴 재검토 필요");
        }

        return jsonArray;
    }

    public void crawlFinanceInfo(String stockCd) throws Exception {
        ArrayList<FinanceInfoCrawlerData> financeResultList = requestFinanceContent(stockCd);
        FinanceInfoCrawlerDAO financeInfoCrawlerDAO = new FinanceInfoCrawlerDAO();
        financeInfoCrawlerDAO.insertFinanceInfo(financeResultList);
    }
}
