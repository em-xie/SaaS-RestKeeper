package ${package.Controller};


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import com.itheima.user.vo.PageVO;
<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
* @version v1.0
*/
@Slf4j
<#if restControllerStyle>
    @Api(tags = {"${table.comment!}"})
    @RestController
<#else>
    @Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
        public class ${table.controllerName} {
    </#if>
    @Autowired
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};

    /**
    * 查询分页数据
    */
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
    @ApiImplicitParam(paramType="query", name = "page", value = "当前页码", required = false, dataType = "Integer"),
    @ApiImplicitParam(paramType="query", name = "pagesize", value = "分大小", required = false, dataType = "Integer"),
    @ApiImplicitParam(paramType="query", name = "searchMap", value = "任意参数组合", required = false, dataType = "Map")
    })
    @PostMapping(value = "/pagelist")
    public PageVO<${entity}> findListByPage(@RequestParam(name = "page", defaultValue = "1") int pageNum,@RequestParam(name = "pagesize", defaultValue = "20") int pageSize, @RequestParam Map searchMap){
    IPage<${entity}> page = new Page<${entity}>(pageNum, pageSize);
    QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
    if(searchMap!=null&!searchMap.isEmpty()) {
    queryWrapper.allEq(searchMap);
    }
    return new PageVO<${entity}>(${(table.serviceName?substring(1))?uncap_first}.page(page,queryWrapper));
    }


    @ApiOperation(value = "查询数据(不分页)")
    @ApiImplicitParam(paramType="query", name = "searchMap", value = "任意参数组合", required = false, dataType = "Map")
    @PostMapping("/list")
    @ResponseBody
    public List<${entity}> list(@RequestParam Map searchMap) {
    QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
    if(searchMap!=null&!searchMap.isEmpty()) {
    queryWrapper.allEq(searchMap);
    }
    return ${(table.serviceName?substring(1))?uncap_first}.list(queryWrapper);
    }


    /**
    * 根据id查询
    */
    @ApiOperation(value = "根据id查询数据")
    @ApiImplicitParam(paramType="query", name = "pkid", value = "主键", required = true, dataType = "String")
    @PostMapping(value = "/getById")
    public ${entity} getById(@RequestParam("pkid") String pkid){
    return ${(table.serviceName?substring(1))?uncap_first}.getById(pkid);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增数据")
    @ApiImplicitParam(paramType="body", name = "${entity?uncap_first}", value = "json数据", required = true, dataType = "${entity}")
    @PostMapping(value = "/add")
    public boolean add(@RequestBody ${entity} ${entity?uncap_first}){
    return ${(table.serviceName?substring(1))?uncap_first}.save(${entity?uncap_first});
    }

    /**
    * 删除
    */
    @ApiOperation(value = "删除数据")
    @ApiImplicitParam(paramType="query", name = "ids", value = "支持主键批量删除", required = true, dataType = "List")
    @PostMapping(value = "/del")
    public boolean delete(@RequestParam("ids") List<String> ids){
    return ${(table.serviceName?substring(1))?uncap_first}.removeByIds(ids);
    }

    /**
    * 修改
    */
    @ApiOperation(value = "更新数据")
    @ApiImplicitParam(paramType="body", name = "${entity?uncap_first}", value = "json数据", required = true, dataType = "${entity}")
    @PostMapping(value = "/update")
    public boolean update(@RequestBody ${entity} ${entity?uncap_first}){
    return ${(table.serviceName?substring(1))?uncap_first}.updateById(${entity?uncap_first});
    }

    }
</#if>