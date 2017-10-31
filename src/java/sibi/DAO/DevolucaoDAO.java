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
 * @author admin
 */
public class DevolucaoDAO {
    
    public List pesquisar(String valorCampo, String valorPesquisa){
        Utils Ut = new Utils();
        SQLUtil sqlu = new SQLUtil();
        String sql = "select "
                + "a.cd_emprestimo, "
                + "b.nm_usuario, "
                + "c.lt_nome "
                + "from app.emprestimo a, app.usuario b, app.material_bibliografico c "
                +"where a.cd_usuario = b.cd_usuario and a.cd_material_bibliografico = c.cd_material_bibliografico and " ;


        if (valorCampo.equals("cd_emprestimo")) {
            int num = 0;
            try {
                num = Integer.parseInt(valorPesquisa); // tenta jogar o valor do textfield na variável do tipo int

            } catch (NumberFormatException ex) {
                num = 0; // caso não seja possível (se o valor do text field for vazio ou uma letra) a variável vai receber 0.

                valorPesquisa = "0";
            }
            sql += valorCampo + "=" + valorPesquisa;

        } else if (valorCampo.equals("cd_usuario")) {
            int num = 0;
            try{
                num = Integer.parseInt(valorPesquisa);
                
            }catch(NumberFormatException ex){
                num = 0;
                valorPesquisa = "0";
            }
            sql += valorCampo + "=" + valorPesquisa;
        
        } else if(valorCampo.equals("cd_material_bibliografico")){
            int num = 0;
            try{
                num = Integer.parseInt("cd_material_bibliografico");
            }catch(NumberFormatException ex){
                num = 0;
                valorPesquisa = "0";
            }
            sql += valorCampo + "=" + valorPesquisa;
        }

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
    
    public boolean alterar(Emprestimo idEmprestimo){
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
//                sistema.setCd_material_bibliografico(idEmprestimo.getCd_material_bibliografico());
//                sistema.setCd_usuario(idEmprestimo.getCd_usuario());
//                sistema.setDt_devolucao(idEmprestimo.getDt_devolucao());
//                sistema.setDt_emprestimo(idEmprestimo.getDt_emprestimo());
                sistema.setLt_situacao(idEmprestimo.getLt_situacao());
            }
        } catch (Exception e) {
            r = false;
        }

        return r;
    }
        
}
