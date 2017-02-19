package tests;


import matrices.DimensionesIncompatibles;
import matrices.Matriz;

public class Matrices {

    public static void main(String[] args) {
    	
    	Matriz m1 = new Matriz(3, 4, true);
        System.out.println(m1);
        Matriz m2 = new Matriz(3, 4, true);
        System.out.println(m2);
        
        try {
            System.out.println(Matriz.sumarDosMatrices(m1, m2));
        } catch (DimensionesIncompatibles ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Example of matrix multiplication:");
        
        Matriz m1prime = new Matriz(7, 12, true);
        System.out.println(m1prime);
        Matriz m2prime = new Matriz(12, 7, true);
        System.out.println(m2prime);
        
        try {
        	System.out.println(Matriz.multiplicarDosMatrices( m2prime,m1prime));
        } catch (DimensionesIncompatibles ex){
        	ex.printStackTrace();
        }
    }
    
}
