/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author DASISAQ
 */
public class ValidatorVencimientoMMYYYY implements Validator{
    private static final String regex="^(0[1-9]|1[0-2])[/](20[0-9]{2})$";
    private static int CANTIDAD_MESES_MINIMO_FECHA_VENCIMIENTO = 3;
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if(uic.getAttributes().get("disable") == null 
                || uic.getAttributes().get("disable").toString().equals("false"))
        {
            if(!Pattern.matches(regex, o.toString())){
                FacesMessage msg = new FacesMessage("La fecha registrada:"+o.toString(),
                                            "Fecha invalida, debe cumplir con el formato MES/AÑO");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
            else{
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                String mesActual = sdf.format(new Date());
                int cantidadMesesFecha = (12*Integer.valueOf(o.toString().split("/")[1])) + Integer.valueOf(o.toString().split("/")[0]);
                int cantidadMesesFechaActual = (12*Integer.valueOf(mesActual.split("/")[1])) + Integer.valueOf(mesActual.split("/")[0]);
                if(cantidadMesesFecha- cantidadMesesFechaActual <CANTIDAD_MESES_MINIMO_FECHA_VENCIMIENTO){
                    FacesMessage msg = new FacesMessage("La fecha registrada:"+o.toString(),
                                                    "La fecha de vencimiento debe tener al menos "+CANTIDAD_MESES_MINIMO_FECHA_VENCIMIENTO+" meses de vigencia");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }
               
        }
    }
    
    
}
