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
import model.coord_grasp;
import model.coordenadas;

/**
 *
 * @author GZAVALA
 */
public class tools 
{

    public static float val_min_vector(float[] v_distancias, int tam_recorrido) {
      float valor=(float)0.0;
        valor=v_distancias[0];
       for(int i=0; i<tam_recorrido;i++)
       { if (v_distancias[i]<valor)
         { valor=v_distancias[i]; 
          } 
       } 
       return valor; 
    }

    public static float val_max_vector(float[] v_distancias, int tam_recorrido) {
      float valor=(float)0.0;
        valor=v_distancias[0];
       for(int i=0; i<tam_recorrido;i++)
       { if (v_distancias[i]>valor)
         { valor=v_distancias[i]; 
          } 
       } 
       return valor; 
    }

    public static float distanciaelite(int xinicial, int yinicial, ArrayList<coord_grasp> solucion_elite) {
       int tam=solucion_elite.size();
       float dist=(float)0.0;
       int a=xinicial; 
       int b=yinicial; 
       for (int i=0; i<tam;i++)
        {  dist=dist+ dist_euclideana(a,b,solucion_elite.get(i).getX(),solucion_elite.get(i).getY() );
           a=solucion_elite.get(i).getX();
           b=solucion_elite.get(i).getY();
         } 
       return dist; 
    
    }

    public static ArrayList<coord_grasp> dos_opt_primera_mejora(int xinicial, int yinicial, ArrayList<coord_grasp> solucion_elite, float v_dist) {
      int tam=solucion_elite.size();
      
      //variables creadas para almacenar los objetos que se intercambiaran 
      coord_grasp obj_elite_i= new coord_grasp();
      coord_grasp obj_elite_j= new coord_grasp();
      
      boolean mejora_opt=false; 
      float v_dist_mejorada=(float)0.0;
      
      // realiza una copia de la solución Elite que entro como parametro para trabajar sobre éste
      ArrayList<coord_grasp> copy_solucion_elite= new ArrayList<coord_grasp>();
      for (int i=0; i<tam;i++)
      { copy_solucion_elite.add(solucion_elite.get(i));
      }
      
      // se obtendrá un lazo de tamaño dos, intercambiando sus elementos para ver si así la distancia se 
      // optimiza teniendo menor valor
      for (int i=0; i<tam-1; i++)
      {
        for (int j=i+1;j<tam ;j++)
       {  
          obj_elite_i.setX(copy_solucion_elite.get(i).getX() );
          obj_elite_i.setY(copy_solucion_elite.get(i).getY());
          obj_elite_i.setPos(copy_solucion_elite.get(i).getPos());
          obj_elite_i.setInd_lrc(copy_solucion_elite.get(i).getInd_lrc());

          obj_elite_j.setX(copy_solucion_elite.get(j).getX() );
          obj_elite_j.setY(copy_solucion_elite.get(j).getY());
          obj_elite_j.setPos(copy_solucion_elite.get(j).getPos());
          obj_elite_j.setInd_lrc(copy_solucion_elite.get(j).getInd_lrc());
          
          copy_solucion_elite.get(i).setX( obj_elite_j.getX()) ;
          copy_solucion_elite.get(i).setY( obj_elite_j.getY());
          copy_solucion_elite.get(i).setPos(obj_elite_j.getPos());
          copy_solucion_elite.get(i).setInd_lrc(obj_elite_j.getInd_lrc());
          
          copy_solucion_elite.get(j).setX( obj_elite_i.getX()) ;
          copy_solucion_elite.get(j).setY( obj_elite_i.getY());
          copy_solucion_elite.get(j).setPos(obj_elite_i.getPos());
          copy_solucion_elite.get(j).setInd_lrc(obj_elite_i.getInd_lrc());

          v_dist_mejorada=distanciaelite(xinicial,yinicial,copy_solucion_elite);
          
          //si se mejora la distancia mínima intercambiando los arcos o nodos, entonces se sale del bucle 
          if (v_dist_mejorada<v_dist)
          {  mejora_opt=true; 
             break; 
            }
          else 
            //sino se tiene solucion mejorada se regresa deja la estructura inicial  
          {   
             while (copy_solucion_elite.size()>0)
             {  copy_solucion_elite.remove(0);
             }  
             for (int t=0; t<tam;t++)
              { copy_solucion_elite.add(solucion_elite.get(t));
             }              

          }   
        }   
       if (mejora_opt==true)
        { break;  
         }   
      }
      
      
      return copy_solucion_elite; 
    }

