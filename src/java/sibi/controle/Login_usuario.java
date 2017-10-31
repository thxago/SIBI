/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.controle;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox;
import sibi.DAO.AtendenteDAO;
import sibi.DAO.BiblioteconomistaDAO;
import sibi.DAO.GerenteDAO;
import sibi.DAO.MaterialBibliograficoDAO;
import sibi.DAO.UsuarioDAO;




/**
 *
 * @author admin
 */
public class Login_usuario extends Window{


    private Window win;
    private Textbox lb_login;
    private Textbox lb_senha;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private GerenteDAO gerenteDAO = new GerenteDAO();
    private BiblioteconomistaDAO biblioteconomistaDAO = new BiblioteconomistaDAO();
    private AtendenteDAO atendenteDAO = new AtendenteDAO();
    private MaterialBibliograficoDAO materialBibliograficoDAO = new MaterialBibliograficoDAO();

    
    
    public void onCreate(){
        this.setLb_login((Textbox) getFellow("lb_login"));
        this.setLb_senha((Textbox) getFellow("lb_senha"));
    }
    


    public void efetuar_login(){
        
        if(!getUsuarioDAO().buscarUsuario(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            boolean r = getUsuarioDAO().buscaPendencia(this.getLb_login().getValue());
            if(r){
                Messagebox.show("Você possui pendências no sistema, por favor, procure a biblioteca.", "Controle de Usuários", Messagebox.OK , Messagebox.INFORMATION);
                
            }else{
                Executions.sendRedirect("menuUsuario.zul");
            }
            
        
        }else if(!getGerenteDAO().buscarGerente(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            Executions.sendRedirect("menuGerente.zul");
            
        }else if(!getAtendenteDAO().buscarAtendente(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            Executions.sendRedirect("menuAtendente.zul");
            
        }else if(!getBiblioteconomistaDAO().buscarBiblioteconomista(getLb_login().getValue(), getLb_senha().getValue()).isEmpty()){
            Executions.sendRedirect("menuBiblioteconomista.zul");

        }else{
            try{
                Messagebox.show("Email ou Senha incorreto(s).", "Login", Messagebox.OK , Messagebox.INFORMATION);
            } catch(Exception ex){
                Logger.getLogger(Login_usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void esqueci_minha_senha(){
        
        if(!getUsuarioDAO().recuperaUsuario(getLb_login().getValue()).isEmpty()){
            List list = getUsuarioDAO().recuperaUsuario(this.getLb_login().getValue());
            String password = "";
            for (Object s : list){
                password += s;
            }
            envia_email(password);
        }else if(!getGerenteDAO().recuperaGerente(getLb_login().getValue()).isEmpty()){
            List list = getGerenteDAO().recuperaGerente(this.getLb_login().getValue());
            String password = "";
            for (Object s : list){
                password += s;
            }
            envia_email(password);
        }else if(!getAtendenteDAO().recuperaAtendente(getLb_login().getValue()).isEmpty()){
            List list = getAtendenteDAO().recuperaAtendente(this.getLb_login().getValue());
            String password = "";
            for (Object s : list){
                password += s;
            }
            envia_email(password);
        }else if(!getBiblioteconomistaDAO().recuperaBiblioteconomista(getLb_login().getValue()).isEmpty()){
            List list = getBiblioteconomistaDAO().recuperaBiblioteconomista(this.getLb_login().getValue());
            String password = "";
            for (Object s : list){
                password += s;
            }
            envia_email(password);
        }else{
            try{
                Messagebox.show("Email não encontrado", "Recupera Senha", Messagebox.OK , Messagebox.INFORMATION);
            } catch(Exception ex){
                Logger.getLogger(Login_usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void envia_email(String password){
        String senha = password;
        String email = getLb_login().getValue();
        
        Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                Session session = Session.getDefaultInstance(props,
                            new javax.mail.Authenticator() {
                                 protected PasswordAuthentication getPasswordAuthentication() 
                                 {
                                       return new PasswordAuthentication("sibi2017iss@gmail.com", "sibi2017iss!@#");
                                 }
                            });
                session.setDebug(true);
                try {

                      Message message = new MimeMessage(session);
                      message.setFrom(new InternetAddress("sibi2017iss@gmail.com")); //Remetente

                      Address[] toUser = InternetAddress //Destinatário(s)
                                 .parse(email);  
                      message.setRecipients(Message.RecipientType.TO, toUser);
                      message.setSubject("Recuperação de senha SIBI");//Assunto
                      message.setText("Olá,\nConforme solicitado, segue sua senha para acesso ao SIBI: "+senha+
                              "\n\nObs: E-mail automático. Por favor, não respode-lo.");

                      Transport.send(message);
                      Messagebox.show("Senha enviada para o email: "+email, "Recupera Senha", Messagebox.OK , Messagebox.INFORMATION);
                 } catch (MessagingException e) {
                      throw new RuntimeException(e);
                }
    }

    /**
     * @return the win
     */
    public Window getWin() {
        return win;
    }

    /**
     * @param win the win to set
     */
    public void setWin(Window win) {
        this.win = win;
    }

    /**
     * @return the lb_login
     */
    public Textbox getLb_login() {
        return lb_login;
    }

    /**
     * @param lb_login the lb_login to set
     */
    public void setLb_login(Textbox lb_login) {
        this.lb_login = lb_login;
    }

    /**
     * @return the lb_senha
     */
    public Textbox getLb_senha() {
        return lb_senha;
    }

    /**
     * @param lb_senha the lb_senha to set
     */
    public void setLb_senha(Textbox lb_senha) {
        this.lb_senha = lb_senha;
    }
    
    
    /**
     * @return the usuarioDAO
     */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    /**
     * @param usuarioDAO the usuarioDAO to set
     */
    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    
    /**
     * @return the gerenteDAO
     */
    public GerenteDAO getGerenteDAO() {
        return gerenteDAO;
    }

    /**
     * @param gerenteDAO the gerenteDAO to set
     */
    public void setGerenteDAO(GerenteDAO gerenteDAO) {
        this.gerenteDAO = gerenteDAO;
    }

    /**
     * @return the biblioteconomistaDAO
     */
    public BiblioteconomistaDAO getBiblioteconomistaDAO() {
        return biblioteconomistaDAO;
    }

    /**
     * @param biblioteconomistaDAO the biblioteconomistaDAO to set
     */
    public void setBiblioteconomistaDAO(BiblioteconomistaDAO biblioteconomistaDAO) {
        this.biblioteconomistaDAO = biblioteconomistaDAO;
    }

    /**
     * @return the atendenteDAO
     */
    public AtendenteDAO getAtendenteDAO() {
        return atendenteDAO;
    }

    /**
     * @param atendenteDAO the atendenteDAO to set
     */
    public void setAtendenteDAO(AtendenteDAO atendenteDAO) {
        this.atendenteDAO = atendenteDAO;
    }

    /**
     * @return the materialBibliograficoDAO
     */
    public MaterialBibliograficoDAO getMaterialBibliograficoDAO() {
        return materialBibliograficoDAO;
    }

    /**
     * @param materialBibliograficoDAO the materialBibliograficoDAO to set
     */
    public void setMaterialBibliograficoDAO(MaterialBibliograficoDAO materialBibliograficoDAO) {
        this.materialBibliograficoDAO = materialBibliograficoDAO;
    }
}
