package com.spdb.training.socket.trans.or01;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import com.spdb.training.beans.stock.Stock;

/**
 * @author 作者: 王腾蛟
 * @version time：2019年6月19日 上午10:20:56 类说明:
 */
@XmlType(propOrder = { "nums", "row" })
public class RspOR01ServiceBody {

	@XmlElement(name = "nums")
	private String nums;
	@XmlElement(name = "")
	private List<Stock> row;

	@XmlTransient
	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	@XmlTransient
	public List<Stock> getRow() {
		return row;
	}

	public void setRow(List<Stock> row) {
		this.row = row;
	}

}
