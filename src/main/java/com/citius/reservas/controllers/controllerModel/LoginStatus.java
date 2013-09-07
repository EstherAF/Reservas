/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.controllerModel;

/**
 *
 * @author Esther Álvarez Feijoo
 */
  public class LoginStatus {
 
    private final boolean loggedIn;
    private final String uniqueName;
    private final String role;
 
    public LoginStatus(boolean loggedIn, String uniqueName, String role) {
      this.loggedIn = loggedIn;
      this.uniqueName = uniqueName;
      this.role = role;
    }
 
    public boolean isLoggedIn() {
      return loggedIn;
    }
 
    public String getUniqueName() {
      return uniqueName;
    }

    public String getRole() {
        return role;
    }
    
    public Boolean isAdmin(){
        return role=="ROLE_TIC";
    }
  }