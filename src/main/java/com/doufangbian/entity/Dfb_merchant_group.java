package com.doufangbian.entity;

/**
 * 小分组
 *
 * @author C_C
 */
public class Dfb_merchant_group {
    private int id;//小组id
    private String name;//小组名称
    private int countyid;   //县Id
    private int gorder;    //顺序
    private int catId; // 大类的id

    private String updatetime;//更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountyid() {
        return countyid;
    }

    public void setCountyid(int countyid) {
        this.countyid = countyid;
    }

    public int getGorder() {
        return gorder;
    }

    public void setGorder(int gorder) {
        this.gorder = gorder;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
}
