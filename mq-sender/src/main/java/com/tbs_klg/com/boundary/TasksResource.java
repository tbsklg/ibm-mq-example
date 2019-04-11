package com.tbs_klg.com.boundary;

import com.tbs_klg.com.control.TaskService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TasksResource {

    @Inject
    TaskService taskService;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{identifier}")
    public Response retrieveTask(@PathParam("identifier") String identifier) {
        if (taskService.retrieveTask(identifier) == null) {
            URI currentStatusPath = uriInfo.getBaseUriBuilder()
                    .path(TasksResource.class)
                    .path(TasksResource.class, "currentStatus")
                    .build(identifier);
            return Response.accepted().header("Location", currentStatusPath).build();
        }
        return Response.ok(taskService.retrieveTask(identifier)).build();
    }

    @GET
    @Path("{identifier}/currentStatus")
    public Response currentStatus(@PathParam("identifier") String identifier) {
        System.out.println("in status");
        if (!taskService.containsKey(identifier)) {
            return Response
                    .ok(taskService.createStatusPayload("pending"))
                    .build();
        }
        URI taskPath = uriInfo.getBaseUriBuilder()
                .path(TasksResource.class)
                .path(TasksResource.class, "retrieveTask")
                .build(identifier);

        return Response.seeOther(taskPath)
                .entity(taskService.createStatusPayload("finished"))
                .build();
    }
}