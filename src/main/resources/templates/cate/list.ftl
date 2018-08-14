<html>
<head>
    <meta charset="UTF-8">
    <title>商品类别列表</title>
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
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr class="default">
                            <th>
                                类别名称
                            </th>
                            <th>
                                类别分类
                            </th>

                            <th>
                                创建时间
                            </th>
                            <th>
                                更改时间
                            </th>
                            <th>
                                操作
                            </th>

                        </tr>
                        </thead>
                        <tbody>
                        <#list categoryList as  productCategory>

                        <tr class="success">
                            <td>
                            ${productCategory.categoryName}
                            </td>
                            <td>
                            ${productCategory.categoryType}
                            </td>
                            <td>
                            ${productCategory.createTime}
                            </td>
                            <td>
                            ${productCategory.updateTime}
                            </td>
                            <td>
                                <a href="/sell/seller/category/index?categoryId=${productCategory.categoryId}">修改</a>
                            </td>

                        </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


</body>

</html>