/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.Ciclo2.ModelFluidos;
import Model.TabelasFluidos.ModelWaterLiquido;
import Model.TabelasFluidos.ModelWaterLiquido;
import Model.TabelasFluidos.ModelWaterLiquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author alysonmp
 */
public class ControlWaterLiquido {
    
    Session session;
    private double Cpl, Prl, Vcl, Mul, kl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
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
                    
                    session.save(new ModelWaterLiquido(Double.parseDouble(water_l[0]), Double.parseDouble(water_l[1]), Double.parseDouble(water_l[2]), Double.parseDouble(water_l[3])));
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
        
        Cpl1 = water1.getCPL()+ (water2.getCPL()- water1.getCPL()) * ((temperatura-water1.getTEMPERATURA())/(water2.getTEMPERATURA()-water1.getTEMPERATURA()));
        Cpl2 = water3.getCPL()+ (water4.getCPL()- water3.getCPL()) * ((temperatura-water3.getTEMPERATURA())/(water4.getTEMPERATURA()-water3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-water1.getPRESSAO())/(water2.getPRESSAO()-water1.getPRESSAO()));
        
        Prl1 = water1.getPRL()+ (water2.getPRL()- water1.getPRL()) * ((temperatura-water1.getTEMPERATURA())/(water2.getTEMPERATURA()-water1.getTEMPERATURA()));
        Prl2 = water3.getPRL()+ (water4.getPRL()- water3.getPRL()) * ((temperatura-water3.getTEMPERATURA())/(water4.getTEMPERATURA()-water3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-water3.getPRESSAO())/(water4.getPRESSAO()-water3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
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
