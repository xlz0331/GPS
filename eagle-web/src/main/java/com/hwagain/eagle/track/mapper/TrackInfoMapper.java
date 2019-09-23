package com.hwagain.eagle.track.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hwagain.eagle.track.entity.TrackAnalyze;
import com.hwagain.eagle.track.entity.TrackInfo;
import com.hwagain.eagle.track.entity.TrackInfoVo;
import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2019-02-22
 */
public interface TrackInfoMapper extends BaseMapper<TrackInfo> {
	public List<TrackInfoVo> findTrackByTaskId(@Param("taskId") Long taskId);
	public List<TrackAnalyze> findAllTrackAnalyze(@Param("startTime") String startTime,@Param("endTime") String endTime);
	public List<TrackAnalyze> findAllTrackAnalyze2(@Param("startTime") String startTime,@Param("endTime") String endTime);
	public List<TrackAnalyze> findAllTaskTrackAnalyze(@Param("startTime") String startTime,@Param("endTime") String endTime);
	public List<TrackAnalyze> findAllTaskTrackAnalyze2(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("supplierName") String supplierName);
}