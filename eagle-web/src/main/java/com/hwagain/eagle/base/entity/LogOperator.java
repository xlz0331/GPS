package com.hwagain.eagle.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;
import com.hwagain.framework.mybatisplus.annotations.TableName;

/**
 * <p>
 * 操作日志表。
注意：以后开发平台的日志服务上线后，需要做功能迁移
 * </p>
 *
 * @author xionglz
 * @since 2019-03-11
 */
@TableName("log_operator")
public class LogOperator implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId("fd_id")
	private Long fdId;
    /**
     * 业务编码
     */
	private Integer code;
    /**
     * 操作类型
     */
	@TableField("operate_type")
	private Integer operateType;
    /**
     * 操作人id
     */
	@TableField("operator_id")
	private Long operatorId;
    /**
     * 操作时间
     */
	@TableField("create_time")
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
