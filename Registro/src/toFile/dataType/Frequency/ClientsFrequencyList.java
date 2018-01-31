/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Frequency;

import java.util.ArrayList;
import registro.Settings;
import toFile.dataType.Base.Pesata;
import toFile.dataType.Ordinals.PesateArray;

/**
 *
 * @author DiegoCG
 */
public class ClientsFrequencyList extends ArrayList<ProductsFrequencyList>
{

    public ClientsFrequencyList()
    {
        super();
    }

    public ProductsFrequencyList get(long idCliente)
    {
        for(ProductsFrequencyList l: this)
        {
            if(l.idCliente == idCliente)
            {
                return l;
            }
        }
        return null;
    }
    
    public String toFile()
    {
        String string = "";
        for(ProductsFrequencyList p: this)
        {
            string += p.toFile() + Settings.SPLITTER_ARRAY;
        }
        return string;
    }
    public static ClientsFrequencyList fromFile(String string)
    {
        if(!string.isEmpty())
        {
            String[] split = string.split(Settings.SPLITTER_ARRAY);

            try
            {
                ClientsFrequencyList c = new ClientsFrequencyList();
                for(String a: split)
                {
                    ProductsFrequencyList p = ProductsFrequencyList.fromFile(a);
                    c.add(p);
                }
                return c;
            }
            catch(ArrayIndexOutOfBoundsException a)
            {
                a.printStackTrace();
               
            }
        }
        return new ClientsFrequencyList();
    }

    public void updateFrequency(long idCliente, PesateArray listaPesate)
    {
        boolean listaClienteTrovata = false;
        for(ProductsFrequencyList lista: this)
        {
            // cerco la lista del cliente
            if(lista.idCliente == idCliente)
            {
                // ho la lista del cliente
                listaClienteTrovata = true;
                for(Pesata p: listaPesate)
                {
                    lista.add(p.idProdotto);
                }
            }
        }
        if(!listaClienteTrovata)
        {
            ProductsFrequencyList lista = new ProductsFrequencyList(idCliente);
            for(Pesata p: listaPesate)
            {

                lista.add(p.idProdotto);
            }
            add(lista);
        }
    }
}
