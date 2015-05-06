/**
 * Created with IntelliJ IDEA.
 * User: jjinius
 * Date: 14. 4. 14
 * Time: 오후 11:34
 * To change this template use File | Settings | File Templates.
 */
public class StockBasicInfoCrawlerData {

    private String stockCd;
    private int currentPrice;
    private float changeRate;
    private int	volume;
    private int startPrice;
    private int highPrice;
    private int lowPrice;

    public String getStockCd() {
        return stockCd;
    }

    public void setStockCd(String stockCd) {
        this.stockCd = stockCd;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(float changeRate) {
        this.changeRate = changeRate;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(int highPrice) {
        this.highPrice = highPrice;
    }

    public int getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(int lowPrice) {
        this.lowPrice = lowPrice;
    }
}
