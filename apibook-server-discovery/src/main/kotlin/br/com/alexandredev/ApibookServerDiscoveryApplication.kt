package br.com.alexandredev

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
open class ApibookServerDiscoveryApplication

fun main(args: Array<String>) {
	runApplication<ApibookServerDiscoveryApplication>(*args)
}
