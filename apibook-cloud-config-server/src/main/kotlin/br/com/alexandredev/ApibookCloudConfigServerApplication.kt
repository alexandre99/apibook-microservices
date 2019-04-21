package br.com.alexandredev

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@EnableConfigServer
@SpringBootApplication
open class ApibookCloudConfigServerApplication

fun main(args: Array<String>) {
	runApplication<ApibookCloudConfigServerApplication>(*args)
}
