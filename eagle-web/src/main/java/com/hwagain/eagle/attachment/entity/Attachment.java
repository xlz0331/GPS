package com.hwagain.eagle.attachment.entity;

import java.io.Serializable;
import java.util.Date;
import com.hwagain.framework.mybatisplus.annotations.TableField;
import com.hwagain.framework.mybatisplus.annotations.TableId;

/**
 * <p>
 * 附件表
对于数量只有唯一一张的，如竹木原材料收购类别，如果是创建人提交的并且记录已存在，则覆盖原来的附件，并且更新表数据；否则state=2，并且做记录新增动作
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId("fd_id")
	private Long fdId;
    /**
     * 附件类型。1:任务图片(task_photo)
     */
	@TableField("attach_type")
	private Integer attachType;
    /**
     * 项目类别。可为空，如果是竹木原材料收购，则1-车牌，2-车头，3-货物，4-车尾
     */
	@TableField("item_type")
	private Integer itemType;
    /**
     * 关联id
     */
	@TableField("related_id")
	private Long relatedId;
    /**
     * 相对路径
     */
	@TableField("related_path")
	private String relatedPath;
    /**
     * 文件扩展名
     */
	private String ext;
    /**
     * 附件大小
     */
	private Integer size;
    /**
     * 状态。1：正常；2：已作废
     */
	private Integer state;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建人id
     */
	@TableField("creator_id")
	private Long creatorId;
    /**
     * 最后修改时间
     */
	@TableField("last_alter_time")
	private Date lastAlterTime;
    /**
     * 最后修改人id
     */
	@TableField("last_altor_id")
	private Long lastAltorId;


	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public Integer getAttachType() {
		return attachType;
	}

	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public String getRelatedPath() {
		return relatedPath;
	}

	public void setRelatedPath(String relatedPath) {
		this.relatedPath = relatedPath;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getLastAlterTime() {
		return lastAlterTime;
	}

	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}

	public Long getLastAltorId() {
		return lastAltorId;
	}

	public void setLastAltorId(Long lastAltorId) {
		this.lastAltorId = lastAltorId;
	}

}
