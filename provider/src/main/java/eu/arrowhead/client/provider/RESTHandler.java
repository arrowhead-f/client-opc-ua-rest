/*
 *  Copyright (c) 2018 AITIA International Inc.
 *
 *  This work is part of the Productive 4.0 innovation project, which receives grants from the
 *  European Commissions H2020 research and innovation programme, ECSEL Joint Undertaking
 *  (project no. 737459), the free state of Saxony, the German Federal Ministry of Education and
 *  national funding authorities from involved countries.
 */
package eu.arrowhead.client.provider;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
//REST service example
public class RESTHandler {

    @GET
    @Path("browseOpcNode/")
    public Response browseOpcNode(@Context SecurityContext context,
                                  @QueryParam("serverAddress") String address,
                                  @QueryParam("ns") int namespaceIndex,
                                  @QueryParam("id") String identifier) {

        if (context.isSecure()) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        NodeId nodeId = new NodeId(namespaceIndex, identifier);

        OPCUAConnection connection = new OPCUAConnection(address);
        try {
            Vector<String> nodesBeneath = OPCUAInteractions.browseNode(connection.getConnectedClient(), nodeId);
            connection.dispose();
            String body = "";
            for(String node:nodesBeneath) {
                body += node;
            }
            return Response.status(200).entity(body).build();
        } catch (Exception ex) {
            Logger.getLogger(RESTHandler.class.getName()).log(Level.SEVERE, null, ex);
            connection.dispose();
            return Response.status(500).build();
        }
    }


    @GET
    @Path("readOpcVariable/{server_address}/{ns}/{id}")
    public Response readOpcVariableVariable(@Context SecurityContext context,
            @PathParam("server_address") String address,
            @PathParam("ns") int namespaceIndex,
            @PathParam("id") int identifier) {

        if (context.isSecure()) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        NodeId nodeId = new NodeId(namespaceIndex, identifier);

        OPCUAConnection connection = new OPCUAConnection(address);
        try {
            OPCUAInteractions.readVariableNode(connection.getConnectedClient(), nodeId);
            connection.dispose();
            return Response.status(200).entity("CONTENT GOES HERE...").build();
        } catch (Exception ex) {
            Logger.getLogger(RESTHandler.class.getName()).log(Level.SEVERE, null, ex);
            connection.dispose();
            return Response.status(500).build();
        }
    }


    @GET
    @Path("writeOpcVariable/{server_address}/{ns}/{id}/{value}")
    public Response writeOpcVariableVariable(@Context SecurityContext context,
            @PathParam("server_address") String address,
            @PathParam("ns") int namespaceIndex,
            @PathParam("id") int identifier,
            @PathParam("value") int value) {

        if (context.isSecure()) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        //FIXME Fix the comments below
        NodeId nodeId = new NodeId(namespaceIndex, identifier);

        OPCUAConnection connection = new OPCUAConnection(address);
        try {
            OPCUAInteractions.writeNode(connection.getConnectedClient(), nodeId, value);
            connection.dispose();
            return Response.status(200).entity("CONTENT GOES HERE...").build();
        } catch (Exception ex) {
            Logger.getLogger(RESTHandler.class.getName()).log(Level.SEVERE, null, ex);
            connection.dispose();
            return Response.status(500).build();
        }
    }


    @GET
    @Path("opcReadwrite/")
    public Response readwriteOpcVariable(@Context SecurityContext context,
            @QueryParam("serverAddress") String address,
            @QueryParam("ns") int namespaceIndex,
            @QueryParam("id") String identifier,
            @QueryParam("value") String value) {

        if (context.isSecure()) {
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();
        }
        NodeId nodeId = new NodeId(namespaceIndex, identifier);

        OPCUAConnection connection = new OPCUAConnection(address);
        String body = "";
        try {
            try {
                // If value was provided (write operation)
                body = OPCUAInteractions.writeNode(connection.getConnectedClient(), nodeId, parseInt(value));
            } catch (NumberFormatException e) {
                // If no value was provided (read operation) or if value was not an integer
                body = OPCUAInteractions.readVariableNode(connection.getConnectedClient(), nodeId);
            }
            connection.dispose();
            return Response.status(200).entity(body).build();
        } catch (Exception ex) {
            Logger.getLogger(RESTHandler.class.getName()).log(Level.SEVERE, null, ex);
            connection.dispose();
            return Response.status(500).build();
        }
    }

}
