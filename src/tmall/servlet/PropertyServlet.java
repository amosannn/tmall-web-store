package tmall.servlet;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;

public class PropertyServlet extends BaseBackServlet{

	public String add(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		
		Property p = new Property();
		String name = request.getParameter("name");
		p.setName(name);
		p.setCategory(c);
		propertyDAO.add(p);
		return "@admin_property_list?cid="+cid;
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		Property p = propertyDAO.get(id);
		propertyDAO.delete(id);
		return "@admin_property_list?cid="+p.getCategory().getId();
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		Property p = propertyDAO.get(id);
		request.setAttribute("p", p);
		return "admin/editProperty.jsp";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		
		Property p = new Property();
		p.setCategory(c);
		String name = request.getParameter("name");
		p.setName(name);
		int id = Integer.parseInt(request.getParameter("id"));
		p.setId(id);
		propertyDAO.update(p);
		return "@admin_property_list?cid="+p.getCategory().getId();
	}
	
	public String list(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		List<Property> ps = propertyDAO.list(cid, page.getStart(), page.getCount());
		int total = propertyDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		
		request.setAttribute("c", c);
		request.setAttribute("ps", ps);
		request.setAttribute("page", page);
		return "admin/listProperty.jsp";
	}
}
