package ${controllerPackageName};

import ${serviceClassName};
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: ${remark!''}
 * @Author: ${author!''}
 * @Date: ${dateTime!''}
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${requestMapping}")
public class ${controllerName} {
  private final ${serviceName} ${serviceName?uncap_first};
}
