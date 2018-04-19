

/*package com.cofar.web;


public abstract class ManagedBean {
    

    
    public  int numFilasPagina=10;
    public  int countBack=1;
    public  int countNext=getNumFilasPagina();
    public  int rowsTipoCliente=0;
    public  int countNext2=getNumFilasPagina();
    private int cantidadeliminar=0;
    
    
    public ManagedBean() {
    }
    
    public int getNumFilasPagina() {
        return numFilasPagina;
    }
    
    public void setNumFilasPagina(int numFilasPagina) {
        this.numFilasPagina = numFilasPagina;
    }
    
    public int getCountBack() {
        return countBack;
    }
    
    public void setCountBack(int countBack) {
        this.countBack = countBack;
    }
    
    public int getCountNext() {
        return countNext;
    }
    
    public void setCountNext(int countNext) {
        this.countNext = countNext;
    }
    public String back(){
        System.out.println("super");
        if(countNext==rowsTipoCliente)
            countNext=countNext2;
        countNext2=countNext2-numFilasPagina;
        countNext=countNext-numFilasPagina;
        countBack=countBack-numFilasPagina;
        return "";
    }
    public String next(){
        System.out.println("super");;
        countNext=countNext+numFilasPagina;
        if(countNext >= rowsTipoCliente){
            countNext=rowsTipoCliente;
        }
        countNext2=countNext2+numFilasPagina;
        countBack=countBack+numFilasPagina;
        return "";
    }
}*/






/*
 * ManagedBean.java
 *
 * Created on 27 de febrero de 2008, 13:42
 */

package com.cofar.web;

import com.cofar.message.MensajeTransaccion;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Wilmer Manzaneda Chavez
 * @company COFAR
 */
public abstract class ManagedBean {
    
    /** Creates a new instance of ManagedBean */
    
    public  int numFilasPagina=10;
    public  int countBack=1;
    public  int countNext=numFilasPagina;
    public  int rowsTipoCliente=0;
    public  int countNext2=getNumFilasPagina();
    private  int cantidadeliminar=0;
    private Boolean checked=new Boolean (false);
    
    public  int begin=1;
    public  int numrows=10;
    public int end=numrows;
    public  int cantidadfilas;
    
    //Control de transacciones
    public String mensaje = "";
    public boolean registradoCorrectamente = false;
    protected boolean transaccionExitosa= false;
    protected Logger LOGGER;
    
        
    
    
    public ManagedBean() {
    }
    
    public int getNumFilasPagina() {
        return numFilasPagina;
    }
    
    public void setNumFilasPagina(int numFilasPagina) {
        this.numFilasPagina = numFilasPagina;
    }
    
    public int getCountBack() {
        return countBack;
    }
    
    public void setCountBack(int countBack) {
        this.countBack = countBack;
    }
    
    public int getCountNext() {
        return countNext;
    }
    
    public void setCountNext(int countNext) {
        this.countNext = countNext;
    }
    public String back(){
        /*System.out.println("super");
        if(countNext==rowsTipoCliente)
            countNext=countNext2;
        countNext2=countNext2-numFilasPagina;
        countNext=countNext-numFilasPagina;
        countBack=countBack-numFilasPagina;
        */
        
        begin=begin-numrows;
        end=end-numrows;
        
        
        
        return "";
    }
    public String next(){
        /*System.out.println("super");;
        countNext=countNext+numFilasPagina;
        if(countNext >= rowsTipoCliente){
            countNext=rowsTipoCliente;
        }
        countNext2=countNext2+numFilasPagina;
        countBack=countBack+numFilasPagina;*/
        begin=begin+numrows;
        end=end+numrows;
        
        
        return "";
    }
    // <editor-fold defaultstate="collapsed" desc="mensajesTransaccion">
        public void mostrarMensajeTransaccionExitosa(String mensajeTransaccion)
        {
            MensajeTransaccion mensaje=(MensajeTransaccion)Util.getSessionBean("MensajeTransaccion");
            mensaje.setMensajeTransaccion(mensajeTransaccion);
            mensaje.setTransaccionExitosa(true);
            transaccionExitosa=true;
        }
        public void mostrarMensajeTransaccionFallida(String mensajeTransaccion)
        {
            MensajeTransaccion mensaje=(MensajeTransaccion)Util.getSessionBean("MensajeTransaccion");
            mensaje.setMensajeTransaccion(mensajeTransaccion);
            mensaje.setTransaccionExitosa(false);
            transaccionExitosa=false;
        }
    //</editor-fold>

    public int getCantidadeliminar() {
        return cantidadeliminar;
    }

    public void setCantidadeliminar(int cantidadeliminar) {
        this.cantidadeliminar = cantidadeliminar;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getNumrows() {
        return numrows;
    }

    public void setNumrows(int numrows) {
        this.numrows = numrows;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
   public void reset(){
        begin=1;
        end=10;
    }

    public int getCantidadfilas() {
        return cantidadfilas;
    }

    public void setCantidadfilas(int cantidadfilas) {
        this.cantidadfilas = cantidadfilas;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isRegistradoCorrectamente() {
        return registradoCorrectamente;
    }

    public void setRegistradoCorrectamente(boolean registradoCorrectamente) {
        this.registradoCorrectamente = registradoCorrectamente;
    }
    protected void cerrarConexion(Connection conexion)
    {
        try
        {
            if(conexion!=null&&!conexion.isClosed())
            {
                conexion.close();
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    protected void rollbackConexion(Connection conexion)
    {
        try{
            if(conexion!=null && !conexion.isClosed() && (!conexion.getAutoCommit())){
                conexion.rollback();
            }
        }
        catch(SQLException ex){
            LOGGER.warn("error", ex);
        }
    }

    public boolean isTransaccionExitosa() {
        return transaccionExitosa;
    }

    public void setTransaccionExitosa(boolean transaccionExitosa) {
        this.transaccionExitosa = transaccionExitosa;
    }
    
    

}

