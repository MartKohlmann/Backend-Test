package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("product")
public class ProductResource {

    @GET
    @Path("products")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        Shop shop = Shop.getShop();
        List<Product> all = shop.getAllProducts();
        Map<String, Object> messages = new HashMap<>();
        int i = 1;
        for (Product p : all) {
            if (p == null){
                break;
            }
            messages.put("Product " + i, p.getName());
            i += 1;
        }
        if (all.isEmpty()){
            messages.put("error", "Er zijn geen producten gevonden!");
        }
        return Response.ok(messages).build();
    }

    @POST
    @Path("addProduct")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(PostProductRequest postProductRequest){
        Shop shop = Shop.getShop();
        List<ShoppingList> shoppingLists = shop.getAllShoppingLists();
        int a = 0;
        for (ShoppingList s : shoppingLists){
            if (s.getName().equals(postProductRequest.shoppingListName)){
                ShoppingList shoppingList = shop.getShoppingListByName(postProductRequest.shoppingListName);
                shoppingList.addItem(postProductRequest.p, postProductRequest.amount);
                a = 1;
                break;
            }
        }
        if (a == 0){
            ShoppingList shoppingList = new ShoppingList(postProductRequest.shoppingListName, new Shopper("Not found!"));
            shoppingList.addItem(postProductRequest.p, postProductRequest.amount);
        }
        return Response.ok(shop.getShoppingListByName(postProductRequest.shoppingListName)).build();
    }
}
