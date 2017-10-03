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
public class tools 
{

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

    private static boolean estacontenido(int x, int y, ArrayList<coordenadas> desplazamiento) 
    {  int tam=0; 
       tam=desplazamiento.size();
       boolean esta=false; 
       for(int i=0; i<tam; i++)
       {  if ( (desplazamiento.get(i).getX()==x)&& (desplazamiento.get(i).getY()==y) )
          { esta=true;  
           }
        } 
      return esta; 
    }

    private static void copia(ArrayList<coordenadas> desplazamiento, ArrayList<coordenadas> auxiliar) {
       coordenadas obj= new coordenadas(); 
       int n=0; 
       for(int i=0; i<desplazamiento.size();i++)
        { obj.setX( desplazamiento.get(i).getX());
          obj.setY( desplazamiento.get(i).getY());
          auxiliar.add(obj);
        }  
       
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
  //el carrito debe partir de una posicion con valor 1
   public static int distanciaminima(int x1,int y1, int x2, int y2,int[][] mapa, int nfil,int ncol, ArrayList<coordenadas> desplazamiento  )
   {  coordenadas obj_coor= new coordenadas();
      ArrayList<coordenadas> auxiliar= new ArrayList<coordenadas>();     
      int v_distancia=0;
      int ocurr=0; 
      int distmin=0; 
      int a=0;
      int b=0; 
      int val_dis=0;
      if (  ( (x1==x2) && (Math.abs(y1-y2)==1) ) ||  ( (y1==y2) && (Math.abs(x1-x2)==1) )  )
       {   
           if (!estacontenido(x1,y1,desplazamiento))
            { obj_coor.setX(x1);
              obj_coor.setY(y1);
              desplazamiento.add(obj_coor);                 
            }
           v_distancia= 1;  
       }   
       else
       {//recorre todas las direcciones posibles al eje x y al eje y
        for (int i=-1; i<2;i++) // i:desplamiento en el eje x
        {
         for (int j=-1;j<2;j++) //j: desplazamiento en el eje y
          {//solo desplazamientos válidos
            if ( ((i==-1)&&(j==0)) ||  ((i==0)&&(j==-1)) || ((i==0)&&(j==1)) || ((i==1)&&(j==0))  ) 
            { //Si la nueva posición está dentro del mapa
               if ( ( x1+i>=0) && (x1+i<=nfil-1) &&(y1+j>=0) && (y1+j<=ncol-1 )    )
              { //la siguiente posicion tiene que ser distinta de rack
                if( (mapa[x1+i][y1+j]!=1 ) && (!estacontenido(x1+i,y1+j,desplazamiento) ) )
                    //&&  ( ( (x1+i>=x1) && (x1+i<=x2)  ) ||( (y1+j>=y1) && (y1+j<=y2))  )  )
                { //agregas el primer elemento que vendría a ser el minimo
                    copia(desplazamiento,auxiliar);
                    obj_coor.setX(x1+i);
                    obj_coor.setY(y1+j);
                    auxiliar.add(obj_coor);
                    val_dis=distanciaminima(x1+i,y1+j,x2,y2,mapa,nfil,ncol,auxiliar);
                  if (ocurr==0) 
                  {  distmin= val_dis;   
                     a=x1+i;
                     b=y1+j;
                  }
                  else
                  { if (val_dis<  distmin)
                    {  distmin= val_dis; 
                       a=x1+i;
                       b=y1+j;
                     }   
                  }
                 ocurr++; 
                } 
              }   
            } 
          }    
        }   
        obj_coor.setX(a);
        obj_coor.setY(b);
        desplazamiento.add(obj_coor);
        v_distancia=distmin+1;
       }

       return v_distancia;
   } 
  
   //cálculo de distancia euclideana entre dos puntos
   public static float dist_euclideana(int x1, int y1, int x2, int y2)
   { 
       float dist=(float) 0.0; 
   
       dist= (float) Math.sqrt(Math.pow(x1-x2, 2)+ Math.pow(y1-y2, 2)); 
       
       return dist;  
   
     }      
   
   //obtencion de un vector que devuelva las distancias desdd un punto inicial a cada una de las coordenadas 
   //del conjunto de puntos cargados
   public static float[] arreglo_distancias(int x, int y, ArrayList<coordenadas>arr_coordenadas )
   { int tam=0; 
       tam=arr_coordenadas.size();
        float[] v_distancias= new float[tam];
        
        for (int i=0; i<tam; i++)
        { v_distancias[i]=dist_euclideana(x,y,arr_coordenadas.get(i).getX(),arr_coordenadas.get(i).getY()  ); 
          }   
       return v_distancias; 
     }       
   
}
