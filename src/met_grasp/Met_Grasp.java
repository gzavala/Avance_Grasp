/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package met_grasp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
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
    
    /* Prueba de metodo para identificar el directorio actual   
     File miDir = new File (".");
     try {
       System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
       }
     catch(Exception e) {
       e.printStackTrace();
       }
    */         
    //carga de mapa un solo mapa
       String arch_mapa=".\\src\\archivos\\map0.txt" ;
       int nfil=30; 
       int ncol=60;
       int[][] mapa= new int[nfil][ncol];
       mapa= tools.carga_maps(arch_mapa,nfil,ncol); 

    /*  Prueba de carga de mapa con valores de 0 y 1 
     for (int i=0; i<nfil; i++)
     {
      for(int j=0; j<ncol;j++)
       { System.out.print(mapa[i][j]);
        }  
       System.out.println("");
      }  
    */          
    int numarch_coord=1; //corresponde al numero de archivos de entrada a leer 
    String nombgen_arch="";
    
    for (int numfile=0;numfile<numarch_coord; numfile++)
    {    
       String arch_coordenadas=".\\src\\archivos\\coordinates"+numfile+".txt" ;
       ArrayList<coordenadas>collect_coordenadas=new ArrayList<coordenadas>();     
       collect_coordenadas= tools.carga_coordenadas(arch_coordenadas); 

    /* Prueba de que se cargaron correctamente las coordenadas
       System.out.println("Carga Finalizada con "+ collect_coordenadas.size() + " coordenadas");
       for(int i=0; i<collect_coordenadas.size();i++ )
       { System.out.println("Coordenada de orden "+ (i+1)+ ": ("+ collect_coordenadas.get(i).getX() +","+collect_coordenadas.get(i).getY()+ ")" );
        }  
     */      
    /* Prueba de que las coordenadas apuntan a direcciones con valor=1 que significa posicion de rack   
       System.out.println("");
        for(int i=0; i<collect_coordenadas.size();i++)
        { System.out.println("Coordenada "+ (i+1)+" con valor : "+ mapa[collect_coordenadas.get(i).getX()][collect_coordenadas.get(i).getY()]);
        }  
    */
    }
    
    
    
    
/*     Pruebas de la busqueda recursiva, éste esquema será mejorado     
       int x1=5;
       int y1=5;
       int x2=3; 
       int y2=5; 
       int min_dist=0; 

       ArrayList<coordenadas> recorrido=new ArrayList<coordenadas>(); 
       coordenadas obj_coor= new coordenadas();
       obj_coor.setX(x1);
       obj_coor.setY(y1);
       recorrido.add(obj_coor);
       min_dist=tools.distanciaminima(x1, y1, x2, y2, mapa,nfil,ncol, recorrido);
       System.out.println("Minima distancia: "+ min_dist);
       
       for (int i=0; i<recorrido.size();i++)
       { System.out.println("Coordenadas: "+ recorrido.get(i).getX() + " , " + recorrido.get(i).getY()  );
        }   
 */     
       

    

    
    }
    
}
