package org.matoujun.cloud.gw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.pattern.PathPattern;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils
        .GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;
import static org.springframework.web.reactive.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

/**
 * @author matoujun

 */
@Slf4j
public class RewriteDiscoveryIpFilter implements GatewayFilter, Ordered {
    private static final Integer DISCOVERY_PORT = 8602;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        /**
         * i.e. http://localhost:8600/discovery/172.22.33.135
         */
        String[] pathElements = uri.getPath().split("\\/");
        String ip = pathElements[2];
        StringBuilder newUri = new StringBuilder("http://");
        newUri.append(ip).append(":").append(DISCOVERY_PORT);
        try {
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new URI(newUri.toString()));
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10001;
    }
}
