package nl.hu.bep.setup;

import nl.hu.bep.shopping.model.MyUser;
import nl.hu.bep.shopping.model.Product;
import nl.hu.bep.shopping.model.Shopper;
import nl.hu.bep.shopping.model.ShoppingList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing application");
        MyUser user = new MyUser("Mart", "1234", "admin");
        MyUser user1 = new MyUser("Dum-Dum", "abcd", "user");
        Shopper p = new Shopper("Dum-Dum");
        Shopper p1 = new Shopper(user.getName());
        ShoppingList il = new ShoppingList("initialList", p);
        ShoppingList al = new ShoppingList("anotherList", p);
        p.addList(il);
        p.addList(al);
        ShoppingList s = new ShoppingList("Marts lijstje", p1);
        p1.addList(s);
        il.addItem(new Product("Cola Zero"), 4);
        il.addItem(new Product("Cola Zero"), 4);
        il.addItem(new Product("Toiletpapier 6stk"), 1);
        al.addItem(new Product("Paracetamol 30stk"), 3);
        s.addItem(new Product("Bierrrrr"), 6);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("terminating application");
    }

}
