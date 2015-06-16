package com.ank.imgshare.app;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ank.imgshare.app.model.Events;
import com.ank.imgshare.app.persist.PMF;

@Controller
@RequestMapping("/events")
public class EventController {

	@ModelAttribute("arr")
	@RequestMapping(value="/allevents",method=RequestMethod.GET)
	public ArrayList<Events> getAllEvents()
	{
		ArrayList<Events> arr = new ArrayList<Events>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Events.class);
		List<Events> list = null;
		try{
			list = (List<Events>) q.execute();
			if(list!= null)
			{
				for(Events e : list)
				{
					arr.add(e);
				}
			}
		}finally{
			q.closeAll();
			pm.close();
		}
		return arr;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String addEvent(HttpServletRequest request,ModelMap map)
	{
		return "addevents";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView addEventToDB(HttpServletRequest request)
	{
		String date = request.getParameter("date");
		String searchText = request.getParameter("search");
		String imageurl = request.getParameter("img");
		String country = request.getParameter("country");
		String language = request.getParameter("lang");
		String eventType = request.getParameter("evetype");
		
		ArrayList<String> searches = new ArrayList<String>();
		if(searchText != null)
		{
			String searchs[] = searchText.split(",");
			for(int i=0;i<searchs.length;i++)
			{
				searches.add(searchs[i]);
			}
		}
		
		Events e = new  Events();
		e.setCountry(country);
		e.setDate(date);
		e.setEventType(eventType);
		e.setImageUrl(imageurl);
		e.setLanguage(language);
		e.setSearchText(searches);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(e);
		}finally{
			pm.close();
		}
		
		return new ModelAndView("redirect:list");
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String listEvents(HttpServletRequest request,ModelMap map)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Events.class);
		List<Events> list = null;
		ArrayList<Events> data = new ArrayList<Events>();
		try{
			list = (List<Events>) q.execute();
			if(list == null)
				map.addAttribute("events", null);
			else{
				for(Events c : list)
					data.add(c);
			}
		}finally{
			q.closeAll();
			pm.close();
		}
		map.addAttribute("events", list);
		return "listevents";
	}
	
	@RequestMapping(value = "/update/{id}",method=RequestMethod.GET)
	public String getUpdateEventUI(@PathVariable String id,ModelMap map)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Long lid = Long.parseLong(id);
		try{
			Events e = (Events) pm.getObjectById(Events.class,lid);
			if(e!=null)
				map.addAttribute("events",e);
		}finally
		{
			pm.close();
		}
		
		return "updateevent";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ModelAndView updateEvent(HttpServletRequest request,ModelMap map)
	{
		String date = request.getParameter("date");
		String searchText = request.getParameter("search");
		String imageurl = request.getParameter("img");
		String country = request.getParameter("country");
		String language = request.getParameter("lang");
		String eventType = request.getParameter("eventtype");
		String id = request.getParameter("id");
		Long lid = Long.parseLong(id);
		ArrayList<String> searches = new ArrayList<String>();
		if(searchText != null)
		{
			String searchs[] = searchText.split(",");
			for(int i=0;i<searchs.length;i++)
			{
				searches.add(searchs[i]);
			}
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Events e = pm.getObjectById(Events.class,lid);
			e.setCountry(country);
			e.setDate(date);
			e.setEventType(eventType);
			e.setImageUrl(imageurl);
			e.setLanguage(language);
			e.setSearchText(searches);
		}finally{
			pm.close();
		}
		return new ModelAndView("redirect:list");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public ModelAndView deleteEvent(@PathVariable String id,ModelMap map)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Long l = Long.parseLong(id);
		try{
			Events e = pm.getObjectById(Events.class,l);
			pm.deletePersistent(e);
		}
		finally{
			pm.close();
		}
		
		return new ModelAndView("redirect:../list");
	}
}
