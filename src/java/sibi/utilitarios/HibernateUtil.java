/*
 * HibernateUtil.java
 *
 * Created on 15 de Dezembro de 2008, 10:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package sibi.utilitarios;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            // Para trabalhar com mapeamentos
            // sessionFactory = new Configuration().configure().buildSessionFactory();

            // Para trabalhar com annotations
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();

        } catch (Throwable ex) {

            // Make sure you log the exception, as it might be swallowed
            System.err.println("Sessão inicial do Hibernate Falhou." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static Session open() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public static void iniciaTransacao(Session session) {
        session.beginTransaction();

    }

    public static void commit(Session session) {
        if (session.beginTransaction().isActive()) {
            session.beginTransaction().commit();
        }
    }

    public static void rollback(Session session) {
        if (session.beginTransaction().isActive()) {
            session.beginTransaction().rollback();
        }
    }
//
//    public static int executeSQL(Session session, String sqlDml) {
//        @Deprecated
//        Connection conn = session.connection();
//
//        int r = 0;
//
//        try {
//            PreparedStatement stmt = conn.prepareStatement(sqlDml);
//
//            iniciaTransacao(session);
//
//            r = stmt.executeUpdate();
//
//            commit(session);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return r;
//
//    }
//
//    public static int executeSQLLote(Session session, String sqlDml) {
//
//        Connection conn = session.connection();
//
//        int r = 0;
//
//        try {
//            PreparedStatement stmt = conn.prepareStatement(sqlDml);
//            r = stmt.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return r;
//
//    }
}