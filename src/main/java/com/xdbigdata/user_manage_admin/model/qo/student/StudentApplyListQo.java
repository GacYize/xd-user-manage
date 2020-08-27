package com.xdbigdata.user_manage_admin.model.qo.student;

import com.xdbigdata.framework.web.model.PageQuery;
import com.xdbigdata.user_manage_admin.model.vo.ManageScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@ApiModel("审核列表查询对象")
public class StudentApplyListQo extends PageQuery {

    @ApiModelProperty("审核状态(0:审核中, 1:通过, 2:驳回)")
    private Integer instructorAudit;

    @ApiModelProperty("学号/工号或姓名")
    private String snOrName;

    @ApiModelProperty(hidden = true)
    private List<ManageScope> manageScopes;

    public void setSnOrName(String snOrName) {
        if (StringUtils.isNotBlank(snOrName)) {
            if (snOrName.contains("%") || snOrName.contains("_")) {
                this.snOrName = snOrName.trim().replace("%", "[%]").replace("_", "[_]");
            } else {
                this.snOrName = snOrName.trim();
            }
        }
    }

    public List<ManageScope> getManageScopes() {
        if (CollectionUtils.isEmpty(this.manageScopes)) {
            return null;
        }
        return manageScopes.stream()
                .filter(mc -> !Objects.equals(mc.getOrganizationCode(), "whut"))
                .collect(Collectors.toList());
    }
}
