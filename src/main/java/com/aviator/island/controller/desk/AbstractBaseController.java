package com.aviator.island.controller.desk;

import com.aviator.island.constants.ResponseCode;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.dto.output.BaseOutputDTO;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.UserService;
import com.aviator.island.utils.ConvertUtil;
import com.aviator.island.utils.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by aviator_ls on 2018/7/20.
 */
@Slf4j
public abstract class AbstractBaseController {

    @Autowired
    private UserService<User> userService;

    public String getPrincipal() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getPrincipal() == null ? null : subject.getPrincipal().toString();
    }

    public Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            return subject.getSession();
        }
        return null;
    }

    /**
     * 得到当前用户
     *
     * @return
     */
    public User getSessionUser() {
        String userName = this.getPrincipal();
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        return userService.getUserByUserName(userName);
    }

    public String getRealPath(HttpServletRequest request, String path) {
        return request.getSession().getServletContext().getRealPath(path);
    }

    protected ModelAndView responsePage(String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        viewName = "desk/" + viewName;
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    protected ResponseContent successResponseBody() {
        return successResponseBody(null);
    }

    protected ResponseContent successResponseBody(Object obj) {
        ResponseContent responseContent = new ResponseContent();
        responseContent.setResponseCode(ResponseCode.SUCCESS.getCode());
        responseContent.setResponseMsg(ResponseCode.SUCCESS.getMsg());
        responseContent.setData(obj);
        return responseContent;
    }

    protected ResponseContent failResponseBody(ResponseCode responseCode, Object... obj) {
        ResponseContent responseContent = new ResponseContent();
        responseContent.setResponseCode(responseCode.getCode());
        responseContent.setResponseMsg(responseCode.getMsg());
        responseContent.setData(obj);
        return responseContent;
    }

    protected <S, T> List<T> convertListToOutputDTOList(List<S> list, BaseOutputDTO<S, T> outputDTO) {
        return ConvertUtil.convertCollectionToOutputDTOList(list, outputDTO);
    }

    protected <S, T> Page<T> convertPageToOutputDTOPage(Page<S> page, BaseOutputDTO<S, T> outputDTO) {
        return ConvertUtil.convertPageToOutputDTOPage(page, outputDTO);
    }
}
