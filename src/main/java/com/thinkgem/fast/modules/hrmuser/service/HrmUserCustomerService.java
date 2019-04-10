package com.thinkgem.fast.modules.hrmuser.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Splitter;
import com.thinkgem.fast.common.persistence.Page;
import com.thinkgem.fast.common.service.CrudService;
import com.thinkgem.fast.modules.customer.dao.CustomerDao;
import com.thinkgem.fast.modules.customer.entity.Customer;
import com.thinkgem.fast.modules.hrmuser.dao.HrmUserCustomerDao;
import com.thinkgem.fast.modules.hrmuser.entity.HrmUserCustomer;

/**
 * 区域经理绑定业务员Service
 *
 * @author 任硕
 * @version 2018-11-19
 */
@Service
@Transactional(readOnly = true)
public class HrmUserCustomerService extends CrudService<HrmUserCustomerDao, HrmUserCustomer> {

    @Autowired
    private HrmUserCustomerDao HrmUserCustomerDao;
    
    
    @Autowired
    private  CustomerDao customerDao;
    @Override
	public HrmUserCustomer get(String id) {
        return super.get(id);
    }

    @Override
	public List<HrmUserCustomer> findList(HrmUserCustomer hrmUserCustomer) {
        return super.findList(hrmUserCustomer);
    }

    @Override
	public Page<HrmUserCustomer> findPage(Page<HrmUserCustomer> page, HrmUserCustomer hrmUserCustomer) {
        return super.findPage(page, hrmUserCustomer);
    }

    @Override
	@Transactional(readOnly = false)
    public void save(HrmUserCustomer hrmUserCustomer) {
        super.save(hrmUserCustomer);
    }

    @Override
	@Transactional(readOnly = false)
    public void delete(HrmUserCustomer hrmUserCustomer) {
        super.delete(hrmUserCustomer);
    }

    @Transactional(readOnly = false)
    public void assignCustomer(String manageId, String ids) {
    	HrmUserCustomerDao.deleteByhrmId(manageId);
        List<String> customerIdList = Splitter.on(",").omitEmptyStrings().splitToList(ids);
		if(CollectionUtils.isNotEmpty(customerIdList)) {
		 for (String cid : customerIdList) {
			 HrmUserCustomer hc = new HrmUserCustomer();
			 hc.setHrmUserId(manageId);
			 hc.setCustomerId(cid);
			 super.save(hc);
		 }	
		 
		}
    }

	public List<Customer> preSelectCustomers() {
		return customerDao.preSelectCustomers();
	}
}