package controller;

import dao.UserImpl;
import lombok.Getter;
import lombok.Setter;
import model.Usuario;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import static services.EmailService.sendEmail;


@Named(value = "usuarioC")
@SessionScoped
public class UsuarioC implements Serializable {
    private @Getter
    @Setter
    Usuario usuario, usuarioAutenticado;
    private @Getter
    @Setter
    UserImpl dao;
    private @Getter
    @Setter
    String user, password;

    public UsuarioC(){
       usuario = new Usuario();
       usuarioAutenticado = new Usuario();
       dao = new UserImpl();
    }

    public void enviarCodeEmail(String email, String code){
        try {
            sendEmail(email,code);
        }catch (Exception e){
            Logger.getLogger(UsuarioC.class.getName()).log(Level.SEVERE, null, e);
        }

    }

   public void iniciarSesion(){
       try {
           usuarioAutenticado = dao.iniciarSesion(usuario);
           if(usuarioAutenticado != null){
               String code =dao.reenviarCode(usuarioAutenticado.getCODUSU());
               enviarCodeEmail(usuarioAutenticado.getEMLUSU(), code );

               FacesContext.getCurrentInstance().addMessage(null,
                       new FacesMessage(FacesMessage.SEVERITY_INFO, "ENVIÓ", "Revisar el correo"));
               FacesContext.getCurrentInstance().getExternalContext().redirect("/LetsGo/view/seguridad.xhtml");
           }else{
               FacesContext.getCurrentInstance().addMessage(null,
                       new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Introduzca el usuario y cpassword correctamente."));
           }

       }catch (Exception e){
           Logger.getLogger(UsuarioC.class.getName()).log(Level.SEVERE, null, e);
       }

   }

   public void reenviarCode(){
        try {
         String nuevoCode =  dao.reenviarCode(usuarioAutenticado.getCODUSU());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Revisa tu correo."));
         enviarCodeEmail(usuarioAutenticado.getEMLUSU(), nuevoCode);
        }catch (Exception e){
            Logger.getLogger(UsuarioC.class.getName()).log(Level.SEVERE, null, e);
        }

   }

    public void verificarCode(){
        try {
          String sesion =  dao.verificarCode(usuarioAutenticado.getCODUSU(), usuario.getCODE());
          if(sesion == null){
              FacesContext.getCurrentInstance().addMessage(null,
                      new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Tu code ha expirado."));

          }else{
              FacesContext.getCurrentInstance().getExternalContext().redirect("/LetsGo/view/central.xhtml");
          }
        }catch (Exception e){
            Logger.getLogger(UsuarioC.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
