/*
 * actualizarClietne.java
 *
 * Created on 13 de octubre de 2008, 08:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar;

import java.sql.*;
import org.joda.time.*;

/**
 *
 * @author lozegarra
 */
public class baco {
    
    /** Creates a new instance of limpiarPresentaciones */
    public baco() {
    }
    
    public static void main(String[] args) {
        
        baco obj=new baco();
        obj.bacoSaldos();
        
        
    }
    
    public void bacoProceso(){
        System.out.println("PROCESO");
        Connection con=null;
        try {
            con=com.cofar.util.CofarConnection.getConnectionJsp();
            System.out.println("vamos!!! "+con);
            String codCapitulo="2,3";
            String codAlmacen="1";
            
            String sqlMat="select m.COD_MATERIAL, m.NOMBRE_MATERIAL from materiales m, grupos g, capitulos c " +
                    " where m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=c.COD_CAPITULO and c.COD_CAPITULO in (2)";
            Statement stMat=con.createStatement();
            ResultSet rsMat=stMat.executeQuery(sqlMat);
            while(rsMat.next()){
                int codMaterial=rsMat.getInt(1);
                String nombrematerial=rsMat.getString(2);
                
                String sqlID="select sum(id.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE id " +
                        " where i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN and id.COD_MATERIAL="+codMaterial+" " +
                        " and i.COD_ESTADO_INGRESO_ALMACEN=1 and i.COD_ALMACEN="+codAlmacen+"";
                Statement stID=con.createStatement();
                ResultSet rsID=stID.executeQuery(sqlID);
                double cantidadID=0;
                while(rsID.next()){
                    cantidadID=rsID.getDouble(1);
                }

                
                
                String sqlIDE="select sum(id.CANTIDAD_PARCIAL) from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE_ESTADO id " +
                        " where i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN and id.COD_MATERIAL="+codMaterial+" " +
                        " and i.COD_ESTADO_INGRESO_ALMACEN=1 and i.COD_ALMACEN="+codAlmacen+"";
                Statement stIDE=con.createStatement();
                ResultSet rsIDE=stIDE.executeQuery(sqlIDE);
                double cantidadIDE=0;
                while(rsIDE.next()){
                    cantidadIDE=rsIDE.getDouble(1);
                }
                
                if(cantidadID!=cantidadIDE){
                    System.out.println(nombrematerial+","+cantidadID+","+cantidadIDE);
                }
                
                
                
                
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    
    public void bacoSaldos(){
        System.out.println("PROCESO");
        Connection con=null;
        try {
            con=com.cofar.util.CofarConnection.getConnectionJsp();
            System.out.println("vamos!!! "+con);
            String codCapitulo="4,8";//"4,8,10,17,11,7,18,16,13";//"4,8,10,17,11,7,18,16,13";//4,8,10 2,3 12 22 4,7,10,17,18 0,2,3,4,7,8,9,10,18,22
            String codAlmacen="2";//8  2 1 1 4 13 12
            
            String sqlMat="select m.COD_MATERIAL, m.NOMBRE_MATERIAL from materiales m, grupos g, capitulos c " +
                    " where m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=c.COD_CAPITULO and c.COD_CAPITULO in ("+codCapitulo+")" +
                    " ";
            Statement stMat=con.createStatement();
            ResultSet rsMat=stMat.executeQuery(sqlMat);
            while(rsMat.next()){
                int codMaterial=rsMat.getInt(1);
                String nombrematerial=rsMat.getString(2);
                
                String sqlIng="select sum(id.CANT_TOTAL_INGRESO_FISICO) from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE id " +
                        " where i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN and id.COD_MATERIAL="+codMaterial+" and  " +
                        " i.COD_ESTADO_INGRESO_ALMACEN=1 and i.COD_ALMACEN="+codAlmacen+" and i.estado_sistema = 1";
                Statement stIng=con.createStatement();
                ResultSet rsIng=stIng.executeQuery(sqlIng);
                double cantidadIng=0;
                while(rsIng.next()){
                    cantidadIng=rsIng.getDouble(1);
                }
                
                
                String sqlSal="select sum(sd.CANTIDAD_SALIDA_ALMACEN) from SALIDAS_ALMACEN s, SALIDAS_ALMACEN_DETALLE sd " +
                        " where s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN and s.COD_ESTADO_SALIDA_ALMACEN=1 and  " +
                        " sd.COD_MATERIAL="+codMaterial+" and s.COD_ALMACEN="+codAlmacen+" and s.estado_sistema = 1";
                Statement stSal=con.createStatement();
                ResultSet rsSal=stSal.executeQuery(sqlSal);
                double cantidadSal=0;
                while(rsSal.next()){
                    cantidadSal=rsSal.getDouble(1);
                }
                
                double saldoKardex=0;
                saldoKardex=cantidadIng-cantidadSal;
                
                
                
                
                
                String sqlRest="select sum(id.CANTIDAD_RESTANTE) from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE_ESTADO id " +
                        " where i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN and i.COD_ESTADO_INGRESO_ALMACEN=1 and  " +
                        " id.COD_MATERIAL="+codMaterial+" and id.CANTIDAD_RESTANTE>0 and i.COD_ALMACEN="+codAlmacen+"";
                Statement stRest=con.createStatement();
                ResultSet rsRest=stRest.executeQuery(sqlRest);
                double cantidadRest=0;
                while(rsRest.next()){
                    cantidadRest=rsRest.getDouble(1);
                }
                
                
                double diferencia=0;
                diferencia=saldoKardex-cantidadRest;
                
                if((diferencia)>0.5 || (diferencia)<-0.5){
                    System.out.println(codMaterial+","+nombrematerial+","+saldoKardex+","+cantidadRest+","+diferencia);
                    
                    
                    /*System.out.println("entro a corregir");
                    diferencia=diferencia*(-1);
                    String sqlIngresos="select id.ETIQUETA, ID.COD_INGRESO_ALMACEN, id.COD_MATERIAL, id.CANTIDAD_RESTANTE " +
                            " from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE_ESTADO id " +
                            " where i.COD_INGRESO_ALMACEN = id.COD_INGRESO_ALMACEN and i.COD_ESTADO_INGRESO_ALMACEN = 1 and id.COD_MATERIAL = "+codMaterial+" " +
                            " and id.CANTIDAD_RESTANTE > 0.05 and i.COD_ALMACEN = "+codAlmacen+" order by i.COD_INGRESO_ALMACEN asc";
                    Statement stIngresos=con.createStatement();
                    ResultSet rsIngresos=stIngresos.executeQuery(sqlIngresos);
                    while(rsIngresos.next() && diferencia>0){
                        String codEtiqueta=rsIngresos.getString(1);
                        int codIngresoAlm=rsIngresos.getInt(2);
                        int codMaterialCorregir=rsIngresos.getInt(3);
                        double cantidadRestanteCorregir=rsIngresos.getDouble(4);
                        
                        
                        if(diferencia<cantidadRestanteCorregir){
                            
                            System.out.println("CORRIGE SALDO UNICO: "+codEtiqueta+" "+codIngresoAlm+" "+nombrematerial+" "+diferencia);
                            double cantidadCorregida=cantidadRestanteCorregir-diferencia;
                            String sqlUpd="update INGRESOS_ALMACEN_DETALLE_ESTADO set cantidad_restante="+cantidadCorregida+" where " +
                                    " etiqueta="+codEtiqueta+" and cod_ingreso_almacen="+codIngresoAlm+" and cod_material="+codMaterialCorregir+"";
                            System.out.println("SQL CORRIGE: "+sqlUpd);
                            Statement stUpd=con.createStatement();
                            stUpd.executeUpdate(sqlUpd);
                            
                            diferencia=0;
                        }else{
                            diferencia=diferencia-cantidadRestanteCorregir;
                            System.out.println("CORRIGE SALDO: "+codEtiqueta+" "+codIngresoAlm+" "+nombrematerial+" "+cantidadRestanteCorregir);
                            
                            String sqlUpd="update INGRESOS_ALMACEN_DETALLE_ESTADO set cantidad_restante=0 where " +
                                    " etiqueta="+codEtiqueta+" and cod_ingreso_almacen="+codIngresoAlm+" and cod_material="+codMaterialCorregir+"";
                            System.out.println("SQL CORRIGE: "+sqlUpd);
                            Statement stUpd=con.createStatement();
                            stUpd.executeUpdate(sqlUpd);
                        }
                        System.out.println("TERMINO CORREGIR");
                    }
                    */
                    
                    
                }
                
                
                
                
                
                
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void proceso2(){
        DateTime dt=new DateTime(2012,11,10,0,0,0,0);
        DateTime dt1=new DateTime(2012,10,01,0,0,0,0);
        Period pe=new Period(dt,dt1);
        System.out.println("dias:  "+pe.getDays());
        
    }
}
