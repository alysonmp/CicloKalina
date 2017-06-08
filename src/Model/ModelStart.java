/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author alysonmp
 */

@Entity
@Table(name = "respostas")
public class ModelStart implements Serializable{
    
    @Id
    @GeneratedValue
    private int cod;
    
    @Column
    private double T1;
    
    @Column
    private double T2;
    
    @Column
    private double T3;
    
    @Column
    private double T4;
    
    @Column
    private double T5;
    
    @Column
    private double T6;
    
    @Column
    private double P1;
    
    @Column
    private double P2;
    
    @Column
    private double P3;
    
    @Column
    private double P4;
    
    @Column
    private double P5;
    
    @Column
    private double P6;
    
    @Column
    private double S1;
    
    @Column
    private double S2;
    
    @Column
    private double S3;
    
    @Column
    private double S4;
    
    @Column
    private double S5;
    
    @Column
    private double S6;
    
    @Column
    private double H1;
    
    @Column
    private double H2;
    
    @Column
    private double H3;
    
    @Column
    private double H4;
    
    @Column
    private double H5;
    
    @Column
    private double H6;
    
    @Column
    private double M;
    
    @Column
    private double Beff;

    @Column
    private double QCon;
    
    @Column
    private double eff;
    
    public ModelStart() {
    }

    public ModelStart(int cod, double T1, double T2, double T3, double T4, double T5, double T6, double P1, double P2, double P3, double P4, double P5, double P6, double S1, double S2, double S3, double S4, double S5, double S6, double H1, double H2, double H3, double H4, double H5, double H6, double M, double Beff, double QCon, double eff) {
        this.cod = cod;
        this.T1 = T1;
        this.T2 = T2;
        this.T3 = T3;
        this.T4 = T4;
        this.T5 = T5;
        this.T6 = T6;
        this.P1 = P1;
        this.P2 = P2;
        this.P3 = P3;
        this.P4 = P4;
        this.P5 = P5;
        this.P6 = P6;
        this.S1 = S1;
        this.S2 = S2;
        this.S3 = S3;
        this.S4 = S4;
        this.S5 = S5;
        this.S6 = S6;
        this.H1 = H1;
        this.H2 = H2;
        this.H3 = H3;
        this.H4 = H4;
        this.H5 = H5;
        this.H6 = H6;
        this.M = M;
        this.Beff = Beff;
        this.QCon = QCon;
        this.eff = eff;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getT1() {
        return T1;
    }

    public void setT1(double T1) {
        this.T1 = T1;
    }

    public double getT2() {
        return T2;
    }

    public void setT2(double T2) {
        this.T2 = T2;
    }

    public double getT3() {
        return T3;
    }

    public void setT3(double T3) {
        this.T3 = T3;
    }

    public double getT4() {
        return T4;
    }

    public void setT4(double T4) {
        this.T4 = T4;
    }

    public double getT5() {
        return T5;
    }

    public void setT5(double T5) {
        this.T5 = T5;
    }

    public double getT6() {
        return T6;
    }

    public void setT6(double T6) {
        this.T6 = T6;
    }

    public double getP1() {
        return P1;
    }

    public void setP1(double P1) {
        this.P1 = P1;
    }

    public double getP2() {
        return P2;
    }

    public void setP2(double P2) {
        this.P2 = P2;
    }

    public double getP3() {
        return P3;
    }

    public void setP3(double P3) {
        this.P3 = P3;
    }

    public double getP4() {
        return P4;
    }

    public void setP4(double P4) {
        this.P4 = P4;
    }

    public double getP5() {
        return P5;
    }

    public void setP5(double P5) {
        this.P5 = P5;
    }

    public double getP6() {
        return P6;
    }

    public void setP6(double P6) {
        this.P6 = P6;
    }

    public double getS1() {
        return S1;
    }

    public void setS1(double S1) {
        this.S1 = S1;
    }

    public double getS2() {
        return S2;
    }

    public void setS2(double S2) {
        this.S2 = S2;
    }

    public double getS3() {
        return S3;
    }

    public void setS3(double S3) {
        this.S3 = S3;
    }

    public double getS4() {
        return S4;
    }

    public void setS4(double S4) {
        this.S4 = S4;
    }

    public double getS5() {
        return S5;
    }

    public void setS5(double S5) {
        this.S5 = S5;
    }

    public double getS6() {
        return S6;
    }

    public void setS6(double S6) {
        this.S6 = S6;
    }

    public double getH1() {
        return H1;
    }

    public void setH1(double H1) {
        this.H1 = H1;
    }

    public double getH2() {
        return H2;
    }

    public void setH2(double H2) {
        this.H2 = H2;
    }

    public double getH3() {
        return H3;
    }

    public void setH3(double H3) {
        this.H3 = H3;
    }

    public double getH4() {
        return H4;
    }

    public void setH4(double H4) {
        this.H4 = H4;
    }

    public double getH5() {
        return H5;
    }

    public void setH5(double H5) {
        this.H5 = H5;
    }

    public double getH6() {
        return H6;
    }

    public void setH6(double H6) {
        this.H6 = H6;
    }

    public double getM() {
        return M;
    }

    public void setM(double M) {
        this.M = M;
    }

    public double getBeff() {
        return Beff;
    }

    public void setBeff(double Beff) {
        this.Beff = Beff;
    }

    public double getQCon() {
        return QCon;
    }

    public void setQCon(double QCon) {
        this.QCon = QCon;
    }

    public double getEff() {
        return eff;
    }

    public void setEff(double eff) {
        this.eff = eff;
    }
}
