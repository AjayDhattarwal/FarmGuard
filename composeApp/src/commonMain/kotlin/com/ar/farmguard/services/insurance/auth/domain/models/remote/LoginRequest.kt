package com.ar.farmguard.services.insurance.auth.domain.models.remote

import com.ar.farmguard.services.insurance.auth.data.dto.UserDataSerializer
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val data: LoginData,
)

@Serializable
data class LoginData(
    val mobile: Long,
    val otp: Long
)

@Serializable
data class LoginResponse(
    val status: Boolean = false,
    val error: String? = null,
    val user: UserData? = null,
    val data: SuccessLoginData? = null
)

@Serializable
data class SuccessLoginData(
    val userData: LoginDataResponse? = null,
    val token: String,
    val application: Application,
    val sessionTTL: String ? = null,
    val roles: List<UserRoles> ? = null,
    val financialData: List<FinancialData>? = null,
)

@Serializable
data class LoginDataResponse(
    val farmerID: String,
    val farmerName: String,
    val passbookName: String? = null,
    val mobile: String,
    val idType: String,
    val idNumber: String,
    val age: Int,
    val gender: String,
    val casteCategory: String,
    val farmerType: String,
    val farmerCategory: String,
    val relativeName: String,
    val relation: String,
    val resStateID: String,
    val resStateName: String,
    val resDistrictID: String,
    val resDistrictName: String,
    val resSubDistrictID: String,
    val resSubDistrictName: String,
    val resVillageID: String,
    val resVillageName: String,
    val resPincode: Int,
    val resAddress: String,
    val createdAt: String? = null,
    val createdBy: String? = null,
    val updatedAt: String? = null,
    val updatedBy: String? = null,
    val status: Int? = null,
    val applicationName: String? = null,
    val source: String? = null,
    val password: String? = null,
    val farmerDisplayID: String? = null,
    val x: String? = null,
    val y: String? = null,
)

@Serializable
data class FinancialData(
    val financialDetailsID: String? = null,
    val bankID: String? =  null,
    val bankName: String? = null,
    val branchID: String? = null,
    val createdBranchID: String? = null,
    val branchName: String? = null,
    val ifscCode: String? = null,
    val accountType: String? = null,
    val accountNumber: String? = null,
    val ledgerAccountNumber: String? = null,
    val bankType: String? = null,
    val accountPassbookMediaID: String? = null,
    val bankStateID: String? = null,
    val bankStateName: String? = null,
    val bankDistrictID: String? = null,
    val bankDistrictName: String? = null,
    val isJoint: Int? = null,
    val nomineeType: String? = null,
    val nomineeName: String? = null,
    val nomineeRelationship: String? = null,
    val nomineeAddress: String? = null,
    val nomineeAge: Int? = null,
    val isLoan: Int? = null,
)

@Serializable
data class UserRoles(
    val roleRightsMasterID: String? = null,
    val displayUserType: String? = null,
    val roleName: String? = null,
    val data: List<RoleData>? = null
)

@Serializable
data class RoleData(
    val unknowns: String? = null,
)

@Serializable
data class Application(
    val logLevel: Int? = null,
    val accessLevel: Int? = null,
)

@Serializable(UserDataSerializer::class)
data class UserData(
    val status: Boolean = false,
    val error: String = "",
    val level: String? = null,
    val data: String? = null,
)


@Serializable
data class LoginCheckResponse(
    val status: Boolean = false,
    val error: String = "",
    val data: LoginCheckData? = null,
)

@Serializable
data class LoginCheckData(
    val login: Login
)

@Serializable
data class Login(
    val login: Boolean? = false,
    val currentDate: Long? = 0,
    val userData: LoginDataResponse,
    val role: List<UserRoles>? = emptyList(),
    val financialData: List<FinancialData>? = emptyList(),
)

