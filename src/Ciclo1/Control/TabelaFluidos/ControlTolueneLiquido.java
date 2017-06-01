/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelTolueneLiquido;
import Ciclo1.Model.TabelasFluidos.ModelTolueneLiquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

/**
 *
 * @author alysonmp
 */
public class ControlTolueneLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlTolueneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaTolueneLiquido(){
        String csvFile = "src/Csv/TOLUENE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelTolueneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Toluene_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelTolueneLiquido(Double.parseDouble(Toluene_l[0]), Double.parseDouble(Toluene_l[1]), Double.parseDouble(Toluene_l[2]), Double.parseDouble(Toluene_l[3]), Double.parseDouble(Toluene_l[4]), Double.parseDouble(Toluene_l[5]),Double.parseDouble(Toluene_l[6])));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void interpolacao(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelTolueneLiquido.class);
        //cr = this.session.createCriteria(ModelTolueneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from toluene where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelTolueneLiquido> Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene1 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from toluene where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene2 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from toluene where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene3 = Toluenes.get(0);
        
        consulta = this.session.createSQLQuery("select * from toluene where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelTolueneLiquido.class));//Sem isso aqui impossível de retornar
	Toluenes = consulta.list(); 
        ModelTolueneLiquido toluene4 = Toluenes.get(0);
        
        double p  = ((pressao - toluene1.getPRESSAO())/(toluene3.getPRESSAO() - toluene1.getPRESSAO()));
        double t1 = ((temperatura - toluene1.getTEMPERATURA())/(toluene2.getTEMPERATURA() - toluene1.getTEMPERATURA()));
        double t2 = ((temperatura - toluene3.getTEMPERATURA())/(toluene4.getTEMPERATURA() - toluene3.getTEMPERATURA()));
        
        Cpl1 = toluene1.getCPL()+ (toluene2.getCPL()- toluene1.getCPL()) * t1;
        Cpl2 = toluene3.getCPL()+ (toluene4.getCPL()- toluene3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = toluene1.getPRL()+ (toluene2.getPRL()- toluene1.getPRL()) * t1;
        Prl2 = toluene3.getPRL()+ (toluene4.getPRL()- toluene3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = toluene1.getKL() + (toluene2.getKL() - toluene1.getKL()) * t1;
        kl2 = toluene3.getKL() + (toluene4.getKL() - toluene3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = toluene1.getMUL() + (toluene2.getMUL() - toluene1.getMUL()) * t1;
        Mul2 = toluene3.getMUL() + (toluene4.getMUL() - toluene3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = toluene1.getVCL() + (toluene2.getVCL() - toluene1.getVCL()) * t1;
        Vcl2 = toluene3.getVCL() + (toluene4.getVCL() - toluene3.getVCL()) * t2;
        Vcl = Vcl1 + (Vcl2 - Vcl1) * p;
    }

    public double getCpl() {
        return Cpl;
    }

    public void setCpl(double Cpl) {
        this.Cpl = Cpl;
    }

    public double getPrl() {
        return Prl;
    }

    public void setPrl(double Prl) {
        this.Prl = Prl;
    }

    public double getKl() {
        return kl;
    }

    public void setKl(double kl) {
        this.kl = kl;
    }

    public double getMul() {
        return Mul;
    }

    public void setMul(double Mul) {
        this.Mul = Mul;
    }

    public double getVcl() {
        return Vcl;
    }

    public void setVcl(double Vcl) {
        this.Vcl = Vcl;
    }
    
}
