package com.zbw.carrental.rest;

import com.zbw.carrental.businesslogic.CustomerService;
import com.zbw.carrental.entities.Customer;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/customer")
public class CustomerResource {

    // ======================================
    // =             Attributes             =
    // ======================================

    @EJB
    private CustomerService customerService = new CustomerService();

    // ======================================
    // =           Public Methods           =
    // ======================================

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Customer entity) {
        customerService.createCustomer(entity);

        return Response.created(UriBuilder.
                fromResource(CustomerResource.class).
                path(String.valueOf(entity.getId())).
                build()).
                build();
    }

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("param") String customerId) {

        int parsedParameter = Integer.valueOf(customerId);

        Customer customer = customerService.findCustomer(parsedParameter);
        return Response.status(200).entity(customer).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers(){
        Customer customer = customerService.findCustomer(2);
        return Response.status(200).entity(customer).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveCustomer(final Customer customer){
        Customer persistedCustomer = customerService.createCustomer(customer);
        return Response.ok(persistedCustomer).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Integer id) {
        Customer entity = customerService.findCustomer(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        customerService.removeCustomer(entity);
        return Response.noContent().build();
    }
}
