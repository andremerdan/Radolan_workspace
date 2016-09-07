package connection;

import gui.SpiralCreator;
import gui.VisCreator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;

import data.DataRead;
import data.UserCreatedEntry;
import data.MongoAccess;
import data.NegativeEntry;
import data.VisPreparation;

@Provider
@SuppressWarnings("unused")
@Path("")
/**
 * Contains all functions for interacting with the web application.
 * 
 * @author Patrick Allan Blair
 *
 */
public class WebApi {
	private HttpServer server;

	public WebApi(){
	}

	@SuppressWarnings("unchecked")
	/**
	 * Starts the server that will be addressed by the web application when querying data.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void startServer() throws IllegalArgumentException, IOException{
		if (server != null){
			server.stop();;
		}

		URI uri = URI.create("http://localhost:8080/radolan");
		DefaultResourceConfig rc = new DefaultResourceConfig(WebApi.class);
		rc.getContainerResponseFilters().add(new CORSFilter());

		server = GrizzlyServerFactory.createHttpServer(uri, ContainerFactory.createContainer(HttpHandler.class, rc));
	}

	/**
	 * Stops the server.
	 */
	public void stopServer(){
		if (server != null){
			server.stop();;
		}
	}


	@GET
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * A Function that only responds with "works" to test the program's server.
	 * 
	 * @return A serialized JSON object.
	 */
	public Response testConnection(){
		return Response.ok(GsonHelper.serialize("Works"), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/data/time")
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Returns a list of time frames and an area. This list always corresponds to the result of the last search attempted.
	 * 
	 * @return A serialized JSON object.
	 */
	public Response getTimeVisualisationData(){
		return Response.ok(GsonHelper.serialize(VisCreator.getVisList()), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/data/found")
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Returns a list of user created entries. This list always corresponds to the result of the last search attempted.
	 * 
	 * @return A serialized JSON object.
	 */
	public Response getFoundVisualisationData(){
		return Response.ok(GsonHelper.serialize(VisCreator.getFoundList()), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/data/{time}/{x}/{y}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * This will return the precipitation value found at the given coordinates for the given time.
	 * 
	 * @param time The queried point of time in milliseconds.
	 * @param x The x-coordinate of the requested precipitation value.
	 * @param y The y-coordinate of the requested precipitation value.
	 * @return A serialized JSON object.
	 */
	public Response getGridValue(@PathParam("time") long time, @PathParam("x") int x, @PathParam("y") int y){
		Date date = null;

		date = new Date(time);
		System.out.println("Restrieving Data at time: " + date + "for coordinates: " + x + "," + y);
		int value = DataRead.getDistinctValue(VisCreator.selectedVisCollection(), date, 900 - y, 900 - x);
		System.out.println("Found Value: " + value);
		if(value < 0) return Response.status(404).build();
		return Response.ok(GsonHelper.serialize(value), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/data/delete/{g}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Deletes a user created entry. Which entry is specified by the entry's glyph parameter, which is unique for each entry.
	 * 
	 * @param glyph The glyph paramater for selecting the entry to be deleted.
	 * @return A serialized JSON object.
	 */
	public Response deleteFoundEntry(@PathParam("g") String glyph){
		MongoAccess.deleteUserCreatedEntry(glyph);
		return Response.status(200).build();
	}

	@GET
	@Path("/data/entry/{e}/{c}/{g}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Creates a new user created entry or updates an existing one based on the existence of the given glyph parameter within the database.
	 * 
	 * @param entry A list of values to be used when creating or updating the entry.
	 * @param entryCenters A list of center values for the created entry.
	 * @param glyph The glyph paramater for selecting the entry to be created or updated.
	 * @return A serialized JSON object.
	 */
	public Response addFoundEntry(@PathParam("e") String entry, @PathParam("c") String entryCenters, @PathParam("g") String glyph){
		System.out.println("Received: " + entry);
		String[] received = (String[]) GsonHelper.deserialize(entry, String[].class);
		int[] center = (int[]) GsonHelper.deserialize(entryCenters, int[].class);

		UserCreatedEntry newEntry = new UserCreatedEntry(new Date(Long.valueOf(received[0])), new Date(Long.valueOf(received[1])), 
				center, Integer.valueOf(received[2]), Integer.valueOf(received[3]), received[4]);
		newEntry.generateGlyphName();
		if(glyph == "undefined"){
			MongoAccess.addUserCreatedEntry(newEntry);
		} else {
			MongoAccess.overwriteUserCreatedEntry(glyph, newEntry);
		}
		return Response.ok(GsonHelper.serialize(newEntry.getGlyphName()), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/data/glyph/{g}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Creates a new glyph for the user created entry specified by the glyph paramter.
	 * 
	 * @param glyphThe glyph paramater for selecting the entry to be represented.
	 * @return A serialized JSON object.
	 */
	public Response createNewGlyph(@PathParam("g") String glyph){
		UserCreatedEntry entry = MongoAccess.querySpecificUserCreatedEntry(glyph);
		if(entry == null) return Response.status(500).build();
		VisPreparation.createEntryPreviewGlyph(entry, VisCreator.getGlyphType());
		return Response.status(200).build();
	}

	@GET
	@Path("/data/negative/{e}/{a}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Sets a negative entry within the database (a marker that tells the program to ignore certain areas and time frames on
	 * subsequent searches unless the user specifies otherwise).
	 * 
	 * @param entry A list of values to be used when creating the entry.
	 * @param entryArea A list of area values for the created entry.
	 * @return A serialized JSON object.
	 */
	public Response setNegativeEntry(@PathParam("e") String entry, @PathParam("a") String entryArea){
		System.out.println("Received: " + entry);
		String[] received = (String[]) GsonHelper.deserialize(entry, String[].class);
		int[] area = (int[]) GsonHelper.deserialize(entryArea, int[].class);
		NegativeEntry newEntry = new NegativeEntry(new Date(Long.valueOf(received[0])), new Date(Long.valueOf(received[1])), area);
		MongoAccess.addNegativeEntry(newEntry);
		return Response.status(200).build();
	}

	@GET
	@Path("/data/positive/{e}/{a}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Deletes or alters a negative entry. The program will attempt to only delete the specified information, e.g. create two
	 * negative entries within the database if the deletion specified here only cuts out a portion of one former entry.
	 * 
	 * @param entry A list of values to be used when deleting the entry.
	 * @param entryArea A list of area values for the deleted entry.
	 * @return A serialized JSON object.
	 */
	public Response deleteNegativeEntry(@PathParam("e") String entry, @PathParam("a") String entryArea){
		System.out.println("Received: " + entry);
		String[] received = (String[]) GsonHelper.deserialize(entry, String[].class);
		int[] area = (int[]) GsonHelper.deserialize(entryArea, int[].class);
		NegativeEntry removeEntry = new NegativeEntry(new Date(Long.valueOf(received[0])), new Date(Long.valueOf(received[1])), area);
		MongoAccess.deleteNegativeEntry(removeEntry);
		return Response.status(200).build();
	}
}
