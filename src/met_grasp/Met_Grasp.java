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
    
    //carga del mapa de carga
       String arch_mapa=".\\src\\archivos\\map0.txt" ;
       int nfil=30; 
       int ncol=60;
       int[][] mapa= new int[nfil][ncol];
       mapa= tools.carga_maps(arch_mapa,nfil,ncol); 

    int numarch_coord=1; //corresponde al numero de archivos de entrada a leer 
    String nombgen_arch="";
    ArrayList<coordenadas>collect_coordenadas=new ArrayList<coordenadas>(); 
    ArrayList<coordenadas>copia_coordenadas=new ArrayList<coordenadas>(); 
    
    
    //Las coordenadas iniciales desde el cual parte el algoritmo
    int x0=0; 
    int y0=0; 
    int v_posinicial=0;
    //variables para la etapa de prepocesamiento
    int x_prepop=0; 
    int y_prepop=0;
    //variable para le número de repeticiones del algoritmo grasp
    long num_repeticiones=100000; 
    float v_distancia_minima=(float) 0.0; 
    
    
    for (int numfile=0;numfile<numarch_coord; numfile++)
    {   
    //*Carga del archivo de Coordenadas    
       String arch_coordenadas=".\\src\\archivos\\coordinates"+numfile+".txt" ;
  
       collect_coordenadas= tools.carga_coordenadas(arch_coordenadas); 
  //-------------*****FASES DEL ALGORITMO GRASP*****------------------------------------------  
     //bucle para el total de repeticiones del algoritmo grasp
     for(int cont=0; cont<num_repeticiones; cont++)
     {
        //copia de las coordenadas cargadas 
         copia_coordenadas=tools.copia_coordenadas(collect_coordenadas); 

         //*0 Para éste caso el índice de sensibilidad viene dado por la distancia euclidieana entre puntos

        //*1 Fase de Preprocesamiento 
        // Consiste en hallar a la primera solucion
        // En este caso corresponderá a la coordenada mas próxima del punto inicial dado x0=0; y0=0,
         float[] v_distancias= new float[copia_coordenadas.size()];
         v_distancias=tools.arreglo_distancias(x0, y0, copia_coordenadas);
         v_posinicial=tools.posicion_minimo_elemento(v_distancias, copia_coordenadas.size());
         x_prepop=copia_coordenadas.get(v_posinicial).getX();
         y_prepop=copia_coordenadas.get(v_posinicial).getY();

        //*2 Fase Constructiva

        //*3 Fase de Búsqueda

        //*4 Mejora Grasp-- > 2opt
     } 

    
    }
    
    
    
   
       

    

    
    }
    
}

    
    /* Prueba de metodo para identificar el directorio actual   
     File miDir = new File (".");
     try {
       System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
       }
     catch(Exception e) {
       e.printStackTrace();
       }
    */     

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

    /*  Prueba de carga de mapa con valores de 0 y 1 
     for (int i=0; i<nfil; i++)
     {
      for(int j=0; j<ncol;j++)
       { System.out.print(mapa[i][j]);
        }  
       System.out.println("");
      }  
    */          


/*  //Esta parte del programa para considerada para el refinamiento de las distancias, candidatas  a usar
    //método de Floyd y Warshall
    Pruebas de la busqueda recursiva, éste esquema será mejorado     
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
