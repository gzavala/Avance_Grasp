/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package met_grasp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import tools.tools;
import model.coordenadas;

/**
 *
 * @author GZAVALA
 */
public class Met_Grasp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
     
       String arch_coordenadas=".\\src\\archivos\\coordinates0.txt" ;
       ArrayList<coordenadas>collect_coordenadas=new ArrayList<coordenadas>();     
       collect_coordenadas= tools.carga_coordenadas(arch_coordenadas); 
/*
       System.out.println("Carga Finalizada con "+ collect_coordenadas.size() + " coordenadas");
       for(int i=0; i<collect_coordenadas.size();i++ )
       { System.out.println("Coordenada de orden "+ (i+1)+ ": ("+ collect_coordenadas.get(i).getX() +","+collect_coordenadas.get(i).getY()+ ")" );
        }  
 */      
       String arch_mapa=".\\src\\archivos\\map0.txt" ;
       int nfil=30; 
       int ncol=60;
       int[][] mapa= new int[nfil][ncol];
       mapa= tools.carga_maps(arch_mapa,nfil,ncol); 
/*       
     for (int i=0; i<nfil; i++)
     {
      for(int j=0; j<ncol;j++)
       { System.out.print(mapa[i][j]);
        }  
       System.out.println("");
      }  
  */     
    /* motodo para identificar el directorio actual   
     File miDir = new File (".");
     try {
       System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
       }
     catch(Exception e) {
       e.printStackTrace();
       }
    */ 
    
    }
    
}
