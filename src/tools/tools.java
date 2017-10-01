/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import model.coordenadas;

/**
 *
 * @author GZAVALA
 */
public class tools {

    public static coordenadas extrae_coordenadas(String cadleida) {
        coordenadas obj_coordenadas=new coordenadas();
        cadleida=cadleida.replace("[","");
        cadleida=cadleida.replace("]","");
 	StringTokenizer tokens=new StringTokenizer(cadleida,",");
        int i=0;
        int x=0; 
        int y=0;
	while(tokens.hasMoreTokens())
        {
         int val=0; 
         String str=tokens.nextToken();
         val=Integer.valueOf(str).intValue(); 
         if (i==0)
           {x=val;}
         if (i==1)
           {y=val;}   
         i++;
        }
         obj_coordenadas.setX(x);
         obj_coordenadas.setY(y);
         return obj_coordenadas;
    }

    public static int[][] carga_maps(String arch_mapa, int nfil, int ncol) {
     int[][]obj_matriz=new int[nfil][ncol];   
                try{
    		    int fil=0;
                    String cadleida=null; 
		    FileReader finput = new FileReader(arch_mapa);
		    BufferedReader vread = new BufferedReader(finput);
		    while((cadleida = vread.readLine())!=null) 
                    { for(int j=0;j<ncol;j++ )
                      { obj_matriz[fil][j]=Integer.parseInt(cadleida.charAt(j)+"" );   
                      }   
                     fil++;  
                    }
                    vread.close();
                    System.out.println("Lectura de archivo Mapa..OK..");       
                   }
                catch(Exception e)
                {   System.out.println("Error leyendo archivo de Mapa ");
                    System.out.println(""+e.getMessage());
                 }    
     return obj_matriz; 
    }

    public tools() {
    }
        public static ArrayList<coordenadas> carga_coordenadas(String archinput  )  
	  { 
              ArrayList<coordenadas> collect_coordenadas = new ArrayList<coordenadas>(); 
              coordenadas obj_coord=new coordenadas() ;  
                try{
    		    String cadleida;
		    FileReader finput = new FileReader(archinput);
		    BufferedReader vread = new BufferedReader(finput);
		    while((cadleida = vread.readLine())!=null) 
                    {  
                        obj_coord= extrae_coordenadas(cadleida);
                        collect_coordenadas.add(obj_coord);
		    }
		    vread.close();
                    System.out.println("Lectura de archivo coordenadas..OK..");       
                }
                catch(Exception e)
                {   System.out.println("Error leyendo archivo de coordenadas ");
                    System.out.println(""+e.getMessage());
                 }    
               return collect_coordenadas;  
	  }
 
}
