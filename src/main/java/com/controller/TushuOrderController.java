
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 图书订单
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/tushuOrder")
public class TushuOrderController {
    private static final Logger logger = LoggerFactory.getLogger(TushuOrderController.class);

    @Autowired
    private TushuOrderService tushuOrderService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private AddressService addressService;
    @Autowired
    private TushuService tushuService;
    @Autowired
    private YonghuService yonghuService;
@Autowired
private CartService cartService;
@Autowired
private TushuCommentbackService tushuCommentbackService;



    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = tushuOrderService.queryPage(params);

        //字典表数据转换
        List<TushuOrderView> list =(List<TushuOrderView>)page.getList();
        for(TushuOrderView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        TushuOrderEntity tushuOrder = tushuOrderService.selectById(id);
        if(tushuOrder !=null){
            //entity转view
            TushuOrderView view = new TushuOrderView();
            BeanUtils.copyProperties( tushuOrder , view );//把实体数据重构到view中

                //级联表
                AddressEntity address = addressService.selectById(tushuOrder.getAddressId());
                if(address != null){
                    BeanUtils.copyProperties( address , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setAddressId(address.getId());
                    view.setAddressYonghuId(address.getYonghuId());
                }
                //级联表
                TushuEntity tushu = tushuService.selectById(tushuOrder.getTushuId());
                if(tushu != null){
                    BeanUtils.copyProperties( tushu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setTushuId(tushu.getId());
                }
                //级联表
                YonghuEntity yonghu = yonghuService.selectById(tushuOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody TushuOrderEntity tushuOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,tushuOrder:{}",this.getClass().getName(),tushuOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            tushuOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        tushuOrder.setInsertTime(new Date());
        tushuOrder.setCreateTime(new Date());
        tushuOrderService.insert(tushuOrder);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody TushuOrderEntity tushuOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,tushuOrder:{}",this.getClass().getName(),tushuOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            tushuOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<TushuOrderEntity> queryWrapper = new EntityWrapper<TushuOrderEntity>()
            .eq("id",0)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        TushuOrderEntity tushuOrderEntity = tushuOrderService.selectOne(queryWrapper);
        if(tushuOrderEntity==null){
            tushuOrderService.updateById(tushuOrder);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        tushuOrderService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<TushuOrderEntity> tushuOrderList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            TushuOrderEntity tushuOrderEntity = new TushuOrderEntity();
//                            tushuOrderEntity.setTushuOrderUuidNumber(data.get(0));                    //订单号 要改的
//                            tushuOrderEntity.setAddressId(Integer.valueOf(data.get(0)));   //收获地址 要改的
//                            tushuOrderEntity.setTushuId(Integer.valueOf(data.get(0)));   //图书 要改的
//                            tushuOrderEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            tushuOrderEntity.setBuyNumber(Integer.valueOf(data.get(0)));   //购买数量 要改的
//                            tushuOrderEntity.setTushuOrderTruePrice(data.get(0));                    //实付价格 要改的
//                            tushuOrderEntity.setTushuOrderCourierName(data.get(0));                    //快递公司 要改的
//                            tushuOrderEntity.setTushuOrderCourierNumber(data.get(0));                    //订单快递单号 要改的
//                            tushuOrderEntity.setTushuOrderTypes(Integer.valueOf(data.get(0)));   //订单类型 要改的
//                            tushuOrderEntity.setTushuOrderPaymentTypes(Integer.valueOf(data.get(0)));   //支付类型 要改的
//                            tushuOrderEntity.setInsertTime(date);//时间
//                            tushuOrderEntity.setCreateTime(date);//时间
                            tushuOrderList.add(tushuOrderEntity);


                            //把要查询是否重复的字段放入map中
                                //订单号
                                if(seachFields.containsKey("tushuOrderUuidNumber")){
                                    List<String> tushuOrderUuidNumber = seachFields.get("tushuOrderUuidNumber");
                                    tushuOrderUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> tushuOrderUuidNumber = new ArrayList<>();
                                    tushuOrderUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("tushuOrderUuidNumber",tushuOrderUuidNumber);
                                }
                        }

                        //查询是否重复
                         //订单号
                        List<TushuOrderEntity> tushuOrderEntities_tushuOrderUuidNumber = tushuOrderService.selectList(new EntityWrapper<TushuOrderEntity>().in("tushu_order_uuid_number", seachFields.get("tushuOrderUuidNumber")));
                        if(tushuOrderEntities_tushuOrderUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(TushuOrderEntity s:tushuOrderEntities_tushuOrderUuidNumber){
                                repeatFields.add(s.getTushuOrderUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [订单号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        tushuOrderService.insertBatch(tushuOrderList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = tushuOrderService.queryPage(params);

        //字典表数据转换
        List<TushuOrderView> list =(List<TushuOrderView>)page.getList();
        for(TushuOrderView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        TushuOrderEntity tushuOrder = tushuOrderService.selectById(id);
            if(tushuOrder !=null){


                //entity转view
                TushuOrderView view = new TushuOrderView();
                BeanUtils.copyProperties( tushuOrder , view );//把实体数据重构到view中

                //级联表
                    AddressEntity address = addressService.selectById(tushuOrder.getAddressId());
                if(address != null){
                    BeanUtils.copyProperties( address , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setAddressId(address.getId());
                }
                //级联表
                    TushuEntity tushu = tushuService.selectById(tushuOrder.getTushuId());
                if(tushu != null){
                    BeanUtils.copyProperties( tushu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setTushuId(tushu.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(tushuOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody TushuOrderEntity tushuOrder, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,tushuOrder:{}",this.getClass().getName(),tushuOrder.toString());
            TushuEntity tushuEntity = tushuService.selectById(tushuOrder.getTushuId());
            if(tushuEntity == null){
                return R.error(511,"查不到该图书");
            }
            // Double tushuNewMoney = tushuEntity.getTushuNewMoney();

            if(false){
            }
            else if((tushuEntity.getTushuKucunNumber() -tushuOrder.getBuyNumber())<0){
                return R.error(511,"购买数量不能大于库存数量");
            }
            else if(tushuEntity.getTushuNewMoney() == null){
                return R.error(511,"图书价格不能为空");
            }

            //计算所获得积分
            Double buyJifen =0.0;
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            tushuOrder.setTushuOrderTypes(3); //设置订单状态为已支付
            tushuOrder.setTushuOrderTruePrice(0.0); //设置实付价格
            tushuOrder.setYonghuId(userId); //设置订单支付人id
            tushuOrder.setTushuOrderUuidNumber(String.valueOf(new Date().getTime()));
            tushuOrder.setTushuOrderPaymentTypes(1);
            tushuOrder.setInsertTime(new Date());
            tushuOrder.setCreateTime(new Date());
                tushuEntity.setTushuKucunNumber( tushuEntity.getTushuKucunNumber() -tushuOrder.getBuyNumber());
                tushuService.updateById(tushuEntity);
                tushuOrderService.insert(tushuOrder);//新增订单
            return R.ok();
    }
    /**
     * 添加订单
     */
    @RequestMapping("/order")
    public R add(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("order方法:,,Controller:{},,params:{}",this.getClass().getName(),params.toString());
        String tushuOrderUuidNumber = String.valueOf(new Date().getTime());

        //获取当前登录用户的id
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        Integer addressId = Integer.valueOf(String.valueOf(params.get("addressId")));

        Integer tushuOrderPaymentTypes = Integer.valueOf(String.valueOf(params.get("tushuOrderPaymentTypes")));//支付类型

        String data = String.valueOf(params.get("tushus"));
        JSONArray jsonArray = JSON.parseArray(data);
        List<Map> tushus = JSON.parseObject(jsonArray.toString(), List.class);

        //获取当前登录用户的个人信息
        YonghuEntity yonghuEntity = yonghuService.selectById(userId);

        //当前订单表
        List<TushuOrderEntity> tushuOrderList = new ArrayList<>();
        //商品表
        List<TushuEntity> tushuList = new ArrayList<>();
        //购物车ids
        List<Integer> cartIds = new ArrayList<>();

        BigDecimal zhekou = new BigDecimal(1.0);
        // 获取折扣
        Wrapper<DictionaryEntity> dictionary = new EntityWrapper<DictionaryEntity>()
                .eq("dic_code", "huiyuandengji_types")
                .eq("dic_name", "会员等级类型")
                .eq("code_index", yonghuEntity.getHuiyuandengjiTypes())
                ;
        DictionaryEntity dictionaryEntity = dictionaryService.selectOne(dictionary);
        if(dictionaryEntity != null ){
            zhekou = BigDecimal.valueOf(Double.valueOf(dictionaryEntity.getBeizhu()));
        }

        //循环取出需要的数据
        for (Map<String, Object> map : tushus) {
           //取值
            Integer tushuId = Integer.valueOf(String.valueOf(map.get("tushuId")));//商品id
            Integer buyNumber = Integer.valueOf(String.valueOf(map.get("buyNumber")));//购买数量
            TushuEntity tushuEntity = tushuService.selectById(tushuId);//购买的商品
            String id = String.valueOf(map.get("id"));
            if(StringUtil.isNotEmpty(id))
                cartIds.add(Integer.valueOf(id));

            //判断商品的库存是否足够
            if(tushuEntity.getTushuKucunNumber() < buyNumber){
                //商品库存不足直接返回
                return R.error(tushuEntity.getTushuName()+"的库存不足");
            }else{
                //商品库存充足就减库存
                tushuEntity.setTushuKucunNumber(tushuEntity.getTushuKucunNumber() - buyNumber);
            }

            //订单信息表增加数据
            TushuOrderEntity tushuOrderEntity = new TushuOrderEntity<>();

            //赋值订单信息
            tushuOrderEntity.setTushuOrderUuidNumber(tushuOrderUuidNumber);//订单号
            tushuOrderEntity.setAddressId(addressId);//收获地址
            tushuOrderEntity.setTushuId(tushuId);//图书
            tushuOrderEntity.setYonghuId(userId);//用户
            tushuOrderEntity.setBuyNumber(buyNumber);//购买数量 ？？？？？？
            tushuOrderEntity.setTushuOrderTypes(3);//订单类型
            tushuOrderEntity.setTushuOrderPaymentTypes(tushuOrderPaymentTypes);//支付类型
            tushuOrderEntity.setInsertTime(new Date());//订单创建时间
            tushuOrderEntity.setCreateTime(new Date());//创建时间

            //判断是什么支付方式 1代表余额 2代表积分
            if(tushuOrderPaymentTypes == 1){//余额支付
                //计算金额
                Double money = new BigDecimal(tushuEntity.getTushuNewMoney()).multiply(new BigDecimal(buyNumber)).multiply(zhekou).doubleValue();

                if(yonghuEntity.getNewMoney() - money <0 ){
                    return R.error("余额不足,请充值！！！");
                }else{
                    //计算所获得积分
                    Double buyJifen =0.0;
                        buyJifen = new BigDecimal(tushuEntity.getTushuPrice()).multiply(new BigDecimal(buyNumber)).doubleValue();
                    yonghuEntity.setYonghuSumJifen(yonghuEntity.getYonghuSumJifen() + buyJifen); //设置总积分
                    yonghuEntity.setYonghuNewJifen(yonghuEntity.getYonghuNewJifen() + buyJifen); //设置现积分
                        if(yonghuEntity.getYonghuSumJifen()  < 10000)
                            yonghuEntity.setHuiyuandengjiTypes(1);
                        else if(yonghuEntity.getYonghuSumJifen()  < 100000)
                            yonghuEntity.setHuiyuandengjiTypes(2);
                        else if(yonghuEntity.getYonghuSumJifen()  < 1000000)
                            yonghuEntity.setHuiyuandengjiTypes(3);


                    tushuOrderEntity.setTushuOrderTruePrice(money);

                }
            }
            tushuOrderList.add(tushuOrderEntity);
            tushuList.add(tushuEntity);

        }
        tushuOrderService.insertBatch(tushuOrderList);
        tushuService.updateBatchById(tushuList);
        yonghuService.updateById(yonghuEntity);
        if(cartIds != null && cartIds.size()>0)
            cartService.deleteBatchIds(cartIds);
        return R.ok();
    }











    /**
    * 退款
    */
    @RequestMapping("/refund")
    public R refund(Integer id, HttpServletRequest request){
        logger.debug("refund方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        String role = String.valueOf(request.getSession().getAttribute("role"));

            TushuOrderEntity tushuOrder = tushuOrderService.selectById(id);
            Integer buyNumber = tushuOrder.getBuyNumber();
            Integer tushuOrderPaymentTypes = tushuOrder.getTushuOrderPaymentTypes();
            Integer tushuId = tushuOrder.getTushuId();
            if(tushuId == null)
                return R.error(511,"查不到该图书");
            TushuEntity tushuEntity = tushuService.selectById(tushuId);
            if(tushuEntity == null)
                return R.error(511,"查不到该图书");
            Double tushuNewMoney = tushuEntity.getTushuNewMoney();
            if(tushuNewMoney == null)
                return R.error(511,"图书价格不能为空");

            Integer userId = (Integer) request.getSession().getAttribute("userId");
            YonghuEntity yonghuEntity = yonghuService.selectById(userId);
            if(yonghuEntity == null)
                return R.error(511,"用户不能为空");
            if(yonghuEntity.getNewMoney() == null)
                return R.error(511,"用户金额不能为空");

            Double zhekou = 1.0;
            // 获取折扣
            Wrapper<DictionaryEntity> dictionary = new EntityWrapper<DictionaryEntity>()
                    .eq("dic_code", "huiyuandengji_types")
                    .eq("dic_name", "会员等级类型")
                    .eq("code_index", yonghuEntity.getHuiyuandengjiTypes())
                    ;
            DictionaryEntity dictionaryEntity = dictionaryService.selectOne(dictionary);
            if(dictionaryEntity != null ){
                zhekou = Double.valueOf(dictionaryEntity.getBeizhu());
            }


            //判断是什么支付方式 1代表余额 2代表积分
            if(tushuOrderPaymentTypes == 1){//余额支付
                //计算金额
                Double money = tushuEntity.getTushuNewMoney() * buyNumber  * zhekou;
                //计算所获得积分
                Double buyJifen = 0.0;
                buyJifen = new BigDecimal(tushuEntity.getTushuPrice()).multiply(new BigDecimal(buyNumber)).doubleValue();
                yonghuEntity.setYonghuSumJifen(yonghuEntity.getYonghuSumJifen() - buyJifen); //设置总积分
                if(yonghuEntity.getYonghuNewJifen() - buyJifen <0 )
                    return R.error("积分已经消费,无法退款！！！");
                yonghuEntity.setYonghuNewJifen(yonghuEntity.getYonghuNewJifen() - buyJifen); //设置现积分

                if(yonghuEntity.getYonghuSumJifen()  < 10000)
                    yonghuEntity.setHuiyuandengjiTypes(1);
                else if(yonghuEntity.getYonghuSumJifen()  < 100000)
                    yonghuEntity.setHuiyuandengjiTypes(2);
                else if(yonghuEntity.getYonghuSumJifen()  < 1000000)
                    yonghuEntity.setHuiyuandengjiTypes(3);

            }

            tushuEntity.setTushuKucunNumber(tushuEntity.getTushuKucunNumber() + buyNumber);



            tushuOrder.setTushuOrderTypes(2);//设置订单状态为退款
            tushuOrderService.updateById(tushuOrder);//根据id更新
            yonghuService.updateById(yonghuEntity);//更新用户信息
            tushuService.updateById(tushuEntity);//更新订单中图书的信息
            return R.ok();
    }


    /**
     * 发货
     */
    @RequestMapping("/deliver")
    public R deliver(Integer id ,String tushuOrderCourierNumber, String tushuOrderCourierName){
        logger.debug("refund:,,Controller:{},,ids:{}",this.getClass().getName(),id.toString());
        TushuOrderEntity  tushuOrderEntity = new  TushuOrderEntity();;
        tushuOrderEntity.setId(id);
        tushuOrderEntity.setTushuOrderTypes(4);
        tushuOrderEntity.setTushuOrderCourierNumber(tushuOrderCourierNumber);
        tushuOrderEntity.setTushuOrderCourierName(tushuOrderCourierName);
        boolean b =  tushuOrderService.updateById( tushuOrderEntity);
        if(!b){
            return R.error("发货出错");
        }
        return R.ok();
    }









    /**
     * 收货
     */
    @RequestMapping("/receiving")
    public R receiving(Integer id){
        logger.debug("refund:,,Controller:{},,ids:{}",this.getClass().getName(),id.toString());
        TushuOrderEntity  tushuOrderEntity = new  TushuOrderEntity();
        tushuOrderEntity.setId(id);
        tushuOrderEntity.setTushuOrderTypes(5);
        boolean b =  tushuOrderService.updateById( tushuOrderEntity);
        if(!b){
            return R.error("收货出错");
        }
        return R.ok();
    }



    /**
    * 评价
    */
    @RequestMapping("/commentback")
    public R commentback(Integer id, String commentbackText, Integer tushuCommentbackPingfenNumber, HttpServletRequest request){
        logger.debug("commentback方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
            TushuOrderEntity tushuOrder = tushuOrderService.selectById(id);
        if(tushuOrder == null)
            return R.error(511,"查不到该订单");
        if(tushuOrder.getTushuOrderTypes() != 5)
            return R.error(511,"您不能评价");
        Integer tushuId = tushuOrder.getTushuId();
        if(tushuId == null)
            return R.error(511,"查不到该图书");

        TushuCommentbackEntity tushuCommentbackEntity = new TushuCommentbackEntity();
            tushuCommentbackEntity.setId(id);
            tushuCommentbackEntity.setTushuId(tushuId);
            tushuCommentbackEntity.setYonghuId((Integer) request.getSession().getAttribute("userId"));
            tushuCommentbackEntity.setTushuCommentbackText(commentbackText);
            tushuCommentbackEntity.setInsertTime(new Date());
            tushuCommentbackEntity.setReplyText(null);
            tushuCommentbackEntity.setUpdateTime(null);
            tushuCommentbackEntity.setCreateTime(new Date());
            tushuCommentbackService.insert(tushuCommentbackEntity);

            tushuOrder.setTushuOrderTypes(1);//设置订单状态为已评价
            tushuOrderService.updateById(tushuOrder);//根据id更新
            return R.ok();
    }







}
