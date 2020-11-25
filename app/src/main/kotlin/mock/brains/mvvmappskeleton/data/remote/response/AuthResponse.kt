package mock.brains.mvvmappskeleton.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @SerializedName("id")
    val id: Long?,

    @SerializedName("token")
    val token: String?
)