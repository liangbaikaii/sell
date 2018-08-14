<html>
<head>
    <meta charset="UTF-8">
    <title>类目添加</title>
    <link href="../../static/css/style.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div id="wrapper" class="toggled">
<#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" action="/sell/seller/category/update" method="post">
                        <input type="hidden"  name="categoryId" value="${category.categoryId}">
                        <div class="form-group">
                            <label for="exampleInputEmail1">类目名字</label><input type="text"
                                                                               value="${category.categoryName}"
                                                                               class="form-control" name="categoryName"/>
                        </div>

                        <div class="form-group">
                            <label for="exampleInputPassword1">类目类型</label>
                            <select name="categoryType" class="form-control">
                            <#list  productCategoryList as productCategory>

                                <option   value="${productCategory.categoryType}"
                                    <#if (productCategory.categoryType)?? && productCategory.categoryType ==category.categoryType >
                                        selected
                                    </#if >
                               > ${productCategory.categoryType}</option>
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