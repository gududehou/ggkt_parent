package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.listener.SubjectListener;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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

    @Autowired
    private SubjectListener subjectListener;

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
     * 导出课程分类
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //从数据库里面，把所有分类查询出来
            List<SubjectEeVo> subjectEeVoList = baseMapper.selectList(null).stream().map(subject -> {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
                return subjectEeVo;
            }).collect(Collectors.toList());
            //开始导出
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(subjectEeVoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Excel导入
     *
     * @param file
     */
    @Override
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class, subjectListener).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
