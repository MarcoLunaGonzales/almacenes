// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TiposSolicitudMantenimiento.java

package com.cofar.bean;


// Referenced classes of package com.cofar.bean:
//            AbstractBean, EstadoReferencial

public class TiposSolicitudMantenimiento extends AbstractBean
{

    public TiposSolicitudMantenimiento()
    {
        codTipoSolMantenimiento = "";
        nombreTipoSolMantenimiento = "";
        estadoReferencial = new EstadoReferencial();
    }

    public String getCodTipoSolMantenimiento()
    {
        return codTipoSolMantenimiento;
    }

    public void setCodTipoSolMantenimiento(String codTipoSolMantenimiento)
    {
        this.codTipoSolMantenimiento = codTipoSolMantenimiento;
    }

    public String getNombreTipoSolMantenimiento()
    {
        return nombreTipoSolMantenimiento;
    }

    public void setNombreTipoSolMantenimiento(String nombreTipoSolMantenimiento)
    {
        this.nombreTipoSolMantenimiento = nombreTipoSolMantenimiento;
    }

    public EstadoReferencial getEstadoReferencial()
    {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial)
    {
        this.estadoReferencial = estadoReferencial;
    }

    private String codTipoSolMantenimiento;
    private String nombreTipoSolMantenimiento;
    private EstadoReferencial estadoReferencial;
}
