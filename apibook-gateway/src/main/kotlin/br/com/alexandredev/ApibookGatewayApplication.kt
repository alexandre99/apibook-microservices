package br.com.alexandredev

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
open class ApibookGatewayApplication

fun main(args: Array<String>) {
	runApplication<ApibookGatewayApplication>(*args)
}
