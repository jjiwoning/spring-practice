package com.example.springbatchkotlin.localstack

import com.amazonaws.auth.AWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.ServiceAbbreviations.S3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import org.testcontainers.utility.DockerImageName
import java.io.IOException

@Testcontainers
@DisplayName("S3 LocalStack 테스트")
class LocalStackTest {

    private val LOCALSTACK_NAME: DockerImageName = DockerImageName.parse("localstack/localstack")

    @JvmField
    @Rule
    var localStackContainer: LocalStackContainer = LocalStackContainer(LOCALSTACK_NAME)
        .withServices(LocalStackContainer.EnabledService { S3 })

    @Test
    @DisplayName("s3 업로드 테스트")
    @Throws(IOException::class)
    fun test() {
        //localstack 컨테이너로 AmazonS3 설정 값에 추가.
        val amazonS3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(localStackContainer.endpoint.toString(), localStackContainer.region))
            .withCredentials(AWSCredentialsProviderChain())
            .build()

        //S3 인스턴스로 버킷 생성 및 put, get test
        val bucketName = "woo"
        amazonS3.createBucket(bucketName)
        println("버킷 생성")

        val key = "aaaa"
        val content = "bbbbb"
        amazonS3.putObject(bucketName, key, content)
        println("파일 업로드")

        val `object` = amazonS3.getObject(bucketName, key)
        println("object.getKey() = " + `object`.key)
        val key2 = `object`.key
        val content2: String = IOUtils.toString(`object`.objectContent, Charsets.UTF_8)

        Assertions.assertAll(
            { Assertions.assertEquals(content, content2) },
            { Assertions.assertEquals(key, key2) }
        )
    }
}
