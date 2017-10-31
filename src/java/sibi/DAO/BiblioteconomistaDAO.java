/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.DAO;

import java.util.List;
import org.hibernate.Session;
import sibi.persistencia.Biblioteconomista;
import sibi.utilitarios.HibernateUtil;
import sibi.utilitarios.SQLUtil;
import utilitarios.Utils;

/**
 *
 * @author alsnogtix
 */

public class BiblioteconomistaDAO {
    public long incluir(Biblioteconomista novo) {
       SQLUtil sqlu = new SQLUtil(); //Cria um novo objeto. Os metódos desse objeto serão utilizados para acessar o BD.
        
        novo.setCd_biblioteconomista((int) sqlu.incremento("app", "biblioteconomista", "cd_biblioteconomista")); 

        Session sessao = HibernateUtil.open(); //Sessão com o banco de dados criada
        HibernateUtil.iniciaTransacao(sessao); //Transaçao inicializada (É possivel fazer utilizando a classe Transaction também)
        
        int id = incluir(sessao, novo);             
        HibernateUtil.commit(sessao);          //Commit da inserção feita
        sessao.close();                        //Importante sempre fechar as sessões para liberar o BD
        return id;
    }
      
    public int incluir(Session session, Biblioteconomista novo) {
        return (Integer) session.save(novo);
    }
    
    
    public boolean alterar(Biblioteconomista idBiblioteconomista) {
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = alterar(session, idBiblioteconomista);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }

    public boolean alterar(Session session, Biblioteconomista idBiblioteconomista) {
        boolean r = true;
        try {
            Biblioteconomista sistema = (Biblioteconomista) session.get(Biblioteconomista.class, idBiblioteconomista.getCd_biblioteconomista());
            if (sistema.getCd_biblioteconomista()> 0) {
                //campos a serem alterados
                sistema.setNm_biblioteconomista(idBiblioteconomista.getNm_biblioteconomista());
                sistema.setNu_cpf(idBiblioteconomista.getNu_cpf());
                sistema.setNu_rg(idBiblioteconomista.getNu_rg());
                sistema.setDt_nascimento(idBiblioteconomista.getDt_nascimento());
                sistema.setNu_cep(idBiblioteconomista.getNu_cep());
                sistema.setLt_endereco(idBiblioteconomista.getLt_endereco());
                sistema.setLt_bairro(idBiblioteconomista.getLt_bairro());
                sistema.setNu_numero(idBiblioteconomista.getNu_numero());
                sistema.setLt_complemento(idBiblioteconomista.getLt_complemento());
                sistema.setLt_sexo(idBiblioteconomista.getLt_sexo());
                sistema.setLt_email(idBiblioteconomista.getLt_email());
                sistema.setLt_senha(idBiblioteconomista.getLt_senha());
                sistema.setLt_status(idBiblioteconomista.getLt_status());
                sistema.setCd_biblioteconomista(idBiblioteconomista.getCd_biblioteconomista());
                sistema.setDb_salario(idBiblioteconomista.getDb_salario());
                sistema.setDt_inicio_trabalho(idBiblioteconomista.getDt_inicio_trabalho());
                sistema.setLt_crb(idBiblioteconomista.getLt_crb());
            }
        } catch (Exception e) {
            r = false;
        }
        return r;
    }

    
     
    public boolean excluir(int idBiblioteconomista) {
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = excluir(session, idBiblioteconomista);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }
    
    public boolean excluir(Session session, int idBiblioteconomista) {
        boolean r = true;
        try {
            Biblioteconomista sistema = (Biblioteconomista) session.get(Biblioteconomista.class, idBiblioteconomista);

            if (sistema.getCd_biblioteconomista()> 0) {
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
                + "cd_biblioteconomista, "
                + "nm_biblioteconomista, "
                + "lt_status "
                + "from app.biblioteconomista "
                +"where " ;


        if (valorCampo.equals("cd_biblioteconomista")) {
            int num = 0;
            try {
                num = Integer.parseInt(valorPesquisa); // tenta jogar o valor do textfield na variável do tipo int

            } catch (NumberFormatException ex) {
                num = 0; // caso não seja possível (se o valor do text field for vazio ou uma letra) a variável vai receber 0.

                valorPesquisa = "0";
            }
            sql += valorCampo + "=" + valorPesquisa;

        } else if (valorCampo.equals("nm_biblioteconomista") || valorCampo.equals("lt_status")) {
            sql += valorCampo 
                    + " like '" 
                    + valorPesquisa 
                    + "%'";
        }

        return sqlu.pesquisar(sql);
    }

    public Object pesquisarCID(Biblioteconomista biblioteconomista) {
        Session session = HibernateUtil.open();
        try {
            Biblioteconomista query = (Biblioteconomista) session.get(Biblioteconomista.class, biblioteconomista.getCd_biblioteconomista());
            session.close();
            return query;

        } catch (Exception e) {
            HibernateUtil.rollback(session);
            session.close();
            return null;
        }

    }

    public List buscarBiblioteconomista(String login, String senha){
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                +"cd_biblioteconomista "
                +"from app.biblioteconomista "
                +"where lt_email = " + "'" + login + "'" + " and lt_senha = " + "'"+ senha + "'";
        return sqlu.pesquisar(sql);
        
    }
    
    public List recuperaBiblioteconomista(String login){
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                +"lt_senha "
                +"from app.biblioteconomista "
                +"where lt_email = " + "'" + login + "'";
        return sqlu.pesquisar(sql);   
    }
    
}
