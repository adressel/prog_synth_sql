
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A very specific dictionary class that provides both, a mapping from <code>String</code>
 * to <code>Long</code> and from <code>Long</code> to <code>String</code>. The 
 * <code>String</code> and <code>Long</code> values can both be seen and used as keys as
 * they must be unique.
 * The dictionary accounts for the uniqueness of the keys by itself.
 *
 * 
 * @author  Andreas Dressel
 */
public class VariableDictionary
{
    
    // ***** MEMBER VARIABLES *****
    private static VariableDictionary _instance = null;
    private long count;
    private final Map<String, Long> variables_string_long;
    private final Map<Long, String> variables_long_string;
    
    
    /**
     * Constructs a new <code>VariableDictionary</code>. The constructor is private; the 
     * singleton instance of the dictionary can be accessed by the static method 
     * <code>getInstance()</code>.
     * 
     */
    private VariableDictionary() 
    {
        this.count = 0;
        this.variables_string_long = new HashMap<String, Long>();
        this.variables_long_string = new HashMap<Long, String>();
    }
    
    
    /**
     * Gets the singleton instance of the <code>VariableDictionary</code> and returns it.
     * 
     * @return  the singleton instance of the <code>VariableDictionary</code>.
     */
    public static synchronized VariableDictionary getInstance() 
    {
        if( _instance == null ) 
        {
            _instance = new VariableDictionary();
        }
        return _instance;
    }
    
    
    /**
     * Gets the next variable symbol that has not been used yet, and returns it.
     * 
     * @return  the net variable symbol that has not yet been assigned to another
     *          <code>String</code> value.
     */
    private long getNextVariableSymbol() 
    {
        count += 1;
        return count;
    }
    
    
    // *************************************************************************
    // ****** provide equivalent methods for keys of type Long, if needed ******
    
    
    /**
     * Checks whether the dictionary contains the provided key. If so, the method
     * returns true, otherwise false.
     * 
     * @param   key the key to look for in the dictionary.
     * @return  true, if the dictionary contains the provided key, false otherwise.
     */
    public boolean containsKey( String key ) 
    {
        return this.variables_string_long.containsKey( key );
    }

    
    /**
     * Gets the <code>Long</code> value that is associated with a provided key
     * and returns it.
     * 
     * @param   key the key corresponding to the searched value.
     * @return  the <code>Long</code> value associated with the provided key, or 
     *          null, if the key does not exist in the dictionary.
     */
    public Long get( String key ) 
    {
        return this.variables_string_long.get( key );
    }
    
    
    /**
     * Gets the number of variables in the dictionary and returns it.
     * 
     * @return  the number of variables in the dictionary, zero if there are not any.
     */
    public long getVariableCount()
    {
        return this.count;
    }
    
    /**
     * Gets the symbol for a variable name and returns it. The method will either
     * return the symbol that has been previously assigned to this variable name, 
     * or calculate a new, unused symbol.
     * This method is used to add elements to the dictionary.
     * 
     * @param   variable_name the variable name to get the symbol for.
     * @return  the symbol associated with the provided variable name.
     */
    public long getSymbolForVariable( String variable_name )
    {
        if( this.variables_string_long.keySet().contains( variable_name ) ) 
        {
            return this.variables_string_long.get( variable_name );
        } else 
        {
            long symbol = getNextVariableSymbol();
            this.variables_string_long.put( variable_name, symbol );
            this.variables_long_string.put( symbol, variable_name );
            return symbol;
        }
    }
    
    
    // *************************************************************************
    
    
    /**
     * Gets the <code>String</code> value associated with a provided key and
     * returns it.
     * 
     * @param   symbol the symbol that represents the searched <code>String</code> 
     *          in the dictionary.
     * @return  the variable name corresponding the the provided symbol, or an
     *          empty <code>String</code>, if the symbol does not exist.
     */
    public String get( long symbol )
    {
        return this.variables_long_string.get( symbol ) != null ? this.variables_long_string.get( symbol ) : "";
    }
    
    
    /**
     * Gets a representation of the dictionary as a <code>Map</code> and returns it.
     * 
     * @return  a representation of the dictionary as a <code>Map</code>.
     */
    public Map<Long, String> getDictionary()
    {
        return this.variables_long_string;
    }
    
    
    /**
     * Writes the <code>VariableDictionary</code> to a file. the form will be as follows:
     * <variable_name_1> : <variable_symbol_1>
     * <variable_name_2> : <variable_symbol_2>
     * ...
     * 
     * 
     * @param file_name the file name that the dictionary is written to.
     */
    public void writeToFile( String file_name )
    {
        BufferedWriter writer = null;
        
        try 
        {
            writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream( new File( file_name ) ) ) );
            
            
            // sort elements prior to writing them to file
            Object[] keys = variables_string_long.keySet().toArray();
            Arrays.sort( keys, new Comparator<Object>() 
            {

                @Override
                public int compare( Object o1, Object o2 )
                {
                    return o1.toString().compareTo( o2.toString() );
                }
            });
            
            
            for( Object key : keys ) 
            {
                
                writer.write( key.toString() + " : " + variables_string_long.get( key.toString() ) );
                writer.newLine();
            }
            writer.flush();
            
            
        } catch( FileNotFoundException ex ) 
        {
            ex.printStackTrace();
        } catch( IOException ex ) 
        {
            ex.printStackTrace();
        } finally 
        {
            
            if( writer != null ) 
            {
                try 
                {
                    writer.close();
                } catch( IOException ex ) 
                {
                    ex.printStackTrace();
                }
            }
        } 
    }
}
