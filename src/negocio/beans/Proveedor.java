/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.beans;

/**
 *
 * @author bryan
 */
public class Proveedor {
    private int codigo;
    private String nit;
    private String razon_social;
    private String direccion;
    private int telefono;
    private String pagina_web;
    private String email;
    private String contacto_principal;

    public Proveedor() {
    }

    public Proveedor(int codigo, String nit, String razon_social, String direccion, int telefono, String pagina_web, String email, String contacto_principal) {
        this.codigo = codigo;
        this.nit = nit;
        this.razon_social = razon_social;
        this.direccion = direccion;
        this.telefono = telefono;
        this.pagina_web = pagina_web;
        this.email = email;
        this.contacto_principal = contacto_principal;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getPagina_web() {
        return pagina_web;
    }

    public void setPagina_web(String pagina_web) {
        this.pagina_web = pagina_web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto_principal() {
        return contacto_principal;
    }

    public void setContacto_principal(String contacto_principal) {
        this.contacto_principal = contacto_principal;
    }
    
    
}
