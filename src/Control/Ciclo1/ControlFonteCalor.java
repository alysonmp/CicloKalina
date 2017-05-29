/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

/**
 *
 * @author alysonmp
 */
public class ControlFonteCalor {
    
    public ControlFonteCalor(){
        
    }
    
}

function [mf, Tf, Pf, X, FON, titulo]=fonte_calor()
disp('Definir Fonte de Calor')
disp('Para Agua(liquido o vapor) Digite 1')
disp('Para Ar-seco Digite 2')
disp('Para mistura de gases Digite 3')
disp('  ');
GG = input(' ');
clc
disp('Definir Temperatura da fonte de Calor em Kelvin')
Tf = input(' ');
clc
disp('Definir vaz„o massica da fonte de Calor em kmol/s')
mf = input(' ');
clc
if GG == 2;
   titulo=1;
    XCO2=0;
    XH2O=0;
    XSO2=0;
    XO2=21;
    XN2=79;
    XCO=0;
    XH2=0;
    XNO=0;
    XTOTAL=XCO2 + XH2O + XSO2 + XO2 + XN2 + XCO + XH2 + XNO;
    Pf=101.325;
    else if GG == 3;
        disp('Digite o % molar de CO2')
        disp('  ');
        XCO2 = input('');
        disp('Digite o % molar de H2O')
        disp('  ');
        XH2O = input('');
        disp('Digite o % molar de SO2 ')
        disp('  ');
        XSO2 = input('');
        disp('Digite o % molar de O2 ')
        disp('  ');
        XO2 = input('');
        disp('Digite o % molar de N2 ')
        disp('  ');
        XN2 = input('');
        disp('Digite o % molar de N2 ')
        disp('  ');
        
        disp('Digite o % molar de CO ')
        disp('  ');
        XCO = input('');
disp('Digite o % molar de H2 ')
        disp('  ');
        XH2 = input('');
disp('Digite o % molar de NO ')
        disp('  ');
        XNO = input('');

        

  
        XTOTAL=XCO2 + XH2O + XSO2 + XO2 + XN2 + XCO + XH2 + XNO;
        clc
        Pf=101.325;
       titulo=1;
        if XTOTAL ~= 100 ;
            disp('A porcentagem volumetrica È diferente de 100%')
            XTOTOL=ERROR;
        end
        else
    disp('Definir a press„o da fonte de Calor em kPa')
Pf = input(' ');
clc
  disp('Definir o titulo se È mezcla L+V')
 titulo = input(' ');
    XCO2=0;
    XH2O=0;
    XSO2=0;
    XO2=0;
    XN2=0;
    XCO=0;
    XH2=0;
    XNO=0;
        end
end
X= [XCO2 XH2O XSO2 XO2 XN2 XCO XH2 XNO]; 
FON=GG;



