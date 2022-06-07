package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("shopper")
public class PersonResource {

    @GET
    @Path("shoppers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShoppers() {
        Shop shop = Shop.getShop();
        Map<String, Object> messages = new HashMap<>();
        messages.put("Shopper" ,shop.getAllPersons());
        return Response.ok(messages).build();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShoppingListsFromPerson(@PathParam("name") String name) {
        Shop shop = Shop.getShop();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        List<ShoppingList> allListsFromPerson = shop.getListFromPerson(name); //warning: might return null!
        Map<String, Object> messages = new HashMap<>();
        if (allListsFromPerson == null){
            messages.put("Er is geen lijst met de naam: " + name, allListsFromPerson);
            return Response.noContent().entity(messages).build();
        }
        else{
            messages.put("De lijst met de naam: " + name, allListsFromPerson);
            return Response.ok(messages).build();
        }
    }

    @POST
    @Path("addShopper")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShopper(PostShopperRequest shopperRequest){
        Shopper shopper = new Shopper(shopperRequest.getName());
        return Response.ok(shopper).build();
    }

    @DELETE
    @Path("delete/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteShopper(@PathParam("name") String shopper){
        for (Shopper shopper1 : Shop.getShop().getAllPersons()){
            if (shopper1.getName().equals(shopper)){
                return shopper1.deleteShopper(shopper1) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
