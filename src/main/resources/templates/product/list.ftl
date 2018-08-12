<html>
<head>
    <meta charset="UTF-8">
    <title>商品列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../static/css/style.css" rel="stylesheet" >
</head>
<body>
<div id="wrapper" class="toggled">
     <#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">

            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr class="default">
                            <th>
                                商品id
                            </th>
                            <th>
                                商品名
                            </th>
                            <th>
                                图片
                            </th>
                            <th>
                                单价
                            </th>
                            <th>
                                库存
                            </th>
                            <th>
                                介绍
                            </th>
                            <th>
                                类别
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th>
                                修改时间
                            </th>
                            <th colspan="2">
                                操作
                            </th>

                        </tr>
                        </thead>
                        <tbody>
                        <#list productInfoPage.content as  product>

                        <tr class="success">
                            <td>
                            ${product.productId}
                            </td>
                            <td>
                            ${product.productName}
                            </td>
                            <td>
                            ${product.productIcon}
                            </td>
                            <td>
                            ${product.productPrice}
                            </td>
                            <td>
                            ${product.productStock}
                            </td>
                            <td>
                            ${product.productDescription}
                            </td>
                            <td>
                            ${product.categoryType}
                            </td>
                            <td>
                            ${product.createTime}
                            </td>
                            <td>
                            ${product.updateTime}
                            </td>
                            <td>
                                <a href="/sell/seller/product/update?productId=${product.productId}">修改</a>
                            </td>
                            <#if product.productStatus ==0 >
                                <td>
                                    <a href="/sell/seller/product/updateState?productStatus=1&productId=${product.productId}">下架</a>
                                </td>
                            </#if>
                            <#if product.productStatus ==1 >
                                <td>
                                    <a href="/sell/seller/product/updateState?productStatus=0&productId=${product.productId}">上架</a>
                                </td>
                            </#if>

                        </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled">
                            <a href="#">上一页</a>
                        </li>
                    <#else>
                        <li>
                            <a href="/sell/seller/product/list?page=${currentPage-1}">上一页</a>
                        </li>
                    </#if>
                    <#list 1.. productInfoPage.totalPages as  index>
                        <#if currentPage ==index>
                            <li class="disabled">
                                <a href="#">${index}</a>
                            </li>
                        <#else>
                            <li>
                                <a href="/sell/seller/product/list?page=${index}">${index}</a>
                            </li>
                        </#if>
                    </#list>
                    <#if currentPage gte  productInfoPage.totalPages>
                        <li class="disabled">
                            <a href="#">下一页</a>
                        </li>
                    <#else>
                        <li>
                            <a href="/sell/seller/product/list?page=${currentPage+1}">下一页</a>
                        </li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>


</body>

</html>