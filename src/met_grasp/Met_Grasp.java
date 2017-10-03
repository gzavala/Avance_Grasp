/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package met_grasp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import tools.tools;
import model.coordenadas;
import model.coord_grasp;

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
       String arch_mapa=".\\src\\archivos\\map_0.txt" ;
       String arch_salida=".\\src\\archivos\\output.txt";
       
       //Abre el archivo de salida 
       FileWriter foutput= new FileWriter(arch_salida);
       BufferedWriter vwrite= new BufferedWriter(foutput);
       PrintWriter pwrite=new PrintWriter(vwrite);
       
       int nfil=30; 
       int ncol=60;
       int[][] mapa= new int[nfil][ncol];
       mapa= tools.carga_maps(arch_mapa,nfil,ncol); 

       int numarch_coord=1; //corresponde al numero de archivos de entrada a leer 
       String nombgen_arch="";
       ArrayList<coordenadas>collect_coordenadas=new ArrayList<coordenadas>(); 
       ArrayList<coordenadas>copia_coordenadas=new ArrayList<coordenadas>(); 
       ArrayList<coord_grasp>copia_coord_posicion=new ArrayList<coord_grasp>();
       ArrayList<coord_grasp>solucion_elite=new ArrayList<coord_grasp>();
       
       float alpha=(float)0.7; //consideraremos esta variable de relajacion 
       
       vwrite.write("id_archivo | Distancia Minima");
       vwrite.newLine();
       
       
    
       //Las coordenadas iniciales desde el cual parte el algoritmo
       int x0=0; 
       int y0=0; 
       int v_posinicial=0;
       //variables para la etapa de prepocesamiento
       int x_prepop=0; 
       int y_prepop=0;
       //variable para le número de repeticiones del algoritmo grasp
       long num_repeticiones=3; //100000 
       int tam_arreglo=0; 
       int v_buscpos=0; 
    for (int numfile=0;numfile<numarch_coord; numfile++)
    {   
    //*Carga del archivo de Coordenadas    
       String arch_coordenadas=".\\src\\archivos\\coordinates_"+numfile+".txt" ;
       float v_distancia_minima=(float) 0.0; 
       float v_dist=(float) 0.0;
       collect_coordenadas= tools.carga_coordenadas(arch_coordenadas); 

       int[]arr_ocurrencias= new int[tam_arreglo];       
  //-------------*****FASES DEL ALGORITMO GRASP*****------------------------------------------  
     //bucle para el total de repeticiones del algoritmo grasp, por cada repetición se obtiene una distancia mínima, 
     //luego se compara y se obtiene la distancia mínima por archivo leido
     for(int cont=0; cont<num_repeticiones; cont++)
       {
       //copia de las coordenadas cargadas 
         copia_coordenadas=tools.copia_coordenadas_leidas(collect_coordenadas); 
         copia_coord_posicion=tools.copia_coord_posicion(collect_coordenadas);
         tam_arreglo=copia_coord_posicion.size();
        //*0 Para éste caso el índice de sensibilidad viene dado por la distancia euclidieana entre  2 puntos
        
        //*1 Fase de Preprocesamiento 
        // Consiste en hallar a la primera solucion (x_prepop,y_prepop)
        // En este caso corresponderá a la coordenada mas próxima del punto inicial dado x0=0; y0=0,
         float[] v_distancias= new float[tam_arreglo];
         v_distancias=tools.arreglo_distancias(x0, y0, copia_coord_posicion);
         v_posinicial=tools.posicion_minimo_elemento(v_distancias, tam_arreglo);
         x_prepop=copia_coordenadas.get(v_posinicial).getX();
         y_prepop=copia_coordenadas.get(v_posinicial).getY();
         //vector inicial de ocurrencias se inicializar en Ceros "0" 
         for (int w=0; w<tam_arreglo;w++)
          { arr_ocurrencias[w]=0; }  
         //marca la posicion inicial como ya ocupada y la elimina en el arreglo de coord_posicion
         arr_ocurrencias[v_posinicial]=1;
         //busca la posicion en el arreglo que guarda las posiciones restantes, 
         //aquella cuya posicion inicial era "v_posinicial" y lo elimina de la coleccion
         v_buscpos=tools.buscapocion_especial(copia_coord_posicion,v_posinicial);     
         //agrega la solucion elite
         solucion_elite.add(copia_coord_posicion.get(v_buscpos));
         //Es la nueva  posicion x0 , y0 para calcular el vector de distancias
         x0=copia_coord_posicion.get(v_buscpos).getX();
         y0=copia_coord_posicion.get(v_buscpos).getY();
         
         copia_coord_posicion.remove(v_buscpos);
         //se tiene la variable dnde se acumulará la distancia para cada corrida
         v_dist=v_distancias[v_posinicial];
         //*2 Fase Constructiva
          int tam_recorrido=copia_coord_posicion.size();
          while (tam_recorrido>0)
          { 
            v_distancias= new float[tam_recorrido];
            v_distancias=tools.arreglo_distancias(x0, y0, copia_coord_posicion);
            float dis_min=(float)0.0;
            float dis_max=(float)0.0;
            dis_min=tools.val_min_vector(v_distancias,tam_recorrido);
            dis_max=tools.val_max_vector(v_distancias,tam_recorrido);
            //Obtiene los extremos del RCL
            float limInfRCL=0; 
            float limSupRCL=0;
            limInfRCL=dis_min;
            limSupRCL=dis_min+alpha*(dis_max-dis_min);
            //Se cuenta cuantos distancias se encuentran en éste límite RCL
            int cant_RLC=0; 
            for(int k=0;k<tam_recorrido;k++)
             { if  ( (v_distancias[k]>=limInfRCL)&&(v_distancias[k]<=limSupRCL))
               { cant_RLC++;
                } 
             }   
            //Del conjunto de valores que satisfacen, selecciona una al azar, da el numero de ocurrencia
            int pos_candidata = (int) (Math.random() * cant_RLC)+1;
            
            int contador_validos=0; 
            for(int k=0;k<tam_recorrido;k++) //k contiene la posicion exacta del objeto a extraer
             { if  ( (v_distancias[k]>=limInfRCL)&&(v_distancias[k]<=limSupRCL))
               { contador_validos++;
                } 
              if (contador_validos==pos_candidata)
              {  //Se ubico al objeto que pasa a la lista elite
                 solucion_elite.add(copia_coord_posicion.get(k)); 
                 copia_coord_posicion.remove(k);
               }  
             }   
            tam_recorrido=copia_coord_posicion.size();
           }  
         
         
         
        //*3 Fase de Búsqueda ó  Mejora Grasp-- > a elegir 2opt con best improvement ó first improment
        
         if (cont==0)
         {  v_distancia_minima= v_dist;
         }
         else
         {  if (v_dist<v_distancia_minima)
            { v_distancia_minima=v_dist; 
             } 
         }
        
       } 

       //Se escribe la distancia mínima obtenida para el archivo de carga ".\\src\\archivos\\coordinates"+numfile+".txt"
       //en el archivo de salida:  arch_salida
       vwrite.write( numfile+  " | "+ v_distancia_minima);
       vwrite.newLine();
    
    }
      pwrite.close();
      vwrite.close();
    

    
    }
    
}
