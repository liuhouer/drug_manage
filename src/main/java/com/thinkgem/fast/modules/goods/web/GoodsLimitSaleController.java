package com.thinkgem.fast.modules.goods.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.fast.common.config.Global;
import com.thinkgem.fast.common.persistence.Page;
import com.thinkgem.fast.common.web.BaseController;
import com.thinkgem.fast.common.utils.StringUtils;
import com.thinkgem.fast.modules.goods.entity.GoodsLimitSale;
import com.thinkgem.fast.modules.goods.service.GoodsLimitSaleService;

/**
 * 商品限销Controller
 * @author shiao
 * @version 2019-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goodsLimitSale")
public class GoodsLimitSaleController extends BaseController {

	@Autowired
	private GoodsLimitSaleService goodsLimitSaleService;
	
	@ModelAttribute
	public GoodsLimitSale get(@RequestParam(required=false) String id) {
		GoodsLimitSale entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsLimitSaleService.get(id);
		}
		if (entity == null){
			entity = new GoodsLimitSale();
		}
		return entity;
	}
	
	@RequiresPermissions("goods:goodsLimitSale:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsLimitSale goodsLimitSale, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsLimitSale> page = goodsLimitSaleService.findPage(new Page<GoodsLimitSale>(request, response), goodsLimitSale); 
		model.addAttribute("page", page);
		return "modules/goods/goodsLimitSaleList";
	}

	@RequiresPermissions("goods:goodsLimitSale:view")
	@RequestMapping(value = "form")
	public String form(GoodsLimitSale goodsLimitSale, Model model) {
		model.addAttribute("goodsLimitSale", goodsLimitSale);
		return "modules/goods/goodsLimitSaleForm";
	}

	@RequiresPermissions("goods:goodsLimitSale:edit")
	@RequestMapping(value = "save")
	public String save(GoodsLimitSale goodsLimitSale, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsLimitSale)){
			return form(goodsLimitSale, model);
		}
		goodsLimitSaleService.save(goodsLimitSale);
		addMessage(redirectAttributes, "保存商品限销成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsLimitSale/?repage";
	}
	
	@RequiresPermissions("goods:goodsLimitSale:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsLimitSale goodsLimitSale, RedirectAttributes redirectAttributes) {
		goodsLimitSaleService.delete(goodsLimitSale);
		addMessage(redirectAttributes, "删除商品限销成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsLimitSale/?repage";
	}

}