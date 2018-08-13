<html>
<head>
    <meta charset="UTF-8">
    <title>商品列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../static/css/style.css" rel="stylesheet">
</head>
<body>
<div id="wrapper" class="toggled">
<#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" action="/sell/seller/product/update" method="post">
                        <input type="hidden"  name="productId" value="${productInfo.productId}">
                        <div class="form-group">
                            <label for="exampleInputEmail1">商品名字</label><input type="text"
                                                                               value="${productInfo.productName}"
                                                                               class="form-control" name="productName"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">商品价格</label><input type="text"
                                                                                  value="${productInfo.productPrice}"
                                                                                  class="form-control"
                                                                                  name="productPrice"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">商品图片</label><input type="text"
                                                                                  value="${productInfo.productIcon}"
                                                                                  class="form-control"
                                                                                  name="productIcon"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">商品库存</label><input type="text"
                                                                                  value="${productInfo.productStock}"
                                                                                  class="form-control"
                                                                                  name="productStock"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">商品介绍</label><input type="text"
                                                                                  value="${productInfo.productDescription}"
                                                                                  class="form-control"
                                                                                  name="productDescription"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">商品类别</label>
                            <select name="categoryType" class="form-control">
                            <#list  productCategoryList as productCategory>

                                <option   value="${productCategory.categoryType}"
                                    <#if (productCategory.categoryType)?? && productCategory.categoryType ==productInfo.categoryType >
                                        selected
                                    </#if >
                               > ${productCategory.categoryName}</option>
                            </#list>

                            </select>
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


</body>

</html>