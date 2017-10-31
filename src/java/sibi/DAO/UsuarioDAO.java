/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.DAO;

import java.util.List;
import org.hibernate.Session;
import sibi.persistencia.Usuario;
import sibi.utilitarios.HibernateUtil;
import sibi.utilitarios.SQLUtil;
import utilitarios.Utils;

/**
 *
 * @author admin
 */
public class UsuarioDAO {
    
        
    public long incluir(Usuario novo) {
       SQLUtil sqlu = new SQLUtil(); //Cria um novo objeto. Os metódos desse objeto serão utilizados para acessar o BD.
        
        novo.setCd_usuario((int) sqlu.incremento("app", "usuario", "cd_usuario")); 

        Session sessao = HibernateUtil.open(); //Sessão com o banco de dados criada
        HibernateUtil.iniciaTransacao(sessao); //Transaçao inicializada (É possivel fazer utilizando a classe Transaction também)
        
        int id = incluir(sessao, novo);             
        HibernateUtil.commit(sessao);          //Commit da inserção feita
        sessao.close();                        //Importante sempre fechar as sessões para liberar o BD
        return id;
    }
      
    public int incluir(Session session, Usuario novo) {
        return (Integer) session.save(novo);
    }
    
    
    public boolean alterar(Usuario idUsuario) {
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = alterar(session, idUsuario);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }

    public boolean alterar(Session session, Usuario idUsuario) {
        boolean r = true;
        try {
            Usuario sistema = (Usuario) session.get(Usuario.class, idUsuario.getCd_usuario());
            if (sistema.getCd_usuario()> 0) {
                //campos a serem alterados
                sistema.setNm_usuario(idUsuario.getNm_usuario());
                sistema.setNu_cpf(idUsuario.getNu_cpf());
                sistema.setNu_rg(idUsuario.getNu_rg());
                sistema.setDt_nascimento(idUsuario.getDt_nascimento());
                sistema.setNu_cep(idUsuario.getNu_cep());
                sistema.setLt_endereco(idUsuario.getLt_endereco());
                sistema.setLt_bairro(idUsuario.getLt_bairro());
                sistema.setNu_numero(idUsuario.getNu_numero());
                sistema.setLt_complemento(idUsuario.getLt_complemento());
                sistema.setLt_sexo(idUsuario.getLt_sexo());
                sistema.setLt_email(idUsuario.getLt_email());
                sistema.setLt_senha(idUsuario.getLt_senha());
                sistema.setLt_status(idUsuario.getLt_status());
                sistema.setCd_usuario(idUsuario.getCd_usuario());
            }
        } catch (Exception e) {
            r = false;
        }
        return r;
    }

    
     
    public boolean excluir(int idUsuario) {
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = excluir(session, idUsuario);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }
    
    public boolean excluir(Session session, int idUsuario) {
        boolean r = true;
        try {
            Usuario sistema = (Usuario) session.get(Usuario.class, idUsuario);

            if (sistema.getCd_usuario()> 0) {
                //campos a serem alterados
                session.delete(sistema);
            }

        } catch (Exception e) {
            r = false;
        }
        return r;
    }
    // pesquisa de registro
    public List pesquisar(String valorCampo, String valorPesquisa) {
        Utils Ut = new Utils();
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                + "cd_usuario, "
                + "nm_usuario, "
                + "lt_status "
                + "from app.usuario "
                +"where " ;


        if (valorCampo.equals("cd_usuario")) {
            int num = 0;
            try {
                num = Integer.parseInt(valorPesquisa); // tenta jogar o valor do textfield na variável do tipo int

            } catch (NumberFormatException ex) {
                num = 0; // caso não seja possível (se o valor do text field for vazio ou uma letra) a variável vai receber 0.

                valorPesquisa = "0";
            }
            sql += valorCampo + "=" + valorPesquisa;

        } else if (valorCampo.equals("nm_usuario") || valorCampo.equals("lt_status")) {
            sql += valorCampo 
                    + " like '" 
                    + valorPesquisa 
                    + "%'";
        }

        return sqlu.pesquisar(sql);
    }

    public Object pesquisarCID(Usuario usuario) {
        Session session = HibernateUtil.open();
        try {
            Usuario query = (Usuario) session.get(Usuario.class, usuario.getCd_usuario());
            session.close();
            return query;

        } catch (Exception e) {
            HibernateUtil.rollback(session);
            session.close();
            return null;
        }

    }
    
    public List buscarUsuario(String login, String senha){
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                +"cd_usuario "
                +"from app.usuario "
                +"where lt_email = " + "'" + login + "'" + " and lt_senha = " + "'"+ senha + "'";
        return sqlu.pesquisar(sql);
    }
    
    public List recuperaUsuario(String login){
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                +"lt_senha "
                +"from app.usuario "
                +"where lt_email = " + "'" + login + "'";
        return sqlu.pesquisar(sql);
    }
    
    public boolean buscaPendencia(String email){
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                + " lt_status "
                + " from app.usuario "
                + " where lt_email = " + "'" + email + "'" + " and lt_status = 'inadimplente'";
        if(sqlu.pesquisar(sql).isEmpty()){
            return false;
        };
        return true;
        
        
    }
    
}
