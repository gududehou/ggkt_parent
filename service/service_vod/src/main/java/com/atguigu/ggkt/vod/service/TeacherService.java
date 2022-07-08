package com.atguigu.ggkt.vod.service;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Mr.Liu
 * @since 2022-07-08
 */
public interface TeacherService extends IService<Teacher> {

    IPage<Teacher> queryTeacherByPage(Long page, Long limit, TeacherQueryVo teacherQueryVo);
}
