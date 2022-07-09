package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Mr.Liu
 * @since 2022-07-09
 */
public interface SubjectService extends IService<Subject> {

    List<Subject> selectList(Long id);
}
