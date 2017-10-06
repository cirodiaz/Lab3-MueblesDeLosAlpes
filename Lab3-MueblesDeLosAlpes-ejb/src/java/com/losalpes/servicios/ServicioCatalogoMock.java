/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Mueble;
import com.losalpes.excepciones.OperacionInvalidaException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;

/**
 *
 * @author ca.diazr1
 */
@Stateful
public class ServicioCatalogoMock implements IServicioCatalogoMockLocal, IServicioCatalogoMockRemote{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    private IServicioPersistenciaMockLocal persistencia;
    
    /**
     * Lista con los muebles del carrito
     */
    private ArrayList<Mueble> inventario;
    
    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------
    
    /**
     * Constructor sin argumentos de la clase
     */
    public ServicioCatalogoMock()
    {
        inventario = new ArrayList<Mueble>();
        persistencia=new ServicioPersistenciaMock();
    }
    
    @Override
    public void agregarMueble(Mueble mueble) throws OperacionInvalidaException{
        Mueble m;
        m = (Mueble)persistencia.findById(Mueble.class, mueble.getReferencia());
        if(m != null){
            
                throw new OperacionInvalidaException("El mueble de referencia '" + mueble.getReferencia()+ "' ya ha sido registrado en el sistema");
            
            
        } else {           
                persistencia.create(mueble);            
        }
    }

    @Override
    public void eliminarMueble(long id) throws OperacionInvalidaException{ 
        Mueble m;
        m = (Mueble) persistencia.findById(Mueble.class, id);
        if(m != null){      
               persistencia.delete(m);              
        }
                  
    }

    @Override
    public List<Mueble> darMuebles() {
        inventario = (ArrayList<Mueble>) persistencia.findAll(Mueble.class);
        
        return inventario;
    }

    @Override
    public void removerEjemplarMueble(long id) throws OperacionInvalidaException{
        
        Mueble muebleEditar;
        muebleEditar = (Mueble)persistencia.findById(Mueble.class, id);
        if(muebleEditar.getCantidad()-1 >=0)
        muebleEditar.setCantidad(muebleEditar.getCantidad()-1);
        else 
            throw new OperacionInvalidaException("El mueble de referencia '" + muebleEditar.getReferencia()+ "' no tiene unidades");
        
        persistencia.update(muebleEditar);
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
