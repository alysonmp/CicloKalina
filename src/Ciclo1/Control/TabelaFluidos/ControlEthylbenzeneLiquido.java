/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelEthylbenzeneLiquido;
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
 * @author leonardo
 */
public class ControlEthylbenzeneLiquido {
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlEthylbenzeneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaEthylbenzeneLiquido(){
        String csvFile = "src/Csv/Ethylbenzene_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {

            Criteria cr = this.session.createCriteria(ModelEthylbenzeneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] ethylbenzene_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelEthylbenzeneLiquido(Double.parseDouble(ethylbenzene_l[0]), Double.parseDouble(ethylbenzene_l[1]), Double.parseDouble(ethylbenzene_l[2]), Double.parseDouble(ethylbenzene_l[3]), Double.parseDouble(ethylbenzene_l[4]), Double.parseDouble(ethylbenzene_l[5]),Double.parseDouble(ethylbenzene_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelEthylbenzeneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from ethylbenzene_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelEthylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelEthylbenzeneLiquido> ethylbenzene_l = consulta.list(); 
        ModelEthylbenzeneLiquido ethylbenzene_l1 = ethylbenzene_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from ethylbenzene_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelEthylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	ethylbenzene_l = consulta.list(); 
        ModelEthylbenzeneLiquido ethylbenzene_l2 = ethylbenzene_l.get(0);

        consulta = this.session.createSQLQuery("select * from ethylbenzene_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelEthylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	ethylbenzene_l = consulta.list(); 
        ModelEthylbenzeneLiquido ethylbenzene_l3 = ethylbenzene_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from ethylbenzene_liquido where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelEthylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	ethylbenzene_l = consulta.list(); 
        ModelEthylbenzeneLiquido ethylbenzene_l4 = ethylbenzene_l.get(0);

        double p  = ((pressao - ethylbenzene_l1.getPRESSAO())/(ethylbenzene_l3.getPRESSAO() - ethylbenzene_l1.getPRESSAO()));
        double t1 = ((temperatura - ethylbenzene_l1.getTEMPERATURA())/(ethylbenzene_l2.getTEMPERATURA() - ethylbenzene_l1.getTEMPERATURA()));
        double t2 = ((temperatura - ethylbenzene_l3.getTEMPERATURA())/(ethylbenzene_l4.getTEMPERATURA() - ethylbenzene_l3.getTEMPERATURA()));
        
        Cpl1 = ethylbenzene_l1.getCPL()+ (ethylbenzene_l2.getCPL()- ethylbenzene_l1.getCPL()) * t1;
        Cpl2 = ethylbenzene_l3.getCPL()+ (ethylbenzene_l4.getCPL()- ethylbenzene_l3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = ethylbenzene_l1.getPRL()+ (ethylbenzene_l2.getPRL()- ethylbenzene_l1.getPRL()) * t1;
        Prl2 = ethylbenzene_l3.getPRL()+ (ethylbenzene_l4.getPRL()- ethylbenzene_l3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = ethylbenzene_l1.getKL() + (ethylbenzene_l2.getKL() - ethylbenzene_l1.getKL()) * t1;
        kl2 = ethylbenzene_l3.getKL() + (ethylbenzene_l4.getKL() - ethylbenzene_l3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = ethylbenzene_l1.getMUL() + (ethylbenzene_l2.getMUL() - ethylbenzene_l1.getMUL()) * t1;
        Mul2 = ethylbenzene_l3.getMUL() + (ethylbenzene_l4.getMUL() - ethylbenzene_l3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = ethylbenzene_l1.getVCL() + (ethylbenzene_l2.getVCL() - ethylbenzene_l1.getVCL()) * t1;
        Vcl2 = ethylbenzene_l3.getVCL() + (ethylbenzene_l4.getVCL() - ethylbenzene_l3.getVCL()) * t2;
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
