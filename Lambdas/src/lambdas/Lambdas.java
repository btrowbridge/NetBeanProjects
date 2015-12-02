/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambdas;

/**
 *
 * @author Bradley Trowbridge
 */
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class Lambdas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //a)
        int[] values = {8,4,5,6,4,5,6,5,4,5,9};
        
        
        
        IntStream.of(values).forEach(value -> System.out.printf("%d ", value));
        System.out.println();

        //b)
        String[] strings = {"Hello","this","is", "a","list","of","strings"};
        
        
        System.out.println("String to uppercase: " +
            Arrays.stream(strings)
                   .map(String::toUpperCase)
                   .collect(Collectors.toList()));
        
        //c)
        Runnable r = () -> System.out.println("Welcome to lambdas!");
        r.run();
        
        
        //d)
        System.out.println( "Square roots: " +
            Arrays.stream(values)
                    .asDoubleStream()
                    .map(Math::sqrt)
                    .boxed()
                    .collect(Collectors.toList()));
                    
                    
             
        
        //e)
        
        IntStream.of(values)
                .forEach(x -> System.out.print(x*x*x + ", "));
        
                
                
    }
    
}
