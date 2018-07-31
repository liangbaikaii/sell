package com.lq.sell.service.impl;

import com.lq.sell.dataobject.ProductInfo;
import com.lq.sell.dto.CartDTO;
import com.lq.sell.enums.ProductStatusEnum;
import com.lq.sell.enums.ResultEnum;
import com.lq.sell.repository.ProductInfoRepository;
import com.lq.sell.sellException.SellException;
import com.lq.sell.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        if (!CollectionUtils.isEmpty(cartDTOList)) {
            Date date = new Date();
            for (CartDTO cartDTO : cartDTOList) {
                ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
                if (productInfo == null) {
                    throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                }
                int stock = productInfo.getProductStock() + cartDTO.getProductQuantity();
                productInfo.setUpdateTime(date);
                productInfo.setProductStock(stock);
                productInfoRepository.save(productInfo);
            }
        }

    }

    @Transactional
    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {

        if (!CollectionUtils.isEmpty(cartDTOList)) {
            for (CartDTO cartDTO : cartDTOList) {

                ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
                if (productInfo == null) {
                    logger.error("productInfo  is  null ");
                    throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                }
                int nowStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
                if (nowStock < 0) {
                    logger.error("nowStock  is {} ", nowStock);
                    throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
                }
                productInfo.setUpdateTime(new Date());
                productInfo.setProductStock(nowStock);
                productInfoRepository.save(productInfo);
                logger.info("decreaseStock  operatation  is  successful now the  product{} stock is {}", productInfo, nowStock);
            }

        } else {
            logger.info("cartDTOList  is  null");
        }
    }
}
