/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.DAO;

import java.util.List;
import org.hibernate.Session;
import org.zkoss.zul.impl.Utils;
import sibi.persistencia.Atendente;
import sibi.utilitarios.HibernateUtil;
import sibi.utilitarios.SQLUtil;

/**
 *
 * @author alsnogtix
 */
public class AtendenteDAO {
    

    
    
    public long incluir(Atendente novo) {
       SQLUtil sqlu = new SQLUtil(); //Cria um novo objeto. Os metódos desse objeto serão utilizados para acessar o BD.
        
        novo.setCd_atendente((int) sqlu.incremento("app", "atendente", "cd_atendente")); 

        Session sessao = HibernateUtil.open(); //Sessão com o banco de dados criada
        HibernateUtil.iniciaTransacao(sessao); //Transaçao inicializada (É possivel fazer utilizando a classe Transaction também)
        
        int id = incluir(sessao, novo);             
        HibernateUtil.commit(sessao);          //Commit da inserção feita
        sessao.close();                        //Importante sempre fechar as sessões para liberar o BD
        return id;
    }
      
    public int incluir(Session session, Atendente novo) {
        return (Integer) session.save(novo);
    }
    
    
    
    
    public boolean alterar(Atendente idAtendente) {
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = alterar(session, idAtendente);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }

    public boolean alterar(Session session, Atendente idAtendente) {
        boolean r = true;
        try {
            Atendente sistema = (Atendente) session.get(Atendente.class, idAtendente.getCd_atendente());
            if (sistema.getCd_atendente()> 0) {
                //campos a serem alterados
                sistema.setNm_nome(idAtendente.getNm_nome());
                sistema.setNu_cpf(idAtendente.getNu_cpf());
                sistema.setNu_rg(idAtendente.getNu_rg());
                sistema.setDt_data_nascimento(idAtendente.getDt_data_nascimento());
                sistema.setNu_cep(idAtendente.getNu_cep());
                sistema.setLt_endereco(idAtendente.getLt_endereco());
                sistema.setLt_bairro(idAtendente.getLt_bairro());
                sistema.setNu_numero(idAtendente.getNu_numero());
                sistema.setLt_complemento(idAtendente.getLt_complemento());
                sistema.setLt_sexo(idAtendente.getLt_sexo());
                sistema.setLt_email(idAtendente.getLt_email());
                sistema.setLt_senha(idAtendente.getLt_senha());
                sistema.setCd_atendente(idAtendente.getCd_atendente());
                sistema.setDb_salario(idAtendente.getDb_salario());
                sistema.setDt_inicio_trabalho(idAtendente.getDt_inicio_trabalho());
            }
        } catch (Exception e) {
            r = false;
        }
        return r;
    }
    
    
         
    public boolean excluir(int idGerente) {
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = excluir(session, idGerente);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }
    
    public boolean excluir(Session session, int idAtendente) {
        boolean r = true;
        try {
            Atendente sistema = (Atendente) session.get(Atendente.class, idAtendente);

            if (sistema.getCd_atendente()> 0) {
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
                + "cd_atendente, "
                + "nm_nome "
                + "from app.atendente "
                +"where " ;


        if (valorCampo.equals("cd_atendente")) {
            int num = 0;
            try {
                num = Integer.parseInt(valorPesquisa); // tenta jogar o valor do textfield na variável do tipo int

            } catch (NumberFormatException ex) {
                num = 0; // caso não seja possível (se o valor do text field for vazio ou uma letra) a variável vai receber 0.

                valorPesquisa = "0";
            }
            sql += valorCampo + "=" + valorPesquisa;

        } else if (valorCampo.equals("nm_nome")) {
            sql += valorCampo 
                    + " like '" 
                    + valorPesquisa 
                    + "%'";
        }

        return sqlu.pesquisar(sql);
    }

    public Object pesquisarCID(Atendente atendente) {
        Session session = HibernateUtil.open();
        try {
            Atendente query = (Atendente) session.get(Atendente.class, atendente.getCd_atendente());
            session.close();
            return query;

        } catch (Exception e) {
            HibernateUtil.rollback(session);
            session.close();
            return null;
        }

    }
    
    public List buscarAtendente(String login, String senha){
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                +"cd_atendente "
                +"from app.atendente "
                +"where lt_email = " + "'" + login + "'" + " and lt_senha = " + "'"+ senha + "'";
        return sqlu.pesquisar(sql);
        
    }
    



}