    public  static ArrayList<coord_grasp> copia_solucion_elite(ArrayList<coord_grasp> solucion_elite, int tam ) {
      ArrayList<coord_grasp> arr_sol_elite= new ArrayList<coord_grasp>(tam);
      coord_grasp obj_sol_elite= new coord_grasp();
      for(int i=0; i<tam; i++)
       { 
         arr_sol_elite.get(i).setX(solucion_elite.get(i).getX());
         arr_sol_elite.get(i).setY(solucion_elite.get(i).getY());
         arr_sol_elite.get(i).setPos(solucion_elite.get(i).getPos() );
         arr_sol_elite.get(i).setInd_lrc(solucion_elite.get(i).getInd_lrc() );
       }  
      return arr_sol_elite; 
    }

    public tools() {
    }
    
    
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

    private static void copia_desplazamiento(ArrayList<coordenadas> desplazamiento, ArrayList<coordenadas> auxiliar) {
       coordenadas obj= new coordenadas(); 
       int n=0; 
       for(int i=0; i<desplazamiento.size();i++)
        { obj.setX( desplazamiento.get(i).getX());
          obj.setY( desplazamiento.get(i).getY());
          auxiliar.add(obj);
        }  
       
    }

    public static ArrayList<coordenadas> copia_coordenadas_leidas(ArrayList<coordenadas> collect_coordenadas) {
      int tam=collect_coordenadas.size();
      ArrayList<coordenadas> arr_coordenadas= new ArrayList<coordenadas>(tam);
      int x;
      int y; 
      for (int i=0; i<tam; i++)
      {  x=collect_coordenadas.get(i).getX();
         y=collect_coordenadas.get(i).getY();
         coordenadas obj= new coordenadas(x,y);
         arr_coordenadas.add(obj);
       }   
       return arr_coordenadas;  
    }

    public static ArrayList<coord_grasp> copia_coord_posicion(ArrayList<coordenadas> collect_coordenadas) {
      int x; 
      int y;
      ArrayList<coord_grasp> arrg_coord_grasp= new ArrayList<coord_grasp>();
      int tam=collect_coordenadas.size(); 
       for (int i=0; i<tam;i++)
       { x=collect_coordenadas.get(i).getX();
         y=collect_coordenadas.get(i).getY();
         coord_grasp obj = new coord_grasp(x,y,i,0);
         arrg_coord_grasp.add(obj);
       }    
      return arrg_coord_grasp;  
    }

    public static int buscapocion_especial(ArrayList<coord_grasp> copia_coord_posicion, int v_posinicial) {
    int tam=0; 
    tam=copia_coord_posicion.size();
    int v_pos=0; 
     for(int i=0; i<tam;i++)
      {  if ( copia_coord_posicion.get(i).getPos()==v_posinicial )
          { v_pos=i; 
          }
      }   
     return v_pos;
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
                    copia_desplazamiento(desplazamiento,auxiliar);
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

//métodos a usarse con mayor precision   
   
   //cálculo de distancia euclideana entre dos puntos
  public static float dist_euclideana(int x1, int y1, int x2, int y2)
   { 
       float dist=(float) 0.0; 
   
       dist= (float) Math.pow((x1-x2)*(x1-x2) + (y1-y2)* (y1-y2), 0.5); 
       
       return dist;  
   
     }      
   
   //obtencion de un vector que devuelva las distancias desdd un punto inicial a cada una de las coordenadas 
   //del conjunto de puntos cargados
  public static float[] arreglo_distancias(int x, int y, ArrayList<coord_grasp>arr_coordenadas )
   { int tam=0; 
       tam=arr_coordenadas.size();
        float[] v_distancias= new float[tam];
        
        for (int i=0; i<tam; i++)
        { v_distancias[i]=dist_euclideana(x,y,arr_coordenadas.get(i).getX(),arr_coordenadas.get(i).getY()  ); 
          }   
       return v_distancias; 
     }       
   
  public static int posicion_minimo_elemento(float[] arr_distancias, int tam)
   { int pos=0; 
      float val_min=0;
      val_min=arr_distancias[0];
      
      for (int i=0; i<tam; i++)
      { if (arr_distancias[i]<val_min)
        { val_min=arr_distancias[i];
          pos=i; 
         }      
      }   
      return pos; 
      
    }     

  public static int posicion_maximo_elemento(float[] arr_distancias, int tam)
   { int pos=0; 
      float val_max=0;
      val_max=arr_distancias[0];
      
      for (int i=0; i<tam; i++)
      { if (arr_distancias[i]>val_max)
        { val_max=arr_distancias[i];
          pos=i; 
         }      
      }   
      return pos; 
      
    }     


  
}
