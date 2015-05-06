import java.util.ArrayList;

/**
 * FinanceInfoCrawler 예제
 */
public class FinanceInfoCrawlerExample {
    public static void main(String[] args) throws Exception {

        // TODO: 교석오빠 STOCKCD를 긁어와서 FOR문으로 돌린다.
        // TODO: 네이버에서 주는 정보가 바뀔 경우 알럿을 줄수 있는 로직 추가가 필요하다.

        //룰룰
        StockBasicInfoCrawlerDAO dao = new StockBasicInfoCrawlerDAO();
        ArrayList list = dao.getStockMasterInfo();

        String[] temStr = {"027740", "097950", "079940", "151910", "090120", "066410", "065130", "023160", "900090", "009150"};
        FinanceInfoCrawler crawler = new FinanceInfoCrawler();

        for(int i = 0; i < list.size(); i++) {
            String stockCd = list.get(i).toString();
//            String stockCd ="007330";
            System.out.println("stockCd==>"+stockCd);
            crawler.crawlFinanceInfo(stockCd);
            Thread.sleep(10000L);
       }

    }
}

