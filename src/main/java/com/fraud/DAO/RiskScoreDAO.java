package com.fraud.DAO;

public class RiskScoreDAO {
    private String enterpriseTransactionId;
    private int riskScore;
    private String riskBand;

    public RiskScoreDAO() {
    }

    public RiskScoreDAO(String enterpriseTransactionId, int riskScore, String riskBand) {
        this.enterpriseTransactionId = enterpriseTransactionId;
        this.riskScore = riskScore;
        this.riskBand = riskBand;
    }

    public String getEnterpriseTransactionId() {
        return enterpriseTransactionId;
    }

    public void setEnterpriseTransactionId(String enterpriseTransactionId) {
        this.enterpriseTransactionId = enterpriseTransactionId;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskBand() {
        return riskBand;
    }

    public void setRiskBand(String riskBand) {
        this.riskBand = riskBand;
    }
}
