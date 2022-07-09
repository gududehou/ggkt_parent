package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Mr.Liu
 * @since 2022-07-08
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping(value = "/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeById(@ApiParam(name = "id", value = "ID", required = true) @PathVariable Long id) {
        teacherService.removeById(id);
        return Result.ok("删除讲师成功");
    }

    @ApiOperation(value = "批量删除讲师")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            teacherService.removeByIds(idList);
        }
        return Result.ok("批量删除成功");
    }

    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public Result findAll() {
        List<Teacher> list = teacherService.list();
        return Result.ok(list, "查询所有讲师成功");
    }

    @ApiOperation(value = "获取分页列表")
    @PostMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "teacherVo", value = "查询对象", required = false) @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        IPage<Teacher> pageModel = teacherService.queryTeacherByPage(page, limit, teacherQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("save")
    public Result save(@RequestBody @Valid Teacher teacher) {
        teacherService.save(teacher);
        return Result.ok("添加讲师成功");
    }

    @ApiOperation(value = "根据id获取讲师")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher, "查询讲师成功");
    }

    @ApiOperation(value = "修改讲师")
    @PutMapping("update")
    public Result updateById(@Valid @RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.ok("修改讲师成功");
    }
}

