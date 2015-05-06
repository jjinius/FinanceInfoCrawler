import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class StockBasicInfoCrawlerDAO {
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

    public int insertStockBasicInfo(Vector<StockBasicInfoCrawlerData> vo, String date) throws Exception {
        int result = 0;
        String sql = "";

        try {
            conn = connect();

            sql = "INSERT INTO SCI_ST_ANALYSIS VALUES(DATE_FORMAT(?,'%y-%m-%d'),?,?,?,?,?,?,?,NOW())";

            preStmt = conn.prepareStatement(sql);

            for(int i=0;i<vo.size();i++) {
                preStmt.setString(1, date);
                preStmt.setString (2, vo.get(i).getStockCd()) ;
                preStmt.setInt (3, vo.get(i).getCurrentPrice());
                preStmt.setFloat (4, vo.get(i).getChangeRate());
                preStmt.setInt (5, vo.get(i).getVolume());
                preStmt.setInt (6, vo.get(i).getStartPrice());
                preStmt.setInt (7, vo.get(i).getHighPrice());
                preStmt.setInt (8, vo.get(i).getLowPrice());

                result = preStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("[StockBasicInfoCrawlerDAO] 커넥션 또는 DB 등록 로직 에러 발생");
        } finally {
            close();
        }

        return result;
    }

    public int deleteStockBasicInfo(String date) throws Exception {
        int result = 0;
        String sql = "";

        try {
            conn = connect();

            sql = "delete from SCI_ST_ANALYSIS where yymmdd = DATE_FORMAT(?,'%y-%m-%d')";

            preStmt = conn.prepareStatement(sql);

            preStmt.setString(1, date);

            result = preStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("[StockBasicInfoCrawlerDAO] 커넥션 또는 DB 등록 로직 에러 발생");
        } finally {
            close();
        }

        return result;
    }

    public ArrayList getStockMasterInfo() throws Exception {
        int result = 0;
        String sql = "";
        ResultSet rs = null;
        ArrayList list = new ArrayList();

        try {
            conn = connect();

            sql = "select STOCK_CD from SCI_ST_MASTER where STOCK_CD > '172580' order by STOCK_CD";

            preStmt = conn.prepareStatement(sql);

            rs = preStmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("STOCK_CD"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("[StockBasicInfoCrawlerDAO] 커넥션 또는 DB 등록 로직 에러 발생");
        } finally {
            close();
        }

        return list;
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
