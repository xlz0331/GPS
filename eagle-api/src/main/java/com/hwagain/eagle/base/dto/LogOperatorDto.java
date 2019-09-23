package com.hwagain.eagle.base.dto;

import java.util.Date;

import java.io.Serializable;


/**
 * <p>
 * 操作日志表。
注意：以后开发平台的日志服务上线后，需要做功能迁移
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
public class LogOperatorDto implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long fdId;
    /**
     * 业务编码
     */
	private Integer code;
    /**
     * 操作类型
     */
	private Integer operateType;
    /**
     * 操作人id
     */
	private Long operatorId;
    /**
     * 操作时间
     */
	private Date createTime;
    /**
     * 状态。1：已处理，2：未处理
     */
	private Integer state;
    /**
     * 内容。如果超过256，建议压缩
     */
	private String content;
    /**
     * 是否已压缩
     */
	private Integer compress;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCompress() {
		return compress;
	}

	public void setCompress(Integer compress) {
		this.compress = compress;
	}

}
