package application

interface SomeApplicationService {
    fun doSomething(someInputDto: InputDto): OutputDto
}

class SomeApplicationServiceImpl : SomeApplicationService {
    override fun doSomething(someInputDto: InputDto): OutputDto {
        val uniqueField = "${someInputDto.field1}-${someInputDto.field2}"
        val output = OutputDto(
            uniqueField = uniqueField
        )
        return output
    }
}

data class InputDto(
    val field1: String,
    val field2: Int,
)

data class OutputDto(
    val uniqueField: String
)

class SomeApplicationServiceException(message: String = "Enhance Your Calm") : Exception(message)