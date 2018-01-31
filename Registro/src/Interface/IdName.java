/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

/**
 *
 * @author DiegoCG
 */
public class IdName
{
    public long id;
    public String name;

    public IdName(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name; //To change body of generated methods, choose Tools | Templates.
    }
}
