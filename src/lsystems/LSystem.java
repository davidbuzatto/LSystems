package lsystems;

import br.com.davidbuzatto.jsge.turtle.Turtle;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Lindenmayer System.
 * 
 * @author Prof. Dr. David Buzatto
 */
public record LSystem(
    String axiom,
    Map<Character, String> rules,
    Map<Character, Consumer<Turtle>> actions ) {
    
    public void apply( int n, Turtle turtle ) {
        
        String production = axiom;
        
        for ( int i = 0; i < n; i++ ) {
            StringBuilder newProduction = new StringBuilder();
            for ( char c : production.toCharArray() ) {
                if ( rules.containsKey( c ) ) {
                    newProduction.append( rules.get( c ) );
                } else {
                    newProduction.append( c );
                }
            }
            production = newProduction.toString();
        }
        
        for ( char c : production.toCharArray() ) {
            // apply just found actions
            if ( actions.containsKey( c ) ) {
                actions.get( c ).accept( turtle );
            }
        }
        
    }
    
}
