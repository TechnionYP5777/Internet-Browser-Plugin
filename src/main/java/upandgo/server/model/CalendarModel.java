package upandgo.server.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import upandgo.server.CoursesServiceImpl;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;

public class CalendarModel {
	private static final String calendarName = "Technion's Lessons Schedule";

	private static final String tokenYear = "2017";
	private static final String tokenMonth = "04";
	private static final int tokenDay = 23;
	
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT = new UrlFetchTransport();

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static AppEngineDataStoreFactory DATA_STORE_FACTORY = AppEngineDataStoreFactory.getDefaultInstance();

	private static String clientSecretJson = "/client_secret.json";
	
	private static GoogleClientSecrets clientSecrets = null;
	
	private Calendar calendarService = null;
	
	private String calendarId = null;
	
	public CalendarModel() {}

	@Deprecated
	public void deleteCalendarIfExists() throws IOException {
		// it doesn't work
	    String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
	    Credential credential = newFlow().loadCredential(userId);
		calendarService = getCalendarService(credential);
		
		// Delete a calendar list entry
		try {
			calendarService.calendarList().delete(calendarName).execute();
			CoursesServiceImpl.someString += "\ndeleted calendar";
		} catch (IOException e) {
			CoursesServiceImpl.someString += "\ncouldn't delete calendar";
			e.printStackTrace();
		}
	}

	public void createCalendar(List<LessonGroup> lessons) throws IOException {
	    String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
	    Credential credential = newFlow().loadCredential(userId);
		calendarService = getCalendarService(credential);
		
		CoursesServiceImpl.someString += "\ngot credentials for creating";
		
		// Create a new calendar
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		calendar.setSummary(calendarName);
		calendar.setTimeZone("Israel");

		// Insert the new calendar
		com.google.api.services.calendar.model.Calendar createdCalendar = calendarService.calendars().insert(calendar).execute();
		calendarId = createdCalendar.getId();
		// Create a new calendar list entry
		CalendarListEntry calendarListEntry = new CalendarListEntry();
		calendarListEntry.setId(calendarId);
		CoursesServiceImpl.someString += "\ncreated calendar";

		// Insert the new calendar list entry
		CalendarListEntry createdCalendarListEntry;
		createdCalendarListEntry = calendarService.calendarList().insert(calendarListEntry).execute();
//			System.out.println(createdCalendarListEntry.getSummary());
		CoursesServiceImpl.someString += "\ninserted calendar: " + createdCalendarListEntry.getSummary();

		String userEmail = UserServiceFactory.getUserService().getCurrentUser().getEmail();
		for(LessonGroup l: lessons) {
			if(l == null)
				continue;
			List<Event> events = createEvents(l);
			for(Event ev: events) {
				ev.setAttendees(Arrays.asList(new EventAttendee().setEmail(userEmail)));
				Event res = calendarService.events().insert(calendarId, ev).execute();
//					System.out.printf("Event created: %s\n", res.getHtmlLink());
				CoursesServiceImpl.someString += "\nEvent created: " + res.getHtmlLink();
			}
		}
	}

	private static GoogleClientSecrets getClientCredential() throws IOException {
	    if (clientSecrets == null) {
	        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
	            new InputStreamReader(CalendarModel.class.getResourceAsStream(clientSecretJson)));
	      }
	  return clientSecrets;
	}
	
	public static String getRedirectUri(HttpServletRequest req) {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath("/oauth2callback");
		return url.build();
	}
	
	public static GoogleAuthorizationCodeFlow newFlow() throws IOException {
	    return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	        getClientCredential(), Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
	        DATA_STORE_FACTORY).setAccessType("offline").build();
	}
	
	private static Calendar getCalendarService(Credential cred) {
		return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred).build();
	}
	
	private static List<Event> createEvents(LessonGroup lg) {
		List<Event> events = new ArrayList<>();
			
		for(Lesson l: lg.getLessons()) {
			if(l == null)
				continue;
			String startTimeStr =
					lessonTimeToRfc(l.getStartTime().getDay(), l.getStartTime().getTime().getHour(), l.getStartTime().getTime().getMinute());
			EventDateTime startTime = new EventDateTime().setDateTime(new DateTime(startTimeStr)).setTimeZone("Israel");
			String endTimeStr =
					lessonTimeToRfc(l.getEndTime().getDay(), l.getEndTime().getTime().getHour(), l.getEndTime().getTime().getMinute());
			EventDateTime endTime = new EventDateTime().setDateTime(new DateTime(endTimeStr)).setTimeZone("Israel");
			
			//create event:
			Event event = new Event()
					.setSummary(l.getCourseId()+"\n"+l.getCourseName())
					.setLocation(l.getPlace()+", "+l.getRoomNumber())
				    .setDescription(String.valueOf(l.getGroup())+"\n"+l.getType().name()+"\n"+l.getRepresenter().getFullName())
				    .setStart(startTime).setEnd(endTime)
				    .setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY"));

			events.add(event);
		}
		
		return events;
	}
	
	private static String lessonTimeToRfc(Day day, int hour, int minute) {
		// TODO: it's just a workaround. need smth better:
		String dayStr = String.valueOf(tokenDay + day.ordinal());
		
		String hourStr = String.valueOf(hour);
		if(hour < 10)
			hourStr = "0"+hourStr;
		
		String minuteStr = String.valueOf(minute);
		if(minute < 10)
			minuteStr = "0"+minuteStr;
		
		return tokenYear+"-"+tokenMonth+"-"+dayStr+"T"+hourStr+":"+minuteStr+":00Z";	// e.g. "1985-04-12T23:20:50.52Z"
	}
	
  /**
   * Returns an {@link IOException} (but not a subclass) in order to work around restrictive GWT
   * serialization policy.
   */
  public static IOException wrappedIOException(IOException e) {
    if (e.getClass() == IOException.class) {
      return e;
    }
    return new IOException(e.getMessage());
}
}
















