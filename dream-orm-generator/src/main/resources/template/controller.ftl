package ${controllerPackageName};

import ${serviceClassName};
import ${voClassName};
import ${boClassName};
import ${dtoClassName};
import com.dream.system.config.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
* @Description: ${remark!''}
* @Author: ${author!''}
* @Date: ${dateTime!''}
*/
@RequiredArgsConstructor
@RestController
public class ${controllerName} {

    private final ${serviceName} service;

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public Page<${voName}> list(${dtoName} ${dtoName?uncap_first}, Page page) {
        return service.selectPage(${dtoName?uncap_first}, page);
    }

    /**
     * 查主键询
     *
     * @param id 主键
     */
    @GetMapping("/query")
    public ${boName} query(Long id) {
        return service.selectById(id);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public Integer save(@RequestBody ${boName} ${boName?uncap_first}) {
        return service.insert(${boName?uncap_first});
    }

    /**
     * 修改
     */
    @PutMapping("/edit")
    public Integer edit(@RequestBody ${boName} ${boName?uncap_first}) {
        return service.updateById(${boName?uncap_first});
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @DeleteMapping("/remove")
    public Integer remove(@RequestParam Long id) {
        return service.deleteById(id);
    }
}
