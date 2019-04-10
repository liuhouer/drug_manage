package com.thinkgem.fast.modules.hrmuser.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.fast.common.config.Global;
import com.thinkgem.fast.common.persistence.Page;
import com.thinkgem.fast.common.utils.Collections3;
import com.thinkgem.fast.common.utils.DateUtils;
import com.thinkgem.fast.common.utils.StringUtils;
import com.thinkgem.fast.common.web.BaseController;
import com.thinkgem.fast.modules.customer.entity.Customer;
import com.thinkgem.fast.modules.customer.service.CustomerService;
import com.thinkgem.fast.modules.hrmuser.entity.HrmAddress;
import com.thinkgem.fast.modules.hrmuser.entity.HrmBank;
import com.thinkgem.fast.modules.hrmuser.entity.HrmEducation;
import com.thinkgem.fast.modules.hrmuser.entity.HrmFamilyContact;
import com.thinkgem.fast.modules.hrmuser.entity.HrmUser;
import com.thinkgem.fast.modules.hrmuser.entity.HrmUserCustomer;
import com.thinkgem.fast.modules.hrmuser.entity.HrmWorkExper;
import com.thinkgem.fast.modules.hrmuser.entity.ManageSalesman;
import com.thinkgem.fast.modules.hrmuser.service.HrmUserCustomerService;
import com.thinkgem.fast.modules.hrmuser.service.HrmUserService;
import com.thinkgem.fast.modules.hrmuser.service.ManageSalesmanService;
import com.thinkgem.fast.modules.sys.service.OfficeService;

