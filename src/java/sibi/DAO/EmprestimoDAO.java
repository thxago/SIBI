/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibi.DAO;

import java.util.List;
import org.hibernate.Session;
import sibi.persistencia.Emprestimo;
import sibi.utilitarios.HibernateUtil;
import sibi.utilitarios.SQLUtil;
import utilitarios.Utils;

/**
 *
 * @author alsnogtix
 */
public class EmprestimoDAO {
    
    public long incluir(Emprestimo novo) {
       SQLUtil sqlu = new SQLUtil(); //Cria um novo objeto. Os metódos desse objeto serão utilizados para acessar o BD.
        
        novo.setCd_emprestimo((int) sqlu.incremento("app", "emprestimo", "cd_emprestimo")); 

        Session sessao = HibernateUtil.open(); //Sessão com o banco de dados criada
        HibernateUtil.iniciaTransacao(sessao); //Transaçao inicializada (É possivel fazer utilizando a classe Transaction também)
        
        long id = incluir(sessao, novo);             
        HibernateUtil.commit(sessao);          //Commit da inserção feita
        sessao.close();                        //Importante sempre fechar as sessões para liberar o BD
        return id;
    }
      
    public long incluir(Session session, Emprestimo novo) {
        return (Integer) session.save(novo);
    }
    
         
    // pesquisa de registro
    public List pesquisar(String valorPesquisa) {
        Utils Ut = new Utils();
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                + "a.cd_emprestimo, "
                + "b.nm_usuario, "
                + "c.lt_nome "
                + "from app.emprestimo a, app.usuario b, app.material_bibliografico c "
                +"where a.cd_usuario = b.cd_usuario and "
                + "a.cd_material_bibliografico = c.cd_material_bibliografico "
                + "and cd_emprestimo = " + valorPesquisa ;
        
        return sqlu.pesquisar(sql);
    }
    
        
    public Object pesquisarCID(Emprestimo emprestimo) {
        Session session = HibernateUtil.open();
        try {
            Emprestimo query = (Emprestimo) session.get(Emprestimo.class, emprestimo.getCd_emprestimo());
            session.close();
            return query;

        } catch (Exception e) {
            HibernateUtil.rollback(session);
            session.close();
            return null;
        }

    }
    public boolean renovacao(Emprestimo idEmprestimo){
        Session session = HibernateUtil.open();
        HibernateUtil.iniciaTransacao(session);
        boolean r = alterar(session, idEmprestimo);
        if (r) {
            HibernateUtil.commit(session);
        } else {
            HibernateUtil.rollback(session);
        }
        session.close();
        return r;
    }

    public boolean alterar(Session session, Emprestimo idEmprestimo) {
        boolean r = true;
        try {
            Emprestimo sistema = (Emprestimo) session.get(Emprestimo.class, idEmprestimo.getCd_emprestimo());
            if (sistema.getCd_emprestimo()> 0) {
                //campos a serem alterados
                sistema.setDt_devolucao(idEmprestimo.getDt_devolucao());
                sistema.setLt_situacao(idEmprestimo.getLt_situacao());
                sistema.setNu_renovacao(idEmprestimo.getNu_renovacao());
            }
        } catch (Exception e) {
            r = false;
        }

        return r;
    }
        

    
    
    public List buscarUsuarioCodigo(String cd_usuario){
        Utils Ut = new Utils();
        String sql= "SELECT NM_USUARIO, "
                + " lt_status "
                + " FROM app.usuario "
                + " WHERE cd_usuario = " + cd_usuario;        
        SQLUtil sqlu = new SQLUtil();
        return sqlu.pesquisar(sql);
        
    }
    
    public List buscarUsuarioEmail(String cd_usuario){
        Utils Ut = new Utils();
        String sql= "SELECT NM_USUARIO, "
                + " lt_status "
                + " FROM app.usuario "
                + " WHERE lt_email = " + cd_usuario;        
        SQLUtil sqlu = new SQLUtil();
        return sqlu.pesquisar(sql);
        
    }
        
    public List buscarMaterialCodigo(Integer cd_material_bibliografico){
        Utils Ut = new Utils();
        String sql= "SELECT lt_nome, "
                + " lt_autor, "
                + " lt_genero, "
                + " nu_quantidade_estoque "
                + " FROM app.material_bibliografico "
                + " WHERE cd_material_bibliografico = " + cd_material_bibliografico;        
        SQLUtil sqlu = new SQLUtil();
        return sqlu.pesquisar(sql);
        
    }
    
    public List pesquisarMaterial(String lt_nome){
        Utils Ut = new Utils();
        String sql = "select cd_material_bibliografico, "
                + " lt_nome, "
                + " lt_autor, "
                + " lt_genero, "
                + " nu_quantidade_estoque "
                + " FROM app.material_bibliografico "
                + " WHERE lt_nome like '" + lt_nome + "%'";        
        SQLUtil sqlu = new SQLUtil();
        return sqlu.pesquisar(sql);
    }
    
     
    public List pesquisarUsuario(String nm_usuario){
        Utils Ut = new Utils();
        String sql = "select cd_usuario, "
                + " nm_usuario, "
                + " lt_status "
                + " FROM app.usuario "
                + " WHERE nm_usuario like '" + nm_usuario + "%'";        
        SQLUtil sqlu = new SQLUtil();
        return sqlu.pesquisar(sql);
    }

    
    public List pesquisarEmprestimos(Integer cd_usuario){
        Utils Ut = new Utils();
        String sql = "select b.cd_emprestimo, "
                + " b.dt_devolucao, "
                + " c.lt_nome "
                + " FROM app.emprestimo b, app.material_bibliografico c "
                + " WHERE b.cd_usuario = " + cd_usuario ;        
        SQLUtil sqlu = new SQLUtil();
        return sqlu.pesquisar(sql);
        
    }
}
