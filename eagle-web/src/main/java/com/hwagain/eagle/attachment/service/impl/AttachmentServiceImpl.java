package com.hwagain.eagle.attachment.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwagain.eagle.attachment.dto.AttachmentDto;
import com.hwagain.eagle.attachment.entity.Attachment;
import com.hwagain.eagle.attachment.mapper.AttachmentMapper;
import com.hwagain.eagle.attachment.service.IAttachmentService;
import com.hwagain.framework.core.util.Assert;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 附件表
对于数量只有唯一一张的，如竹木原材料收购类别，如果是创建人提交的并且记录已存在，则覆盖原来的附件，并且更新表数据；否则state=2，并且做记录新增动作 服务实现类
 * </p>
 *
 * @author lufl
 * @since 2019-02-25
 */
@Service("attachmentService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(Attachment.class, AttachmentDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(AttachmentDto.class, Attachment.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public String save(AttachmentDto attachmentDto) {
		Assert.notNull(attachmentDto.getRelatedPath(), "相对路径不能为空");
		validate(attachmentDto, false);
		Attachment entity = dtoToEntityMapper.map(attachmentDto, Attachment.class);
		entity.setCreateTime(new Date());
		super.insert(entity);
		return "保存成功";
	}
	
	private void validate(AttachmentDto attachmentDto, boolean isUpdate ){
//		Assert.notNull(attachmentDto.getRelatedId(), "关联ID不能为空");
		Assert.notNull(attachmentDto.getRelatedPath(), "相对路径不能为空");
//		Assert.notNull(attachmentDto.getExt(), "文件扩展名不能为空");
//		Assert.notNull(attachmentDto.getSize(), "附件大小不能为空");
//		Assert.notNull(attachmentDto.getState(), "状态不能为空");
		if (isUpdate) {
			Assert.notNull(attachmentDto.getLastAltorId(), "最后修改人ID不能为空");
		} else {
			Assert.notNull(attachmentDto.getCreatorId(), "创建人ID不能为空");
		}
	}
}
