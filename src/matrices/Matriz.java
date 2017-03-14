/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(10);
        }
    }

    public Matriz(int datos[][]){
        this.datos = datos;
    }

    public void set_value_at(int i, int j, int value){
    	this.datos[i][j] = value;
    }
    
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    } 

    public static Matriz matrizInversa(Matriz a) throws DimensionesIncompatibles {
        if(a.getDimension().height != (a.getDimension().width)) throw new DimensionesIncompatibles("La matriz debe ser cuadrada");

        
        Matriz transMatriz = new Matriz(a.getDimension().height, a.getDimension().width, false);
        
        if( determinante(a) != 0 ){
            double detInverso=1/determinante(a);
            Matriz adjMatriz=matrizAdjunta(a);
            transMatriz = matrizTranspuesta(adjMatriz);
            transMatriz = multiplicarMatriz(detInverso, transMatriz);
        }
        
        return transMatriz;
    }

    public static Matriz multiplicarMatriz(double n, Matriz a) {
        int i, j, dimensionA;
        dimensionA=a.getDimension().height;
        System.out.println("MultiPrincipio"+a.datos[0][0]);
        for(i=0;i<dimensionA;i++){
                for(j=0;j<dimensionA;j++){
                        System.out.println("I: "+i+" J: "+j+" dato1: "+a.datos[i][j]+" n: "+n);
                        a.datos[i][j]= (int) (a.datos[i][j]*n);
                }
        }
        System.out.println("Multi"+a);
        return a;
    }
 
    public static Matriz matrizAdjunta(Matriz a){
        return matrizCofactores(a);
    }
 
    public static Matriz matrizCofactores(Matriz a){
        int i, j, k, l, dimensionA;
        dimensionA=a.getDimension().height;
        Matriz nm = new Matriz(dimensionA, dimensionA, false);
        for(i=0;i<dimensionA;i++) {
            for(j=0;j<dimensionA;j++) {
                Matriz det= new Matriz(dimensionA-1, dimensionA-1, false);
                double detValor;
                for(k=0;k<dimensionA;k++) {
                    if(k!=i) {
                        for(l=0;l<dimensionA;l++) {
                            if(l!=j) {
                                int indice1=k<i ? k : k-1 ;
                                int indice2=l<j ? l : l-1 ;
                                det.set_value_at(indice1, indice2, a.datos[k][l]);
                                //det[indice1][indice2]=a.datos[k][l];
                            }
                        }
                    }
                }
                detValor=determinante(det);
                nm.datos[i][j]= (int)(detValor * (double)Math.pow(-1, i+j+2));
            }
        }
        return nm;
    }
 
    public static Matriz matrizTranspuesta(Matriz a){
        int i, j, dimensionA;
        dimensionA=a.getDimension().height;
        Matriz nuevam = new Matriz(dimensionA, dimensionA, false);
        for(i=0; i<dimensionA; i++){
            for(j=0; j<dimensionA; j++)
                nuevam.datos[i][j]=a.datos[j][i];
        }
        return nuevam;
    }
 
    public static double determinante(Matriz a){
        int i, j, k, dimensionA;
        double det;
        dimensionA=a.getDimension().height;
        if(dimensionA==2){
            det=(a.datos[0][0]*a.datos[1][1])-(a.datos[1][0]*a.datos[0][1]);
            return det;
        }
        double suma=0;
        for(i=0; i<dimensionA; i++){ 
        Matriz nm = new Matriz(dimensionA-1, dimensionA-1, false);
            for(j=0; j<dimensionA; j++){
                if(j!=i){
                    for(k=1; k<dimensionA; k++){
                        int indice=-1;
                        if(j<i)
                            indice=j;
                        else if(j>i)
                            indice=j-1;
                        nm.datos[indice][k-1]=a.datos[j][k];
                    }
                }
            }
            if(i%2==0)
                suma+=a.datos[i][0] * determinante(nm);
            else
                suma-=a.datos[i][0] * determinante(nm);
        }
        System.out.println("det1: "+suma);  
        return suma;
    }
    
    
    // Multiples A %*% B
    public static Matriz multiplicarDosMatrices(Matriz b, Matriz a) throws DimensionesIncompatibles {  

        if(b.getDimension().height != a.getDimension().width) throw new DimensionesIncompatibles("El número de columnas de A debe ser igual al número de filas de B ");
        int i, j, filasA, columnasB, columnasA;
        filasA = a.getDimension().height;
        columnasB = b.getDimension().width;
        columnasA = a.getDimension().width;
        
        
        Matriz matrizResultante = new Matriz(filasA, columnasB, false);
        
        for (i = 0; i < filasA; i++) {
            for (j = 0; j < columnasB; j++) {
                matrizResultante.datos[j][i] = 0;
                for (int k = 0; k < columnasA; k++) {

                    matrizResultante.datos[j][i] += a.datos[k][i] * b.datos[j][k];
                }
            }
        }
        return matrizResultante;
    }
    
    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
}