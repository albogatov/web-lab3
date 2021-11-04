package data;


public class Result {
    private Double x;
    private Double y;
    private Double r;
    private String currTime;
    private Double executionTime;
    private Boolean hit;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getCurrTime() {
        return currTime;
    }

    public void setCurrTime(String currTime) {
        this.currTime = currTime;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public boolean getHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public String toString() {
        return "Result{" +
                "xValue=" + x +
                ", yValue=" + y +
                ", rValue=" + r +
                ", currTime=" + currTime +
                ", executionTime=" + executionTime +
                ", hitResult=" + hit +
                '}';
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() +
                r.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Result) {
            Result resultObj = (Result) obj;
            return x.equals(resultObj.getX()) &&
                    y.equals(resultObj.getY()) &&
                    r.equals(resultObj.getR()) &&
                    currTime.equals(resultObj.getCurrTime()) &&
                    hit.equals(resultObj.getHit());
        }
        return false;
    }

//    public Result(double x, double y, double r, String currTime, double executionTime, boolean hit) {
//        this.x = x;
//        this.y = y;
//        this.r = r;
//        this.currTime = currTime;
//        this.executionTime = executionTime;
//        this.hit = hit;
//    }
}
