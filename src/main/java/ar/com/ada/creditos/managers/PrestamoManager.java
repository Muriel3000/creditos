package ar.com.ada.creditos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.creditos.entities.*;

public class PrestamoManager {
   
    
    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(prestamo);


        session.getTransaction().commit();
        session.close();
    }

    public Prestamo read(int prestamoId) {
        Session session = sessionFactory.openSession();

        Prestamo prestamo = session.get(Prestamo.class, prestamoId);

        session.close();

        return prestamo;
    }

    // FORMA DIFERENTE
    /*  public Prestamo readByPrestamoId(int prestamoId) {
            Session session = sessionFactory.openSession();

            Prestamo prestamo = session.byNaturalId(Prestamo.class).using("prestamo_id", prestamoId).load();

            session.close();

            return prestamo;
        }
    */


    /**
     * Este metodo en la vida real no debe existir ya qeu puede haber miles de
     * usuarios
     * 
     * @return
     */
    
    public List<Prestamo> mostrarTodos() {

        Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM prestamo", Prestamo.class);
        //query = session.createQuery("From Obse")
        List<Prestamo> todos = query.getResultList();

        return todos;
    }

    public List<Prestamo> buscarPrestamos(Cliente cliente) {

        Session session = sessionFactory.openSession();

        Query queryConJPQL = session.createNativeQuery("SELECT * from prestamo where cliente_id = ?",
         Prestamo.class);                     // NATIVE: SELECT *, ?, PARAMETROS CON NUMERO
        queryConJPQL.setParameter(1, cliente.getClienteId());

        List<Prestamo> prestamos = queryConJPQL.getResultList();

        return prestamos;

    }
}
