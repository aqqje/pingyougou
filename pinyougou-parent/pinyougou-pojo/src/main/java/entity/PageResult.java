package entity;

import java.util.List;

/**
 * 分页实体类
 * @author AqqJe
 *
 */
public class PageResult {

	private Long totle; // 总记录数
	private List row; // 当前页记录
	public PageResult(Long totle, List row) {
		super();
		this.totle = totle;
		this.row = row;
	}
	public Long getTotle() {
		return totle;
	}
	public void setTotle(Long totle) {
		this.totle = totle;
	}
	public List getRow() {
		return row;
	}
	public void setRow(List row) {
		this.row = row;
	}
	
}
