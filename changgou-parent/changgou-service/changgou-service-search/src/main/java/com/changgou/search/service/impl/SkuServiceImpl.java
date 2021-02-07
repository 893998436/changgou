package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuService;
import entity.Result;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 导入sku数据到es
     */
    @Override
    public void importEs() {
        //1.调用 goods微服务的fegin 查询 符合条件的sku的数据
        Result<List<Sku>> skuResult = skuFeign.findByStatus("1");
        //sku的列表
        List<Sku> data = skuResult.getData();
        //将sku的列表 转换成es中的skuinfo的列表
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(data), SkuInfo.class);
        for (SkuInfo skuInfo : skuInfos) {
            //获取规格的数据  {"电视音响效果":"立体声","电视屏幕尺寸":"20英寸","尺码":"165"}

            //转成MAP  key: 规格的名称  value:规格的选项的值
            Map<String, Object> map = JSON.parseObject(skuInfo.getSpec(), Map.class);
            skuInfo.setSpecMap(map);
        }
        // 2.调用spring data elasticsearch的API 导入到ES中
        skuEsMapper.saveAll(skuInfos);
    }

    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        //条件构建
        NativeSearchQueryBuilder nativeSearchQuery = nativeSearchQueryBuilder(searchMap);
        //集合搜索
        Map<String, Object> resultMap = searchList(nativeSearchQuery);
        //分类分组搜索
        List<String> categoryList = searchCategoryList(nativeSearchQuery);
        resultMap.put("categoryList",categoryList);
        //品牌己合查询搜索条件
        List<String> brandList = searchBrandList(nativeSearchQuery);
        resultMap.put("brandList",brandList);
        //规格查询
        Map<String, Set<String>> specList =searchSpecList(nativeSearchQuery);
        resultMap.put("specList",specList);
        return resultMap;
    }
        //条件构建

    private NativeSearchQueryBuilder nativeSearchQueryBuilder(Map<String, String> searchMap) {
        //搜索条件构建对象，封装条件
        NativeSearchQueryBuilder  nativeSearchQuery=new NativeSearchQueryBuilder();

        if(searchMap!=null && searchMap.size()>0){
            //根据关键字搜索
            String keywords = searchMap.get("keywords");
            //根据条件搜索
           if(!StringUtils.isEmpty(keywords)){
               nativeSearchQuery.withQuery(
                       QueryBuilders.queryStringQuery(keywords).field("name"));
           }
        }
        return nativeSearchQuery;
    }

    //数据结果集搜索

    private Map<String, Object> searchList(NativeSearchQueryBuilder nativeSearchQuery) {
        /*
          *执行搜索,响应结果给我
          *1)搜索条件封装对象
          *2)搜索的结果集(集合数据)需要转换的类型
          *3)AggregatedPage<SkuInfo>:搜索结果集的封装
          * */
        AggregatedPage<SkuInfo> page=elasticsearchTemplate.queryForPage(nativeSearchQuery.build(),SkuInfo.class);
        //分页参数=总记录数
        long totalElements = page.getTotalElements();
        //总页数
        int totalPages = page.getTotalPages();
        //获取结果集
        List<SkuInfo> list = page.getContent();
        //封存一个Map存储所有数据并返回
        Map<String, Object> resultMap=new HashMap<String, Object>();
        resultMap.put("rows",list);
        resultMap.put("total",totalElements);
        resultMap.put("totalPages",totalPages);
        return resultMap;
    }
    /*
    * 分类集合搜索实现
    *
    * */

    private List<String> searchCategoryList(NativeSearchQueryBuilder nativeSearchQuery) {
        /*分类查询集合
        *addAggregation()添加一个聚合操作
        * terms()取别名
        */
        nativeSearchQuery.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
        AggregatedPage<SkuInfo> aggregatedPage=  elasticsearchTemplate.queryForPage(nativeSearchQuery.build(),SkuInfo.class);
        //获取分组数
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuCategory");
        List<String> categoryList = new ArrayList<String>();
        for (StringTerms.Bucket bucket:stringTerms.getBuckets()) {
             String categoryName= bucket.getKeyAsString();
             categoryList.add(categoryName);
        }
        return categoryList;
    }
    /*
    * 根据品牌进行分类查询
    * */

    private List<String> searchBrandList(NativeSearchQueryBuilder nativeSearchQuery) {
        /*分组查询品牌
         *addAggregation()添加一个聚合操作
         * terms()取别名
         */
        nativeSearchQuery.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
        AggregatedPage<SkuInfo> aggregatedPage=  elasticsearchTemplate.queryForPage(nativeSearchQuery.build(),SkuInfo.class);
        //获取分组数
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuBrand");
        List<String> brandList = new ArrayList<String>();
        for (StringTerms.Bucket bucket:stringTerms.getBuckets()) {
            //获取品牌名字
            String brandName= bucket.getKeyAsString();
            brandList.add(brandName);
        }
        return brandList;
    }


    /*
     * 根据规格进行分类查询
     * */

    private  Map<String, Set<String>> searchSpecList(NativeSearchQueryBuilder nativeSearchQuery) {
        /*分组查询规格
         *addAggregation()添加一个聚合操作
         * terms()取别名
         */
        nativeSearchQuery.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword"));
        AggregatedPage<SkuInfo> aggregatedPage=  elasticsearchTemplate.queryForPage(nativeSearchQuery.build(),SkuInfo.class);
       /*
       *获取分组数
       *根据 skuSpec分类
       *
       * */
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuSpec");
        List<String> specList = new ArrayList<String>();
        for (StringTerms.Bucket bucket:stringTerms.getBuckets()) {
            //获取规格名字
            String specName= bucket.getKeyAsString();
            specList.add(specName);
        }
        //合并后的Map对象
        //将每个Map 转成 Map<String,set<String>>
        Map<String, Set<String>> allSpec = new HashMap<String,Set<String>>();

        //取出来的是字符串specList
        for (String spec:specList) {
            //将每个json字符串转成Map
             Map<String, String>  specMap=JSON.parseObject(spec,Map.class);
            //合并流程
            for (Map.Entry<String, String> entry:specMap.entrySet()) {
                //规格名称
                String key = entry.getKey();
                //规格内容
                String value = entry.getValue();
                //获取对应的set集合
                Set<String> specSet=allSpec.get(key);
                if(specSet==null){
                    specSet=new HashSet<>();
                }
                specSet.add(value);
                allSpec.put(key, specSet);
            }
        }
        return allSpec;
    }
}
