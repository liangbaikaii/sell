<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        订单id
                    </th>
                    <th>
                        订单总金额
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr class="success">
                    <td id="orderid">
                    ${orderDTO.orderId}
                    </td>
                    <td>
                    ${orderDTO.orderAmount}
                    </td>
                </tr>
                </tbody>
            </table>

            <table class="table  table-bordered">
                <thead>
                <tr>
                    <th>
                        商品id
                    </th>
                    <th>
                        商品名
                    </th>
                    <th>
                        价格
                    </th>
                    <th>
                        数量
                    </th>
                    <th>
                        金额
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as orderDetail>
                <tr class="success">
                    <td>
                    ${orderDetail.productId}
                    </td>
                    <td>
                    ${orderDetail.productName}
                    </td>
                    <td>
                    ${orderDetail.productPrice}
                    </td>
                    <td>
                    ${orderDetail.productQuantity}
                    </td>
                    <td>
                    ${orderDetail.productQuantity * orderDetail.productPrice }
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button"
               class="btn btn-default btn-block btn-success">完结订单</a>
            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button"
               class="btn btn-default btn-warning btn-block">取消订单</a>
        </div>
    </div>
</div>
</div>
</body>

</html>