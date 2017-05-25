/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR142BLiquido;
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
public class ControlR142BLiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR142BLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR142BLiquido(){
        String csvFile = "src/Csv/R142B_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR142BLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R142B_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR142BLiquido(Double.parseDouble(R142B_l[0]), Double.parseDouble(R142B_l[1]), Double.parseDouble(R142B_l[2]), Double.parseDouble(R142B_l[3])));
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
    
    public void interpolacaoButano(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelR142BLiquido.class);
        //cr = this.session.createCriteria(ModelR142BLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R142B where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR142BLiquido> R142Bs = consulta.list(); 
        ModelR142BLiquido R142B1 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BLiquido R142B2 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BLiquido R142B3 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BLiquido R142B4 = R142Bs.get(0);
        
        Cpl1 = R142B1.getCPL()+ (R142B2.getCPL()- R142B1.getCPL()) * ((temperatura-R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA()-R142B1.getTEMPERATURA()));
        Cpl2 = R142B3.getCPL()+ (R142B4.getCPL()- R142B3.getCPL()) * ((temperatura-R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA()-R142B3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R142B1.getPRESSAO())/(R142B2.getPRESSAO()-R142B1.getPRESSAO()));
        
        Prl1 = R142B1.getPRL()+ (R142B2.getPRL()- R142B1.getPRL()) * ((temperatura-R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA()-R142B1.getTEMPERATURA()));
        Prl2 = R142B3.getPRL()+ (R142B4.getPRL()- R142B3.getPRL()) * ((temperatura-R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA()-R142B3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R142B3.getPRESSAO())/(R142B4.getPRESSAO()-R142B3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
