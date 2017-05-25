/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelIpentaneLiquido;
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
public class ControlIpentaneLiquido {
    private Session session;
    
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;

    public ControlIpentaneLiquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaIpentaneLiquido(){
        String csvFile = "src/Csv/IPENTANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelIpentaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] ipentane_liquido = line.split(csvSplitBy);
                    
                    this.session.save(new ModelIpentaneLiquido(Double.parseDouble(ipentane_liquido[0]),Double.parseDouble(ipentane_liquido[1]),Double.parseDouble(ipentane_liquido[2]),Double.parseDouble(ipentane_liquido[3])));   
                }
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
    
    public void interpolacaoIpentaneLiquido(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelIpentaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelIpentaneLiquido> ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido1 = ipentane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido2 = ipentane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido3 = ipentane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido4 = ipentane_liquido.get(0);
     
        double p1 = ((pressao - ipentane_liquido1.getPRESSAO())/(ipentane_liquido2.getPRESSAO() - ipentane_liquido1.getPRESSAO()));
        double p2 = ((pressao - ipentane_liquido3.getPRESSAO())/(ipentane_liquido4.getPRESSAO() - ipentane_liquido3.getPRESSAO()));
        double t1 = ((temperatura - ipentane_liquido1.getTEMPERATURA())/(ipentane_liquido2.getTEMPERATURA() - ipentane_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - ipentane_liquido3.getTEMPERATURA())/(ipentane_liquido4.getTEMPERATURA() - ipentane_liquido3.getTEMPERATURA()));
        
        Cpl1 = ipentane_liquido1.getCPL()+ (ipentane_liquido2.getCPL()- ipentane_liquido1.getCPL()) * t1;
        Cpl2 = ipentane_liquido3.getCPL()+ (ipentane_liquido4.getCPL()- ipentane_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p1;
        
        Prl1 = ipentane_liquido1.getPRL()+ (ipentane_liquido2.getPRL()- ipentane_liquido1.getPRL()) * t1;
        Prl2 = ipentane_liquido3.getPRL()+ (ipentane_liquido4.getPRL()- ipentane_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p2;
    }
}
