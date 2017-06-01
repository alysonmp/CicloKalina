package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelD4Liquido;
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
public class ControlD4Liquido {
    private Session session;
    
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2,kl1 , kl2, Mul1, Mul2, Vcl1, Vcl2;

    public ControlD4Liquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaD4Liquido(){
        String csvFile = "src/Csv/D4_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelD4Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] d4_liquido = line.split(csvSplitBy);
                    
                    this.session.save(new ModelD4Liquido(Double.parseDouble(d4_liquido[0]),Double.parseDouble(d4_liquido[1]),Double.parseDouble(d4_liquido[2]),Double.parseDouble(d4_liquido[3]),Double.parseDouble(d4_liquido[4]),Double.parseDouble(d4_liquido[5]),Double.parseDouble(d4_liquido[6])));   
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
    
    public void interpolacao(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelD4Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from d4_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelD4Liquido> d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido1 = d4_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido2 = d4_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido3 = d4_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d4_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD4Liquido.class));//Sem isso aqui impossível de retornar
	d4_liquido = consulta.list(); 
        ModelD4Liquido d4_liquido4 = d4_liquido.get(0);
     
        double p = ((pressao - d4_liquido1.getPRESSAO())/(d4_liquido3.getPRESSAO() - d4_liquido1.getPRESSAO()));
        double t1 = ((temperatura - d4_liquido1.getTEMPERATURA())/(d4_liquido2.getTEMPERATURA() - d4_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - d4_liquido3.getTEMPERATURA())/(d4_liquido4.getTEMPERATURA() - d4_liquido3.getTEMPERATURA()));
        
        Cpl1 = d4_liquido1.getCPL()+ (d4_liquido2.getCPL()- d4_liquido1.getCPL()) * t1;
        Cpl2 = d4_liquido3.getCPL()+ (d4_liquido4.getCPL()- d4_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = d4_liquido1.getPRL()+ (d4_liquido2.getPRL()- d4_liquido1.getPRL()) * t1;
        Prl2 = d4_liquido3.getPRL()+ (d4_liquido4.getPRL()- d4_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = d4_liquido1.getKL() + (d4_liquido2.getKL() - d4_liquido1.getKL()) * t1;
        kl2 = d4_liquido3.getKL() + (d4_liquido4.getKL() - d4_liquido3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = d4_liquido1.getMUL() + (d4_liquido2.getMUL() - d4_liquido1.getMUL()) * t1;
        Mul2 = d4_liquido3.getMUL() + (d4_liquido4.getMUL() - d4_liquido3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = d4_liquido1.getVCL() + (d4_liquido2.getVCL() - d4_liquido1.getVCL()) * t1;
        Vcl2 = d4_liquido3.getVCL() + (d4_liquido4.getVCL() - d4_liquido3.getVCL()) * t2;
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
