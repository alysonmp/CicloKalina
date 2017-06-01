/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelWaterLiquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author alysonmp
 */
public class ControlWaterLiquido {
    
    Session session;
    private double Cpl, Prl, Vcl, Mul, kl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlWaterLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaWaterLiquido(){
        String csvFile = "src/Csv/WATER_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelWaterLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] water_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelWaterLiquido(Double.parseDouble(water_l[0]), Double.parseDouble(water_l[1]), Double.parseDouble(water_l[2]), Double.parseDouble(water_l[3]), Double.parseDouble(water_l[4]), Double.parseDouble(water_l[5]),Double.parseDouble(water_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelWaterLiquido.class);
        //cr = this.session.createCriteria(ModelWaterLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from water_liquido where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelWaterLiquido> waters = consulta.list(); 
        ModelWaterLiquido water1 = waters.get(0);
        
        consulta = this.session.createSQLQuery("select * from water_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterLiquido.class));//Sem isso aqui impossível de retornar
	waters = consulta.list(); 
        ModelWaterLiquido water2 = waters.get(0);
        
        consulta = this.session.createSQLQuery("select * from water_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterLiquido.class));//Sem isso aqui impossível de retornar
	waters = consulta.list(); 
        ModelWaterLiquido water3 = waters.get(0);
        
        consulta = this.session.createSQLQuery("select * from water_liquido where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelWaterLiquido.class));//Sem isso aqui impossível de retornar
	waters = consulta.list(); 
        ModelWaterLiquido water4 = waters.get(0);
        
        double p  = ((pressao - water1.getPRESSAO())/(water3.getPRESSAO() - water1.getPRESSAO()));
        double t1 = ((temperatura - water1.getTEMPERATURA())/(water2.getTEMPERATURA() - water1.getTEMPERATURA()));
        double t2 = ((temperatura - water3.getTEMPERATURA())/(water4.getTEMPERATURA() - water3.getTEMPERATURA()));
        
        Cpl1 = water1.getCPL()+ (water2.getCPL()- water1.getCPL()) * t1;
        Cpl2 = water3.getCPL()+ (water4.getCPL()- water3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = water1.getPRL()+ (water2.getPRL()- water1.getPRL()) * t1;
        Prl2 = water3.getPRL()+ (water4.getPRL()- water3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = water1.getKL() + (water2.getKL() - water1.getKL()) * t1;
        kl2 = water3.getKL() + (water4.getKL() - water3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = water1.getMUL() + (water2.getMUL() - water1.getMUL()) * t1;
        Mul2 = water3.getMUL() + (water4.getMUL() - water3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = water1.getVCL() + (water2.getVCL() - water1.getVCL()) * t1;
        Vcl2 = water3.getVCL() + (water4.getVCL() - water3.getVCL()) * t2;
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

    public double getVcl() {
        return Vcl;
    }

    public void setVcl(double Vcl) {
        this.Vcl = Vcl;
    }

    public double getMul() {
        return Mul;
    }

    public void setMul(double Mul) {
        this.Mul = Mul;
    }

    public double getKl() {
        return kl;
    }

    public void setKl(double kl) {
        this.kl = kl;
    }

}
