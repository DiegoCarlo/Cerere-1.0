/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals;

/**
 *
 * @author DiegoCG
 */
public enum Order
{
    DEFAULT ("Default", 0),
    ALPHABETIC ("Alfabetico", 1),
    CARDINAL ("Cardinale", 2),
    HOUR ("Orario", 3),
    FREQUENCY ("Frequenza", 4);

    private final String text;
    private final int val;
    
    private Order(final String text, int val) {
        this.text = text;
        this.val = val;
    }
    
    @Override
    public String toString() {
        return text;
    }
    public int get()
    {
        return val;
    }
}
