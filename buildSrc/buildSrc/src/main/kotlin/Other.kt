val commonCompilerArgs = emptyList<String>()

val jvmCompilerArgs = listOf(
    "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
    "-Xuse-experimental=kotlinx.serialization.InternalSerializationApi",
    "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
    "-Xopt-in=kotlin.contracts.ExperimentalContracts",
    "-Xjvm-default=all",
)

object Config {

}