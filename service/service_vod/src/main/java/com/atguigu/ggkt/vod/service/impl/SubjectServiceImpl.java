package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Mr.Liu
 * @since 2022-07-09
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * 查询下一层的课程分类
     *
     * @param id parent_id
     * @return
     */
    @Override
    public List<Subject> selectList(Long id) {
        return baseMapper.selectList(new LambdaQueryWrapper<Subject>().eq(Subject::getParentId, id)).stream()
                .peek(subject -> {
                    //当前节点是否有子节点
                    subject.setHasChildren(hasChildren(subject.getId()));
                }).collect(Collectors.toList());
    }

    /**
     * 判断当前节点是否有子节点
     *
     * @param id 当前节点
     * @return
     */
    private boolean hasChildren(Long id) {
        return baseMapper.selectCount(new LambdaQueryWrapper<Subject>().eq(Subject::getParentId, id)) > 0;
    }
}
