package com.lq.sell.service.impl;

import com.lq.sell.dataobject.OrderDetail;
import com.lq.sell.dataobject.OrderMaster;
import com.lq.sell.dataobject.ProductInfo;
import com.lq.sell.dto.CartDTO;
import com.lq.sell.dto.OrderDTO;
import com.lq.sell.enums.OrderStatusEnum;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.repository.OrderDetailRepository;
import com.lq.sell.repository.OrderMasterRepository;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.OrderService;
import com.lq.sell.service.ProductService;
import com.lq.sell.utils.OrderMaster2OrderDTO;
import com.lq.sell.utils.UniqueKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
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
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAILR_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpneId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpneId, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Transactional
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) || orderDTO.getOrderStatus().equals(OrderStatusEnum.NOPAY.getCode()) || orderDTO.getOrderStatus().equals(OrderStatusEnum.PAY.getCode())) {
            orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
            orderDTO.setUpdateTime(new Date());
            BeanUtils.copyProperties(orderDTO, orderMaster);
            OrderMaster updateOrderMaster = orderMasterRepository.save(orderMaster);
            if (updateOrderMaster == null) {
                throw new SellException(ResultEnum.CANCEL_ORDER_ERROR);
            }
        } else {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROT);
        }

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        if (orderDTO.getPayStatus().equals(OrderStatusEnum.PAY.getCode())) {
            //TODO
//            退款
        }
        return orderDTO;
    }

    @Transactional
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        if (!orderDTO.equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROT);
        }

        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderDTO.setUpdateTime(new Date());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateMaster = orderMasterRepository.save(orderMaster);
        if (updateMaster == null) {
            throw new SellException(ResultEnum.FINISH_ORDER_ERROR);
        }
        return orderDTO;
    }

    @Transactional
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        if (!orderDTO.equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROT);
        }

        if (!orderDTO.equals(OrderStatusEnum.WAIT.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROT);
        }

        orderDTO.setOrderStatus(OrderStatusEnum.PAY.getCode());
        orderDTO.setUpdateTime(new Date());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateMaster = orderMasterRepository.save(orderMaster);
        if (updateMaster == null) {
            throw new SellException(ResultEnum.UPDATE_ORDER_ERROR);
        }
        return orderDTO;
    }
}
