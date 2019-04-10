package com.thinkgem.fast.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.fast.common.service.CrudService;
import com.thinkgem.fast.modules.test.entity.Test;
import com.thinkgem.fast.modules.test.dao.TestDao;

/**
 * 测试Service
 * @author renshuo
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
