package com.atguigu.jdbc;

public class Fruit01 {
    private int fid;
    private String fname;
    private int fcount;
    private int price;
    private String reamrk;

    public Fruit01(int fid , String fname, int price, int fcount, String reamrk) {
        this.fid = fid;
        this.fname = fname;
        this.fcount = fcount;
        this.price = price;
        this.reamrk = reamrk;
    }

    public String getFname() {
        return fname;
    }

    public int getFcount() {
        return fcount;
    }

    public int getPrice() {
        return price;
    }

    public String getReamrk() {
        return reamrk;
    }

    public String toString() {
        return "Fruit{" +
                "fid=" + fid +
                ", fname='" + fname + '\'' +
                ", price=" + price +
                ", fcount=" + fcount +
                ", remark='" + reamrk + '\'' +
                '}';
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setReamrk(String reamrk) {
        this.reamrk = reamrk;
    }
}
