package com.xdbigdata.user_manage_admin.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "v_jw_xscj")
public class StudentScore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    @Column(name = "xh")
    private String xh;
    /**
     * 姓名
     */
    @Column(name = "xm")
    private String xm;
    /**
     * 课程号
     */
    @Column(name = "kch")
    private String kch;
    /**
     * 课程名称
     */
    @Column(name = "kcmc")
    private String kcmc;
    /**
     * 学年
     */
    @Column(name = "xn")
    private String xn;
    @Column(name = "xqm")
    private String xqm;
    /**
     * 课程成绩
     */
    @Column(name = "kccj")
    private String kccj;
    /**
     * 学分
     */
    @Column(name = "xf")
    private String xf;
    /**
     * 成绩类型
     */
    @Column(name = "cjlx")
    private String cjlx;
    /**
     * 成绩类型名称
     */
    @Column(name = "cjlxmc")
    private String cjlxmc;
    /**
     * 课程类型
     */
    @Column(name = "kclx")
    private String kclx;
    /**
     * 绩点
     */
    @Column(name = "jd")
    private String jd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getKch() {
        return kch;
    }

    public void setKch(String kch) {
        this.kch = kch;
    }

    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }

    public String getXn() {
        return xn;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXqm() {
        return xqm;
    }

    public void setXqm(String xqm) {
        this.xqm = xqm;
    }

    public String getKccj() {
        return kccj;
    }

    public void setKccj(String kccj) {
        this.kccj = kccj;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }

    public String getCjlx() {
        return cjlx;
    }

    public void setCjlx(String cjlx) {
        this.cjlx = cjlx;
    }

    public String getCjlxmc() {
        return cjlxmc;
    }

    public void setCjlxmc(String cjlxmc) {
        this.cjlxmc = cjlxmc;
    }

    public String getKclx() {
        return kclx;
    }

    public void setKclx(String kclx) {
        this.kclx = kclx;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }
}
