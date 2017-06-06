package Control.Conversao;

/**
 *
 * @author leonardo
 */
public class ControlConverte {
    
    public ControlConverte(){
    }
    
    public double converte(String tipo,String tipo2, double valor){
        int precision = 4;
        
        if(tipo.equals("K") || tipo.equals("kPa") || tipo.equals("kg/s")){
            switch(tipo2){
                case "°C":
                    return round(valor - 273,precision);
                case "°F":
                    return round((valor*1.8 - 459.67),precision);
                case "atm":
                    return round(valor/101.325,precision);
                case "bar":
                    return round(valor/100,precision);
                case "kg/m":
                    return round(valor/60,precision);
                case "kg/h":
                    return round(valor/3600,precision);
                case "lb/s":
                    return round(valor/2.204623,precision);
                case "lb/m":
                    return round(valor/132.277357,precision);
                case "lb/h":
                    return round(valor/7936.641438,precision);
                default:
                    return round(valor,precision);
            }
        }       
                
        if(tipo2.equals("K") || tipo2.equals("kPa") || tipo2.equals("kg/s")){
            switch (tipo) {
                case "°C":
                    return round(valor + 273,precision);
                case "°F":
                    return round(((valor + 459.67) / 1.8),precision);
                case "atm":
                    return round((valor*101.325),precision);
                case "bar":
                    return round((valor * 100),precision);
                case "kg/m":
                    return round((valor*60),precision);
                case "kg/h":
                    return round((valor * 3600),precision);
                case "lb/s":
                    return round((valor * 2.204623),precision);
                case "lb/m":
                    return round((valor * 132.277357),precision);
                case "lb/h":
                    return round((valor * 7936.641438),precision);
                default:
                    return round(valor,precision);
            }
        }
        
        //conversao temperatura
        if(tipo.equals("°C") && tipo2.equals("°F")){
            return round((valor *1.8 + 32),precision);
        }else if(tipo.equals("°F") && tipo2.equals("°C")){
            return round(((valor-32)/1.8),precision);
        }
        
        //conversao pressao
        if(tipo.equals("atm") && tipo2.equals("bar")){
            return round(valor * 1.01325,precision);
        }else if(tipo.equals("bar") && tipo2.equals("atm")){
            return round(valor * 0.986923,precision);
        }
        
        //Conversao da massa
        if(tipo.equals("kg/m") && tipo2.equals("kg/h")){
            
        }else if(tipo.equals("kg/m") && tipo2.equals("lb/s")){
        
        }else if(tipo.equals("kg/m") && tipo2.equals("lb/m")){
            
        }else if(tipo.equals("kg/m") && tipo2.equals("lb/h")){
            
        }else if(tipo.equals("kg/h") && tipo2.equals("kg/m")){
            
        }else if(tipo.equals("kg/h") && tipo2.equals("lb/s")){
            
        }else if(tipo.equals("kg/h") && tipo2.equals("lb/m")){
            
        }else if(tipo.equals("kg/h") && tipo2.equals("lb/h")){
            
        }else if(tipo.equals("lb/s") && tipo2.equals("kg/m")){
            
        }else if(tipo.equals("lb/s") && tipo2.equals("kg/h")){
            
        }else if(tipo.equals("lb/s") && tipo2.equals("lb/m")){
            
        }else if(tipo.equals("lb/s") && tipo2.equals("lb/h")){
            
        }else if(tipo.equals("lb/m") && tipo2.equals("kg/m")){
            
        }else if(tipo.equals("lb/m") && tipo2.equals("kg/h")){
            
        }else if(tipo.equals("lb/m") && tipo2.equals("lb/s")){
            
        }else if(tipo.equals("lb/m") && tipo2.equals("lb/h")){
            
        }else if(tipo.equals("lb/h") && tipo2.equals("kg/m")){
            
        }else if(tipo.equals("lb/h") && tipo2.equals("kg/h")){
            
        }else if(tipo.equals("lb/h") && tipo2.equals("lb/s")){
            
        }else if(tipo.equals("lb/h") && tipo2.equals("lb/m")){
            
        }
        
        return 0;
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
