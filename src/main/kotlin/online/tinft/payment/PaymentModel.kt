package online.tinft.payment

import kotlinx.serialization.Serializable

@Serializable
data class Instruction(
    val txSigned: InstructionSigned,
)

@Serializable
data class InstructionSigned(
    val data: ByteArray,
)
