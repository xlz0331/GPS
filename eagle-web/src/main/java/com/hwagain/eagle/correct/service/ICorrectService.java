package com.hwagain.eagle.correct.service;

import java.util.List;

import com.hwagain.eagle.correct.dto.CorrectDto;
import com.hwagain.eagle.correct.entity.Correct;
import com.hwagain.eagle.correct.entity.CorrectVo;
import com.hwagain.eagle.region.entity.Region;
import com.hwagain.eagle.task.entity.TaskResultVo;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xionglz
 * @since 2019-05-11
 */
public interface ICorrectService extends IService<Correct> {

	List<CorrectDto> subsidyCorrect(String ids);

	List<CorrectDto> sentToOA(List<CorrectDto> dtos);

	List<TaskResultVo> findAllSentOA();

	List<Region> findRegionByParam(String supplierName, String province, String material, String city);

	List<CorrectVo> findByOaCode(String oaCode);

	String audit(Integer state, String reason, String oaCode,Long correntId,String node, String empName, String empNo,String flowDjbh,
			String flowDjlsh);

	List<CorrectDto> findAllAudited();

	List<TaskResultVo> queryTaskResultByOaCode(String oaCode);

	List<TaskResultVo> findAllAudited1();

	
	
}
