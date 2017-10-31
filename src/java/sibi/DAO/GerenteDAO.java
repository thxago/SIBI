/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.DAO;

import java.util.List;
import org.hibernate.Session;
import sibi.persistencia.Gerente;
import sibi.utilitarios.HibernateUtil;
import sibi.utilitarios.SQLUtil;
import utilitarios.Utils;
/**
 *
 * @author alsnogtix
 */
public class GerenteDAO {

    public long incluir(Gerente novo) {
       SQLUtil sqlu = new SQLUtil(); //Cria um novo objeto. Os metódos desse objeto serão utilizados para acessar o BD.
        
        novo.setCd_gerente((int) sqlu.incremento("app", "gerente", "cd_gerente")); 

        Session sessao = HibernateUtil.open(); //Sessão com o banco de dados criada
        HibernateUtil.iniciaTransacao(sessao); //Transaçao inicializada (É possivel fazer utilizando a classe Transaction também)
        
        int id = incluir(sessao, novo);             
        HibernateUtil.commit(sessao);          //Commit da inserção feita
        sessao.close();                        //Importante sempre fechar as sessões para liberar o BD
        return id;
    }
      
    public int incluir(Session session, Gerente novo) {
        return (Integer) session.save(novo);
    }
    
    
    
    
    public boolean alterar(Gerente idGerente) {
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = alterar(session, idGerente);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }

    public boolean alterar(Session session, Gerente idGerente) {
        boolean r = true;
        try {
            Gerente sistema = (Gerente) session.get(Gerente.class, idGerente.getCd_gerente());
            if (sistema.getCd_gerente()> 0) {
                //campos a serem alterados
                sistema.setNm_gerente(idGerente.getNm_gerente());
                sistema.setNu_cpf(idGerente.getNu_cpf());
                sistema.setNu_rg(idGerente.getNu_rg());
                sistema.setDt_data_nascimento(idGerente.getDt_data_nascimento());
                sistema.setNu_cep(idGerente.getNu_cep());
                sistema.setLt_endereco(idGerente.getLt_endereco());
                sistema.setLt_bairro(idGerente.getLt_bairro());
                sistema.setNu_numero(idGerente.getNu_numero());
                sistema.setLt_complemento(idGerente.getLt_complemento());
                sistema.setLt_sexo(idGerente.getLt_sexo());
                sistema.setLt_email(idGerente.getLt_email());
                sistema.setLt_senha(idGerente.getLt_senha());
                sistema.setCd_gerente(idGerente.getCd_gerente());
                sistema.setDb_salario(idGerente.getDb_salario());
                sistema.setDt_inicio_trabalho(idGerente.getDt_inicio_trabalho());
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
    
    public boolean excluir(Session session, int idGerente) {
        boolean r = true;
        try {
            Gerente sistema = (Gerente) session.get(Gerente.class, idGerente);

            if (sistema.getCd_gerente()> 0) {
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
                + "cd_gerente, "
                + "nm_gerente "
                + "from app.gerente "
                +"where " ;


        if (valorCampo.equals("cd_gerente")) {
            int num = 0;
            try {
                num = Integer.parseInt(valorPesquisa); // tenta jogar o valor do textfield na variável do tipo int

            } catch (NumberFormatException ex) {
                num = 0; // caso não seja possível (se o valor do text field for vazio ou uma letra) a variável vai receber 0.

                valorPesquisa = "0";
            }
            sql += valorCampo + "=" + valorPesquisa;

        } else if (valorCampo.equals("nm_gerente")) {
            sql += valorCampo 
                    + " like '" 
                    + valorPesquisa 
                    + "%'";
        }

        return sqlu.pesquisar(sql);
    }

    public Object pesquisarCID(Gerente gerente) {
        Session session = HibernateUtil.open();
        try {
            Gerente query = (Gerente) session.get(Gerente.class, gerente.getCd_gerente());
            session.close();
            return query;

        } catch (Exception e) {
            HibernateUtil.rollback(session);
            session.close();
            return null;
        }

    }

    
    

    
    public List buscarGerente(String login, String senha){
//        Utils Ut = new Utils();
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                +"cd_gerente "
                +"from app.gerente "
                +"where lt_email = " + "'" + login + "'" + " and lt_senha = " + "'"+ senha + "'";
        return sqlu.pesquisar(sql);
        
    }
    
    public List recuperaGerente(String login){
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                +"lt_senha "
                +"from app.gerente "
                +"where lt_email = " + "'" + login + "'";
        return sqlu.pesquisar(sql);
        
    }
    
}
