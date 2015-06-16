package com.ank.imgshare.app;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ank.imgshare.app.model.ImageCategory;
import com.ank.imgshare.app.persist.PMF;

@Controller
@RequestMapping("/images")
public class ImageShareController {
	
	
	@ModelAttribute("img")
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public ImageCategory getImageCategoryTest()
	{
		long id = 1;
		ImageCategory img = new ImageCategory();
		img.setId(id);
		img.setCountry("ind");
		img.setHasSubCategory(false);
		img.setImageUrl("images-url");
		img.setLanguage("eng");
		img.setParentCategoryId(id);
		img.setSearchText("test search string");
		img.setTitle("test title");
		img.setVersion(1);
		
		return img;
	}
	
	@ModelAttribute("arr")
	@RequestMapping(value="/allcategories",method=RequestMethod.GET)
	public ArrayList<ImageCategory> getAllImageCategories()
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		ArrayList<ImageCategory> arr = new ArrayList<ImageCategory>();
		
		Query q = pm.newQuery(ImageCategory.class);
		
		List<ImageCategory> list;
		
		try{
			list = (List<ImageCategory>) q.execute();
			
			if(list!= null)
			{
				for(ImageCategory c : list)
				{
					arr.add(c);
				}
			}
		}finally{
			q.closeAll();
			pm.close();
		}
		
		return arr;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAddCategoryPage(ModelMap map)
	{
		return "addcategory";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView addCategory(HttpServletRequest request){
		String version = request.getParameter("version");
		String title = request.getParameter("title");
		String searchText = request.getParameter("search");
		String imageurl = request.getParameter("img");
		String country = request.getParameter("country");
		String language = request.getParameter("lang");
		String hasSubCategory = request.getParameter("subcat");
		String parent = request.getParameter("parent");
		boolean subcat = (hasSubCategory!=null && hasSubCategory.equalsIgnoreCase("yes"))?true:false;
		
		ImageCategory category = new ImageCategory();
		category.setVersion(Integer.parseInt(version));
		category.setTitle(title);
		category.setSearchText(searchText);
		category.setImageUrl(imageurl);
		category.setCountry(country);
		category.setLanguage(language);
		category.setParentCategoryId(Long.parseLong(parent));
		category.setHasSubCategory(subcat);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(category);
		}finally{
			pm.close();
		}
		
		return new ModelAndView("redirect:list");
	}
	
	@RequestMapping(value = "/list" , method = RequestMethod.GET)
	public String getCategoryListPage(ModelMap map)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(ImageCategory.class);
		List<ImageCategory> list = null;
		ArrayList<ImageCategory> data = new ArrayList<ImageCategory>();
		try{
			list = (List<ImageCategory>) q.execute();
			if(list == null)
				map.addAttribute("categories", null);
			else{
				for(ImageCategory c : list)
					data.add(c);
			}
		}finally{
			q.closeAll();
			pm.close();
		}
		map.addAttribute("categories", list);
		return "listcategory";
	}
}
