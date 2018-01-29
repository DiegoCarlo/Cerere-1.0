/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Base;

/**
 *
 * @author DiegoCG
 */
public enum UnitOfMeasure
{
    KILOGRAMMO ("Kg"),
    QUANTITY ("n.")
    ;

    private final String text;
    
    private UnitOfMeasure(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}
