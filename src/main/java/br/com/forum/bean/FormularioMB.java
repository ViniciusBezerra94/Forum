/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forum.bean;

import br.com.forum.dao.UserDAO;
import br.com.forum.model.User;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author SUPORTE116
 */
@Named(value = "formularioMB")
@ViewScoped
public class FormularioMB implements Serializable{

    
    
    /**
     * Creates a new instance of FormularioMB
     */
    public FormularioMB() {
        u = new User();
    }
    
    private User u ;
    
    public void salvar(){
        UserDAO uDAO = new UserDAO();
        if( uDAO.salvar( u ) )
        {
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso","Usuário cadastrado" ));
            u = new User();
        }
        else
        {
           FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro","Erro ao cadastrar usuário, por favor consulte o administrador do sistema" )); 
        }
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
    
    
    
    
}
