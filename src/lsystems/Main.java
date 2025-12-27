package lsystems;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.math.MathUtils;
import br.com.davidbuzatto.jsge.turtle.Turtle;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * L-Systems examples
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Main extends EngineFrame {
    
    private static final int STEP_INCREMENT = 6;
    
    private Turtle turtle;
    private int steps = 1;
    
    public Main() {
        super( 800, 450, "L-Systems", 60, true );
    }
    
    @Override
    public void create() {
        
        turtle = new Turtle();
        
        //buildFractalBinaryTree( 7, turtle, getScreenWidth() / 2, getScreenHeight() - 20, -90, 3 );
        //buildCantorSet( 7, turtle, 10, getScreenHeight() / 2, 0, 3 );
        //buildKochCurve( 5, turtle, 10, getScreenHeight() - 20, 0, 3 );
        //buildHilbertCurve( 6, turtle, getScreenWidth() - 10, getScreenHeight() - 10, -90, 3 );
        //buildPeanoCurve( 4, turtle, 10, getScreenHeight() - 10, -90, 3 );
        //buildSierpinskiTriangle( 6, turtle, getScreenWidth() - 180, getScreenHeight() - 20, -180, 7 );
        //buildSierpinskiArrowCurve( 8, turtle, 10, getScreenHeight() - 20, 0, 2 );
        //buildDragonCurve( 14, turtle, getScreenWidth() / 2 - 150, getScreenHeight() / 2 + 60, 90, 3 );
        //buildFractalPlant( 6, turtle, 50, getScreenHeight() - 10, -90, 3 );
        //buildFractalPlant2( 6, turtle, 50, getScreenHeight() - 10, -90, 3 );
        //buildPenrosePattern( 2, turtle, getScreenWidth() / 2, getScreenHeight() / 2, -90, 15 * ((double)6/2) );
        
    }

    @Override
    public void update( double delta ) {
        
        if ( isKeyPressed( KEY_ONE ) ) {
            buildFractalBinaryTree( 7, turtle, getScreenWidth() / 2, getScreenHeight() - 20, -90, 3 );
        } else if ( isKeyPressed( KEY_TWO ) ) {
            buildCantorSet( 7, turtle, 10, getScreenHeight() / 2, 0, 3 );
        } else if ( isKeyPressed( KEY_THREE ) ) {
            buildKochCurve( 4, turtle, 10, getScreenHeight() - 20, 0, 5 );
        } else if ( isKeyPressed( KEY_FOUR ) ) {
            buildHilbertCurve( 6, turtle, getScreenWidth() - 10, getScreenHeight() - 10, -90, 3 );
        } else if ( isKeyPressed( KEY_FIVE ) ) {
            buildPeanoCurve( 4, turtle, 10, getScreenHeight() - 10, -90, 3 );
        } else if ( isKeyPressed( KEY_SIX ) ) {
            buildSierpinskiTriangle( 6, turtle, getScreenWidth() - 180, getScreenHeight() - 20, -180, 7 );
        } else if ( isKeyPressed( KEY_SEVEN ) ) {
            buildSierpinskiArrowCurve( 8, turtle, 10, getScreenHeight() - 20, 0, 2 );
        } else if ( isKeyPressed( KEY_EIGHT ) ) {
            buildDragonCurve( 14, turtle, getScreenWidth() / 2 - 150, getScreenHeight() / 2 + 60, 90, 3 );
        } else if ( isKeyPressed( KEY_NINE ) ) {
            buildFractalPlant( 6, turtle, 50, getScreenHeight() - 10, -90, 3 );
        } else if ( isKeyPressed( KEY_ZERO ) ) {
            buildFractalPlant2( 6, turtle, 50, getScreenHeight() - 10, -90, 3 );
        } else if ( isKeyPressed( KEY_MINUS ) ) {
            int n = 4;
            double maxN = 6;
            buildPenrosePattern( n, turtle, getScreenWidth() / 2, getScreenHeight() / 2, -90, 15 * (maxN/n) );
        }
        
        if ( getKeyPressed() != KEY_NULL ) {
            steps = 1;
        }
        
        steps = MathUtils.clamp( steps + STEP_INCREMENT, 1, turtle.getFrameCount() );
        
    }
    
    @Override
    public void draw() {
        clearBackground( WHITE );
        turtle.draw( steps, this );
        //turtle.draw( this );
    }
    
    private void buildFractalBinaryTree( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        double angle = 45;
        
        String axiom = "0";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( '0', "1[0]0" );
        rules.put( '1', "11" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( '0', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '1', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '[', t -> {
            t.save();
            t.rotate( -angle );
        });
        actions.put( ']', t -> {
            t.restoreNotPurge();
            t.rotate( angle );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildCantorSet( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        
        String axiom = "A";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'A', "ABA" );
        rules.put( 'B', "BBB" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'A', t -> {
            t.lowerBrush();
            t.moveForward( drawLength );
        });
        actions.put( 'B', t -> {
            t.raiseBrush();
            t.moveForward( drawLength );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildKochCurve( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        double angle = 90;
        
        String axiom = "F";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'F', "F+F-F-F+F" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'F', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '+', t -> {
            t.rotate( -angle );
        });
        actions.put( '-', t -> {
            t.rotate( angle );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildHilbertCurve( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        double angle = 90;
        
        String axiom = "A";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'A', "-BF+AFA+FB-" );
        rules.put( 'B', "+AF-BFB-FA+" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'F', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '+', t -> {
            t.rotate( angle );
        });
        actions.put( '-', t -> {
            t.rotate( -angle );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildPeanoCurve( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        double angle = 90;
        
        String axiom = "X";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'X', "XFYFX+F+YFXFY-F-XFYFX" );
        rules.put( 'Y', "YFXFY-F-XFYFX+F+YFXFY" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'F', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '+', t -> {
            t.rotate( angle );
        });
        actions.put( '-', t -> {
            t.rotate( -angle );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildSierpinskiTriangle( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        double angle = 120;
        
        String axiom = "F-G-G";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'F', "F-G+F+G-F" );
        rules.put( 'G', "GG" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'F', t -> {
            t.moveForward( drawLength );
        });
        actions.put( 'G', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '+', t -> {
            t.rotate( -angle );
        });
        actions.put( '-', t -> {
            t.rotate( angle );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildSierpinskiArrowCurve( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        double angle = 60;
        
        String axiom = "A";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'A', "B-A-B" );
        rules.put( 'B', "A+B+A" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'A', t -> {
            t.moveForward( drawLength );
        });
        actions.put( 'B', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '+', t -> {
            t.rotate( -angle );
        });
        actions.put( '-', t -> {
            t.rotate( angle );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildDragonCurve( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {
        
        turtle.reset( x, y, startingAngle );
        double angle = 90;
        
        String axiom = "F";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'F', "F+G" );
        rules.put( 'G', "F-G" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'F', t -> {
            t.moveForward( drawLength );
        });
        actions.put( 'G', t -> {
            t.moveForward( drawLength );
        });
        actions.put( '+', t -> {
            t.rotate( angle );
        });
        actions.put( '-', t -> {
            t.rotate( -angle );
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildFractalPlant( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {

        turtle.reset( x, y, startingAngle );
        double angle = 25;
        
        String axiom = "-X";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'X', "F+[[X]-X]-F[-FX]+X" );
        rules.put( 'F', "FF" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'F', t -> {
            t.moveForward( drawLength );
        });
        actions.put( 'X', t -> {
            // do nothing
        });
        actions.put( '+', t -> {
            t.rotate( -angle );
        });
        actions.put( '-', t -> {
            t.rotate( angle );
        });
        actions.put( '[', t -> {
            t.save();
        });
        actions.put( ']', t -> {
            t.restoreNotPurge();
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildFractalPlant2( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {

        turtle.reset( x, y, startingAngle );
        double angle = 25;
        
        String axiom = "-X";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'X', "F[+X][-X]FX" );
        rules.put( 'F', "FF" );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        actions.put( 'F', t -> {
            t.moveForward( drawLength );
        });
        actions.put( 'X', t -> {
            // do nothing
        });
        actions.put( '+', t -> {
            t.rotate( -angle );
        });
        actions.put( '-', t -> {
            t.rotate( angle );
        });
        actions.put( '[', t -> {
            t.save();
        });
        actions.put( ']', t -> {
            t.restoreNotPurge();
        });
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    private void buildPenrosePattern( int n, Turtle turtle, double x, double y, double startingAngle, double drawLength ) {

        turtle.reset( x, y, startingAngle );
        double angle = 36;
        
        String axiom = "[X]++[X]++[X]++[X]++[X]";
        
        Map<Character, String> rules = new HashMap<>();
        rules.put( 'W', "YF++ZF4-XF[-YF4-WF]++" );
        rules.put( 'X', "+YF--ZF[3-WF--XF]+" );
        rules.put( 'Y', "-WF++XF[+++YF++ZF]-" );
        rules.put( 'Z', "--YF++++WF[+ZF++++XF]--XF" );
        rules.put( 'F', "" );
        
        final Map<String, Integer> values = new HashMap<>();
        values.put( "repeats", 1 );
        
        Map<Character, Consumer<Turtle>> actions = new HashMap<>();
        
        actions.put( 'F', t -> {
            for ( int i = 0; i < values.get( "repeats" ); i++ ) {
                t.setBrushPaint( ColorUtils.fade( BLACK, 0.2 ) );
                t.moveForward( drawLength );
            }
            values.put( "repeats", 1 );
        });
        
        actions.put( '+', t -> {
            for ( int i = 0; i < values.get( "repeats" ); i++ ) {
                t.rotate( angle );
            }
            values.put( "repeats", 1 );
        });
        
        actions.put( '-', t -> {
            for ( int i = 0; i < values.get( "repeats" ); i++ ) {
                t.rotate( -angle );
            }
            values.put( "repeats", 1 );
        });
        
        actions.put( '[', t -> {
            t.save();
        });
        
        actions.put( ']', t -> {
            t.restoreNotPurge();
        });
        
        for ( char c = '0'; c <= '9'; c++ ) {
            final int v = (int) c - '0';
            actions.put( c, t -> {
                values.put( "repeats", v );
            });
        }
        
        LSystem system = new LSystem( axiom, rules, actions );
        system.apply( n, turtle );
        
    }
    
    public static void main( String[] args ) {
        new Main();
    }
    
}
