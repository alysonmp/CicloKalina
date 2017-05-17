/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Ciclo2.ControlCompequi;
import Control.Ciclo2.ControlConequi;
import Control.Ciclo2.ControlConstantes;
import Control.Ciclo2.ControlConstantesMix;
import Control.Ciclo2.ControlCubica;
import Control.Ciclo2.ControlDadT_Mix;
import Control.Ciclo2.ControlFug;
import Control.Ciclo2.ControlFugmix;
import Control.Ciclo2.ControlH_Dep;
import Control.Ciclo2.ControlH_Depmix;
import Control.Ciclo2.ControlH_Sistemamix;
import Control.Ciclo2.ControlH_ideal_Gas_Mix;
import Control.Ciclo2.ControlIsoentropiaTurbina;
import Control.Ciclo2.ControlPdeVapor;
import Control.Ciclo2.ControlS_Dep;
import Control.Ciclo2.ControlS_Depmix;
import Control.Ciclo2.ControlS_Ideal_Gas_Mix;
import Control.Ciclo2.ControlS_SistemaMix;
import Control.Ciclo2.ControlSeparadorRankine;
import Control.Ciclo2.ControlTBolha;
import Control.Ciclo2.ControlTSaida;
import Control.Ciclo2.ControlT_Ref;
import Control.Ciclo2.ControlTorvalho;
import Control.Ciclo2.ControlZetamix;
import Model.Ciclo2.ModelBomba;
import Model.Ciclo2.ModelFluidos;
import Model.ModelConstantesMat;
import Model.ModelLinear;
import Util.HibernateUtil;
import View.Condensador.ViewCondensadorImage;
import View.Evaporador.ViewEvaporadorImage;
import View.Recuperador.ViewRecuperadorImage;
import View.Separador.ViewSeparadorImage;
import View.Bomba.ViewBombaImage;
import View.Evaporador.ViewEvaporadorPanelRankine;
import View.Regenerador.ViewRegeneradorImage;
import View.ViewPrincipal;
import View.Turbina.ViewTurbinaImage;
import View.ViewCiclos;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlPrincipal {
    private ViewPrincipal viewPrincipal;
    private ArrayList<JPanel> panel_usado = new ArrayList();
    Session session;
    
    @SuppressWarnings("empty-statement")
    public ControlPrincipal(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        this.session = sf.openSession();
        
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        List results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();

            session.save(new ModelFluidos(1, "Agua"));
            session.save(new ModelFluidos(2, "Toluene (C7H8)"));
            session.save(new ModelFluidos(3, "Ethylbenzene (C8H10)"));
            session.save(new ModelFluidos(4, "Propylbenzene (C9H12)"));
            session.save(new ModelFluidos(5, "Dimetylbenzene(C8H10)"));
            session.save(new ModelFluidos(6, "Octamethyltrisiloxane (MDM)"));
            session.save(new ModelFluidos(7, "Decamethyltetrasiloxane (MD2M)"));
            session.save(new ModelFluidos(8, "Dodecamethylpentasiloxane (MD3M)"));
            session.save(new ModelFluidos(9, "Dodecamethylcyclohexasiloxane (D6)"));
            session.save(new ModelFluidos(10, "Isobutano"));
            session.save(new ModelFluidos(11, "Isopentano"));
            session.save(new ModelFluidos(12, "n-Butano"));
            session.save(new ModelFluidos(13, "n-Pentano"));
            session.save(new ModelFluidos(14, "R-134a (1,1,1,2-Tetrafluoroethane)"));
            session.save(new ModelFluidos(15, "R-290 (PROPANE)"));
            session.save(new ModelFluidos(16, "R-142b (1,1-Difluoro-1-chloroethane)"));
            session.save(new ModelFluidos(17, "R-1270 (1-Propene)"));
            session.save(new ModelFluidos(18, "R-40  (Clorometano)"));
            session.save(new ModelFluidos(19, "Hexamethyldisiloxane, (MM)"));
            session.save(new ModelFluidos(20, "Tetradecamethylhexasiloxane (MD4M)"));
            session.save(new ModelFluidos(21, "Octamethylcyclotetrasiloxane (D4)"));
            session.save(new ModelFluidos(22, "Decamethylcyclopentasiloxane (D5)"));
            session.save(new ModelFluidos(23, "Trichlorofluoromethane (R11)"));
            session.save(new ModelFluidos(24, "Dichlorodifluoromethane (R12)"));
            session.save(new ModelFluidos(25, "1,3-Dichloro-1,1,2,2,3,3-hexafluoropropane (R216ca)"));
            session.save(new ModelFluidos(26, "1,2-Dichlorotetrafluoroethane (R114)"));
            session.save(new ModelFluidos(27, "1,1-Dichloro-1-fluoroethane (R141b)"));
            session.save(new ModelFluidos(28, "1,1-Difluoroethane (R152a)"));
            session.save(new ModelFluidos(29, "1-Chloro-1,1-difluoroethane (R142b)"));
            session.save(new ModelFluidos(30, "n-Hexane"));
            session.save(new ModelFluidos(31, "n-Heptane"));
            session.save(new ModelFluidos(32, "n-octane"));
            session.save(new ModelFluidos(33, "Chlorodifluoromethane (R22)"));

            tx.commit();
        }
        
        cr = this.session.createCriteria(ModelConstantesMat.class);
        results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();
            
            double[][] valoresC1 = {{90.483, -4669.7, -11.607, 0.017194, 1.0}};
            session.save(new ModelConstantesMat("C1", valoresC1));
            
            double[][] valoresC2 = {{73.649, -7258.2, -7.3037, 4.1653E-6, 2.0}};
            session.save(new ModelConstantesMat("C2", valoresC2));

            double[][] valoresCC = {{33.60134, -0.002145, -3.999E-6, 4.752E-8, -3.533E-11, 0.0, 0.0}, 
                                    {33.573, -0.012581, 8.8906E-5, -7.1783E-8, 1.8569E-11, 0.0, 0.0}};
            session.save(new ModelConstantesMat("CC", valoresCC));
            
            tx.commit();
        }
        
        
        cr = this.session.createCriteria(ModelLinear.class);
        results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();

            session.save(new ModelLinear(new double[]{142.2, -0.01509422,-8.3348292E-8,-2.3243455E-13,6.5603658E-17,-3.4487625E-20,2.0808293E-23,-5.8664246E-27,9.1463375E-31,-7.446578E-35,2.4885769E-39}));
            session.save(new ModelLinear(new double[]{143.90002, -0.014493643, -4.6103987E-8, 1.3981357E-12, 2.3993308E-16, -9.8949381E-21, 1.6261678E-24, 0.0, 0.0, 0.0, 0.0}));
            session.save(new ModelLinear(new double[]{145.2, -5.6111011E-4, -0.094364859, 1.0896055E-7,2.3103997E-5, -7.4868329E-12, -2.5130974E-9, 1.9128073E-16, 1.2643961E-13, -7.5492826E-22, -2.4248126E-18}));
            session.save(new ModelLinear(new double[]{135.20007, -0.01007358, 1.2520524E-7, 2.5722111E-12, 4.3171784E-17, -3.9207826E-20, 1.2906424E-24, 0.0, 0.0, 0.0, 0.0}));
            session.save(new ModelLinear(new double[]{159.7099, -0.0061700647, 5.3500968E-8, -3.4218675E-11, 9.9938341E-15, -1.6897954E-18, 1.6983127E-22, -9.9200576E-27, 3.0981733E-31, -3.9847803E-36, 0.0}));
            session.save(new ModelLinear(new double[]{-4501439.8, 384.02683, 3.2531763E10, -0.020762985, -1.3089914E14, 6.9584129E-7, 2.0965934E17, -1.3226177E-11, 9.2327351E19, 1.0920871E-16, 0.0}));
            
            tx.commit();
        }
        
        viewPrincipal = new ViewPrincipal(this);
    }
    
    //FUNÇÃO QUE CRIA O DESENHO DO PRIMEIRO CICLO E INDICA OS LOCAIS DOS JPANELS INSERIDOS
    public void criaCiclo1() {
        viewPrincipal.getPainelCiclos().removeAll();
        viewPrincipal.getTabbedPanel().removeAll();
        panel_usado.clear();
        
        ViewCiclos ciclo = new ViewCiclos(this, "src/Images/Ciclo1/ciclo1.png", (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.9));
        ciclo.setLayout(null);
        ciclo.setBounds((int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.1), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.05), 
                          (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.9));
        viewPrincipal.getPainelCiclos().add(ciclo);
        
        criaPanel(new ViewTurbinaImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.28), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.04), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewSeparadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.21), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.04), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1));
        
        criaPanel(new ViewEvaporadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.13), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.166), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.075), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewCondensadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.285), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.725), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.07), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewRecuperadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.184), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.35), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.069), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewRecuperadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.268), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.558), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.069), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewBombaImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.205), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.875), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.035), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.04));
        
        viewPrincipal.getFramePrincipal().repaint();
    }
    
    //FUNÇÃO QUE CRIA O DESENHO DO SEGUNDO CICLO E INDICA OS LOCAIS DOS JPANELS INSERIDOS
    public void criaCiclo2(){                
        //ControlSeparadorRankine c = new ControlSeparadorRankine(45, 500, 0.2, 20, 300, session);
        //System.exit(0);
        
        viewPrincipal.getPainelCiclos().removeAll();
        viewPrincipal.getTabbedPanel().removeAll();
        panel_usado.clear();
        
        ViewCiclos ciclo = new ViewCiclos(this, "src/Images/Ciclo2/ciclo2.png", (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.7));
        ciclo.setLayout(null);
        ciclo.setBounds((int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.1), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.05), 
                          (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.7));
        viewPrincipal.getPainelCiclos().add(ciclo);
        
        criaPanel(new ViewTurbinaImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.224), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.05), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewRegeneradorImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.236), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.288), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.06), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewEvaporadorImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.15), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.148), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.055), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.16));
        
        criaPanel(new ViewCondensadorImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.275), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.61), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.075), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.11));
        
        criaPanel(new ViewBombaImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.326), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.38), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.065), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.14));
        
        viewPrincipal.getFramePrincipal().repaint();
    }
    
    //FUNÇÃO QUE EDITA OS JPANELS ONDE SERÃO MOSTRADOS OS CICLOS E OS INSERE NO PAINEL
    public void criaPanel(JPanel obj, int x, int y, int width, int height){
        obj.setLayout(null);
        obj.setBackground(Color.black);
        obj.setOpaque(false);
        obj.setBounds(x, y, width, height);
        viewPrincipal.getPainelCiclos().add(obj);
    }  
    
    //FUNÇÃO QUE AJUSTA A MASSA DE TODOS OS COMPONENTES
    public void ajustaMassa() {
        Component[] components = viewPrincipal.getTabbedPanel().getComponents();
        for(Component c: components){
            switch(c.getName()){
                case "Evaporador":
                    ViewEvaporadorPanelRankine painel = (ViewEvaporadorPanelRankine)c;
                    //painel.
                    break;
            }
        }
    }
    
    public void removeTab(){
        this.getViewPrincipal().getTabbedPanel().remove(0);
        this.getPanel_usado().remove(0);
    }
    
    public List getFluidos(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        this.session = sf.openSession();
        
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        cr.setProjection(Projections.property("nome"));
        
        List results = cr.list();
        
        return results;
    }
    
    public ViewPrincipal getViewPrincipal() {
        return viewPrincipal;
    }

    public void setViewPrincipal(ViewPrincipal viewPrincipal) {
        this.viewPrincipal = viewPrincipal;
    }

    public ArrayList<JPanel> getPanel_usado() {
        return panel_usado;
    }

    public void setPanel_usado(ArrayList<JPanel> panel_usado) {
        this.panel_usado = panel_usado;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}


