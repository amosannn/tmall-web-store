package tmall.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.PropertyValue;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.comparator.ProductAllComparator;
import tmall.comparator.ProductDateComparator;
import tmall.comparator.ProductPriceComparator;
import tmall.comparator.ProductReviewComparator;
import tmall.comparator.ProductSaleCountComparator;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.util.Page;

public class ForeServlet extends BaseForeServlet{
	public String home(HttpServletRequest request, HttpServletResponse response, Page page){
		
		List<Category> cs = categoryDAO.list();
		new ProductDAO().fill(cs);
		new ProductDAO().fillByRow(cs);
		
		request.setAttribute("cs", cs);
		return "home.jsp";
	}
	
	public String register(HttpServletRequest request, HttpServletResponse response, Page page){
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		name = HtmlUtils.htmlEscape(name);
		System.out.println(name);
		
		if(userDAO.isExist(name)){
			request.setAttribute("msg", "该用户名已存在");
			return "register.jsp";
		}
		
		
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		userDAO.add(user);
		
		return "@registerSuccess.jsp";
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response, Page page){
		String name = request.getParameter("name");
		name = HtmlUtils.htmlEscape(name);// 转义 
		String password = request.getParameter("password");
		User user = userDAO.get(name, password);
		
		if(null == user){
			request.setAttribute("msg", "账号或密码错误");
			return "login.jsp";
		}
		
		request.getSession().setAttribute("user", user);
		return "@forehome";
	}
	
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page){
		request.getSession().removeAttribute("user");
		return "@forehome";
	}
	
	public String product(HttpServletRequest request, HttpServletResponse response, Page page){
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		
		List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
		List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
		
		p.setProductDetailImages(productDetailImages);
		p.setProductSingleImages(productSingleImages);
		
		List<PropertyValue> pvs = propertyValueDAO.list(pid);
		
		List<Review> reviews = reviewDAO.list(pid);
		
		productDAO.setSaleAndReviewNumber(p);
		
		request.setAttribute("reviews", reviews);
		
		request.setAttribute("p", p);
		request.setAttribute("pvs", pvs);
		return "product.jsp";
	}
	
	/**
	 * 若取到session中的user则返回success，表示用户已登录。
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User)request.getSession().getAttribute("user");
		
		if(null != user){
			return "%success";
		}
		return "%fail";
	}
	
	/**
	 * 模态窗口点击登录后ajax请求该方法返回值确定账号密码正确性及用户是否存在。
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page){
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		User user = userDAO.get(name, password);
		
		if(null == user){
			return "%fail";
		}
		
		request.getSession().setAttribute("user", user);
		return "%success";
	}
	
	/**
	 * 特定分类页，可排序（按价格销量评论数等）
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String category(HttpServletRequest request, HttpServletResponse response, Page page){
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		Category c = new CategoryDAO().get(cid);
		new ProductDAO().fill(c);
		new ProductDAO().setSaleAndReviewNumber(c.getProducts());
		
		String sort = request.getParameter("sort");
		if(null!=sort){
			switch (sort) {
			case "review":
				Collections.sort(c.getProducts(), new ProductReviewComparator());
				break;
			case "date":
				Collections.sort(c.getProducts(), new ProductDateComparator());
				break;
			case "saleCount":
				Collections.sort(c.getProducts(), new ProductSaleCountComparator());
				break;	
			case "price":
				Collections.sort(c.getProducts(), new ProductPriceComparator());
				break;
			case "all":
				Collections.sort(c.getProducts(), new ProductAllComparator());
				break;
			}
		}
		
		request.setAttribute("c", c);
		return "category.jsp";
	}
	
	public String search(HttpServletRequest request, HttpServletResponse response, Page page){
		String keyword = request.getParameter("keyword");
		List<Product> ps = new ProductDAO().search(keyword, 0, 20);
		productDAO.setSaleAndReviewNumber(ps);
		request.setAttribute("ps", ps);
		return "searchResult.jsp";
	}
	
	/**
	 * 用于立即购买的响应
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String buyone(HttpServletRequest request, HttpServletResponse response, Page page){
		int number = Integer.parseInt(request.getParameter("num"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		int oiid = 0;
		User user = (User)request.getSession().getAttribute("user");
		
		boolean found = false;
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for(OrderItem oi : ois){
			if(oi.getProduct().getId() == p.getId()){
				oi.setNumber(oi.getNumber()+number);
				orderItemDAO.update(oi);
				found = true;
				oiid = oi.getId();
				break;
			}
		}
		
		if(!found){
			OrderItem oi = new OrderItem();
			oi.setNumber(number);
			oi.setProduct(p);
			oi.setUser(user);
			orderItemDAO.add(oi);
			oiid = oi.getId();
		}
		
		return "@forebuy?oiid="+oiid;
	}
	
	/**
	 * 接收来自立即购买的请求或来自购物车内的购买请求
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String buy(HttpServletRequest request, HttpServletResponse response, Page page){
		String[] oiids = request.getParameterValues("oiid");
		List<OrderItem> ois = new ArrayList<>();
		float selectedPrice =  0;
		
		for(String tempOiid : oiids){
			int oiid = Integer.parseInt(tempOiid);
			OrderItem oi = orderItemDAO.get(oiid);
			selectedPrice += oi.getNumber()*oi.getProduct().getPromotePrice();
			ois.add(oi);
		}
		
		request.getSession().setAttribute("ois", ois);
		request.setAttribute("total", selectedPrice);
		return "buy.jsp";
	}
	
	/**
	 * 添加购物车操作，分购物车中已有该商品和没有该商品两种情况考虑
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String addCart(HttpServletRequest request, HttpServletResponse response, Page page){
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		Product p = productDAO.get(pid);
		boolean found = false;
		
		User user = (User)request.getSession().getAttribute("user");
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for(OrderItem oi : ois){
			if(p.getId() == oi.getProduct().getId()){
				oi.setNumber(oi.getNumber()+num);
				orderItemDAO.update(oi);
				found = true;
				break;
			}
		}
		
		if(!found){
			OrderItem oi = new OrderItem();
			oi.setUser(user);
			oi.setNumber(num);
			oi.setProduct(p);
			orderItemDAO.add(oi);
		}
		return "%success";
	}
	
	public String cart(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User)request.getSession().getAttribute("user");
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		request.setAttribute("ois", ois);
		return "cart.jsp";
	}
	
	/**
	 * Ajax实时更改购物车中某商品的数量
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User)request.getSession().getAttribute("user");
		if(null == user){
			return "%fail";
		}
		
//		cartPage传递oiid而不是pid，就不用迭代购物车并比较product，减少系统负担（需更改cartPage中的函数）
//		int oiid = Integer.parseInt(request.getParameter("oiid"));
//		int number = Integer.parseInt(request.getParameter("number"));
//		OrderItem oi = orderItemDAO.get(oiid);
//		oi.setNumber(number);
//		orderItemDAO.update(oi);
		
		int pid = Integer.parseInt(request.getParameter("pid"));
	    int number = Integer.parseInt(request.getParameter("number"));
	    List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
	    for (OrderItem oi : ois) {
	        if(oi.getProduct().getId()==pid){
	            oi.setNumber(number);
	            orderItemDAO.update(oi);
	            break;
	        }
	         
	    }
	    
		return "%success";
	}
	
	/**
	 * 删除购物车中的某商品
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User)request.getSession().getAttribute("user");
		if(null == user)
			return "%fail";
		int oiid = Integer.parseInt(request.getParameter("oiid"));
		orderItemDAO.delete(oiid);
		return "%success";
	}
	
	public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		
		List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
		if(ois.isEmpty()){
			return "@login.jsp";//观察跳转时候的地址栏会不会变成login。jsp
		}
		
		String address = request.getParameter("address");
		String post = request.getParameter("post");
		String receiver = request.getParameter("receiver");
		String mobile = request.getParameter("mobile");
		String userMessage = request.getParameter("userMessage");
		
		Order order = new Order();
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) 
				+ RandomUtils.nextInt(10000);
		
		order.setOrderCode(orderCode);
		order.setAddress(address);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		order.setUserMessage(userMessage);
		order.setCreateDate(new Date());
		order.setUser(user);
		order.setStatus(OrderDAO.waitPay);
		
		orderDAO.add(order);
		float total = 0;
		for(OrderItem oi:ois){
			oi.setOrder(order);//将购物车中的每个订单项标记在同一个订单下
			orderItemDAO.update(oi);
			total+=oi.getProduct().getPromotePrice()*oi.getNumber();
		}
		return "@forealipay?oid=" + order.getId() + "&total=" + total; 
				
	}
	
	public String alipay(HttpServletRequest request, HttpServletResponse response, Page page){
		return "alipay.jsp";
	}
	
	public String payed(HttpServletRequest request, HttpServletResponse response, Page page){
//		User user = (User) request.getSession().getAttribute("user");
//		if(null == user){
//			return "@login.jsp";
//		}
		
		int oid = Integer.parseInt(request.getParameter("oid"));
		
		// 此处不取，total这个parameter依旧可以在下一个页面（请求转发才行，重定向request作用域会失效）中取出，不会失效。
//		float total = Float.parseFloat(request.getParameter("total"));
		Order order = orderDAO.get(oid);
		order.setStatus(orderDAO.waitDelivery);
		order.setPayDate(new Date());
		orderDAO.update(order);
		request.setAttribute("o", order);
		
		return "payed.jsp";
	}
	
	/**
	 * 我的订单页面
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String bought(HttpServletRequest request, HttpServletResponse response, Page page){
		User user = (User) request.getSession().getAttribute("user");
		List<Order> os = orderDAO.list(user.getId(), orderDAO.delete);
		
		orderItemDAO.fill(os);
		
		request.setAttribute("os", os);
		
		return "bought.jsp";
	}
	
	/**
	 * 付款到中间商，状态改为确认收货
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page){
		// 在我的订单页面重启tomcat测试用户未登录状态是否也可以成功跳转  ×   ForeAuthFilter生效，未登录状态会跳转至登陆页面 
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		
		orderItemDAO.fill(order);
		
		request.setAttribute("o", order);
		
		return "confirmPay.jsp";
	}
	
	/**
	 * 最终的确认付款（已收货），更改状态为待评价并且设置确认收货时间
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String orderConfirmed(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		order.setStatus(orderDAO.waitReview);
		order.setConfirmDate(new Date());
		orderDAO.update(order);
		return "orderConfirmed.jsp";
	}
	
	/**
	 * 删除订单，仅设置订单状态为delete，ajax隐藏改状态订单
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		order.setStatus(orderDAO.delete);
		orderDAO.update(order);
		return "%success";
	}
	
	/**
	 * 进入评价页面
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	public String review(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		orderItemDAO.fill(order);
		Product p = order.getOrderItems().get(0).getProduct();
		List<Review> reviews = reviewDAO.list(p.getId());
		productDAO.setSaleAndReviewNumber(p);
		request.setAttribute("p", p);
		request.setAttribute("o", order);
		request.setAttribute("reviews", reviews);
		return "review.jsp";
	}
	
	public String doreview(HttpServletRequest request, HttpServletResponse response, Page page){
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(orderDAO.finish);
		orderDAO.update(o);
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		
		String content = request.getParameter("content");
		content = HtmlUtils.htmlEscape(content);
		
		User user = (User) request.getSession().getAttribute("user");
		Review review = new Review();
		review.setContent(content);
		review.setProduct(p);
		review.setCreateDate(new Date());
		review.setUser(user);
		reviewDAO.add(review);
		
		return "@forereview?oid="+oid+"&showonly=true";
	}
}
