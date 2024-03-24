package com.example.springbatchkotlin.localstack

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import java.io.IOException

@Testcontainers
@DisplayName("S3 LocalStack 테스트")
class LocalStackTest {

    @Container
    var localStackContainer: LocalStackContainer = LocalStackContainer()
        .withServices(LocalStackContainer.Service.S3)

    @Test
    @DisplayName("s3 업로드 테스트")
    @Throws(IOException::class)
    fun test() {
        //localstack 컨테이너로 AmazonS3 설정 값에 추가.
        val amazonS3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(localStackContainer.getEndpointConfiguration(LocalStackContainer.Service.S3))
            .withCredentials(localStackContainer.defaultCredentialsProvider)
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
