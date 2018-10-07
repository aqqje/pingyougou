package entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类
 * @author AqqJe
 *
 */
public class PageResult implements Serializable{

	private Long totle; // 总记录数
	private List rows; // 当前页记录
	public PageResult(Long totle, List rows) {
		super();
		this.totle = totle;
		this.rows = rows;
	}
	public Long getTotle() {
		return totle;
	}
	public void setTotle(Long totle) {
		this.totle = totle;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