/**
 * 内部员工信息操作Controller
 *
 * @author 任硕
 * @version 2018-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/hrmuser/hrmUser")
public class HrmUserController extends BaseController {

    @Autowired
    private HrmUserService hrmUserService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private ManageSalesmanService manageSalesmanService;
    @Autowired
    private HrmUserCustomerService hrmUserCustomerService;
    @Autowired
    private CustomerService customerService;


    @ModelAttribute
    public HrmUser get(@RequestParam(required = false) String id) {
        HrmUser entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hrmUserService.get(id);
        }
        if (entity == null) {
            entity = new HrmUser();
        }
        return entity;
    }

    @RequiresPermissions("hrmuser:hrmUser:view")
    @RequestMapping(value = {"list", ""})
    public String list(HrmUser hrmUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HrmUser> page = hrmUserService.findPage(new Page<HrmUser>(request, response), hrmUser);
        model.addAttribute("page", page);
        return "modules/hrmuser/hrmUserList";
    }

    @RequiresPermissions("hrmuser:hrmUser:view")
    @RequestMapping(value = "form")
    public String form(HrmUser hrmUser, Model model) {
        if (StringUtils.isBlank(hrmUser.getId())) {
            String yy = DateUtils.getLastYearYY();
            int total = hrmUserService.findCount();
            int emps = total + 1;
            String ss = StringUtils.frontCompWithZore(emps, 4);
            String empNumber = yy + ss;
            hrmUser.setEmpNumber(empNumber);
        }
        model.addAttribute("hrmUser", hrmUser);
        return "modules/hrmuser/hrmUserForm";
    }

    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "save")
    public String save(HrmUser hrmUser, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hrmUser)) {
            return form(hrmUser, model);
        }
        //TODO 页面删除后下标还在原来位置映射后list中的下标对应所以需要判断改list集合中对象的属性是否不为空在进行保存
        this.filterParam(hrmUser);
        hrmUserService.save(hrmUser);
        addMessage(redirectAttributes, "保存内部员工成功");
        return "redirect:" + Global.getAdminPath() + "/hrmuser/hrmUser/?repage";
    }

    /**
     * 过滤user中的参数
     *
     * @param hrmUser
     */
    private void filterParam(HrmUser hrmUser) {

        List<HrmAddress> addressList = hrmUser.getHrmAddressList();
        if (CollectionUtils.isNotEmpty(addressList) && "2".equals(hrmUser.getCompanyType())) {
            Iterator<HrmAddress> it = addressList.iterator();
            while (it.hasNext()) {
                HrmAddress hrmAddress = it.next();
                if (hrmAddress == null) {
                    it.remove();
                } else {
                    if (hrmAddress.getArea() == null ||
                            StringUtils.isBlank(hrmAddress.getArea().getId()) ||
                            StringUtils.isBlank(hrmAddress.getContactPhone()) ||
                            StringUtils.isBlank(hrmAddress.getReceivingAddress()) ||
                            StringUtils.isBlank(hrmAddress.getReceivingName())) {
                        it.remove();
                    }
                }
            }
        } else {
            if (CollectionUtils.isNotEmpty(addressList)) {
                addressList.clear();
            }
        }

        List<HrmBank> hrmBanksList = hrmUser.getHrmBanksList();
        if (CollectionUtils.isNotEmpty(hrmBanksList)) {
            Iterator<HrmBank> it = hrmBanksList.iterator();
            while (it.hasNext()) {
                HrmBank hrmBank = it.next();
                if (hrmBank == null) {
                    it.remove();
                } else {
                    if (StringUtils.isBlank(hrmBank.getBank()) ||
                            StringUtils.isBlank(hrmBank.getBankNumber()) ||
                            //StringUtils.isBlank(hrmBank.getStatus()) ||
                            StringUtils.isBlank(hrmBank.getPositivePhoto())) {
                        it.remove();
                    }
                }
            }
        }
        List<HrmEducation> hrmEduList = hrmUser.getHrmEduList();
        if (CollectionUtils.isNotEmpty(hrmEduList)) {
            Iterator<HrmEducation> it = hrmEduList.iterator();
            while (it.hasNext()) {
                HrmEducation hrmEducation = it.next();
                if (hrmEducation == null) {
                    it.remove();
                } else {
                    if (StringUtils.isBlank(hrmEducation.getMajor()) ||
                            StringUtils.isBlank(hrmEducation.getSchoolName()) ||
                            hrmEducation.getStartDate() == null ||
                            hrmEducation.getEndDate() == null) {
                        it.remove();
                    }
                }
            }
        }
        List<HrmFamilyContact> hrmFamilyList = hrmUser.getHrmFamilyList();
        if (CollectionUtils.isNotEmpty(hrmFamilyList)) {
            Iterator<HrmFamilyContact> it = hrmFamilyList.iterator();
            while (it.hasNext()) {
                HrmFamilyContact hrmFamilyContact = it.next();
                if (hrmFamilyContact == null) {
                    it.remove();
                } else {
                    if (StringUtils.isBlank(hrmFamilyContact.getName()) ||
                            StringUtils.isBlank(hrmFamilyContact.getRelationship())) {
                        it.remove();
                    }
                }
            }
        }
        List<HrmWorkExper> hrmWorkExperList = hrmUser.getHrmWorkExperList();
        if (CollectionUtils.isNotEmpty(hrmWorkExperList)) {
            Iterator<HrmWorkExper> it = hrmWorkExperList.iterator();
            while (it.hasNext()) {
                HrmWorkExper hrmWorkExper = it.next();
                if (hrmWorkExper == null) {
                    it.remove();
                } else {
                    if (StringUtils.isBlank(hrmWorkExper.getCompanyName()) ||
                            StringUtils.isBlank(hrmWorkExper.getCompanyAddress())) {
                        it.remove();
                    }
                }
            }
        }
    }


    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "delete")
    public String delete(HrmUser hrmUser, RedirectAttributes redirectAttributes) {
        hrmUserService.delete(hrmUser);
        addMessage(redirectAttributes, "删除内部员工成功");
        return "redirect:" + Global.getAdminPath() + "/hrmuser/hrmUser/?repage";
    }

    /**
     * 分配业务员 -- 打开分配对话框
     *
     * @param hrmUser
     * @param model
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "bindSale")
    public String selectUserToManage(HrmUser hrmUser, Model model) {
        ManageSalesman ms = new ManageSalesman();
        ms.setManageUserId(hrmUser.getId());
        List<ManageSalesman> userList = manageSalesmanService.findList(ms);
        model.addAttribute("hrmUser", hrmUser);
        model.addAttribute("userList", userList);
        model.addAttribute("selectIds", Collections3.extractToString(userList, "salemanHrmUser.id", ","));
        model.addAttribute("officeList", officeService.findAll());
        return "modules/hrmuser/salesmanList";
    }

    /**
     * 分配业务员 -- 根据部门编号获取员工列表
     *
     * @param hrmUser
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @ResponseBody
    @RequestMapping(value = "hrmUserByOffice")
    public List<Map<String, Object>> users(HrmUser hrmUser) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
//        Page<User> page = systemService.findUser(new Page<User>(1, -1), user);
        List<HrmUser> list = hrmUserService.findList(hrmUser);
        for (HrmUser e : list) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("pId", 0);
            map.put("name", e.getEmpName());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 分配业务员给区域经理
     *
     * @param manageId
     * @param idsArr
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "assignSaleman")
    public String assignSaleman(String manageId, String[] idsArr, RedirectAttributes redirectAttributes) {
        StringBuilder msg = new StringBuilder();
        manageSalesmanService.assignSaleman(manageId, idsArr);
        addMessage(redirectAttributes, "已成功分配业务员");
        return "redirect:" + adminPath + "/hrmuser/hrmUser";
    }
    
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "salesmans")
    public String findSaleman(@RequestParam(value="manageId",required=false) String manageId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HrmUser> page = hrmUserService.findSalesManPage(new Page<HrmUser>(request, response), manageId);
        model.addAttribute("page", page);
        return "modules/hrmuser/salesmanTable";
    }
    
    
    /**
     * 删除选中的业务员
     *
     * @param manageId
     * @param saleId
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @ResponseBody
    @RequestMapping(value = "removeSale")
    public Map<String, Object> removeSale(String manageId, String saleId) {
        ManageSalesman sm = new ManageSalesman();
        sm.setManageUserId(manageId);
        sm.setSalesmanUserId(saleId);
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            res.put("code","1");
            manageSalesmanService.delete(sm);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code","0");
        }
        return res;
    }

    /**
     * 用户 与 客户的关系页面
     *
     * @param hrmUser
     * @param model
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "bindCustomer")
    public String bindCustomer(HrmUser hrmUser, Model model) {
        HrmUserCustomer huc = new HrmUserCustomer();
        huc.setHrmUserId(hrmUser.getId());
        List<HrmUserCustomer> selectedCustomerList = hrmUserCustomerService.findList(huc);
        //员工信息
        model.addAttribute("hrmUser", hrmUser);
        
        //未选中的
        List<Customer> preSelectCustomers = hrmUserCustomerService.preSelectCustomers();
        String preIds = Joiner.on(",").skipNulls().join(preSelectCustomers.stream().map(c->c.getId()).collect(Collectors.toList()));
        model.addAttribute("preIds", preIds);
        model.addAttribute("customerList", preSelectCustomers);
        
        //客户列表信息
        model.addAttribute("selectedCustomerList", selectedCustomerList);
        //已经选中的
        model.addAttribute("selectIds", Collections3.extractToString(selectedCustomerList, "customer.id", ","));
        return "modules/hrmuser/selectCustomerList";
    }


    /**
     * 删除选中的业务员
     *
     * @param manageId
     * @param saleId
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @ResponseBody
    @RequestMapping(value = "removeCustomer")
    public Map<String, Object> removeCustomer(String manageId, String saleId) {
        ManageSalesman sm = new ManageSalesman();
        sm.setManageUserId(manageId);
        sm.setSalesmanUserId(saleId);
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            res.put("code","1");
            manageSalesmanService.delete(sm);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code","0");
        }
        return res;
    }
    
    @RequiresPermissions("hrmuser:hrmUser:edit")
	@ResponseBody
	@RequestMapping(value = "regionManager/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		HrmUser hrmUser = new HrmUser();
		hrmUser.setUserType("1");
		List<HrmUser> list = hrmUserService.findList(hrmUser);
		for (int i=0; i<list.size(); i++){
			HrmUser e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id",  e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getEmpName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
    
    /**
     * 区域经理+业务员
     * @param officeId
     * @param response
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
   	@ResponseBody
   	@RequestMapping(value = "managerClerk/list")
   	public List<Map<String, Object>> userTreeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
   		List<Map<String, Object>> mapList = Lists.newArrayList();
   		Map<String, Object> para = Maps.newHashMap();
   		para.put("userType", "'1','2'");
   		List<HrmUser> list = hrmUserService.findListByCondition(para);
   		for (int i=0; i<list.size(); i++){
   			HrmUser e = list.get(i);
   			Map<String, Object> map = Maps.newHashMap();
   			map.put("id",  e.getId());
   			map.put("pId", officeId);
   			map.put("name", StringUtils.replace(e.getEmpName(), " ", ""));
   			mapList.add(map);
   		}
   		return mapList;
   	}
    
    /**
     * 查询客户列表
     * @param manageId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "customers")
    public String customers(@RequestParam(value="manageId",required=false) String manageId,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Customer> page = hrmUserService.findCustomerPage(new Page<Customer>(request, response), manageId);
        model.addAttribute("page", page);
        model.addAttribute("manageId", manageId);
        return "modules/hrmuser/customerTable";
    }
    
    
    /**
     * 查询客户列表 根据页面参数
     * @param manageId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "customersList")
    public String customersList(Customer customer,HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Customer> page = customerService.findPage(new Page<Customer>(request, response), customer);
        model.addAttribute("page", page);
        model.addAttribute("customer", customer);
        return "modules/hrmuser/customerTable";
    }
    
   
    
    
    
    /**
     * 绑定客户给  业务员|区域经理
     *
     * @param manageId
     * @param ids
     * @return
     */
    @RequiresPermissions("hrmuser:hrmUser:edit")
    @RequestMapping(value = "assignCustomer")
    @ResponseBody
    public Map<String, Object> assignCustomer(String manageId, String ids) {
    	Preconditions.checkNotNull(manageId,"必须勾选区域经理或业务员");
    	Preconditions.checkNotNull(ids,"必须勾选需要绑定的客户");
        Map<String, Object> map = Maps.newHashMap();
        try {
			
        	hrmUserCustomerService.assignCustomer(manageId, ids);
        	map.put("msg", "成功绑定相关客户");
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "绑定客户失败");
		}
        
        return map;
    }
}