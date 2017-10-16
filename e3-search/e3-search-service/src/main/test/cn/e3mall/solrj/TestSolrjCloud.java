package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * 测试solrcloud集群
 */
public class TestSolrjCloud {
    /**
     * 添加索引库
     */
    @Test
    public void testSolrCloudAddDocument() throws Exception{
        //第一步：把solrJ相关的jar包添加到工程中。
        //第二步：创建一个SolrServer对象，需要使用CloudSolrServer子类。构造方法的参数是zookeeper的地址列表。
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.130:2184,192.168.25.130:2182,192.168.25.130:2183");
        //第三步：需要设置DefaultCollection属性。
        cloudSolrServer.setDefaultCollection("collection2");
        //第四步：创建一SolrInputDocument对象。
        SolrInputDocument document = new SolrInputDocument();
        //第五步：向文档对象中添加域
        document.addField("item_title", "手机");
        document.addField("item_price", "1000");
        document.addField("item_image", "图片");
        document.addField("id", "001");
        //第六步：把文档对象写入索引库。
        cloudSolrServer.add(document);
        //第七步：提交。
        cloudSolrServer.commit();
    }

    /**
     * 简单查询索引库
     */
    @Test
    public void testSolrCloudQueryDocument() throws Exception{
        //创建一个CloudSolrServer对象
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.130:2184,192.168.25.130:2182,192.168.25.130:2183");
        //设置默认链接
        solrServer.setDefaultCollection("collection2");
        //创建一个SolrQuery对象。
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件。
//        solrQuery.setQuery("*:*");
        solrQuery.set("q", "*:*");
        //执行查询，QueryResponse对象。
        QueryResponse queryResult = solrServer.query(solrQuery);
        SolrDocumentList results = queryResult.getResults();
        //取文档列表。取查询结果的总记录数
        for (SolrDocument solrDocument:results) {
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("id"));
        }
    }

    /**
     * 查询索引库复杂方法
     */
    @Test
    public void testSolrCloudQueryFuza() throws Exception{
        //创建cloudSolrServer对象，获取solrServer
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.130:2184,192.168.25.130:2182,192.168.25.130:2183");
        //设置默认连接
        solrServer.setDefaultCollection("collection2");
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("手机");
        query.setStart(0);
        query.setRows(20);
        query.set("df", "item_title");
        query.setHighlight(true);//设置高亮
        //设置样式
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("查询结果总记录数："+results.getNumFound());
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();


        //获取文档列表打印
        for (SolrDocument solrDocument:results) {
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if(list != null && list.size()>0){
                title = list.get(0);
            }else{
                title = String.valueOf(solrDocument.get("item_title"));
            }
            System.out.println(title);
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("id"));
        }
    }
}
