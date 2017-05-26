/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

/**
 *
 * @author leonardo
 */
public class ControlCaixa {
    private double FCX, NPV1, NPV2;

    public ControlCaixa(double CBio, double FCI, double  mice, double OM, double  Wn, double Pele, double Pice, double mb, double PEC) {
        double Taxa_R_B=0.2115;	
        double Taxa_L_L= 0.33;
        double Taxa_Dep=0.05;

        double FC=0.80; //Fator de carga
        double T_OP=52*7*24*FC;



        // Receita Bruta de Vendas  
        double RBV=(Wn*Pele*T_OP)+(mice*T_OP*Pice);
        // - Impostos Proporcionais (taxa R_B)
        double IP=Taxa_R_B*RBV;
        // Receita Líquida de Vendas
        double RLV=RBV-IP;
        // - Custos Fixos
        double CF=OM;
        // - Custos Variáveis
        double CV=CBio*mb*T_OP;
        // Lucro Bruto
        double LB=RLV-CF-CV;
        // - Despesas Fixas
        double DF=0;
        // - Despesas Variáveis
        double DV=0;
        // - Depreciação
        double DEP=Taxa_Dep*PEC;
        // - Despesas Financeiras (Juros)
        double DFIN=0;
        // Lucro Operacional
        double LOPE=LB-DF-DV-DEP-DFIN;
        // - Despesas não operacionais
        double DNO=0;
        // + Receitas não operacionais
        double RNO=0;
        // Lucro antes do IR
        double LAIR=LOPE+RNO-DNO;
        // - IRPJ / CSLL
        double IRPJ=Taxa_L_L*LAIR;
        // Lucro Líquido
        double L_L=LAIR-IRPJ;
        // 
        // + Depreciação

        // - Resultado não operacional

        // - Amortização dívidas

        // - Investimento

        // + Liberação Financiamento

        // + Valor Residual

        // Fluxo de Caixa
        FCX=L_L+DEP;

        double NV1=pvfix(0.12, 10, FCX);
        double NV2=pvfix(0.12, 20, FCX);
        NPV1=NV1-FCI;
        NPV2=NV2-FCI;
    }
    
    
}
