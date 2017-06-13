package tmall.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Order;
import tmall.bean.Product;
import tmall.dao.OrderDAO;
import tmall.dao.ProductDAO;
import tmall.util.Page;

public class OrderServlet extends BaseBackServlet{
	public String add(HttpServletRequest request, HttpServletResponse response, Page page){
		return null;
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page){
		return null;
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page){
		return null;
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response, Page page){
		return null;
	}
	
	public String list(HttpServletRequest request, HttpServletResponse response, Page page){
		List<Order> os = orderDAO.list(page.getStart(), page.getCount());
		orderItemDAO.fill(os);
		int total = orderDAO.getTotal();
		page.setTotal(total);
		
		request.setAttribute("os", os);
		request.setAttribute("page", page);
		return "admin/listOrder.jsp";
	}
	
	public String delivery(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		Order o = orderDAO.get(id);
		o.setDeliveryDate(new Date());
		o.setStatus(OrderDAO.waitConfirm);
		orderDAO.update(o);
		
		return "@admin_order_list";
	}
	
	public String refund(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("id"));
		Order o = orderDAO.get(oid);
		o.setStatus(orderDAO.refunded);
		orderDAO.update(o);
		
		return "@admin_order_list";
	}
	
	public String search(HttpServletRequest request, HttpServletResponse response, Page page){
		String keyword = request.getParameter("keyword");
		System.out.println(keyword+"  "+page.getStart());
		List<Order> os = orderDAO.search(keyword, page.getStart(), page.getCount());
		
//		List<Order> os = orderDAO.list(page.getStart(), page.getCount());
		orderItemDAO.fill(os);
		int total = orderDAO.getSearchTotal(keyword);
		System.out.println(total);
		page.setTotal(total);
		
		request.setAttribute("os", os);
		request.setAttribute("page", page);
		return "admin/listOrder.jsp";
	}
	
}
