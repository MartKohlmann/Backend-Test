package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.*;

@Path("list")
public class ListResource {

    @GET
    @Path("lists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShoppingLists() {
        Shop shop = Shop.getShop();
        List<ShoppingList> shoppingLists = shop.getAllShoppingLists();
        return Response.ok(shoppingLists).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response getShoppingListByName(@PathParam("name") String name) {
        Shop shop = Shop.getShop();
        ShoppingList s = shop.getShoppingListByName(name);
        Map<String, Object> messages = new HashMap<>();
        if (s == null) {
            messages.put("error", "no list by that name");
            messages.put("Requested name", name);
            return Response.noContent().entity(messages).build();
        } else {
            messages.put("Items gevonden met de naam", name);
            messages.put("Items\n", s);
            return Response.ok(messages).build();
        }
    }

    @POST
    @Path("createShoppingList")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createShoppinglist(PostShoppinglistRequest shoppinglistRequest){
        Shop shop = Shop.getShop();
        Shopper shopper1 = new Shopper("Not Found!");
        ShoppingList shoppingList = new ShoppingList(shoppinglistRequest.name, shopper1);
        shopper1.addList(shoppingList);
        return Response.ok(shop.getShoppingListByName(shoppinglistRequest.name)).build();

    }

    @PUT
    @Path("reset")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response resetShoppinglist(@Context SecurityContext sc, PostShoppinglistRequest shoppinglistRequest){
        if (sc.getUserPrincipal() instanceof MyUser){
            MyUser current = (MyUser) sc.getUserPrincipal();
            List shoppingLists = Shop.getShop().getListFromPerson(current.getName());
//            shoppingLists.resetLis    t();
            return Response.ok(shoppingLists).build();

        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("lijsten")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShopper(@Context SecurityContext sc){
        if (sc.getUserPrincipal() instanceof MyUser){
            MyUser current = (MyUser) sc.getUserPrincipal();
            if (current.getRole().equals("admin")){
                return Response.ok(Shop.getShop().getAllShoppingLists()).build();
            }
            List<ShoppingList> shoppingLists = Shop.getShop().getListFromPerson(current.getName());
            return Response.ok(shoppingLists).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
