import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinanceInfoCrawlerData {
    private String STOCKCD;
    private String STOCKDT;
    private float SP;
    private float OP;
    private float PUBOP;
    private float PTP;
    private float NI;
    private float NIC;
    private float NINCI;
    private float AT;
    private float DT;
    private float CT;
    private float CTC;
    private float CTNC;
    private float CP;
    private float CFOO;
    private float CFOIA;
    private float CFOF;
    private float ROOP;
    private float RONI;
    private float ROE;
    private float ROA;
    private float RODT;
    private float ROCR;
    private float EPS;
    private float PER;
    private float BPS;
    private float PBR;
    private float DPS;
    private float ROCD;
    private float NOSH;

    private static final HashMap<String, String> nameToVariableMap  = new HashMap() {
        {
            put("주식코드", "STOCKCD");
            put("분기정보", "STOCKDT");
            put("매출액", "SP");
            put("영업이익", "OP");
            put("영업이익(발표기준)", "PUBOP");
            put("세전계속사업이익", "PTP");
            put("당기순이익", "NI");
            put("(지배)당기순이익", "NIC");
            put("(비지배)당기순이익", "NINCI");
            put("자산총계", "AT");
            put("부채총계", "DT");
            put("자본총계", "CT");
            put("(지배)자본총계", "CTC");
            put("(비지배)자본총계", "CTNC");
            put("자본금", "CP");
            put("영업활동현금흐름", "CFOO");
            put("투자활동현금흐름", "CFOIA");
            put("재무활동현금흐름", "CFOF");
            put("영업이익률", "ROOP");
            put("순이익률", "RONI");
            put("ROE(%)", "ROE");
            put("ROA(%)", "ROA");
            put("부채비율", "RODT");
            put("자본유보율", "ROCR");
            put("유보율", "ROCR");
            put("EPS(원)", "EPS");
            put("PER(배)", "PER");
            put("BPS(원)", "BPS");
            put("PBR(배)", "PBR");
            put("수정DPS(원)", "DPS");
            put("현금배당률", "ROCD");
            put("발행주식수(보통주)", "NOSH");
        }
    };

    public static FinanceInfoCrawlerData createInstance(HashMap<String, String> map) throws Exception {
        FinanceInfoCrawlerData f = new FinanceInfoCrawlerData();
        Class c = f.getClass();

        try {
            for(Map.Entry<String, String> entry : map.entrySet()){
                String key = nameToVariableMap.get(entry.getKey());
                String sValue = entry.getValue();

                if (key == null) {
                    throw new Exception("존재하지 않는 키: " + entry.getKey());
                }

                Method method = null;

                if (key == "STOCKCD" || key == "STOCKDT") {
                    method = c.getMethod("set" + key, String.class);
                    method.invoke(f, sValue);

                } else {
                    float fValue;
                    if (sValue.equals("") || sValue == null) {
                        sValue = "0";
                    }
                    fValue = Float.parseFloat(sValue);
                    method = c.getMethod("set" + key, float.class);
                    method.invoke(f, fValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("데이터 매핑 중 오류 발생");
        }

        return f;
    }

    public String getSTOCKCD() {
        return STOCKCD;
    }

    public void setSTOCKCD(String STOCKCD) {
        this.STOCKCD = STOCKCD;
    }

    public String getSTOCKDT() {
        return STOCKDT;
    }

    public void setSTOCKDT(String STOCKDT) {
        this.STOCKDT = STOCKDT;
    }

    public float getSP() {
        return SP;
    }

    public void setSP(float SP) {
        this.SP = SP;
    }

    public float getOP() {
        return OP;
    }

    public void setOP(float OP) {
        this.OP = OP;
    }

    public float getPUBOP() {
        return PUBOP;
    }

    public void setPUBOP(float PUBOP) {
        this.PUBOP = PUBOP;
    }

    public float getPTP() {
        return PTP;
    }

    public void setPTP(float PTP) {
        this.PTP = PTP;
    }

    public float getNI() {
        return NI;
    }

    public void setNI(float NI) {
        this.NI = NI;
    }

    public float getNIC() {
        return NIC;
    }

    public void setNIC(float NIC) {
        this.NIC = NIC;
    }

    public float getNINCI() {
        return NINCI;
    }

    public void setNINCI(float NINCI) {
        this.NINCI = NINCI;
    }

    public float getAT() {
        return AT;
    }

    public void setAT(float AT) {
        this.AT = AT;
    }

    public float getDT() {
        return DT;
    }

    public void setDT(float DT) {
        this.DT = DT;
    }

    public float getCT() {
        return CT;
    }

    public void setCT(float CT) {
        this.CT = CT;
    }

    public float getCTC() {
        return CTC;
    }

    public void setCTC(float CTC) {
        this.CTC = CTC;
    }

    public float getCTNC() {
        return CTNC;
    }

    public void setCTNC(float CTNC) {
        this.CTNC = CTNC;
    }

    public float getCP() {
        return CP;
    }

    public void setCP(float CP) {
        this.CP = CP;
    }

    public float getCFOO() {
        return CFOO;
    }

    public void setCFOO(float CFOO) {
        this.CFOO = CFOO;
    }

    public float getCFOIA() {
        return CFOIA;
    }

    public void setCFOIA(float CFOIA) {
        this.CFOIA = CFOIA;
    }

    public float getCFOF() {
        return CFOF;
    }

    public void setCFOF(float CFOF) {
        this.CFOF = CFOF;
    }

    public float getROOP() {
        return ROOP;
    }

    public void setROOP(float ROOP) {
        this.ROOP = ROOP;
    }

    public float getRONI() {
        return RONI;
    }

    public void setRONI(float RONI) {
        this.RONI = RONI;
    }

    public float getROE() {
        return ROE;
    }

    public void setROE(float ROE) {
        this.ROE = ROE;
    }

    public float getROA() {
        return ROA;
    }

    public void setROA(float ROA) {
        this.ROA = ROA;
    }

    public float getRODT() {
        return RODT;
    }

    public void setRODT(float RODT) {
        this.RODT = RODT;
    }

    public float getROCR() {
        return ROCR;
    }

    public void setROCR(float ROCR) {
        this.ROCR = ROCR;
    }

    public float getEPS() {
        return EPS;
    }

    public void setEPS(float EPS) {
        this.EPS = EPS;
    }

    public float getPER() {
        return PER;
    }

    public void setPER(float PER) {
        this.PER = PER;
    }

    public float getBPS() {
        return BPS;
    }

    public void setBPS(float BPS) {
        this.BPS = BPS;
    }

    public float getPBR() {
        return PBR;
    }

    public void setPBR(float PBR) {
        this.PBR = PBR;
    }

    public float getDPS() {
        return DPS;
    }

    public void setDPS(float DPS) {
        this.DPS = DPS;
    }

    public float getROCD() {
        return ROCD;
    }

    public void setROCD(float ROCD) {
        this.ROCD = ROCD;
    }

    public float getNOSH() {
        return NOSH;
    }

    public void setNOSH(float NOSH) {
        this.NOSH = NOSH;
    }
}