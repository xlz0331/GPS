package com.hwagain.eagle.attachment.mapper;

import com.hwagain.eagle.attachment.entity.Attachment;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 附件表
对于数量只有唯一一张的，如竹木原材料收购类别，如果是创建人提交的并且记录已存在，则覆盖原来的附件，并且更新表数据；否则state=2，并且做记录新增动作 Mapper 接口
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {

}