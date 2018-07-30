package com.lq.sell.service.impl;

import com.lq.sell.dataobject.OrderDetail;
import com.lq.sell.dataobject.OrderMaster;
import com.lq.sell.dataobject.ProductInfo;
import com.lq.sell.dto.CartDTO;
import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.repository.OrderDetailRepository;
import com.lq.sell.repository.OrderMasterRepository;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.OrderService;
import com.lq.sell.service.ProductService;
import com.lq.sell.utils.UniqueKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Transactional
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new SellException("orderDTO is  null");
        }
//        查询商品 数量 价格
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        if (orderDetailList == null && orderDetailList.size() <= 0) {
            throw new SellException("orderDetailList not  have a   data");
        }
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = UniqueKey.generateUniqueKey();
        Date date = new Date();
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null || productInfo.getProductId() == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //        计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(UniqueKey.generateUniqueKey());
            orderDetail.setOrderId(orderId);
            //        写入数据库 （orderdetail ）
            orderDetail.setUpdateTime(date);
            orderDetailRepository.save(orderDetail);
        }
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setUpdateTime(date);
        //        写入数据库 （ ordermaster）
        orderMasterRepository.save(orderMaster);
        //        扣库存
        List<CartDTO> cartDTOList = orderDetailList.stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpneId, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
