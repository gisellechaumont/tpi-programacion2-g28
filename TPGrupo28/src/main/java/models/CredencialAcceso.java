/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Daniela Bonetti
 */


public class CredencialAcceso extends Base {
   private String hashPassword;
   private String salt;
   private LocalDateTime ultimoCambio;
   private boolean requiereReset;

    public CredencialAcceso( long id, String hashPassword, LocalDateTime ultimoCambio, boolean requiereReset) {
        super(id, false);
        this.hashPassword = hashPassword;
        this.ultimoCambio = ultimoCambio;
        this.requiereReset = requiereReset;
    }

    public CredencialAcceso() {
        super ();
    }
    
    

    public String getHashPassword() {
        return hashPassword;
    }

    public String getSalt() {
        return salt;
    }

    public LocalDateTime getUltimoCambio() {
        return ultimoCambio;
    }

    public boolean isRequiereReset() {
        return requiereReset;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setUltimoCambio(LocalDateTime ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }

    public void setRequiereReset(boolean requiereReset) {
        this.requiereReset = requiereReset;
    }

    @Override
    public String toString() {
        return "CredencialAcceso{" + "id =" + getId() + "hashPassword=" + hashPassword + ", salt=" + salt + ", ultimoCambio=" + ultimoCambio + ", requiereReset=" + requiereReset + "eliminado =" + isEliminado () +  '}'+"\n";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.hashPassword);
        hash = 97 * hash + Objects.hashCode(this.salt);
        hash = 97 * hash + Objects.hashCode(this.ultimoCambio);
        hash = 97 * hash + (this.requiereReset ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CredencialAcceso other = (CredencialAcceso) obj;
        if (this.requiereReset != other.requiereReset) {
            return false;
        }
        if (!Objects.equals(this.hashPassword, other.hashPassword)) {
            return false;
        }
        if (!Objects.equals(this.salt, other.salt)) {
            return false;
        }
        return Objects.equals(this.ultimoCambio, other.ultimoCambio);
    }
   
    
    
  
}
