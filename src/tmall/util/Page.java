package tmall.util;

public class Page {
	int start;// 每页首条项目id号
	int count;
	int total;
	String param;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Page(int start, int count){
		super();
		this.start = start;
		this.count = count;
	}
	
	public boolean isHasPreviouse(){
		if(start == 0)
			return false;
		return true;
	}
	public boolean isHasNext(){
		if(start == getLast())
			return false;
		return true;
	}
	
	/**
	 * ��ȡ��ҳ��
	 * @return
	 */
	public int getTotalPage(){
		int totalPage;
		//����������50�����ܹ���5�����ģ���ô����10ҳ
		if(0 == total % count)
			totalPage = total/count;
		//����������51���޷���5��������ô����11ҳ
		else
			totalPage = total/count + 1;
		
		if(0 == totalPage)
			totalPage = 1;
		return totalPage;
	}
	
	/**
	 * ��ȡ���һҳ���������ݱ��start
	 * @return
	 */
	public int getLast(){
		int last;
		//��������Ϊ50���ܹ���5��������ô���һҳ��ʼ����45
		if(0 == total % count)
			last = total-count;
		//��������Ϊ51���޷���5��������ô���һҳ��ʼ����50
		else 
			last = total - total % count;
		
		last = last<0?0:last;
		return last;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	
}
