package MiningOverview;

public class Miner {

    Double currentHashRate;
    Double averageHashrate;
    Integer activeWorkers;
    Long unpaid;
    Long validShares;
    Long invalidShares;
    Long staleShares;
    String Date;


    public Double getCurrentHashRate() {
        return currentHashRate;
    }

    public void setCurrentHashRate(Double currentHashRate) {
        this.currentHashRate = currentHashRate;
    }

    public Double getAverageHashrate() {
        return averageHashrate;
    }

    public void setAverageHashrate(Double averageHashrate) {
        this.averageHashrate = averageHashrate;
    }

    public Integer getActiveWorkers() {
        return activeWorkers;
    }

    public void setActiveWorkers(Integer activeWorkers) {
        this.activeWorkers = activeWorkers;
    }

    public Long getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Long unpaid) {
        this.unpaid = unpaid;
    }

    public Long getValidShares() {
        return validShares;
    }

    public void setValidShares(Long validShares) {
        this.validShares = validShares;
    }

    public Long getInvalidShares() {
        return invalidShares;
    }

    public void setInvalidShares(Long invalidShares) {
        this.invalidShares = invalidShares;
    }

    public Long getStaleShares() {
        return staleShares;
    }

    public void setStaleShares(Long staleShares) {
        this.staleShares = staleShares;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}