/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelIsobutanLiquido;
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
 * @author leonardo
 */
public class ControlIsobutanLiquido {
    private Session session;
    
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;

    public ControlIsobutanLiquido(Session session) {
        this.session = this.session;
    }
    
    public void criaTabelaIsobutanLiquido(){
        String csvFile = "src/Csv/ISOBUTAN_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelIsobutanLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] isobutan_liquido = line.split(csvSplitBy);
                    
                    this.session.save(new ModelIsobutanLiquido(Double.parseDouble(isobutan_liquido[0]),Double.parseDouble(isobutan_liquido[1]),Double.parseDouble(isobutan_liquido[2]),Double.parseDouble(isobutan_liquido[3])));   
                }
                tx.commit();
            }
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void interpolacaoIsobutanLiquido(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelIsobutanLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from isobutan_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelIsobutanLiquido> isobutan_liquido = consulta.list(); 
        ModelIsobutanLiquido isobutan_liquido1 = isobutan_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from isobutan_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanLiquido.class));//Sem isso aqui impossível de retornar
	isobutan_liquido = consulta.list(); 
        ModelIsobutanLiquido isobutan_liquido2 = isobutan_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from isobutan_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanLiquido.class));//Sem isso aqui impossível de retornar
	isobutan_liquido = consulta.list(); 
        ModelIsobutanLiquido isobutan_liquido3 = isobutan_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from isobutan_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIsobutanLiquido.class));//Sem isso aqui impossível de retornar
	isobutan_liquido = consulta.list(); 
        ModelIsobutanLiquido isobutan_liquido4 = isobutan_liquido.get(0);
     
        double p1 = ((pressao - isobutan_liquido1.getPRESSAO())/(isobutan_liquido2.getPRESSAO() - isobutan_liquido1.getPRESSAO()));
        double p2 = ((pressao - isobutan_liquido3.getPRESSAO())/(isobutan_liquido4.getPRESSAO() - isobutan_liquido3.getPRESSAO()));
        double t1 = ((temperatura - isobutan_liquido1.getTEMPERATURA())/(isobutan_liquido2.getTEMPERATURA() - isobutan_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - isobutan_liquido3.getTEMPERATURA())/(isobutan_liquido4.getTEMPERATURA() - isobutan_liquido3.getTEMPERATURA()));
        
        Cpl1 = isobutan_liquido1.getCPL()+ (isobutan_liquido2.getCPL()- isobutan_liquido1.getCPL()) * t1;
        Cpl2 = isobutan_liquido3.getCPL()+ (isobutan_liquido4.getCPL()- isobutan_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p1;
        
        Prl1 = isobutan_liquido1.getPRL()+ (isobutan_liquido2.getPRL()- isobutan_liquido1.getPRL()) * t1;
        Prl2 = isobutan_liquido3.getPRL()+ (isobutan_liquido4.getPRL()- isobutan_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p2;
    }
}
