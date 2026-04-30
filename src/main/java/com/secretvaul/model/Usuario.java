package com.secretvaul.model;

/**
 * El usuario maestro
 */
public class Usuario {
    private int idUsuario;
    private String username;
    private String passwordHash;
    private String salt;

    public Usuario() {

    }
    public Usuario(int idUsuario, String username, String passwordHash) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getSalt(){
        return salt;
    }
    public void setSalt(String s){
        this.salt = s;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}