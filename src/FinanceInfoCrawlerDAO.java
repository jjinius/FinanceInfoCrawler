import java.sql.*;
import java.util.ArrayList;

public class FinanceInfoCrawlerDAO {
    // TODO: 프로퍼티파일로 중요 정보 가져오기
    private final String DB_URL = "jdbc:mysql://localhost:3306/stock";
    private final String DB_ID = "jjini";
    private final String DB_PW = "stockDev!23";

    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement preStmt = null;

    private Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);

        return conn;
    }

    public int insertFinanceInfo(ArrayList financeResultList) throws Exception {
        int result = 0;
        String sql = "";

        try {
            conn = connect();

            sql = "INSERT INTO stock.sci_st_finance (STOCK_CD, YYMM, sp, op, pubop, ptp, ni, nic, ninci, at, dt, " +
                    "ct, ctc, ctnc, cp, cfoo, cfoia, cfof, roop, roni, roe, roa, rodt, rocr, eps, per, bps, pbr, dps, rocd, nosh)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preStmt = conn.prepareStatement(sql);

            for (int i = 0; i < financeResultList.size(); i++) {
                FinanceInfoCrawlerData financeInfoCrawlerData = (FinanceInfoCrawlerData)financeResultList.get(i);
                preStmt.setString(1, financeInfoCrawlerData.getSTOCKCD());
                preStmt.setString(2, financeInfoCrawlerData.getSTOCKDT()) ;
                preStmt.setFloat(3, financeInfoCrawlerData.getSP());
                preStmt.setFloat(4, financeInfoCrawlerData.getOP());
                preStmt.setFloat(5, financeInfoCrawlerData.getPUBOP());
                preStmt.setFloat(6, financeInfoCrawlerData.getPTP());
                preStmt.setFloat(7, financeInfoCrawlerData.getNI());
                preStmt.setFloat(8, financeInfoCrawlerData.getNIC());
                preStmt.setFloat(9, financeInfoCrawlerData.getNINCI());
                preStmt.setFloat(10, financeInfoCrawlerData.getAT());
                preStmt.setFloat(11, financeInfoCrawlerData.getDT());
                preStmt.setFloat(12, financeInfoCrawlerData.getCT());
                preStmt.setFloat(13, financeInfoCrawlerData.getCTC());
                preStmt.setFloat(14, financeInfoCrawlerData.getCTNC());
                preStmt.setFloat(15, financeInfoCrawlerData.getCP());
                preStmt.setFloat(16, financeInfoCrawlerData.getCFOO());
                preStmt.setFloat(17, financeInfoCrawlerData.getCFOIA());
                preStmt.setFloat(18, financeInfoCrawlerData.getCFOF());
                preStmt.setFloat(19, financeInfoCrawlerData.getROOP());
                preStmt.setFloat(20, financeInfoCrawlerData.getRONI());
                preStmt.setFloat(21, financeInfoCrawlerData.getROE());
                preStmt.setFloat(22, financeInfoCrawlerData.getROA());
                preStmt.setFloat(23, financeInfoCrawlerData.getRODT());
                preStmt.setFloat(24, financeInfoCrawlerData.getROCR());
                preStmt.setFloat(25, financeInfoCrawlerData.getEPS());
                preStmt.setFloat(26, financeInfoCrawlerData.getPER());
                preStmt.setFloat(27, financeInfoCrawlerData.getBPS());
                preStmt.setFloat(28, financeInfoCrawlerData.getPBR());
                preStmt.setFloat(29, financeInfoCrawlerData.getDPS());
                preStmt.setFloat(30, financeInfoCrawlerData.getROCD());
                preStmt.setFloat(31, financeInfoCrawlerData.getNOSH());

                result = preStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("[FinanceInfoCrawlerDAO] 커넥션 또는 DB 등록 로직 에러 발생");
        } finally {
            close();
        }

        return result;
    }

    private void close() throws SQLException {
        if (preStmt != null) {
            preStmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
