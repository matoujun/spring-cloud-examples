package org.matoujun.cloud.api.service;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @author matoujun

 */
public class ElasticSearchDslTest {
    @Test
    public void createDslTest() {
        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery
                ("1221133333").field("phone"));
        System.out.println("createDslTest: " + query.toString());

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();

    }
}
