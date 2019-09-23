package com.hwagain.eagle.supplier.dto;

import java.io.Serializable;

public class MaterialPurchasePlanRptDto extends MaterialPurchasePlanDto implements Serializable  {
	private static final long serialVersionUID = 1L;
	//序号
	private Integer seq;
	
	//序号文本
	private String seqText;
	
	//数量
	private Integer count;
	
	//数据行类型-0:正常的明细数据;1:算薪部门(需合并单元格);2:小计行;3:总计行;
	private Integer rowType;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getSeqText() {
		return seqText;
	}

	public void setSeqText(String seqText) {
		this.seqText = seqText;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRowType() {
		return rowType;
	}

	public void setRowType(Integer rowType) {
		this.rowType = rowType;
	}
	
}
