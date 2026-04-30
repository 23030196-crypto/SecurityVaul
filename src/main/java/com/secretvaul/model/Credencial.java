package com.secretvaul.model;

/**
 * Credencial almacenada en el baúl.
 */
public class Credencial {
    private int    idCredencial;
    private int    idUsuario;
    private String sitio;
    private String correo;
    private String passwordCifrado;
    private String notas;

    public Credencial() {

    }

    // Getters
    public int getIdCredencial() {
        return idCredencial;
    }
    public int getIdUsuario(){
        return idUsuario;
    }
    public String getSitio(){
        return sitio;
    }
    public String getCorreo(){
        return correo;
    }
    public String getPasswordCifrado(){
        return passwordCifrado;
    }
    public String getNotas(){
        return notas;
    }
    public void setIdCredencial(int id){
        this.idCredencial = id;
    }
    public void setIdUsuario(int id){
        this.idUsuario = id;
    }
    public void setSitio(String s){
        this.sitio = s;
    }
    public void setCorreo(String c) {
        this.correo = c;
    }
    public void setPasswordCifrado(String p){
        this.passwordCifrado = p;
    }
    public void setNotas(String n){
        this.notas = n;
    }

    /**
     * Patron builder
     */
    public static class Builder {
        private final Credencial credencial = new Credencial();
        public Builder idUsuario(int id){
            credencial.idUsuario= id;
            return this;
        }
        public Builder sitio(String s){
            credencial.sitio =s;
            return this;
        }
        public Builder correo(String c){
            credencial.correo =c;
            return this;
        }
        public Builder passwordCifrado(String p){
            credencial.passwordCifrado = p;
            return this;
        }
        public Builder notas(String n){
            credencial.notas = n;
            return this;
        }

        public Credencial build() {

            if (credencial.passwordCifrado == null || credencial.passwordCifrado.isBlank()){
                throw new IllegalStateException("Ingresa una contraseña no puede estar vacío");
            }

            if (credencial.correo == null || credencial.correo.isBlank()) {
                throw new IllegalStateException("Ingresa un correo.");
            }
            if (credencial.sitio == null || credencial.sitio.isBlank()) {
                throw new IllegalStateException("El sitio no puede estar vacío");
            }
            return credencial;
        }
    }
}