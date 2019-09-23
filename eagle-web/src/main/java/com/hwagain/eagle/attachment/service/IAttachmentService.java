package com.hwagain.eagle.attachment.service;

import com.hwagain.eagle.attachment.dto.AttachmentDto;
import com.hwagain.eagle.attachment.entity.Attachment;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 * 附件表
对于数量只有唯一一张的，如竹木原材料收购类别，如果是创建人提交的并且记录已存在，则覆盖原来的附件，并且更新表数据；否则state=2，并且做记录新增动作 服务类
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
public interface IAttachmentService extends IService<Attachment> {

	String save(AttachmentDto companyDto);
	
}
